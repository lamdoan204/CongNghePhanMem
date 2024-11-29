package com.Project.CongNghePhanMem.Service;

import java.util.List;

import com.Project.CongNghePhanMem.Entity.RevenueStatistic;

public interface IStatisticService {
	List<RevenueStatistic> getRevenueByWeek(int brandId);
    List<RevenueStatistic> getRevenueByMonth(int brandId);
    List<RevenueStatistic> getRevenueByQuarter(int brandId);
    List<RevenueStatistic> getRevenueByYear(int brandId);
    void testRevenueQueries(int brandId);

}
