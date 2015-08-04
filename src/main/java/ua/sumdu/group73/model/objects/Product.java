package ua.sumdu.group73.model.objects;

import java.util.Date;

public class Product {

    private int id;
    private int sellerID;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private int startPrice;
    private int buyoutPrice;
    private int currentPrice;
    private int currentBuyerID;
    private boolean isActive;
	
    
    public Product(int id, int sellerID, String name, String description,
    		Date startDate, Date endDate, int startPrice, int buyoutPrice,
    		int currentPrice, int currentBuyerID, boolean isActive) {
    	setId(id);
    	setSellerID(sellerID);
    	setName(name);
    	setDescription(description);
    	setStartDate(startDate);
    	setEndDate(endDate);
    	setStartPrice(startPrice);
    	setBuyoutPrice(buyoutPrice);
    	setCurrentPrice(currentPrice);
		setCurrentBuyerID(currentBuyerID);
		setActive(isActive);
    }


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getSellerID() {
		return sellerID;
	}


	public void setSellerID(int sellerID) {
		this.sellerID = sellerID;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public int getBuyoutPrice() {
		return buyoutPrice;
	}


	public void setBuyoutPrice(int buyoutPrice) {
		this.buyoutPrice = buyoutPrice;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getCurrentPrice() {
		return currentPrice;
	}


	public void setCurrentPrice(int currentPrice) {
		this.currentPrice = currentPrice;
	}


	public boolean isActive() {
		return isActive;
	}


	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}


	public int getCurrentBuyerID() {
		return currentBuyerID;
	}


	public void setCurrentBuyerID(int currentBuyerID) {
		this.currentBuyerID = currentBuyerID;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public int getStartPrice() {
		return startPrice;
	}


	public void setStartPrice(int startPrice) {
		this.startPrice = startPrice;
	}
    
}
