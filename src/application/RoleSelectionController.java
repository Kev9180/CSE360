package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RoleSelectionController {

    public void handlePatient(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("patient_login.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void handleNurseDoctor(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("nurse_doctor_login.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}

