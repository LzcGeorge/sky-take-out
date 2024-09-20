package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.entity.User;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.*;
import net.bytebuddy.asm.Advice;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;
    public TurnoverReportVO getTurnover(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);

        LocalDate cur = begin;
        while(!cur.equals(end)) {
            cur = cur.plusDays(1);
            dateList.add(cur);
        }

        List<Double> turnoverList = new ArrayList<>();
        for(LocalDate date: dateList) {
            LocalDateTime start1 = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime end1 = LocalDateTime.of(date, LocalTime.MAX);


            Double turnover = orderMapper.sumByStatusAndOrderTime(Orders.COMPLETED, start1, end1);
            turnoverList.add(turnover == null ? 0.0 : turnover);
        }

        return TurnoverReportVO.builder()
                .dateList(StringUtils.join(dateList,","))
                .turnoverList(StringUtils.join(turnoverList,","))
                .build();
    }

    public OrderReportVO getOrder(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);

        LocalDate cur = begin;
        while(!cur.equals(end)) {
            cur = cur.plusDays(1);
            dateList.add(cur);
        }

        List<Integer> totalOrderCountList = new ArrayList<>();
        List<Integer> validOrderCountList = new ArrayList<>();
        Integer totalCount = 0;
        Integer validCount = 0;
        for(LocalDate date: dateList) {
            LocalDateTime start1 = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime end1 = LocalDateTime.of(date, LocalTime.MAX);

            Integer valid = orderMapper.countByStatusAndOrderTime(Orders.COMPLETED, start1, end1);
            Integer total = orderMapper.countByStatusAndOrderTime(null,start1,end1);
            validOrderCountList.add(valid);
            totalOrderCountList.add(total);
            totalCount += total;
            validCount += valid;
        }
        Double completionRate = 0.0;
        if(totalCount != 0) {
            completionRate = validCount.doubleValue() / totalCount;
        }
        return OrderReportVO.builder()
                .dateList(StringUtils.join(dateList,","))
                .validOrderCountList(StringUtils.join(validOrderCountList,","))
                .validOrderCount(validCount)
                .orderCountList(StringUtils.join(totalOrderCountList,","))
                .totalOrderCount(totalCount)
                .orderCompletionRate(completionRate)
                .build();
    }

    public UserReportVO getUser(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);

        LocalDate cur = begin;
        while(!cur.equals(end)) {
            cur = cur.plusDays(1);
            dateList.add(cur);
        }
        List<Integer> newUserList = new ArrayList<>();
        List<Integer> totalUserList = new ArrayList<>();
        Integer sum = 0;
        for(LocalDate date: dateList) {
            LocalDateTime start1 = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime end1 = LocalDateTime.of(date, LocalTime.MAX);

            Integer newUser = userMapper.countByCreateTime(start1, end1);
            newUserList.add(newUser);
            sum += newUser;
            totalUserList.add(sum);
        }

        return UserReportVO.builder()
                .dateList(StringUtils.join(dateList,","))
                .newUserList(StringUtils.join(newUserList,","))
                .totalUserList(StringUtils.join(totalUserList,","))
                .build();
    }

    @Override
    public SalesTop10ReportVO getTop10(LocalDate begin, LocalDate end) {
        LocalDateTime start1 = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime end1 = LocalDateTime.of(end, LocalTime.MAX);

        List<GoodsSalesDTO> list = orderMapper.getSalesTop10(start1, end1);

        String nameList = StringUtils.join(list.stream().map(GoodsSalesDTO::getName).collect(Collectors.toList()),",");
        String numberList = StringUtils.join(list.stream().map(GoodsSalesDTO::getNumber).collect(Collectors.toList()),",");

        return SalesTop10ReportVO.builder()
                .nameList(nameList)
                .numberList(numberList)
                .build();
    }
}
