package com.example.player.serviceImpl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.example.player.entity.Video;
import com.example.player.mapper.VideoMapper;
import com.example.player.service.VideoPlayService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Arrays;

@Service
public class VideoPlayServiceImpl implements VideoPlayService {

    @Autowired
    VideoMapper videoMapper;

    @Override
    public void play(int id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Video dbVideo = videoMapper.selectById(id);
        String url = dbVideo.getUrl();
        ServletOutputStream outputStream = response.getOutputStream();

        if(!FileUtil.exist(url)){
            response.getOutputStream().close();
            return;
        }

        File file = new File(url);
        RandomAccessFile randomAccessFile = new RandomAccessFile(file,"r");

        String Range = request.getHeader("Range");
        String[] Ranges =  Range.split("=");
        long start = 0;
        long end = 0;
        int size = 1024*1024;
        long contentLength = file.length();



        if(Ranges.length > 1){
            String[] range = Ranges[1].split("-",-1);
            start = Long.parseLong(range[0]);
            randomAccessFile.seek(start);

            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            response.setHeader(HttpHeaders.ACCEPT_RANGES, "bytes");

            if(!StrUtil.isEmpty(range[1])){
                end = Long.parseLong(range[1]);
                contentLength = end - start;
                response.setHeader(HttpHeaders.CONTENT_RANGE," bytes " + start + "-" + end + "/" + file.length());
                size = (int)(end - start + 1);
                byte[] buff = new byte[size];
                int len = randomAccessFile.read(buff);
                if(len != -1){
                    try{
                        outputStream.write(buff,0,len);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }
            else{
                contentLength = file.length() - start;
                end = Math.min(size + start, file.length()-1);

                response.setHeader(HttpHeaders.CONTENT_RANGE," bytes " + start + "-" + end + "/" + file.length());
                byte[] buff = new byte[size];
                int len = randomAccessFile.read(buff);
                if(len != -1){
                    try{
                        outputStream.write(buff,0,len);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                randomAccessFile.close();
            }
            response.setHeader(HttpHeaders.CONTENT_LENGTH, "" + contentLength);

        }
        else{
            response.setHeader("Content-Disposition", "attachment; filename=\"" +dbVideo.getMd5()+".mp4" + "\"");
            response.setHeader(HttpHeaders.CONTENT_LENGTH, "" + (file.length() - (int)start));
            response.setHeader(HttpHeaders.CONTENT_RANGE, "" + (file.length()-1));
            response.setHeader(HttpHeaders.ACCEPT_RANGES, "bytes");
            InputStream inStream=new FileInputStream(file);
            byte[] buffer = new byte[size];
            int len;
            while ((len = inStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }

            inStream.close();
        }

//        byte[] bytes =FileUtil.readBytes(url);
//        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }
}
