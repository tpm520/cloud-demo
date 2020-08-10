package com.tpblog.springcloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.result.UpdateResult;
import com.tpblog.common.entity.Repository;
import com.tpblog.springcloud.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
public class RepositoryServiceImpl implements RepositoryService {

    private final String REPOSITORY_KEY = "rep";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    public Repository deduct(Integer num, Integer code) {
        // 查询库存
        Integer res = inventory(code);
        if (res < num) {
            return null;
        }
        Update update = Update.update("rest", res-num);
        Query query = Query.query(Criteria.where("code").is(code));
        UpdateResult writeResult = mongoTemplate.updateFirst(query, update, Repository.class);
        if (writeResult.getModifiedCount() > 0) {
            return getByCode(code);
        }
        return null;
    }

    public Integer inventory(Integer code) {

        Integer num = mongoTemplate.findOne(Query.query(Criteria.where("code").is(code)),Repository.class).getRest();
        return num;
    }

    public Repository getByCode(Integer code) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object rep = valueOperations.get(REPOSITORY_KEY+":"+code);
        Repository repository = null;
        if (rep==null) {
            repository = mongoTemplate.findOne(Query.query(Criteria.where("code").is(code)),Repository.class);
            // 防止缓存穿透
            if (repository == null) {
                // 保存一个默认值，设置过期时间为2分钟
                valueOperations.set(REPOSITORY_KEY+":"+code, JSON.toJSONString(new Repository()), 2, TimeUnit.MINUTES);
                return null;
            }
            
            valueOperations.set(REPOSITORY_KEY+":"+code, JSON.toJSONString(repository));
            return repository;
        }
        repository = JSON.parseObject(rep.toString(), Repository.class);
        return repository;
    }

    public Repository saveGoods(Repository repository) {
        System.out.println(repository);
        mongoTemplate.save(repository);
        return repository;
    }
}
