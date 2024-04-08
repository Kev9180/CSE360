package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PatientViewController {

    @FXML private Button categoryAllButton;
    @FXML private Button categoryCurrentButton;
    
    //Method to logout the patient before going back to the previous screen
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
    private void messageButton(ActionEvent event) throws Exception {
        event.consume();
        System.out.println("message button");
        SceneManager.loadScene(getClass(), "/FXML/patient_message_board.fxml", event);
    }
    
    @FXML
    private void passwordButton(ActionEvent event) throws Exception {
        event.consume();
        System.out.println("message button");
        SceneManager.loadScene(getClass(), "/FXML/forgot_password.fxml", event);
    }
    
    @FXML
    private void settingButton(ActionEvent event) throws Exception {
        event.consume();
        System.out.println("setting button");
        SceneManager.loadScene(getClass(), "/FXML/patient_setting_view.fxml", event);
    }
   
    @FXML
    private void newAppointmentButton(ActionEvent event) throws Exception {
        event.consume();
        System.out.println("new appointment button");
        SceneManager.loadScene(getClass(), "/FXML/new_appointment_form.fxml", event);
    }
    
    @FXML
    private void recordsButton(ActionEvent event) throws Exception {
        event.consume();
        System.out.println("records button");
        SceneManager.loadScene(getClass(), "/FXML/patient_appointment_view.fxml", event);
    }
    
    @FXML
    private void billingButton(ActionEvent event) throws Exception {
        event.consume();
        System.out.println("billing button");
        SceneManager.loadScene(getClass(), "/FXML/patient_billing.fxml", event);
    }
    
   
    
    @FXML
    private void appointmentButton(ActionEvent event) throws Exception {
        event.consume();
        System.out.println("appointment button");
        SceneManager.loadScene(getClass(), "/FXML/patient_appointment_view.fxml", event);
    }
    
    @FXML
    private void aboutUsButton(ActionEvent event) {
        event.consume();
        System.out.println("about us button");
    }
    
   
    
    //-------------------------------

}
