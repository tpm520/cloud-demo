package com.tpblog.yaml.controller;

import com.tpblog.yaml.util.YamlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@Slf4j
public class YamlController {
    @Value("${userInfo.name}")
    private String name;

    @PostMapping("/yaml")
    public String changeYaml(String username) throws Exception {
        //LinkedHashMap<String,Object> map = (LinkedHashMap<String, Object>) YamlUtil.yamlToMap("/application.yml");

        //System.out.println(map);

        //boolean lisi = YamlUtil.updateYaml("userInfo.name", "wangwu", "/application.yml");
        Map<String, Object> stringObjectMap = YamlUtil.yamlToMap("/application.yml");
        Map map = (Map) stringObjectMap.get("userInfo");
        map.put("name",username);

        String yml = YamlUtil.mapToYaml((LinkedHashMap<String, Object>) YamlUtil.confirmLevel((LinkedHashMap<String, Object>) stringObjectMap,0), 0);
        //System.out.println(yml);

        YamlUtil.updateYml(yml);
        return name;
    }


}
