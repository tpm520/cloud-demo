package com.tpblog.common.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 资金账户
 */
@Document(collection = "fund")
public class Fund implements Serializable {
    private Integer fid;

    // 账户所属用户
    private Integer uid;

    // 剩余资金
    private Integer money;

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Fund{" +
                "fid=" + fid +
                ", uid=" + uid +
                ", money=" + money +
                '}';
    }
}
