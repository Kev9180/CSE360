package application;

//The User class defines the attributes for Doctors, Nurses, and Patients
public class User {
	private String username;
	private String password;
	private Role role;
	protected String firstName;
	protected String lastName;
	
	//Constructor
	public User(String username, String password, Role role, String firstName, String lastName) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.firstName = firstName;
		this.lastName = lastName;
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
		
	//Get role
	public Role getRole() {
		return role;
	}
	
	//Set role
	public void setRole(Role role) {
		this.role = role;
	}
	
	//Get first name
	public String getFirstName() {
		return firstName;
	}
	
	//Set first name
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	//Get last name
	public String getLastName() {
		return lastName;
	}
	
	//Set last name
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	//Get full name
	public String getFullName() {
		return firstName + " " + lastName;
	}
	
}
