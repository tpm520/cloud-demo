package com.tpblog.springcloud.service.impl;

import com.tpblog.common.oracleEntity.User;
import com.tpblog.springcloud.repository.AcceptMoneyRepository;
import com.tpblog.springcloud.service.AcceptMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AcceptMoneyServiceImpl implements AcceptMoneyService {

    @Autowired
    private AcceptMoneyRepository repository;

    public User updateAccount(Double money, Integer id) {
        Integer info = repository.updateAccount(money, id);
        if (info > 0) {
            return repository.findById(id);
        }
        return null;
    }
}
