package com.tpblog.common.entity;


import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 仓库
 */
@Document(collection = "repository")
public class Repository implements Serializable {
    private Integer rid;

    // 商品编号
    private Integer code;

    // 仓库剩余存货
    private Integer rest;

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getRest() {
        return rest;
    }

    public void setRest(Integer rest) {
        this.rest = rest;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Repository{" +
                "rid=" + rid +
                ", code=" + code +
                ", rest=" + rest +
                '}';
    }
}
