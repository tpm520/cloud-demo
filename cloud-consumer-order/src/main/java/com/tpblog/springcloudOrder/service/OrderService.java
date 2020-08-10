package com.tpblog.springcloudOrder.service;


import com.tpblog.common.entity.Order;

import java.util.List;

public interface OrderService {
    // 下单
    Order placeOrder(Order order);

    // 修改订单状态
    long changeState(String oid, int state);

    // 查询所有订单
    List<Order> findOrderAll();

}
