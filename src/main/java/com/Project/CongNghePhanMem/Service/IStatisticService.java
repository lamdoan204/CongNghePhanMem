package com.Project.CongNghePhanMem.Service;

import java.util.List;

import com.Project.CongNghePhanMem.Entity.RevenueStatistic;

public interface IStatisticService {
	List<RevenueStatistic> getRevenueByWeekAndKind(int brandId);
    List<RevenueStatistic> getRevenueByMonthAndKind(int brandId);
    List<RevenueStatistic> getRevenueByQuarterAndKind(int brandId);
    List<RevenueStatistic> getRevenueByYearAndKind(int brandId);
    

}
