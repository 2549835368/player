package com.example.player.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.player.entity.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface VideoMapper extends BaseMapper<Video> {

    @Select("select MAX(id) from t_video")
    int getMax();

    @Select("select Min(id) from t_video")
    int getMin();

    @Select("select MAX(id) from t_video where part_id = #{part}")
    int getMaxByPart(int part);

    @Select("select Min(id) from t_video where part_id = #{part}")
    int getMinByPart(int part);



    @Select("select auth_id,id,cover_url,title,timestamp,duration from t_video where id>=#{random} and part_id = #{part} order by rand() limit #{num}")
    List<Video> getVideoByPart(int part, int num, int random);

    @Select("select auth_id,id,cover_url,title,timestamp,duration from t_video where id>=#{random} order by rand() limit #{num}")
    List<Video> getVideo(int num, int random);
}
