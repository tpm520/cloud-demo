package com.tpblog.springcloud.service;

import com.tpblog.common.oracleEntity.User;

public interface TransferService {
    User findById(Integer id);

    User updateAccount(Double money, Integer id1);
}
