package com.tpblog.springcloud.controller;


import com.tpblog.common.entity.Repository;
import com.tpblog.common.oracleEntity.User;
import com.tpblog.common.response.Result;
import com.tpblog.common.response.ResultGenerate;
import com.tpblog.springcloud.service.AcceptMoneyService;
import com.tpblog.springcloud.service.RepositoryService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/repository")
public class RepositoryController {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private AcceptMoneyService acceptMoneyService;

    @PostMapping("/save")
    public Result saveGoods(@RequestBody Repository repository){
        Repository r = repositoryService.saveGoods(repository);
        return ResultGenerate.success(200, "success", r);
    }

    @GetMapping("/rep/{code}")
    public Result findRepository(@PathVariable("code") Integer code){
        return ResultGenerate.success(200, "success", repositoryService.getByCode(code));
    }

    @PostMapping("/deduct/storage")
    public  Result deduct(@RequestParam(value = "num") Integer num, @RequestParam(value = "code") Integer code){
        Repository r = repositoryService.deduct(num,code);

        return ResultGenerate.success(200, "success", r);
    }

    /**
     * 收款
     * @param money
     * @param uid1
     * @return
     */
    @PostMapping("/transfer")
    public Result addMoney(@RequestParam(value = "money", required = true) Double money,
                           @RequestParam(value = "id", required = true) Integer uid1){


        User user = acceptMoneyService.updateAccount(money, uid1);

        return ResultGenerate.success(200, "success", user);
    }
}
