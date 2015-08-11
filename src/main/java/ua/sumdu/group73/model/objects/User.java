package ua.sumdu.group73.model.objects;


public class User {
	
	  private int id;
	  
	  private String login;
	  private String password;
	  private String name;
	  private String secondName;
	  private int age;
	  private String eMail;
	  private String phone;
	  private boolean isAdmin;
		  
	  public User(int id, String login, String password, String name,
			  String secondName, int age, String eMail, String phone, String status) {
		  setId(id);
		  setLogin(login);
		  setPassword(password);
		  setName(name);
		  setSecondName(secondName);
		  setAge(age);
		  setPhone(phone);
		  setAdmin(status.equals("admin"));	//'user' && 'admin'
	  }

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
