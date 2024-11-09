package com.example.player.serviceImpl;

import cn.hutool.core.io.FileUtil;
import com.example.player.bean.Result;
import com.example.player.exception.ServiceException;
import com.example.player.mapper.VideoMapper;
import com.example.player.service.CollectionService;
import com.example.player.service.ImageUploadService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

@Service
public class ImageUploadServiceImp implements ImageUploadService {
    @Value("${ip:localhost}")
    public String ip;

    @Value("${server.port}")
    public String port;

//    public String ROOT_PATH = System.getProperty("user.dir") + File.separator;
    @Value("${RootPath:M:\\Videos\\}")
    public String ROOT_PATH;

    @Value("${filePath:files}")
    public void setROOT_PATH(String filePath){
        ROOT_PATH += filePath;
    }

    @Value("${imagePath:image}")
    public String imagePath;

    @Override
    public Result upload(MultipartFile file) {
        String name = file.getOriginalFilename();//文件原始名
        String extName = FileUtil.extName(name);//文件拓展名
        String timeStamp = String.valueOf(System.currentTimeMillis());//时间戳
        String saveName = timeStamp + "." +extName;
        String path = ROOT_PATH + File.separator + imagePath+ File.separator + saveName;
        FileUtil.mkdir(ROOT_PATH + File.separator + imagePath+ File.separator);//判断路径是否存在
        File saveFile = new File(path);
        try {
            file.transferTo(saveFile);
        }catch (IOException e){
            e.printStackTrace();
            throw new ServiceException("存储错误");
        }
        String url = "http://" + ip + ":" + port + "/image/download/"  + saveName;

        return Result.success(url);
    }

    @Override
    public void download(String fileName, HttpServletResponse response) {
        String filePath = ROOT_PATH + File.separator + imagePath+ File.separator + fileName;
        if(!FileUtil.exist(filePath)){
            return;
        }
        byte[] byes = FileUtil.readBytes(filePath);
        try{
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(byes);
            outputStream.flush();
            outputStream.close();
        }catch (Exception e){
            throw new ServiceException("下载出错啦");
        }

    }

    @Override
    public void coverDownload(String fileName, HttpServletResponse response) {
        String filePath = ROOT_PATH + File.separator + "cover" + File.separator + fileName;
        if(!FileUtil.exist(filePath)){
            return;
        }
        byte[] byes = FileUtil.readBytes(filePath);
        try{
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(byes);
            outputStream.flush();
            outputStream.close();
        }catch (Exception e){
            throw new ServiceException("下载出错啦");
        }
    }
}
