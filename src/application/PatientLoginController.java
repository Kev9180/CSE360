package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PatientLoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label loginErrorLabel;
    @FXML private Button patientLoginBtn;
    
    @FXML
    public void initialize() {
    	Platform.runLater(() -> patientLoginBtn.requestFocus());
    }
    
    //Handle the login process for a patient
    public void login(ActionEvent event) throws Exception {
        UserManager userManager = UserManager.getInstance();
        boolean isValid = userManager.login(usernameField.getText(), passwordField.getText());
        
        //If login was validated successfully, proceed to the Patient Portal
        if (isValid) {
        	loginErrorLabel.setVisible(false);
        	System.out.println("Login successful!");
        	
        	loadScene("/FXML/patient_view.fxml", event);
        	
        } 
        
        //If login was unsuccessful, show the loginErrorLabel
        else {
        	loginErrorLabel.setText("Login unsuccessful");
        	loginErrorLabel.setVisible(true);
        	System.out.println("Login unsuccessful!");
        }
    }

    //Take the user to the reset password screen
    public void forgotPassword(ActionEvent event) {
        // Handle forgot password logic here
    	System.out.println("Forgot password button pressed.");
    }

    //Take the user to the new user registration screen
    public void newUser(ActionEvent event) throws Exception {
        // Handle new user logic here
    	System.out.println("New user button pressed.");
    	
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	Parent root = FXMLLoader.load(getClass().getResource("/FXML/new_user.fxml"));
    	stage.setScene(new Scene(root, 800, 600));
    	stage.show();
    }
    
    //Take the user back to the role selection screen if they press the back button
    public void goBack(ActionEvent event) throws Exception {
    	UserManager userManager = UserManager.getInstance();
    	
    	//Get the current logged in user
    	User currentUser = userManager.getCurrentUser();
    	
    	//If currentUser is not null, log the user out
    	if (currentUser != null) {
    		System.out.println("Current user: " + currentUser.getUsername() + " logged out.");
    		userManager.logout();
    	} else {
    		System.out.println("No user currently logged in.");
    	}    	
    	
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/role_selection.fxml"));
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }
    private void loadScene(String fxmlFile, ActionEvent event) throws Exception {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
    	Parent root = loader.load();
    	loader.getController();
    	stage.setScene(new Scene(root, 800, 600));
    	stage.show();
    }
}

