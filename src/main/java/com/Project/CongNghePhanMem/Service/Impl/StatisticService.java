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
    public List<RevenueStatistic> getRevenueByWeekAndKind(int brandId) {
        List<Object[]> results = orderDetailRepository.findRevenueByWeekAndKind(brandId);
        return results.stream()
                .map(result -> new RevenueStatistic(
                        ((Number) result[0]).intValue(), // weekNumber
                        ((Number) result[1]).intValue(),
                        (String) result[2],//kind
                        ((Number) result[3]).doubleValue() // totalRevenue
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<RevenueStatistic> getRevenueByMonthAndKind(int brandId) {
        List<Object[]> results = orderDetailRepository.findRevenueByMonthAndKind(brandId);
        return results.stream()
                .map(result -> new RevenueStatistic(
                        ((Number) result[0]).intValue(), // month
                        ((Number) result[1]).intValue(), // year
                        (String) result[2],             // kind
                        ((Number) result[3]).doubleValue() // totalRevenue
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<RevenueStatistic> getRevenueByQuarterAndKind(int brandId) {
        List<Object[]> results = orderDetailRepository.findRevenueByQuarterAndKind(brandId);
        return results.stream()
                .map(result -> new RevenueStatistic(
                        ((Number) result[0]).intValue(), // quarter
                        ((Number) result[1]).intValue(), // year
                        (String) result[2],             // kind
                        ((Number) result[3]).doubleValue() // totalRevenue
                ))
                .collect(Collectors.toList());
    }


    @Override
    public List<RevenueStatistic> getRevenueByYearAndKind(int brandId) {
        List<Object[]> results = orderDetailRepository.findRevenueByYearAndKind(brandId);
        return results.stream()
                .map(result -> new RevenueStatistic(
                        ((Number) result[0]).intValue(), // year
                        ((Number) result[0]).intValue(),                           // period is not applicable for yearly stats
                        (String) result[1],             // kind
                        ((Number) result[2]).doubleValue() // totalRevenue
                ))
                .collect(Collectors.toList());
    }

}
