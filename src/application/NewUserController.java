package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
	
	//Method to create a new account as well as a new User and Patient entity for the new patient
	public void createNewPatientAccount() {
		
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
}
