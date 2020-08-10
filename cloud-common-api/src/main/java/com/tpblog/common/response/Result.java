package com.tpblog.common.response;

public class Result {
    public Integer code;
    public String info;
    public Object object;

    public Result() {
    }

    public Result(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Result(Integer code, String info, Object object) {
        this.code = code;
        this.info = info;
        this.object = object;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

}
