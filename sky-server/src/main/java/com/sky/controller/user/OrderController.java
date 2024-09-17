package com.sky.controller.user;

import com.sky.dto.OrdersDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.web.bind.annotation.*;

@RestController("userOrderController")
@Slf4j
@RequestMapping("/user/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/submit")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO) {
        log.info("用户下单: {}", ordersSubmitDTO);
        OrderSubmitVO orderSubmitVO = orderService.submitOrder(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    @PutMapping("/payment")
    @ApiOperation("订单支付")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
        OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);
        log.info("生成预支付交易单：{}", orderPaymentVO);
        orderService.paySuccess(ordersPaymentDTO.getOrderNumber());
        return Result.success(orderPaymentVO);
    }

    @GetMapping("/historyOrders")
    public Result<PageResult> page(int page,int pageSize,Integer status) {
        PageResult pageResult = orderService.pageQuery(page, pageSize, status);
        return Result.success(pageResult);
    }

    @GetMapping("/orderDetail/{id}")
    public Result<OrderVO> details(@PathVariable Long id) {
        OrderVO orderVO = orderService.details(id);
        return Result.success(orderVO);
    }

    /*
        - 待支付和待接单状态下，用户可直接取消订单
        - 商家已接单状态下，用户取消订单需电话沟通商家
        - 派送中状态下，用户取消订单需电话沟通商家
        - 如果在待接单状态下取消订单，需要给用户退款
        - 取消订单后需要将订单状态修改为“已取消”
    *
    * */
    @PutMapping("/cancel/{id}")
    public Result cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return Result.success();
    }

    @PostMapping("/repetition/{id}")
    public Result repetOrderById(@PathVariable Long id) {
        orderService.repetOrderById(id);
        return Result.success();
    }
}
