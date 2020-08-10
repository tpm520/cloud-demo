package com.tpblog.springcloud.service.impl;

import com.tpblog.common.oracleEntity.User;
import com.tpblog.springcloud.repository.TransferRepository;
import com.tpblog.springcloud.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    private TransferRepository repository;

    @Override
    public User findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public User updateAccount(Double money, Integer id1) {
        Double rest = findById(id1).getAccount();
        if (rest < 0) {
            throw new IllegalArgumentException("账户余额不足");
        }
        Integer info = repository.updateAccount(money, id1);
        if (info > 0) {
            return findById(id1);
        }
        return null;
    }
}
