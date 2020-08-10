package com.tpblog.springcloudOrder.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ts")
public class TS {
    @Id
    private Integer tsId;
    private Integer sid;
    private Integer tid;

    public Integer getTsId() {
        return tsId;
    }

    public void setTsId(Integer tsId) {
        this.tsId = tsId;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    @Override
    public String toString() {
        return "TS{" +
                "tsId=" + tsId +
                ", sid=" + sid +
                ", tid=" + tid +
                '}';
    }
}
