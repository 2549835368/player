package com.example.player.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result {

    public static final int CODE_SUCCESS =200;
    public static final int CODE_AUTH_ERROR =401;
    public static final int CODE_SYS_ERROR =500;

    private int code;
    private String message;
//    private Map<String, Object> data = new HashMap<>();
    private Object data = new Object();
    public static Result success(){
        return new Result(CODE_SUCCESS,"请求成功",null);
    }

    public static Result success(Object object){
        return new Result(CODE_SUCCESS,"请求成功",object);
    }
    public static Result success(int code,String message){
        return new Result(code,message,null);
    }


    public static Result error(){
        return new Result(CODE_SYS_ERROR,"请求失败",null);
    }
    public static Result error(Object object){
        return new Result(CODE_SYS_ERROR,"请求失败",object);
    }
    public static Result error(int code,String message){
        return new Result(code,message,null);
    }

//    public static Result data(String key, Object value){
//        Result r = new Result();
//        r.data.put(key, value);
//        return r;
//    }

//    public static Result data(Map<String, Object> map){
//        Result r = new Result();
//        r.setData(map);
//        return r;
//    }
}
