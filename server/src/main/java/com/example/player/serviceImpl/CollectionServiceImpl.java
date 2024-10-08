package com.example.player.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.player.bean.Result;
import com.example.player.entity.Collection;
import com.example.player.mapper.CollectionMapper;
import com.example.player.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    CollectionMapper collectionMapper;


    @Override
    public Result save(Collection collection) {
        collectionMapper.insert(collection);
        return Result.success(collection);
    }

    @Override
    public Result get(int cid) {
        QueryWrapper<Collection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid",cid).orderByAsc("seq");
        List<Collection> dbCollectionList = collectionMapper.selectList(queryWrapper);
        return Result.success(dbCollectionList);
    }
}
