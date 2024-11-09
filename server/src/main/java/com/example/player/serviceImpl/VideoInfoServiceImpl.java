package com.example.player.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.player.bean.Result;
import com.example.player.entity.CollectionVideo;
import com.example.player.entity.Video;
import com.example.player.mapper.VideoMapper;
import com.example.player.service.CollectionService;
import com.example.player.service.VideoInfoService;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

@Service
public class VideoInfoServiceImpl implements VideoInfoService {

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private CollectionService collectionService;

    @Override
    public Result getInfo(int vid) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","profile","duration","cover_url","tags","collection_id","auth_id","part_id","timestamp")
                .eq("id",vid);
        Video dbVideo = videoMapper.selectOne(queryWrapper);
        return Result.success(dbVideo);
    }

    @Override
    public Result getListByUid(int uid){
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("auth_id",uid)
                .select("auth_id","id","cover_url","part_id","title","tags","timestamp","url","profile","collection_id");
        List<Video> dbVideoList = videoMapper.selectList(queryWrapper);
        return Result.success(dbVideoList);
    }

    @Override
    public Result submit(Video video) {

        System.err.println(video);
        video.setState(1);
        videoMapper.updateById(video);

        Video dbVideo = videoMapper.selectById(video.getId());
        if(dbVideo.getCollectionId() != 0){
            CollectionVideo collectionVideo = new CollectionVideo();
            collectionVideo.setCid(dbVideo.getCollectionId());
            collectionVideo.setVid(dbVideo.getId());
            collectionService.createCollectionVideo(collectionVideo);
        }

        return Result.success(video);
    }

    @Override
    public Result getVideoList(int num, int part) {



        Timestamp t1 = new Timestamp(System.currentTimeMillis());
        Integer max = null;
        Integer min = null;
        if(part == 0){
            max = videoMapper.getMax();
            min = videoMapper.getMin();
        }else{
            max = videoMapper.getMaxByPart(part);
            min = videoMapper.getMinByPart(part);
        }

        Random random = new Random();
        int rand = random.nextInt(min,max);
        System.err.println(rand);
        List<Video> dbVideoList = null;
        if(part == 0){
            dbVideoList = videoMapper.getVideo(num,rand);
        }else{
            dbVideoList =  videoMapper.getVideoByPart(part,num,rand);
        }
        Timestamp t2 = new Timestamp(System.currentTimeMillis());

        System.err.println(t2.getTime()-t1.getTime());
        return Result.success(dbVideoList);
    }
}
