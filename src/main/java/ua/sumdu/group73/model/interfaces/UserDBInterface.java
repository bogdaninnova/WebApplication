package ua.sumdu.group73.model.interfaces;

import java.util.List;

import ua.sumdu.group73.model.objects.*;

public interface UserDBInterface {

	/**
	 * Creating of a new User.
	 * @return false if login or email is not free or happened some error.
	 * */
	public boolean addUser(String login, String password, String name, String secondName,
			long birthDate, String eMail, String phone);
	
	public User getUser(int id);
	
	public User getUser(String login);
	
	public boolean isLoginFree(String login);
	
	public boolean isEmailFree(String newEmail);
	
	public User authorization(String login, String password);

	public User authorizationByEmail(String eMail, String password);
	
	public boolean isAdmin(int id);
	
	public List<Product> getUsersProducts(int userID);
	
	public List<User> getAllUsers();
		
	public boolean setUserBan(List<Integer> usersID);
	
	public boolean unBanAllUsers();
	
	public boolean activateUser(String login);
	
	public boolean removeUnactivatedUsers();
	
	public boolean changePassword(int userID, String oldPassword, String newPassword);
	
	public boolean changeDate(int userID, String name, String secondName, long birthDate, String phone);
	
	public boolean changeEMail(int userID, String newEmail);
}
