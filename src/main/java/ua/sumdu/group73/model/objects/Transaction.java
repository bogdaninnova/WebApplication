package ua.sumdu.group73.model.objects;

import java.util.Date;

public class Transaction {

	private int id;
	private int buyerID;
	private int sellerID;
	private int productID;
	private int price;
	private Date saleDate;
	
	
	public Transaction(int id, int buyerID, int sellerID, int productID, int price, Date saleDate) {
		setId(id);
		setBuyerID(buyerID);
		setSellerID(sellerID);
		setProductID(productID);
		setPrice(price);
		setSaleDate(saleDate);
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getBuyerID() {
		return buyerID;
	}


	public void setBuyerID(int buyerID) {
		this.buyerID = buyerID;
	}


	public int getProductID() {
		return productID;
	}


	public void setProductID(int productID) {
		this.productID = productID;
	}


	public int getSellerID() {
		return sellerID;
	}


	public void setSellerID(int sellerID) {
		this.sellerID = sellerID;
	}


	public Date getSaleDate() {
		return saleDate;
	}


	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}
	
}
