package com.Project.CongNghePhanMem.Entity;

public class RevenueStatistic {
	private int period; // Tuần, Tháng, Quý hoặc Năm
	private int year;
    private double totalRevenue;

    public RevenueStatistic(int period,  int year,double totalRevenue) {
        this.period = period;
        this.year =year;
        this.totalRevenue = totalRevenue;
    }

    // Getters và Setters
    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

}
