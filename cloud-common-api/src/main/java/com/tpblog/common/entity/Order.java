package com.tpblog.common.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 订单
 */
@Document(collection = "order")
public class Order implements Serializable {
    private String oid;

    // 下订单的用户
    private Integer uid;

    // 订单数量
    private Integer num;

    // 订单单价
    private Integer price;

    // 商品编号
    private Integer code;

    // 订单状态 1 成功  0 失败
    private int state;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Order{" +
                "oid=" + oid +
                ", uid=" + uid +
                ", num=" + num +
                ", price=" + price +
                ", code=" + code +
                ", state=" + state +
                '}';
    }
}
