package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class NewAppointmentFormController {

    @FXML private Button categoryAllButton;
    @FXML private Button categoryCurrentButton;
    
    
    public void logoutPatient() {
    	UserManager userManager = UserManager.getInstance();
    	
    	//Get the current logged in user
    	User currentUser = userManager.getCurrentUser();
    	
    	//If currentUser is not null, log the user out
    	if (currentUser != null) {
    		System.out.println("Current user: " + currentUser.getUsername() + " logged out.");
    		userManager.logout();
    	} else {
    		System.out.println("No user currently logged in.");
    	}
    }
    
	//Handle logout button 
    public void logout(ActionEvent event) throws Exception {
    	logoutPatient();
        SceneManager.loadScene(getClass(), "/FXML/role_selection.fxml", event);
    }
  
    @FXML
    private void finishButton(ActionEvent event) throws Exception {
        event.consume();
        SceneManager.loadScene(getClass(), "/FXML/patient_view.fxml", event);
    }
}
