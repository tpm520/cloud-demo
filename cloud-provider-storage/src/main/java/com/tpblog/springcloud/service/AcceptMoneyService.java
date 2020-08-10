package com.tpblog.springcloud.service;

import com.tpblog.common.oracleEntity.User;

public interface AcceptMoneyService {

    User updateAccount(Double money, Integer id);
}
