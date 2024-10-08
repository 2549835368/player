package com.example.player.entity.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class VideoChunkDto {
    private MultipartFile file;
    private String md5;
    private int index;
    private int max;
}
