package com.tpblog.springcloudOrder.controller;

import com.tpblog.springcloudOrder.entity.Book;
import com.tpblog.springcloudOrder.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/save")
    public Book save(@RequestBody Book book){
        return bookService.save(book);
    }
}
