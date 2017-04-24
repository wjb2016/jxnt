package com.jxlt.stage.model;

import java.math.BigDecimal;

public class PayVM extends Pay {
	/*
	 * 统计销售名称
	 */
	private String name;
    /*
     * 订单销售量
     */
	private int orderCount;
	/*
	 * 系统保养销售量
	 */
	private int systemCount;
	/*
	 * 设备保养销售量
	 */
	private int deviceCount;
	/*
	 * 合同延保销售量
	 */
	private int acppCount;
	/*
	 * 订单总数
	 */
	private int allCount;
	/*
	 * 支付总次数
	 */
	private int payCount;
	/*
	 * 支付总金额
	 */
	private double payAmount;
	/*
	 * 最终评价
	 */
	private double assess;
	/*
	 * 评价总数
	 */
	private int assessCount;
	/*
	 * 评价总分值
	 */
	private int assessValue;
	/*
	 * 订单销售额
	 */
	private double orderAmount;
	/*
	 * 系统保养销售额
	 */
	private double systemAmount;
	/*
	 * 设备保养销售额
	 */
	private double deviceAmount;
	/*
	 * 合同延保销售额
	 */
	private double acppAmount;
	/*
	 * 订单总销售额
	 */
	private double allAmount;
	
	public double getAssess() {		
		double t = 0.0;
		if(assessCount > 0)
	    	t = assessValue/(double) assessCount;
		BigDecimal b = new BigDecimal(t);  
		double assess = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		return assess;
	}

	public void setAssess(double assess) {
		this.assess = assess;
	}

	
	public int getAcppCount() {
		return acppCount;
	}

	public void setAcppCount(int acppCount) {
		this.acppCount = acppCount;
	}

	public int getDeviceCount() {
		return deviceCount;
	}

	public void setDeviceCount(int deviceCount) {
		this.deviceCount = deviceCount;
	}

	public int getSystemCount() {
		return systemCount;
	}

	public void setSystemCount(int systemCount) {
		this.systemCount = systemCount;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	public int getAllCount() {
		return allCount;
	}

	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}

	public double getAllAmount() {
		BigDecimal b = new BigDecimal(allAmount);  
		double allAmount = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		return allAmount;
	}

	public void setAllAmount(double allAmount) {
		this.allAmount = allAmount;
	}

	public double getAcppAmount() {
		BigDecimal b = new BigDecimal(acppAmount);  
		double acppAmount = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		return acppAmount;
	}

	public void setAcppAmount(double acppAmount) {
		this.acppAmount = acppAmount;
	}

	public double getDeviceAmount() {
		BigDecimal b = new BigDecimal(deviceAmount);  
		double deviceAmount = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		return deviceAmount;
	}

	public void setDeviceAmount(double deviceAmount) {
		this.deviceAmount = deviceAmount;
	}

	public double getSystemAmount() {
		BigDecimal b = new BigDecimal(systemAmount);  
		double systemAmount = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		return systemAmount;
	}

	public void setSystemAmount(double systemAmount) {
		this.systemAmount = systemAmount;
	}

	public double getOrderAmount() {
		BigDecimal b = new BigDecimal(orderAmount);  
		double orderAmount = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
		return orderAmount;
	}

	public void setOrderAmount(double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public int getAssessCount() {
		return assessCount;
	}

	public void setAssessCount(int assessCount) {
		this.assessCount = assessCount;
	}

	public int getAssessValue() {
		return assessValue;
	}

	public void setAssessValue(int assessValue) {
		this.assessValue = assessValue;
	}

	public int getPayCount() {
		return payCount;
	}

	public void setPayCount(int payCount) {
		this.payCount = payCount;
	}

	public double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
