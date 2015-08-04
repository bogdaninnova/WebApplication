package ua.sumdu.group73.model.objects;

public class Category {

	private int id;
	private int parentID;
	private String name;
	
	public Category(int id, int parentID, String name) {
		setId(id);
		setParentID(parentID);
		setName(name);
	}
	
	public Category(int id, String name) {
		setId(id);
		setParentID(0);
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

}
