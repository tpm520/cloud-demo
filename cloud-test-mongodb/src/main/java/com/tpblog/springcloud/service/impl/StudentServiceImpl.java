package com.tpblog.springcloudOrder.service.impl;

import com.mongodb.WriteResult;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import com.tpblog.springcloudOrder.entity.Student;
import com.tpblog.springcloudOrder.service.StudentService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private GridFsTemplate fsTemplate;

    public Student save(Student student) {
        mongoTemplate.save(student);
        return student;
    }

    public Student findById(String id) {
        Criteria criteria = Criteria.where("_id").is(id);
        Query query = new Query(criteria);
        Student s = mongoTemplate.findOne(query, Student.class);
        return s;
    }

    public List<Student> findAll() {
        List<Student> studentList = mongoTemplate.findAll(Student.class);
        return studentList;
    }

    public Student findByName(String name) {

        Criteria criteria = Criteria.where("name").is(name);
        Query query = new Query(criteria);
        Student student = mongoTemplate.findOne(query,Student.class);
        return student;
    }

    public Student updateAgeByName(String name,Integer age) {

        Criteria criteria = Criteria.where("name").is(name);

        Query query = new Query(criteria);

        Update update = new Update().set("age",age);
        UpdateResult writeResult = mongoTemplate.updateFirst(query, update, Student.class);
        Student student = null;
        if (writeResult.getModifiedCount()>0){
            student = findByName(name);
        }
        return student;
    }

    public Student delete(String name) {
        Student student = findByName(name);
        Criteria criteria = Criteria.where("name").is(name);
        Query query = new Query(criteria);
        DeleteResult result = mongoTemplate.remove(query, Student.class);

        if (result.getDeletedCount()==0){
            student = null;
        }
        return student;
    }

    /**
     *
     * GridFs文件上传
     * @param file
     */
    public String uploadFile(MultipartFile file) {
        // 获取文件名
        String filename = file.getOriginalFilename();
        // 设置元数据值
        HashMap<String,String> metadata = new HashMap<String, String>();
        metadata.put("tags","test");
        metadata.put("info","what are you doing");
        ObjectId object = null;
        try {
            // 获取文件输入流
            InputStream inputStream = file.getInputStream();
            // 保存到mongodb, 返回文件基本信息
            object = fsTemplate.store(inputStream,filename,metadata);
        } catch (IOException e) {}

        return object.toString();
    }

    public void download(String fileId, HttpServletRequest request, HttpServletResponse response) {

    }


   /* public void download(@RequestParam("fileId") String fileId,
                         HttpServletRequest request,
                         HttpServletResponse response){
        // 获取文件
        GridFSDBFile gridFS = fsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));

        if (gridFS==null) {
            return;
        }

        // 获取文件名
        String filename = gridFS.getFilename();

        try {
            // 处理中文文件名乱码
            if (request.getHeader("User-Agent").toUpperCase().contains("MSIE") ||
                    request.getHeader("User-Agent").toUpperCase().contains("TRIDENT")
                    || request.getHeader("User-Agent").toUpperCase().contains("EDGE")) {
                filename = java.net.URLEncoder.encode(filename, "UTF-8");
            } else {
                // 非IE浏览器的处理：
                filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
            }

            // 通知浏览器下载
            response.setContentType(gridFS.getContentType());
            response.setHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");
            gridFS.writeTo(response.getOutputStream());
        }catch (Exception e){}

    }*/
}
