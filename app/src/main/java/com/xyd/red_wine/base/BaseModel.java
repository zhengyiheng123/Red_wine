package com.xyd.red_wine.base;

import java.io.Serializable;

/**
 * @author: zhaoxiaolei
 * @date: 2017/4/17
 * @time: 14:24
 * @description:
 */

public class BaseModel<T> implements Serializable{

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    //private Boolean ReState = false;
    private T data;
    private int code;
    private String message;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String msg;
    // private String ReToken;


//    public T getReResult() {
//        return ReResult;
//    }
//
//    public void setReResult(T reResult) {
//        ReResult = reResult;
//    }
//
//    public Boolean isReState() {
//        return ReState;
//    }
//
//    public void setReState(Boolean reState) {
//        ReState = reState;
//    }
//
//    public String getReMessage() {
//        return ReMessage;
//    }
//
//    public void setReMessage(String reMessage) {
//        ReMessage = reMessage;
//    }
//
//    public String getReToken() {
//        return ReToken;
//    }
//
//    public void setReToken(String reToken) {
//        ReToken = reToken;
//    }
}
