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

public class NurseDoctorLoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    public void login(ActionEvent event) {
        // Handle login logic here
    }

    public void forgotPassword(ActionEvent event) {
        // Handle forgot password logic here
    }
    
    public void goBack(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/application/role_selection.fxml"));
        stage.setScene(new Scene(root, 800, 600)); // Set the preferred size here as well
        stage.show();
    }
}

