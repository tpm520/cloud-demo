package com.tpblog.springcloudOrder.service;


import com.tpblog.common.entity.Fund;
import com.tpblog.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value = "cloud-provider-payment", path = "/fund")
public interface FundService {
    @PostMapping("/save")
    public Result saveFund(@RequestBody Fund fund);

    @PostMapping("/deduct/money")
    public Result deductMoney(@RequestParam(value = "money") Integer money, @RequestParam(value = "uid") Integer uid);

    @PostMapping("/transfer")
    public Result transferAccount(@RequestParam(value = "money", required = true) Double money,
                                  @RequestParam(value = "id", required = true) Integer uid1);
}
