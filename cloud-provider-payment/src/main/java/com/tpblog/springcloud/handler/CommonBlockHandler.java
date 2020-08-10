package com.tpblog.springcloudOrder.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.tpblog.common.entity.Order;
import com.tpblog.common.response.Result;
import com.tpblog.common.response.ResultGenerate;

public class CommonBlockHandler {
    public static Result error(Order order,BlockException e){
        System.out.println("error----哈哈哈"+e+"---"+order);
        return ResultGenerate.success(200, "warning", "系统繁忙");

        //return "服务繁忙。。。。。";
    }
}
