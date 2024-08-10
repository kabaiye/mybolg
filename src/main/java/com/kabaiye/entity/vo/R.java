package com.kabaiye.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kabaiye.entity.enums.StatusCode;
import lombok.Getter;

@Getter
public class R {
    private Integer code = 0;

    private String msg = "成功";

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;
    public static R ok(Object data){
        R r = new R();
        r.data = data;
        return r;
    }

    public static R error(StatusCode statusCode){
        R r = new R();
        r.msg = statusCode.getMessage();
        r.code = statusCode.getCode();
        return r;
    }

    public static R error(String massage){
        R r = new R();
        r.msg = massage;
        r.code = 400;
        return r;
    }
    public R setCode(Integer code){
        this.code = code;
        return this;
    }
    public R setMessage(String massage){
        this.msg = massage;
        return this;
    }
    public R setData(Object data){
        this.data = data;
        return this;
    }

}
