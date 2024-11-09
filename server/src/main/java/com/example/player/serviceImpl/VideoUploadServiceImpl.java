package com.example.player.serviceImpl;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.player.bean.Result;
import com.example.player.entity.CollectionVideo;
import com.example.player.entity.Video;
import com.example.player.entity.dto.VideoChunkDto;
import com.example.player.exception.ServiceException;
import com.example.player.mapper.UserMapper;
import com.example.player.mapper.VideoMapper;
import com.example.player.service.CollectionService;
import com.example.player.service.VideoUploadService;
import com.example.player.utils.VideoUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@Service
public class VideoUploadServiceImpl implements VideoUploadService {

    @Value("${ip:localhost}")
    private String ip;

    @Value("${server.port}")
    private String port;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    private VideoMapper videoMapper;



//    private String ROOT_PATH = System.getProperty("user.dir") + File.separator;
    @Value("${ROOTPath:M:\\Videos\\}")
    public String ROOT_PATH;

    @Value("${filePath:file}")
    public void setROOT_PATH(String filePath){
        ROOT_PATH += filePath;
    }

    public HashMap<String, Integer> preUpload(Video video){
        String md5 = video.getMd5();

        QueryWrapper<Video> qw = new QueryWrapper<>();
        qw.eq("md5",md5);
        List<Video> dbVideos = videoMapper.selectList(qw);

        HashMap<String ,Integer> hashMap = new HashMap<>();

//        if(!dbVideos.isEmpty()){
//            video.setUrl(dbVideos.get(0).getUrl());
//            videoMapper.insert(video);
//            hashMap.put("index",-1);
//            hashMap.put("id",video.getId());
//            return hashMap;
//        }
        videoMapper.insert(video);
        hashMap.put("id",video.getId());
        System.err.println(video.getId());
        if(redisTemplate.opsForHash().hasKey(md5,"max")){
            hashMap.put("index",getCurrentChunk(md5));
            return hashMap;
        }
        else{
            redisTemplate.opsForHash().put(md5,"max",video.getMax());
        }
        hashMap.put("index",1);
        return hashMap;
    }


    public String chunkUpload(VideoChunkDto videoChunkDto){
        MultipartFile file = videoChunkDto.getFile();
        String md5 = videoChunkDto.getMd5();
        int index = videoChunkDto.getIndex();

        FileUtil.mkdir(ROOT_PATH + File.separator +"chunks");
        String path = ROOT_PATH + File.separator +"chunks"+ File.separator + md5 + "_" + index;
        File saveFile = new File(path);
        try {
            file.transferTo(saveFile);
        }catch (IOException e){
            return null;
        }

        redisTemplate.opsForHash().put(md5,String.valueOf(index),path);

        return "分片"+index+"上传成功";
    }



    public int getCurrentChunk(String md5){
        int max = 0;
        try{
            max = (int)redisTemplate.opsForHash().get(md5,"max");
        }catch (Exception ignored){
            System.err.println("出错了");
        }

        int index = 1;
        for(index = 1 ;index <= max;index++)
        {
            Boolean hasKey = redisTemplate.opsForHash().hasKey(md5,String.valueOf(index));
            if(!hasKey){
                break;
            }
        }
        return index;
    }

    public Result mergeChunk(String md5,int id){
        int max = (int) redisTemplate.opsForHash().get(md5,"max");

        Video dbVideo = videoMapper.selectById(id);

        if(dbVideo == null){
            throw new ServiceException(500,"上传错误");
        }

        FileUtil.mkdir(ROOT_PATH);

        String path = ROOT_PATH + File.separator + md5 +"_" + id + "." +dbVideo.getSuffix();
        File saveFile = new File(path);
        dbVideo.setUrl(path);
        FileOutputStream fos = null;
        double duration;
        try{
            fos = new FileOutputStream(saveFile);
            for(int i = 1; i <= max; i++){
                String theUrl = (String)redisTemplate.opsForHash().get(md5,String.valueOf(i));

                File theFile = new File(theUrl);

                try (InputStream is = new FileInputStream(theFile)) {
                    IOUtils.copy(is, fos);
                }
            }
            duration = VideoUtils.getVideoDuration(path);
            dbVideo.setDuration(duration);
            dbVideo.setTimestamp(new Timestamp(System.currentTimeMillis()));
            videoMapper.updateById(dbVideo);

        }
        catch (IOException e) {
            throw new ServiceException(500,"合并失败");
        } finally {
            IOUtils.closeQuietly(fos);
        }

        String coverPath = ROOT_PATH + File.separator +"cover"+ File.separator;



        List<String> saveNameList = VideoUtils.getVideoCover(path,coverPath,md5,duration);

        String url = "http://" + ip + ":" + port + "/image/download/cover/";

        saveNameList.replaceAll(s -> url + s);

        return Result.success(saveNameList);
    }

    public int deleteVideo(int id){
        return videoMapper.deleteById(id);
    }
}
