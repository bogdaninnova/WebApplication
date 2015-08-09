package ua.sumdu.group73.model.objects;

public class Picture {

	private int id;
	private int productID;
	private String URL;
	
	public Picture(int id, int productID, String URL) {
		this.setID(id);
		this.setProductID(productID);
		this.setURL(URL);
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
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
