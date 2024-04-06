package application;

//The User class defines the attributes for Doctors, Nurses, and Patients
public class User {
	private String username;
	private String password;
	private MessageBoard messageBoard;
	private Role role;
	
	//Constructor
	public User(String username, String password, Role role) {
		this.username = username;
		this.password = password;
		this.messageBoard = new MessageBoard(this);
		this.role = role;
	}
	
	//Get username
	public String getUsername() {
		return username;
	}
	
	//Set username
	public void setUsername(String username) {
		this.username = username;
	}
	
	//Get password
	public String getPassword() {
		return password;
	}
	
	//Set password
	public void setPassword(String password) {
		this.password = password;
	}
	
	//Get MessageBoard
	public MessageBoard getMessageBoard() {
		return this.messageBoard;
	}
	
	//Get role
	public Role getRole() {
		return role;
	}
	
	//Set role
	public void setRole(Role role) {
		this.role = role;
	}
	
	
}
