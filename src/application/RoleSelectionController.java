package application;

import javafx.event.ActionEvent;

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

