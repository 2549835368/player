package com.example.player.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.player.entity.Danmu;
import com.example.player.mapper.DanmuMapper;
import com.example.player.service.DanmuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DanmuServiceImpl implements DanmuService {

    @Autowired
    DanmuMapper danmuMapper;

    @Override
    public Danmu save(Danmu danmu) {
        danmuMapper.insert(danmu);
        return danmu;
    }

    @Override
    public List<Object> getDanmus(String vid) {
        QueryWrapper<Danmu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("vid",vid);
        List<Danmu> dblist = danmuMapper.selectList(queryWrapper);
        List<Object> ResList = new ArrayList<>();
        for(Danmu danmu:dblist){
            List<Object> tList = new ArrayList<>();
            tList.add(danmu.getTime());
            tList.add(danmu.getType());
            tList.add(danmu.getColor());
            tList.add(danmu.getAuthor());
            tList.add(danmu.getText());
            ResList.add(tList);
        }
        return ResList;
    }
}
