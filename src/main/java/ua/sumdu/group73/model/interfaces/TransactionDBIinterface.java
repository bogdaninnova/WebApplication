package ua.sumdu.group73.model.interfaces;

import java.util.Date;
import java.util.List;

import objects.Transaction;

public interface TransactionDBIinterface {

	public List<Integer> getSalersTransaction(int sellerID);
	
	public List<Integer> getBuyersTransaction(int buyerID);
	
	public Transaction getTransaction(int transactionID);
	
	public boolean addTransaction(int buyerID, int sellerID, int productID, int price, Date saleDate);
	
	
}
