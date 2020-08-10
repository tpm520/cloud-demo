package com.tpblog.springcloudOrder.controller;

import com.tpblog.springcloudOrder.entity.Student;
import com.tpblog.springcloudOrder.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/student/{id}")
    public String getStudentBySid(@PathVariable("id") Integer id){
        return "success";
    }

    @PostMapping("/student/save")
    public Student save(@RequestBody Student student){
        studentService.save(student);
        return student;
    }

    @GetMapping("/student/findall")
    public List<Student> getStudentAll(){
        return studentService.findAll();
    }

    @GetMapping("/student/byname/{name}")
    public Student getStudentByName(@PathVariable("name") String name){
        return studentService.findByName(name);
    }

    @GetMapping("/student/{name}/{age}")
    public Student updateAgeByName(@PathVariable("age") Integer age,@PathVariable("name") String name){
        return studentService.updateAgeByName(name,age);
    }

    @PostMapping("/student/delete/{name}")
    public Student deleteStudentByName(@PathVariable("name") String name){
        return studentService.delete(name);
    }

    @PostMapping("/upload/file")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        return studentService.uploadFile(file);
    }

    @GetMapping("/file/down")
    public void download(@RequestParam("fileId") String fileId,
                         HttpServletRequest request,
                         HttpServletResponse response){
        studentService.download(fileId, request, response);
    }
}
