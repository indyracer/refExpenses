package org.launchcode.refExpenses.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;



@Entity
@Table(name = "expenses")

public class Expenses extends AbstractEntity{
	
	private User user;
	private String date;
	private String expenseType;
	private double miles; //for mileage only
	private double amount;
	private String vendor; //store bought from, etc. not applicable to mileage
	private boolean haveReceipt;
	
	//no arg for hibernate
	public Expenses(){}
	
	public Expenses(User user, String date, String expenseType, double miles, double amount, String vendor, boolean haveReceipt){
		
		super();
		
		this.user = user;
		this.date = date;
		this.expenseType = expenseType;
		this.miles = miles;
		this.amount = amount;
		this.vendor = vendor;
		this.haveReceipt = haveReceipt;
	}
	
	@ManyToOne
	public User getUser(){
		return user;
	}
	
	public void setUser(User user){
		this.user = user;
	}
	
	@NotNull
	@Column(name = "date")
	public String getDate(){
		return date;
	}
	
	public void setDate(String date){
		this.date = date;
	}
	
	@NotNull
	@Column(name = "expenseType")
	public String getExpenseType(){
		return expenseType;
	}
	
	public void setExpenseType(String expenseType){
		this.expenseType = expenseType;
	}
	
	@Column(name = "miles")
	public double getMiles(){
		return miles;
	}
	
	public void setMiles (double miles){
		this.miles = miles;
	}
	
	
	@NotNull
	@Column(name = "amount")
	public double getAmount(){
		if(expenseType == "Mileage" || expenseType == "mileage"){
			return miles * user.getMileageAllowance();
		}
		
		return amount;
	}
	
	public void setAmount(double amount){
		this.amount = amount;
	}
	
	@Column(name = "vendor")
	public String getVendor(){
		return vendor;
	}
	
	public void setVendor(String vendor){
		this.vendor = vendor;
	}
	
	@Column(name = "haveReceipt")
	public boolean getHaveReceipt(){
		return haveReceipt;
	}
	
	public void setHaveReceipt(boolean haveReceipt){
		this.haveReceipt = haveReceipt;
	}

}
