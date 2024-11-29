package com.Project.CongNghePhanMem.Service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.CongNghePhanMem.Entity.RevenueStatistic;
import com.Project.CongNghePhanMem.Repository.OrderDetailRepository;
import com.Project.CongNghePhanMem.Service.IStatisticService;

@Service
public class StatisticService implements IStatisticService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<RevenueStatistic> getRevenueByWeek(int brandId) {
        List<Object[]> results = orderDetailRepository.findRevenueByWeek(brandId);
        return results.stream()
                .map(result -> new RevenueStatistic(
                        ((Number) result[0]).intValue(), // weekNumber
                        ((Number) result[1]).intValue(),
                        ((Number) result[2]).doubleValue() // totalRevenue
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<RevenueStatistic> getRevenueByMonth(int brandId) {
        List<Object[]> results = orderDetailRepository.findRevenueByMonth(brandId);
        return results.stream()
                .map(result -> new RevenueStatistic(
                        ((Number) result[0]).intValue(), // month
                        ((Number) result[1]).intValue(),
                        ((Number) result[2]).doubleValue() // totalRevenue
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<RevenueStatistic> getRevenueByQuarter(int brandId) {
        List<Object[]> results = orderDetailRepository.findRevenueByQuarter(brandId);
        return results.stream()
                .map(result -> new RevenueStatistic(
                        ((Number) result[0]).intValue(), // quarter
                        ((Number) result[1]).intValue(),
                        ((Number) result[2]).doubleValue() // totalRevenue
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<RevenueStatistic> getRevenueByYear(int brandId) {
        List<Object[]> results = orderDetailRepository.findRevenueByYear(brandId);
        return results.stream()
                .map(result -> new RevenueStatistic(
                        ((Number) result[0]).intValue(), // year
                        ((Number) result[0]).intValue(),
                        ((Number) result[1]).doubleValue() // totalRevenue
                ))
                .collect(Collectors.toList());
    }
  

    public void testRevenueQueries(int brandId) {
        // Test doanh thu theo quý
        List<Object[]> quarterlyRevenue = orderDetailRepository.findRevenueByQuarter(brandId);
        for (Object[] result : quarterlyRevenue) {
            System.out.println("Quarter: " + result[0] + ", Year: " + result[1] + ", Revenue: " + result[2]);
        }

        // Test doanh thu theo năm
        List<Object[]> yearlyRevenue = orderDetailRepository.findRevenueByYear(brandId);
        for (Object[] result : yearlyRevenue) {
            System.out.println("Year: " + result[0] + ", Revenue: " + result[1]);
        }
    }
}
