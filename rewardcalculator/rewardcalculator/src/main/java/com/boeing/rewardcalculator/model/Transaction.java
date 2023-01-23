package com.boeing.rewardcalculator.model;

public class Transaction {

	private String name;
	private String month;
    private double amount;

	public Transaction(String name, String month, double amount) {
		this.setName(name);
		this.month = month;
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String getMonth() {
		return month;
	}
	
	public void setMonth(String month) {
		this.month = month;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
