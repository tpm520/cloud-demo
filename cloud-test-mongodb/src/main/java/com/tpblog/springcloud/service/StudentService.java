package com.tpblog.springcloudOrder.service;

import com.tpblog.springcloudOrder.entity.Student;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface StudentService {
    Student save(Student student);

    Student findById(String id);

    List<Student> findAll();

    Student findByName(String name);

    Student updateAgeByName(String name,Integer age);

    Student delete(String name);

    String uploadFile(MultipartFile file);

    void download(@RequestParam("fileId") String fileId,
                         HttpServletRequest request,
                         HttpServletResponse response);
}
