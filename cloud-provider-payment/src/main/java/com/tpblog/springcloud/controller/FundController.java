package com.tpblog.springcloud.controller;


import com.tpblog.common.entity.Fund;
import com.tpblog.common.oracleEntity.User;
import com.tpblog.common.response.Result;

import com.tpblog.common.response.ResultGenerate;
import com.tpblog.springcloud.service.FundService;
import com.tpblog.springcloud.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fund")
public class FundController {

    @Autowired
    private FundService fundService;

    @Autowired
    private TransferService transferService;

    @PostMapping("/save")
    public Result saveFund(@RequestBody Fund fund){
        Fund f = fundService.saveFund(fund);

        return ResultGenerate.success(200, "success", f);
    }

    @PostMapping("/deduct/money")
    public Result deductMoney(@RequestParam(value = "money") Integer money,@RequestParam(value = "uid") Integer uid){



        Fund fund = fundService.deductMoney(money,uid);

        return ResultGenerate.success(200, "success", fund);
    }

    /**
     * 转账
     * @param money
     * @param uid1
     * @return
     */
    @PostMapping("/transfer")
    public Result transferAccount(@RequestParam(value = "money", required = true) Double money,
                                  @RequestParam(value = "id", required = true) Integer uid1){
        money = -Math.abs(money);

        User user = transferService.updateAccount(money, uid1);

        return ResultGenerate.success(200, "success", user);
    }
}
