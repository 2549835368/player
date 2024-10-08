package com.example.player.service;

import com.example.player.bean.Result;
import com.example.player.entity.Video;
import com.example.player.entity.dto.VideoChunkDto;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

public interface VideoUploadService {
    public HashMap<String,Integer> preUpload(Video video);

    public String chunkUpload(VideoChunkDto videoChunkDto);

    public int getCurrentChunk(String md5);

    public String mergeChunk(String md5,int id);

    public int deleteVideo(int id);
}
