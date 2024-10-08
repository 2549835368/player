package com.example.player.service;

import com.example.player.entity.Danmu;

import java.util.List;

public interface DanmuService {
    public Danmu save(Danmu danmu);

    public List<Object> getDanmus(String vid);

}
