/*
 * �û����ݱ��һ������
 * �������ݱ�һһӳ��
 */
package com.xrq.test3;

public class UserBean {
	private String ProductName="";
	private String price="";
	private String number="";
	
	
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
	
}
