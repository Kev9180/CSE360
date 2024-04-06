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
        SceneManager.loadScene(getClass(), "/FXML/patient_login.fxml", event);
    }
    
    //Handle nurse or doctor login
    public void handleNurseDoctor(ActionEvent event) throws Exception {
        SceneManager.loadScene(getClass(), "/FXML/nurse_doctor_login.fxml", event);
    }
}

