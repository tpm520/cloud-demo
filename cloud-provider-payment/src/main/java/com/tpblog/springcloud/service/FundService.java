package com.tpblog.springcloud.service;


import com.tpblog.common.entity.Fund;

public interface FundService {
    // 扣资金
    Fund deductMoney(Integer money, Integer uid);

    // 根据用户id查询资金
    Fund getFundByUid(Integer uid);

    // 账户打入资金
    Fund saveFund(Fund fund);
}
