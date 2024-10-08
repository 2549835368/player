package com.example.player.service;

import com.example.player.bean.Result;
import com.example.player.entity.PartCaption;

public interface PartCaptionService {
    Result save(PartCaption partCaption);
    Result getList();
    Result getOne(int code);
}
