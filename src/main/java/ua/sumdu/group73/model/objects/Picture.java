package ua.sumdu.group73.model.objects;

public class Picture {

	private int productID;
	private String URL;
	
	public Picture(int productID, String URL) {
		this.setProductID(productID);
		this.setURL(URL);
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}
	
}
