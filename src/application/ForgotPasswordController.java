package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ForgotPasswordController {
	@FXML private TextField usernameTF;
	@FXML private PasswordField newPasswordPF;
	@FXML private PasswordField confirmPasswordPF;
	
	//Method to verify that password matches confirmPassword, and email matches confirmEmail
	public boolean validateFields() {
		String password = newPasswordPF.getText().strip();
		String confirmPW = confirmPasswordPF.getText().strip();
		
		//Check for empty fields
	    if (usernameTF.getText().isBlank() || newPasswordPF.getText().isBlank() ||
	        confirmPasswordPF.getText().isBlank()) {

	        showAlert("Validation Error", "All fields must be filled. Please complete the form.");
	        return false;
	    }
		
	    //Check if passwords and emails match
		if (!password.equals(confirmPW)) {
			showAlert("Passwords do not match", "Please ensure that your passwords match.");
			return false;
		}
		
		//If all the checks pass, return true
		return true;
	}
	
	//Method to show an alert window with a specified prompt
	public void showAlert(String header, String content) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Validation Error");
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
	
	//Method to reset the password for the specified user
	public void resetPassword() throws Exception {
		if (!validateFields()) {
			return;
		}
		
		String username = usernameTF.getText().strip();
		String newPassword = newPasswordPF.getText().strip();
		String currentPassword = DatabaseUtil.getCurrentPassword(username);
		
		//check if the new password is the same as the old one
		if (newPassword.equals(currentPassword)) {
			showAlert("Password Reset Error", "The new password cannot be the same as the old password. Please create a new password.");
			return;
		}
		
		//Attempt to perform the password reset
		boolean passwordReset = DatabaseUtil.updatePassword(username, newPassword);
		
		//If reset was performed successfully (matching username found in database)
		if (passwordReset) {
			showAlert("Password Reset", "Your password has been reset. Returning to the login screen.");
			System.out.println("Password reset for: " + username);
			SceneManager.loadScene(getClass(), "/FXML/patient_login.fxml", usernameTF);
		} else {
			showAlert("Password Reset Failed!", "Failed to reset your password. Please ensure you have entered the correct username, and try again.");
		}	
	}
	
	//Take the user back to the patient login screen if they press the back button
    public void goBack(ActionEvent event) throws Exception {    	
        SceneManager.loadScene(getClass(), "/FXML/role_selection.fxml", event);
    }
   
}
