package com.tpblog.yaml.util;

import com.tpblog.yaml.ChangeYaml;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Slf4j
public class YamlUtil {


    private static StringBuffer ymlData = new StringBuffer();

    private final static String TRANSFER_LINE = "\r\n";

    private static String ymlPath = YamlUtil.class.getResource("/test.yml").getPath();
    /**
     * 将yaml转化为map集合
     * @param yamlPath
     * @return
     */
    public static Map<String, Object> yamlToMap(String yamlPath){
        LinkedHashMap<String, Object> maps = new LinkedHashMap<String, Object>();

        Yaml yaml = new Yaml();
        InputStream inputStream = YamlUtil.class.getResourceAsStream(yamlPath);

        maps = yaml.loadAs(inputStream, LinkedHashMap.class);

        return maps;
    }

    /**
     * map转yaml
     * @param maps
     * @return
     */
    public static String mapToYaml(LinkedHashMap<String, Object> maps, int level){

        Set<Map.Entry<String, Object>> entries = maps.entrySet();

        for (Map.Entry<String, Object> entry : entries) {
            String  key = entry.getKey();
            Object  value = entry.getValue();

            if (value instanceof Map) {
                // 获取层级
                level = (Integer) ((Map) value).get("level");
                // 获取缩进
                String sp = space_two(level);
                ymlData.append(sp+key+": "+TRANSFER_LINE);
                // map对象继续递归
                mapToYaml((LinkedHashMap<String, Object>) value, level);
            }else {
                // 去掉自定义的level属性
                if ("level".equals(key)) continue;
                // 获取缩进
                String sp = space(level);
                // 如果是List集合进一步遍历
                if (value instanceof Collection) {
                    StringBuffer sb = new StringBuffer();
                   for (Object v : (List)value){
                        sb.append(TRANSFER_LINE+sp+sp+"- "+v.toString()+TRANSFER_LINE);
                   }
                   value=sb.toString();
                }
                ymlData.append(sp+key+": "+value+TRANSFER_LINE);
            }
        }
        // System.out.println(ymlData);
        return ymlData.toString();
    }

    /**
     * 确定yml属性层级关系
     * @param maps
     * @param level
     * @return
     */
    public static Map<String, Object> confirmLevel(LinkedHashMap<String, Object> maps, int level){

        Set<Map.Entry<String, Object>> entries = maps.entrySet();
        for (Map.Entry<String, Object> entry: entries) {
            Object val = entry.getValue();
            if (val instanceof Map) {
                ((Map) val).put("level", level);
                // map对象递归
                confirmLevel((LinkedHashMap<String, Object>) val, ++level);
                // 递归结束后层级归0
                level = 0;
            }
        }
        return maps;
    }

    /**
     * 缩进个数
     * 适用于值为非map的缩进
     * @param level
     * @return
     */
    public static String space(int level){
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < (1+level)*2; i++) {
            stringBuffer.append(" ");
        }
        return stringBuffer.toString();
    }

    /**
     * 适用于值为Map的缩进
     * @param level
     * @return
     */
    public static String space_two(int level){
        if (level==0) return "";
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < (level)*2; i++) {
            stringBuffer.append(" ");
        }
        return stringBuffer.toString();
    }

    /**
     * 更新yml文件
     * @param yml
     * @return
     */
    public static boolean updateYml(String yml){
        //String ymlPath = YamlUtil.class.getResource("/test.yml").getPath();
        File ymlFile = new File(ymlPath);
        if (!ymlFile.exists()) {
           throw new IllegalArgumentException("yml文件不存在");
        }
        try {
            FileWriter fileWriter = new FileWriter(ymlFile);
            fileWriter.write(yml);
            fileWriter.flush();
            fileWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
