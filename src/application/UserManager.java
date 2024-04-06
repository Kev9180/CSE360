package application;

/*	The UserManager class is a singleton and it's primary role is to manage user data
 * 	and interactions related to user authentication and session management across the 
 * 	application. 
 */
public class UserManager {
	private static UserManager instance;
	private User currentUser;
	
	//Constructor
	public UserManager() {};
	
	//Get the UserManager instance
	public static synchronized UserManager getInstance() {
		if (instance == null) {
			instance = new UserManager();
		}
		
		return instance;
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
		if (DatabaseUtil.checkCredentials(username, password)) {
			currentUser = DatabaseUtil.getUserByUsername(username);
			return true;
		}
		
		return false;
	}
	
	//Method to log out the current user
	public void logout() {
		currentUser = null;
	}	
}
