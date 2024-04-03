package application;

import java.io.IOException;
import java.time.LocalDate;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
	
	//Method to create a new account as well as a new User and Patient entity for the new patient
	public void createNewPatientAccount() throws IOException {
		if (!validateFields()) {
			return;
		}
		
		//Collect the form data from the textfields, pickers, and password fields
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
		
		//Create a new Patient entity
		Patient newPatient = new Patient(username, password, patientRole, firstName, lastName, dateOfBirth, phoneNumber,
										 streetAddress, city, state, zipcode, email, secQuestion, secAnswer);
		
		//Add the patient to the list of Users
		UserManager.getInstance().addUser(newPatient);
		
		//Add the patient to the patient list
		PatientManager.getInstance().addPatient(newPatient);
		
		//Show a confirmation alert to let the user know their account has been created
		showAlert("Account Created!", "The new patient account was successfully created. Thank you!");
		
		//TODO: Print a confirmation to the console for testing purposes
		System.out.println("User account created for: " + newPatient.getRole() + " " + newPatient.getName());
		
		//Take the patient back to the login screen
		goBackToLogin();
	}
	
	//Method to verify that password matches confirmPassword, and email matches confirmEmail
	public boolean validateFields() {
		String password = passwordPF.getText().strip();
		String confirmPW = confirmPasswordPF.getText().strip();
		String email = emailTF.getText().strip();
		String confirmEmail = confirmEmailTF.getText().strip();
		
		//Check for empty fields
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
		
	    //Check if passwords and emails match
		if (!password.equals(confirmPW)) {
			showAlert("Passwords do not match", "Please ensure that your passwords match.");
			return false;
		} else if (!email.equals(confirmEmail)) {
			showAlert("Emails do not match", "Please ensure that your emails match.");
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
	
	//Method to clear all of the text fields and date picker
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
	
	
	//Take the user back to the patient login screen if they press the back button
    public void goBack(ActionEvent event) throws Exception {
    	System.out.println("Back button pressed.");
    	
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/patient_login.fxml"));
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }
    
    //Take the user back to the patient login screen
    public void goBackToLogin() throws IOException {
    	Stage stage = (Stage) firstNameTF.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/patient_login.fxml"));
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }
}