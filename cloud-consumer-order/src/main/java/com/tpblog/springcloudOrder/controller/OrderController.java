package com.tpblog.springcloudOrder.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.tpblog.common.entity.Order;
import com.tpblog.common.oracleEntity.User;
import com.tpblog.common.response.Result;
import com.tpblog.common.response.ResultGenerate;
import com.tpblog.springcloudOrder.service.FundService;
import com.tpblog.springcloudOrder.service.OrderService;
import com.tpblog.springcloudOrder.service.RepositoryService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private FundService fundService;

    @Autowired
    private RepositoryService repositoryService;


    /**
     * 转账
     * @param money
     * @param uid1
     * @param uid2
     * @return
     */
    @PostMapping("/transfer")
    @GlobalTransactional(rollbackFor = Exception.class)
    public Result transfer(@RequestParam("money") Double money,
                           @RequestParam("uid1") Integer uid1,
                           @RequestParam("uid2") Integer uid2){
        Result r1 = fundService.transferAccount(money, uid1);
        Result r2 = repositoryService.addMoney(money, uid2);

        List<Object> users = new ArrayList<Object>();
        users.add(r1.getObject());
        users.add(r2.getObject());

        return ResultGenerate.success(200,"success",users);
    }



    @GetMapping("/testC")
    // @SentinelResource(value = "testC",blockHandler = "handler")
    public String testB(@RequestParam(value = "p1",required = false) String p1,@RequestParam(value = "p2",required = false) String p2){
        return "testC";
    }
    public String handler(String p1, String p2, BlockException block){
        return "handler->服务繁忙";
    }

    /*@GetMapping("/test")
    @SentinelResource(value = "test", blockHandler = "testE")
    public String test(){
        return "hello world";
    }

    public String testE(){
        return "降级降级降级";
    }*/


    /*@SentinelResource(value = "goods",
            fallback = "error2",
            blockHandler = "error")*/
    @PostMapping("/goods")
    @GlobalTransactional
    public Result placeOrder(@RequestBody Order order){
        log.info("保存订单start");
        String oid = System.nanoTime()+"";
        order.setOid(oid);
        orderService.placeOrder(order);
        log.info("保存订单end");

        log.info("扣除资金start");
        fundService.deductMoney(order.getNum()*order.getPrice(),order.getUid());
        log.info("扣除资金end");

        log.info("扣除库存start");
        repositoryService.deduct(order.getNum(), order.getCode());
        log.info("扣除库存end");

        log.info("改变订单状态");
        orderService.changeState(oid, 1);
        log.info("下单成功");

        return ResultGenerate.success(200, "success", null);
    }

    public Result error(Order order,BlockException block){
        System.out.println("error----哈哈哈"+block+"---"+order);
        return ResultGenerate.success(200, "warning", "系统繁忙");

        //return "服务繁忙。。。。。";
    }

    public Result error2(Order order,Throwable block){
        //System.out.println("error----哈哈哈"+block+"---"+order);
        return ResultGenerate.success(200, "error", "系统繁忙");
    }
    /**
     * 获取所有订单
     * @return
     */
    @GetMapping("/get/all")
    public Result findOrderAll(){
        /*try {
            TimeUnit.SECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        List<Order> orders = orderService.findOrderAll();
        return ResultGenerate.success(200, "success", orders);
    }
}
