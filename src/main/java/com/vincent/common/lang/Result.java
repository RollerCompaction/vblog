package com.vincent.common.lang;

import lombok.Data;
import java.io.Serializable;

/**
 * 异步统一返回的结果封装类
 * 用来返回操作结果
 */
@Data
public class Result implements Serializable {

    //状态码，0成功，1失败
    private int status;
    private String msg;     //结果信息
    private Object data;    //结果数据
    private String action;

    public static Result success() {
        return Result.success("操作成功", null);
    }

    public static Result success(Object data) {
        return Result.success("操作成功", data);
    }

    public static Result success(String msg, Object data) {
        Result result = new Result();
        result.status = 0;
        result.msg = msg;
        result.data = data;
        return result;
    }

    public static Result fail(String msg) {
        Result result = new Result();
        result.status = -1;
        result.msg = msg;
        result.data = null;
        return result;
    }

    public Result action(String action) {
        this.action = action;
        return this;
    }

}
