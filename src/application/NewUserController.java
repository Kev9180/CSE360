package application;

import java.time.LocalDate;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class NewUserController {
	@FXML private TextField firstNameTF;
	@FXML private TextField lastNameTF;
	@FXML private DatePicker dateOfBirthPicker;
	@FXML private TextField phoneNumberTF;
	@FXML private TextField streetAddressTF;
	@FXML private TextField cityTF;
	@FXML private TextField stateTF;
	@FXML private TextField zipcodeTF;
	@FXML private TextField usernameTF;
	@FXML private PasswordField passwordPF;
	@FXML private PasswordField confirmPasswordPF;
	@FXML private TextField emailTF;
	@FXML private TextField confirmEmailTF;
	@FXML private TextField secQuestionTF;
	@FXML private TextField secAnswerTF;
	@FXML private Button clearFieldsBtn;
	
	@FXML
	public void initialize() {
		Platform.runLater(() -> clearFieldsBtn.requestFocus());
	}
	
	// Method to create a new account as well as a new User and Patient entity for the new patient
	public void createNewPatientAccount() throws Exception {
		if (!validateFields()) {
			return;
		}
		
		// Collect the form data from the textfields, pickers, and password fields
		String firstName = firstNameTF.getText().strip();
		String lastName = lastNameTF.getText().strip();
		LocalDate dateOfBirth = dateOfBirthPicker.getValue();
		String phoneNumber = phoneNumberTF.getText().strip();
		String streetAddress = streetAddressTF.getText().strip();
		String city = cityTF.getText().strip();
		String state = stateTF.getText().strip();
		String zipcode = zipcodeTF.getText().strip();
		String username = usernameTF.getText().strip();
		String password = passwordPF.getText().strip();
		String email = emailTF.getText().strip();
		String secQuestion = secQuestionTF.getText().strip();
		String secAnswer = secAnswerTF.getText().strip();
		Role patientRole = Role.PATIENT;
			
		DatabaseUtil.addUser(new Patient(username, password, patientRole, firstName, lastName, dateOfBirth, phoneNumber, streetAddress, city, state, zipcode, email, secQuestion, secAnswer));
		
		// Show a confirmation alert to let the user know their account has been created
		showAlert("Account Created!", "The new patient account was successfully created. Thank you!");
		
		//TODO: Print a confirmation to the console for testing purposes
		System.out.println("User account created for: " + username);
		
		//Take the patient back to the login screen
		SceneManager.loadScene(getClass(), "/FXML/role_selection.fxml", zipcodeTF);
	}
	
	// Method to verify that password matches confirmPassword, and email matches confirmEmail
	public boolean validateFields() {
		String username = usernameTF.getText().strip();
		String password = passwordPF.getText().strip();
		String confirmPW = confirmPasswordPF.getText().strip();
		String email = emailTF.getText().strip();
		String confirmEmail = confirmEmailTF.getText().strip();
		
		// Check for empty fields
	    if (firstNameTF.getText().isBlank() || lastNameTF.getText().isBlank() ||
	        dateOfBirthPicker.getValue() == null || phoneNumberTF.getText().isBlank() ||
	        streetAddressTF.getText().isBlank() || cityTF.getText().isBlank() ||
	        stateTF.getText().isBlank() || zipcodeTF.getText().isBlank() ||
	        usernameTF.getText().isBlank() || passwordPF.getText().isBlank() ||
	        confirmPasswordPF.getText().isBlank() || emailTF.getText().isBlank() ||
	        confirmEmailTF.getText().isBlank() || secQuestionTF.getText().isBlank() ||
	        secAnswerTF.getText().isBlank()) {

	        showAlert("Validation Error", "All fields must be filled. Please complete the form.");
	        return false;
	    }
		
	    // Check if passwords and emails match
		if (!password.equals(confirmPW)) {
			showAlert("Passwords do not match", "Please ensure that your passwords match.");
			return false;
		} else if (!email.equals(confirmEmail)) {
			showAlert("Emails do not match", "Please ensure that your emails match.");
			return false;
		}
		
		// Check if the username is taken
		if (DatabaseUtil.usernameExists(username)) {
			showAlert("Username Taken", "Please choose a different username.");
			return false;
		}
		
		// If all the checks pass, return true
		return true;
	}
	
	// Method to show an alert window with a specified prompt
	public void showAlert(String header, String content) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Validation Error");
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
	
	// Method to clear all of the text fields and date picker
	public void clearFields() {
		firstNameTF.setText("");
	    lastNameTF.setText("");
	    dateOfBirthPicker.setValue(null);
	    phoneNumberTF.setText("");
	    streetAddressTF.setText("");
	    cityTF.setText("");
	    stateTF.setText("");
	    zipcodeTF.setText("");
	    usernameTF.setText("");
	    passwordPF.setText("");
	    confirmPasswordPF.setText("");
	    emailTF.setText("");
	    confirmEmailTF.setText("");
	    secQuestionTF.setText("");
	    secAnswerTF.setText("");
	    
	    System.out.println("Cleared fields.");
	}
	
	
	// Take the user back to the patient login screen if they press the back button
    public void goBack(ActionEvent event) throws Exception {    	
    	SceneManager.loadScene(getClass(), "/FXML/role_selection.fxml", event);
    }
}
