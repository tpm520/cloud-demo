package com.tpblog.springcloudOrder.service.impl;

import com.tpblog.springcloudOrder.entity.TS;
import com.tpblog.springcloudOrder.service.TSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TSServiceImpl implements TSService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public TS save(TS ts) {
        mongoTemplate.save(ts);
        return ts;
    }

    public List<TS> findBytId(Integer tid) {
        List<TS> tsList = mongoTemplate.find(Query.query(Criteria.where("tid").is(tid)), TS.class);
        return tsList;
    }
}
