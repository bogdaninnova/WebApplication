package ua.sumdu.group73.model.interfaces;

import java.util.List;

import ua.sumdu.group73.model.objects.*;

public interface FollowingDBInterface {


	public boolean followProduct(int followerID, int productID);
	
	public boolean isFollowProduct(int followerID, int productID);
	
	public boolean unfollowProduct(int followerID, int productID);
	
	/**
	 * Returns list of products ID, that followed by user.
	 * With this ID's we can restore objects "Product".
	 * */
	public List<Product> getFollowingProductsID(int userID);

}
