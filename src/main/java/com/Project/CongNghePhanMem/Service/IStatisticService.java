package com.Project.CongNghePhanMem.Service;

import java.util.List;

import com.Project.CongNghePhanMem.Entity.RevenueStatistic;
import com.Project.CongNghePhanMem.dto.StockReport;

public interface IStatisticService {
	public List<RevenueStatistic> getRevenueByMonthAndProduct(int brandId);
    
    List<StockReport> getStockReport(int brandId);
    

}
