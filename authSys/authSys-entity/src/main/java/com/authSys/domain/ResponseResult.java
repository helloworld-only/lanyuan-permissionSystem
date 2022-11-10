package com.authSys.domain;

import java.io.Serializable;
import java.util.HashMap;

// 响应客户端的数据类
public class ResponseResult extends HashMap<String,Object> {

    private static final long serialVersionUID = 1L;

    /** 状态码 */
    public static final String CODE_TAG = "code";

    /** 返回内容 */
    public static final String MSG_TAG = "msg";

    /** 数据对象 */
    public static final String DATA_TAG = "data";

    public ResponseResult(){}

    public ResponseResult(int code, String msg){
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    public ResponseResult(int code, String msg, Object data){
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if(data != null){
            super.put(DATA_TAG, data);
        }
    }

    // 操作成功
    public static ResponseResult success(String msg, Object data){
        return new ResponseResult(HttpStatus.SUCCESS, msg, data);
    }
    public static ResponseResult success(){
        return success("操作成功");
    }
    public static ResponseResult success(String msg){
        return success(msg, null);
    }
    public static ResponseResult success(Object data){
        return success("操作成功", data);
    }


    // 操作失败
    public static ResponseResult fail(String msg, Object data){
        return new ResponseResult(HttpStatus.ERROR, msg, data);
    }
    public static ResponseResult fail(){
        return fail("操作失败");
    }
    public static ResponseResult fail(String msg){
        return fail(msg, null);
    }
    public static ResponseResult fail(Object data){
        return fail("操作失败", data);
    }


    public ResponseResult put(String key, Object value){
        super.put(key, value);
        return this;
    }

    public void setCode(int code){
        super.put(CODE_TAG, code);
    }

    public void setMsg(String msg){
        super.put(MSG_TAG, msg);
    }

    public void setData(Object data){
        super.put(DATA_TAG, data);
    }

}
