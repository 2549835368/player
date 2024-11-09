package com.example.player.controller;

import com.example.player.bean.Result;
import com.example.player.entity.dto.VideoChunkDto;
import com.example.player.service.ImageUploadService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class ImageUploadController {

    @Autowired
    private ImageUploadService imageUploadService;

    @PostMapping("/upload")
    public Result upload(MultipartFile file){

        return imageUploadService.upload(file);
    }

    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response){
        imageUploadService.download(fileName,response);
    }

    @GetMapping("/download/cover/{fileName}")
    public void downloadCover(@PathVariable String fileName, HttpServletResponse response){
        imageUploadService.coverDownload(fileName,response);
    }
}
