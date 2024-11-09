package com.example.player.service;

import com.example.player.bean.Result;
import com.example.player.entity.Collection;
import com.example.player.entity.CollectionVideo;
import jakarta.servlet.http.HttpServletRequest;

public interface CollectionService {
    Result save(CollectionVideo collectionVideo);

    Result get(int cid);

    Result getByUid(int uid);

    Result createCol(Collection collection, HttpServletRequest request);

    Result delCollection(int cid, HttpServletRequest request);

    Result getCollectionVideoByVid(int vid);

    Result createCollectionVideo(CollectionVideo collectionVideo);

}
