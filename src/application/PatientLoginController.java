package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class PatientLoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label loginErrorLabel;
    @FXML private Button patientLoginBtn;
    
    @FXML
    public void initialize() {
    	Platform.runLater(() -> usernameField.requestFocus());
    }
    
    // Handle the login process for a patient
    public void login(ActionEvent event) throws Exception {
        UserManager userManager = UserManager.getInstance();
        boolean isValid = userManager.login(usernameField.getText(), passwordField.getText());
        
        // If login was successful, set the error label to false and proceed with login process
        if (isValid) {
        	loginErrorLabel.setVisible(false);
        	System.out.println("Login successful!");
        	
        	SceneManager.loadScene(getClass(), "/FXML/patient_view.fxml", event);      	
        } 
        
        // If login was unsuccessful, show the error label
        else {
        	loginErrorLabel.setText("Login unsuccessful");
        	loginErrorLabel.setVisible(true);
        	System.out.println("Login unsuccessful!");
        }
    }

    // Take the user to the reset password screen
    public void forgotPassword(ActionEvent event) throws Exception {
    	String fxmlFile = "/FXML/forgot_password.fxml";
    	SceneManager.loadScene(getClass(), fxmlFile, event);
    }

    // Take the user to the new user registration screen
    public void newUser(ActionEvent event) throws Exception {
        // Handle new user logic here    	
    	SceneManager.loadScene(getClass(), "/FXML/new_user.fxml", event);
    }
    
    // Method to log the user out of the system
    public void logout() {
    	UserManager userManager = UserManager.getInstance();
    	
    	// Get the current logged in user
    	User currentUser = userManager.getCurrentUser();
    	
    	// If currentUser is not null, log the user out
    	if (currentUser != null) {
    		System.out.println("Current user: " + currentUser.getUsername() + " logged out.");
    		userManager.logout();
    	} else {
    		System.out.println("No user currently logged in.");
    	}    	
    }
    
    // Take the user back to the role selection screen if they press the back button
    public void goBack(ActionEvent event) throws Exception {
    	logout();
    	
    	SceneManager.loadScene(getClass(), "/FXML/role_selection.fxml", event);
    }
   
}

