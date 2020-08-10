package com.tpblog.springcloudOrder.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "spring.data.mongodb")
public class MongoConfig extends MongoProperties {
    @Bean(name = "mongoDbFactory")
    @Override
    public MongoDbFactory mongoDbFactory() {
        System.out.println(super.getPassword());
        // 有认证的初始化方法
        ServerAddress serverAddress = new ServerAddress(super.getHost(), super.getPort());
        List<MongoCredential> mongoCredentialList = new ArrayList<MongoCredential>();
        //mongoCredentialList.add(MongoCredential.createCredential(super.getUsername(),super.getDatabase(),super.getPassword().toCharArray()));

        mongoCredentialList.add(MongoCredential.createScramSha1Credential(super.getUsername(), super.getDatabase(),super.getPassword().toCharArray()));
        return new SimpleMongoDbFactory(new MongoClient(serverAddress,mongoCredentialList), super.getDatabase());
        // 无认证的初始化方法
//        return new SimpleMongoDbFactory(new MongoClient(super.getHost(), super.getPort()), super.getDatabase());
    }

    @Bean(name = "mongoTemplate")
    @Override
    public MongoTemplate mongoTemplate(@Qualifier("mongoDbFactory") MongoDbFactory mongoDbFactory) {
        return new MongoTemplate(mongoDbFactory);
    }


}
