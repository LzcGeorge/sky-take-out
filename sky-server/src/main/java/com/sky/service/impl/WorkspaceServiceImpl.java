package com.sky.service.impl;


import com.sky.entity.Dish;
import com.sky.entity.Orders;
import com.sky.entity.Setmeal;
import com.sky.mapper.DishMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    public BusinessDataVO getBusinessData() {
        LocalDateTime start1 = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime end1 = LocalDateTime.now().with(LocalTime.MAX);

        Integer newUser = userMapper.countByCreateTime(start1, end1);
        Integer validCount = orderMapper.countByStatusAndOrderTime(Orders.COMPLETED, start1, end1);
        Integer totalCount = orderMapper.countByStatusAndOrderTime(null, start1, end1);
        Double turnover = orderMapper.sumByStatusAndOrderTime(Orders.COMPLETED, start1, end1);
        turnover = turnover == null ? 0 : turnover;

        Double completionRate = 0.0;
        Double unitPrice = 0.0;
        if(totalCount != 0)
            completionRate = validCount * 1.0 / totalCount;
        if(validCount != 0)
            unitPrice = turnover / validCount;

        return BusinessDataVO.builder()
                .newUsers(newUser)
                .orderCompletionRate(completionRate)
                .turnover(turnover)
                .unitPrice(unitPrice)
                .validOrderCount(validCount)
                .build();

    }

    public OrderOverViewVO getOverviewOrders() {
        LocalDateTime start1 = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime end1 = LocalDateTime.now().with(LocalTime.MAX);

        Integer allOrder = orderMapper.countByStatusAndOrderTime(null, start1, end1);
        Integer cancelledOrder = orderMapper.countByStatusAndOrderTime(Orders.CANCELLED, start1, end1);
        Integer completedOrder = orderMapper.countByStatusAndOrderTime(Orders.COMPLETED, start1, end1);
        Integer deliveredOrder = orderMapper.countByStatusAndOrderTime(Orders.CONFIRMED, start1, end1);
        Integer waitingOrder = orderMapper.countByStatusAndOrderTime(Orders.TO_BE_CONFIRMED, start1, end1);

        return OrderOverViewVO.builder()
                .allOrders(allOrder)
                .cancelledOrders(cancelledOrder)
                .completedOrders(completedOrder)
                .deliveredOrders(deliveredOrder)
                .waitingOrders(waitingOrder)
                .build();



    }

    public DishOverViewVO getOverviewDishes() {
        Integer sold = dishMapper.countBystatus(1);
        Integer discontinued = dishMapper.countBystatus(0);
        return DishOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }

    public SetmealOverViewVO getOverviewSetmeals() {
        Integer sold = setmealMapper.countByStatus(1);
        Integer discontinued = setmealMapper.countByStatus(0);
        return SetmealOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }

    public BusinessDataVO getBusinessDataByTime(LocalDateTime start1, LocalDateTime end1) {
        Integer newUser = userMapper.countByCreateTime(start1, end1);
        Integer validCount = orderMapper.countByStatusAndOrderTime(Orders.COMPLETED, start1, end1);
        Integer totalCount = orderMapper.countByStatusAndOrderTime(null, start1, end1);
        Double turnover = orderMapper.sumByStatusAndOrderTime(Orders.COMPLETED, start1, end1);
        turnover = turnover == null ? 0 : turnover;

        Double completionRate = 0.0;
        Double unitPrice = 0.0;
        if(totalCount != 0)
            completionRate = validCount * 1.0 / totalCount;
        if(validCount != 0)
            unitPrice = turnover / validCount;

        return BusinessDataVO.builder()
                .newUsers(newUser)
                .orderCompletionRate(completionRate)
                .turnover(turnover)
                .unitPrice(unitPrice)
                .validOrderCount(validCount)
                .build();
    }
}
