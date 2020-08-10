package com.tpblog.springcloud.service;


import com.tpblog.common.entity.Repository;

public interface RepositoryService {
    // 减库存
    Repository deduct(Integer num, Integer code);

    // 查询库存
    Integer inventory(Integer code);

    // 根据商品编号查询商品
    Repository getByCode(Integer code);

    // 商品入库
    Repository saveGoods(Repository repository);
}
