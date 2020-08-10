package com.tpblog.springcloudOrder.service.impl;

import com.tpblog.springcloudOrder.entity.Teacher;
import com.tpblog.springcloudOrder.service.TSService;
import com.tpblog.springcloudOrder.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TSService tsService;
    public Teacher save(Teacher teacher) {
        mongoTemplate.save(teacher);
        return teacher;
    }

    public Teacher findById(Integer id) {
        //List<TS> ts = tsService.findBytId(id);
        System.out.println(id);
        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("ts").localField("_id").foreignField("tid").as("teacherResult");
        
        AggregationOperation match = Aggregation.match(Criteria.where("_id").is(id));



        Aggregation aggregation = Aggregation.newAggregation(match,lookupOperation);
        List<Map> maps = mongoTemplate.aggregate(aggregation,"teacher", Map.class).getMappedResults();
        
        System.out.println(maps);

        return null;
    }

    public Map findBookById(Integer tid) {

        /*
            对于sql语句：
                SELECT * FROM teacher t INNER JOIN book b ON t._id=b.tid WHERE _id=?
         */

        // 关联操作
        LookupOperation operation = LookupOperation.newLookup()
                .from("book") // 关联的表
                .localField("_id") // 自身的关联字段
                .foreignField("tid") // 关联表的关联字段
                .as("books"); // 查询出关联表结果集名
        // 添加条件
        AggregationOperation match = Aggregation.match(Criteria.where("_id").is(tid));

        // 整合关联关系和查询条件
        Aggregation aggregation = Aggregation.newAggregation(match,operation);



        Map teacher = mongoTemplate.aggregate(aggregation, "teacher", Map.class).getUniqueMappedResult();
        return teacher;
    }


}
