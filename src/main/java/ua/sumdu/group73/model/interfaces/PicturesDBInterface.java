package ua.sumdu.group73.model.interfaces;

import java.util.List;

import ua.sumdu.group73.model.objects.Picture;

public interface PicturesDBInterface {

	public List<Picture> getPictures(int productID);
	
	public boolean addPicturesToProduct(int productID, List<String> URLs);
	
	public List<Picture> getAllPictures();
	
}
