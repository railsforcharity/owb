package com.owb.playhelp.shared;

public class MoneyInfo extends ResourceInfo {

	private double amount;
	

	public double getAmount(){
		return this.amount;
	}

	public void setAmount(double amount){
		this.amount = amount;
	}
	
	@Override
	public String[] toStringArray(){
		String[] strResource = new String[4];

		strResource[0] = this.getName();
		strResource[1] = this.getDescription();
		strResource[2] = this.getStartDate().toString()+"-"+this.getEndDate().toString();
		strResource[3] = "$"+Double.toString(this.getAmount());
		
		return strResource;
	}
}
