package com.tpblog.springcloud.service.impl;

import com.mongodb.client.result.UpdateResult;
import com.tpblog.common.entity.Fund;
import com.tpblog.springcloud.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class FundServiceImpl implements FundService {

    @Autowired
    private MongoTemplate mongoTemplate;


    public Fund deductMoney(Integer money, Integer uid) {
        // 查询余额
        Integer balance = getFundByUid(uid).getMoney();

        if (balance < money) {
            throw new IllegalArgumentException("资金不足");
        }
        Update update = Update.update("money", balance-money);
        Query query = Query.query(Criteria.where("uid").is(uid));
        UpdateResult writeResult = mongoTemplate.updateFirst(query, update, Fund.class);
        if (writeResult.getModifiedCount() > 0) {
            return getFundByUid(uid);
        }else{
            throw new IllegalArgumentException("扣除失败");
        }
    }

    public Fund getFundByUid(Integer uid) {

        return mongoTemplate.findOne(Query.query(Criteria.where("uid").is(uid)),Fund.class);
    }

    public Fund saveFund(Fund fund) {
        mongoTemplate.save(fund);
        return fund;
    }
}
