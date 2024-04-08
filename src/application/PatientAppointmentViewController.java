package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PatientAppointmentViewController {

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
    
	//Handle back button (goes home)
    public void previousScene(ActionEvent event) throws Exception {
    	SceneManager.loadScene(getClass(), "/FXML/patient_view.fxml", event);
    }
    
	//Handle logout button 
    public void logout(ActionEvent event) throws Exception {
        SceneManager.loadScene(getClass(), "/FXML/role_selection.fxml", event);
    }
    
    @FXML
    private void messageButton(ActionEvent event) throws Exception {
        event.consume();
        System.out.println("message button");
        SceneManager.loadScene(getClass(), "/FXML/pateint_meesage_board.fxml", event);
    }
    
    @FXML
    private void newAppointmentButton(ActionEvent event) throws Exception {
        event.consume();
        System.out.println("message button");
        SceneManager.loadScene(getClass(), "/FXML/new_appointment_form.fxml", event);
    }
    
    @FXML
    private void mainButton(ActionEvent event) throws Exception {
        event.consume();
        System.out.println("main button");
        SceneManager.loadScene(getClass(), "/FXML/patient_view.fxml", event);
    }
    
    @FXML
    private void aboutUsButton(ActionEvent event) {
        event.consume();
        System.out.println("about us button");
    }
    
    @FXML
    private void refillButton(ActionEvent event) {
        event.consume();
        System.out.println("refill button");
    }
    
    @FXML
    private void settingButton(ActionEvent event) throws Exception {
        event.consume();
        System.out.println("setting button");
        SceneManager.loadScene(getClass(), "/FXML/patient_setting_view.fxml", event);
    }
}
