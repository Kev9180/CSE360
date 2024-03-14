package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RoleSelectionController {
	
	//Handle patient login
    public void handlePatient(ActionEvent event) throws Exception {
        loadScene("patient_login.fxml", event);
    }
    
    //Handle nurse or doctor login
    public void handleNurseDoctor(ActionEvent event) throws Exception {
        loadScene("nurse_doctor_login.fxml", event);
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

