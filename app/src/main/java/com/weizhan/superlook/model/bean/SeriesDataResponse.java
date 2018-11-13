package com.weizhan.superlook.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/11/8.
 */

public class SeriesDataResponse<T> {
    int code;
    String timestamp;
    String msg;
    String path;
    int status;
    List<T> body;

    public int getCode() {
        return code;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getMsg() {
        return msg;
    }

    public String getPath() {
        return path;
    }

    public int getStatus() {
        return status;
    }

    public List<T> getBody() {
        return body;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setBody(List<T> body) {
        this.body = body;
    }
}
