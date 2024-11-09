package com.example.player.service;

import com.example.player.bean.Result;

import java.util.ArrayList;

public interface UserInfoService {
    Result getById(int id);

    Result getNamesById(ArrayList<Integer> idList);
}
