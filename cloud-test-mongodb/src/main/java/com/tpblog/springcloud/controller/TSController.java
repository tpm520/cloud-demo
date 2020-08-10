package com.tpblog.springcloudOrder.controller;

import com.tpblog.springcloudOrder.entity.TS;
import com.tpblog.springcloudOrder.service.TSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ts")
public class TSController {

    @Autowired
    private TSService tsService;
    @PostMapping("/save")
    public TS save(@RequestBody TS ts){
       return tsService.save(ts);
    }
}
