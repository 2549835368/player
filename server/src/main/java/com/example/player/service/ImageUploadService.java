package com.example.player.service;

import com.example.player.bean.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {
    Result upload(MultipartFile file);

    void download(String fileName, HttpServletResponse response);

    void coverDownload(String fileName, HttpServletResponse response);
}
