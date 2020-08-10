package com.tpblog.springcloudOrder.controller;

import com.tpblog.springcloudOrder.entity.Teacher;
import com.tpblog.springcloudOrder.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/save")
    public Teacher save(@RequestBody Teacher teacher){
        return teacherService.save(teacher);
    }

    @GetMapping("byId/{tid}")
    public void findTeacherById(@PathVariable Integer tid){
        teacherService.findById(tid);
    }

    @GetMapping("book/{tid}")
    public Map findBookById(@PathVariable Integer tid){
        return teacherService.findBookById(tid);
    }
}
