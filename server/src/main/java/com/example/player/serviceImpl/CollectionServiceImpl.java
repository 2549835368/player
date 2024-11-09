package com.example.player.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.player.bean.Result;
import com.example.player.entity.Collection;
import com.example.player.entity.CollectionVideo;
import com.example.player.entity.User;
import com.example.player.exception.ServiceException;
import com.example.player.mapper.CollectionMapper;
import com.example.player.mapper.CollectionVideoMapper;
import com.example.player.mapper.UserMapper;
import com.example.player.service.CollectionService;
import com.example.player.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    CollectionVideoMapper collectionVideoMapper;

    @Autowired
    CollectionMapper collectionMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public Result save(CollectionVideo collectionVideo) {
        collectionVideoMapper.insert(collectionVideo);
        return Result.success(collectionVideo);
    }

    @Override
    public Result get(int cid) {
        QueryWrapper<CollectionVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid",cid).orderByAsc("seq");
        List<CollectionVideo> dbCollectionVideoList = collectionVideoMapper.selectList(queryWrapper);
        return Result.success(dbCollectionVideoList);
    }

    @Override
    public Result getByUid(int uid){
        QueryWrapper<Collection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",uid);
        List<Collection> dbcollectionList = collectionMapper.selectList(queryWrapper);
        return Result.success(dbcollectionList);
    }

    @Override
    public Result createCol(Collection collection, HttpServletRequest request) {
        String token = request.getHeader("token");
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        User dbuser = userMapper.queryIdByUsername(username);
        collection.setUid(dbuser.getId());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        collection.setCreatedTime(timestamp);
        collectionMapper.insert(collection);
        return Result.success(collection);
    }

    @Override
    public Result delCollection(int cid, HttpServletRequest request) {
        String token = request.getHeader("token");
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        User dbuser = userMapper.queryIdByUsername(username);

        Collection dbCollection =  collectionMapper.selectById(cid);
        if(dbCollection.getUid() == dbuser.getId()){
            collectionMapper.deleteById(cid);
            return Result.success(dbCollection);
        }else{
            throw new ServiceException("账号异常");
        }
    }

    @Override
    public Result getCollectionVideoByVid(int vid) {
        QueryWrapper<CollectionVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("vid",vid);
        CollectionVideo dbCollectionVideo = collectionVideoMapper.selectOne(queryWrapper);
        QueryWrapper<CollectionVideo> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("cid",dbCollectionVideo.getCid()).orderByAsc("seq");
        List<CollectionVideo> dbCollectionVideoList = collectionVideoMapper.selectList(queryWrapper1);
        return Result.success(dbCollectionVideoList);
    }


    @Override
    public Result createCollectionVideo(CollectionVideo collectionVideo) {
        collectionVideo.setTime(new Timestamp(System.currentTimeMillis()));
        if(collectionVideo.getSeq() == 0){
            QueryWrapper<CollectionVideo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("cid",collectionVideo.getCid());
            long count = collectionVideoMapper.selectCount(queryWrapper) + 1;
            collectionVideo.setSeq((int) count);
        }
        collectionVideoMapper.insert(collectionVideo);
        return Result.success(collectionVideo);
    }
}
