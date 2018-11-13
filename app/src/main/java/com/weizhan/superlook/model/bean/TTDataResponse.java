package com.weizhan.superlook.model.bean;


/**
 * Created by Administrator on 2018/11/7.
 */

public class TTDataResponse<T> {

    private int code;

    private int status;

    private T body;

    private String timestamp;

    private String msg;

    public int getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }

    public T getBody() {
        return body;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
