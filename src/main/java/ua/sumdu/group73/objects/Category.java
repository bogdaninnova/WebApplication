package ua.sumdu.group73.objects;

public class Category {

	private int id;
	private int parentID;
	private int productID;
	private String name;
	
	public Category(int id, int parentID, int productID, String name) {
		setId(id);
		setParentID(parentID);
		setProductID(productID);
		setName(name);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getParentID() {
		return parentID;
	}
	public void setParentID(int parentID) {
		this.parentID = parentID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}
}
