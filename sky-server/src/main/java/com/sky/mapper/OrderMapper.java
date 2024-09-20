package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.GoodsSalesDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {
    void insert(Orders order);

    /**
     * 根据订单号和用户id查询订单
     * @param orderNumber
     * @param userId
     */
    @Select("select * from orders where number = #{orderNumber} and user_id= #{userId}")
    Orders getByNumberAndUserId(String orderNumber, Long userId);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);

    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    @Select("select * from orders where id = #{id}")
    Orders getById(Long id);


    @Select("select count(*) from orders where status = #{status}")
    Integer countByStatus(Integer status);

    @Select("select * from orders where status = #{status} and order_time < #{time} ")
    List<Orders> getByOrderTimeLT(LocalDateTime time, Integer status);

    @Select("select sum(amount) from orders where status = #{status} and order_time > #{start1} and order_time < #{end1}")
    Double sumByStatusAndOrderTime(Integer status, LocalDateTime start1, LocalDateTime end1);

    Integer countByStatusAndOrderTime(Integer status, LocalDateTime start1, LocalDateTime end1);

    List<GoodsSalesDTO> getSalesTop10(LocalDateTime start1, LocalDateTime end1);
}
