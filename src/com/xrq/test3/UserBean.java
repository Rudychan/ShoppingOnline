/*
 * 用户数据表的一个抽象
 * 根据数据表一一映射
 */
package com.xrq.test3;

public class UserBean {
	private String ProductName="";
	private String price="";
	private String number="";
	private String id="";
	
	
	public void setproductName(String productName)
	{
		this.ProductName=productName;
	}
	
	public void setPrice(String price)
	{
		this.price = price;
	}
	public void setNumber(String number)
	{
		this.number=number;
	}

	public String getProductName() {
		return ProductName;
	}

	public void setProductName(String productName) {
		ProductName = productName;
	}

	public String getPrice() {
		return price;
	}

	public String getNumber() {
		return number;
	}
	public String getID() {
		return id;
	}
	public void setID(String id)
	{
		this.id=id;
	}
	
}
