package com.tpblog.springcloudOrder.service.impl;

import com.tpblog.springcloudOrder.entity.Book;
import com.tpblog.springcloudOrder.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private MongoTemplate mongoTemplate;
    public Book save(Book book) {
        mongoTemplate.save(book);
        return book;
    }
}
