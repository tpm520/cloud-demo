package com.tpblog.springcloudOrder.service.impl;

import com.mongodb.client.result.UpdateResult;
import com.tpblog.common.entity.Order;
import com.tpblog.springcloudOrder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final String ORDER_KEY = "order:all";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    public Order placeOrder(Order order) {
        mongoTemplate.save(order);
        return order;
    }

    public long changeState(String oid, int state) {
        long info = 0;
        if (state == 0 || state == 1) {
            UpdateResult writeResult = mongoTemplate.updateFirst(Query.query(Criteria.where("oid").is(oid)),
                    Update.update("state", state), Order.class);
            info = writeResult.getModifiedCount();
        }else {
            throw new IllegalArgumentException("订单状态不满足规则");
        }

        return info;
    }

    /**
     * 将订单保存到redis中
     * @return
     */
    public List<Order> findOrderAll() {
        // 查询数据库之前先判断redis缓存中是否存中
        ListOperations listOperations = redisTemplate.opsForList();
        Long size = listOperations.size(ORDER_KEY);
        List<Order> orders = null;

        // 缓存中存中数据
        if (size > 0) {
            orders = listOperations.range(ORDER_KEY,0, size);
            return orders;
        }

        // 缓存中没有查询数据库
        orders = mongoTemplate.findAll(Order.class);
        // 将数据保存至redis缓存
        for (Order order : orders) {
            listOperations.rightPush(ORDER_KEY, order);
        }
        return orders;
    }


}
