package com.example.player.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.player.bean.Result;
import com.example.player.entity.PartCaption;
import com.example.player.mapper.PartMapper;
import com.example.player.service.PartCaptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartCaptionServiceImpl implements PartCaptionService {

    @Autowired
    private PartMapper partMapper;

    @Override
    public Result save(PartCaption partCaption) {
        partMapper.insert(partCaption);
        return Result.success(partCaption);
    }

    @Override
    public Result getList() {
        List<PartCaption> list = partMapper.selectList(null);
        return Result.success(list);
    }

    @Override
    public Result getOne(int code) {
        QueryWrapper<PartCaption> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code",code);
        PartCaption partCaption = partMapper.selectOne(queryWrapper);
        return Result.success(partCaption);
    }
}
