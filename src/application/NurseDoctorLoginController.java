package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NurseDoctorLoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label loginErrorLabel;
    
    //Handle the login validation process for doctors and nurses
    public void login(ActionEvent event) throws Exception {
        UserManager userManager = UserManager.getInstance();
        boolean isValid = userManager.login(usernameField.getText(), passwordField.getText());
        
        //If login was successful, set the error label to false and proceed with login process
        if (isValid) {
        	loginErrorLabel.setVisible(false);
        	
        	loadScene("/FXML/nurse_patient_list.fxml", event);
        } 
        
        //If login was unsuccessful, show the error label
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
    
    //Take the user back to the role selection screen if they press the back button
    public void goBack(ActionEvent event) throws Exception {
    	UserManager userManager = UserManager.getInstance();
    	
    	User currentUser = userManager.getCurrentUser();
    	
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
    
    //Method to load the scene
    private void loadScene(String fxmlFile, ActionEvent event) throws Exception {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
    	Parent root = loader.load();
    	loader.getController();
    	stage.setScene(new Scene(root, 800, 600));
    	stage.show();
    }
}

