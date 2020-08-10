package com.tpblog.springcloudOrder.service;

import com.tpblog.springcloudOrder.entity.Teacher;

import java.util.Map;

public interface TeacherService {
    Teacher save(Teacher teacher);

    Teacher findById(Integer id);

    Map findBookById(Integer tid);
}
