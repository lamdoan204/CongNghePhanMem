package com.Project.CongNghePhanMem.Entity;

public class RevenueStatistic {
	private int period; // Tuần, Tháng, Quý hoặc Năm
	private int year;
	private String kind;
    private double totalRevenue;

    public RevenueStatistic(int period,  int year,String kind,double totalRevenue) {
        this.period = period;
        this.year =year;
        this.kind = kind;
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

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

}
