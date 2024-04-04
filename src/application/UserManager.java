package application;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/*	The UserManager class is a singleton and it's primary role is to manage user data
 * 	and interactions related to user authentication and session management across the 
 * 	application. 
 */
public class UserManager {
	private static UserManager instance;
	private Map<String, User> users = new HashMap<>();
	private User currentUser;
	
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
	
	//Get the current logged in user
	public User getCurrentUser() {
		return currentUser;
	}
	
	//Get the current logged in user's role
	public Role getCurrentUserRole() {
		if (currentUser != null) {
			return currentUser.getRole();
		}
		
		return null;
	}
	
	//Method to update the currentUser on successful logins
	public boolean login(String username, String password) {
		if (validateLogin(username, password)) {
			currentUser = getUser(username);
			return true;
		}
		
		return false;
	}
	
	//Validate user login
	public boolean validateLogin(String username, String password) {
		User user = getUser(username);
		
		if (user != null && user.getPassword().equals(password)) {
			return true;
		}
		
		return false;
	}
	
	//Method to log out the current user
	public void logout() {
		currentUser = null;
	}
	
	//TODO: Preload a user for each role for testing purposes, remove later
	private void preloadUsers() {
		addUser(new User("patient1", "password", Role.PATIENT));
		addUser(new User("nurse1", "password", Role.NURSE));
		addUser(new User("doctor1", "password", Role.DOCTOR));
		
		// instantiate the patient and give them visit info
		PatientManager myPatientManager = PatientManager.getInstance();
		Patient myPatient = new Patient("username", "password", Role.PATIENT, "first", "last", LocalDate.now(), "1234567890", "address", "city", "state", "00000", "email@example.com", "securityQuestion", "securityAnswer");
		Visit myVisit = new Visit(100, 100, 100, 100, null, null, null, "concerns", 10, "location", "examNotes", "medicationNotes");
		myPatient.addVisit(myVisit);
		myPatientManager.addPatient(myPatient);
	}
	
}
