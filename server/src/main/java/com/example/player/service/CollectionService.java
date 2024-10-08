package com.example.player.service;

import com.example.player.bean.Result;
import com.example.player.entity.Collection;

public interface CollectionService {
    Result save(Collection collection);

    Result get(int cid);
}
