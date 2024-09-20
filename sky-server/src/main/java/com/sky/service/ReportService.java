package com.sky.service;

import com.sky.vo.*;

import java.time.LocalDate;

public interface ReportService {

    TurnoverReportVO getTurnover(LocalDate begin, LocalDate end);

    OrderReportVO getOrder(LocalDate begin, LocalDate end);

    UserReportVO getUser(LocalDate begin, LocalDate end);

    SalesTop10ReportVO getTop10(LocalDate begin, LocalDate end);
}
