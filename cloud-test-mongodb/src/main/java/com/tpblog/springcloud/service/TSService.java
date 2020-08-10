package com.tpblog.springcloudOrder.service;

import com.tpblog.springcloudOrder.entity.TS;

import java.util.List;

public interface TSService {
    TS save(TS ts);

    List<TS> findBytId(Integer tid);
}
