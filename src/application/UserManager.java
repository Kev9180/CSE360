package application;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
	private static UserManager instance;
	private Map<String, User> users = new HashMap<>();
	
	//Constructor
	public UserManager() {
		preloadUsers();	//TODO: Predefining sample users for testing purposes, remove later
	}
	
	//Get the UserManager instance
	public static UserManager getInstance() {
		if (instance == null) {
			instance = new UserManager();
		}
		
		return instance;
	}
	
	//Add a user to the map
	public void addUser(User user) {
		users.put(user.getUsername(), user);
	}
	
	//Retrieve a user by username
	public User getUser(String username) {
		return users.get(username);
	}
	
	//Validate user login
	public boolean validateLogin(String username, String password) {
		User user = getUser(username);
		
		if (user != null && user.getPassword().equals(password)) {
			return true;
		}
		
		return false;
	}
	
	//TODO: Preload a user for each role for testing purposes, remove later
	private void preloadUsers() {
		addUser(new User("patient1", "password", new Role("Patient")));
		addUser(new User("nurse1", "password", new Role("Nurse")));
		addUser(new User("doctor1", "password", new Role("Doctor")));
	}
	
}
