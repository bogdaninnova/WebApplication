package ua.sumdu.group73.model.interfaces;

import java.util.List;

public interface PicturesDBInterface {

	public List<String> getPicturesURLs(int productID);
	
	public boolean addPictures(int productID, String URL);
	
}
