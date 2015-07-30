package ua.sumdu.group73.model.interfaces;

import java.util.Date;
import java.util.List;

import objects.*;

public interface UserDBInterface {

	/**
	 * Creating of a new User.
	 * @return false if login or email is not free or happened some error.
	 * */
	public boolean addUser(String login, String password, String name, String secondName,
			Date birthDate, String eMail, String phone, boolean isAdmin);
	
	public User getUser(int id);
		
	public boolean isLoginFree(String login);
	
	public boolean isEmailFree(String login);
	
	
	/**
	 * Check login and password and do authorization.
	 * @return id of authorizated user. If login or password is wrong - method returns -1.
	 * 
	 * */
	public int authorization(String login, String password);
	
	public boolean isAdmin(int id);
	
	public List<Integer> getUsersProducts();
		
}
