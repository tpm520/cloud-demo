package com.tpblog.springcloudOrder.service;


import com.tpblog.common.entity.Repository;
import com.tpblog.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(value = "cloud-provider-storage", path = "/repository")
public interface RepositoryService {
    @PostMapping("/save")
    public Result saveGoods(@RequestBody Repository repository);

    @GetMapping("/rep/{code}")
    public Result findRepository(@PathVariable("code") Integer code);

    @PostMapping("/deduct/storage")
    public  Result deduct(@RequestParam(value = "num") Integer num, @RequestParam(value = "code") Integer code);

    @PostMapping("/transfer")
    public Result addMoney(@RequestParam(value = "money", required = true) Double money,
                           @RequestParam(value = "id", required = true) Integer uid1);
}
