package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PatientLoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    public void login(ActionEvent event) {
        UserManager userManager = UserManager.getInstance();
        boolean isValid = userManager.validateLogin(usernameField.getText(), passwordField.getText());
        
        //If login was validated successfully, proceed to the Patient Portal
        if (isValid) {
        	System.out.println("Login successful!");
        } else {
        	System.out.println("Login unsuccessful!");
        }
    }

    public void forgotPassword(ActionEvent event) {
        // Handle forgot password logic here
    	System.out.println("Forgot password button pressed.");
    }

    public void newUser(ActionEvent event) {
        // Handle new user logic here
    	System.out.println("New user button pressed.");
    }
    
    public void goBack(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/application/role_selection.fxml"));
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }
}

