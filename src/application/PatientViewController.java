package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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
        loadScene("/FXML/role_selection.fxml", event);
    }
    
    @FXML
    private void messageButton(ActionEvent event) throws Exception {
        event.consume();
        System.out.println("message button");
        loadScene("/FXML/pateint_meesage_board.fxml", event);
    }
    
    @FXML
    private void newAppointmentButton(ActionEvent event) {
        event.consume();
        System.out.println("new appointment button");
    }
    
    @FXML
    private void settingButton(ActionEvent event) {
        event.consume();
        System.out.println("setting button");
    }
    
    @FXML
    private void comingAppointmentButton(ActionEvent event) {
        event.consume();
        System.out.println("coming appointment button");
    }
    
    @FXML
    private void recordsButton(ActionEvent event) {
        event.consume();
        System.out.println("records button");
    }
    
    @FXML
    private void billingButton(ActionEvent event) {
        event.consume();
        System.out.println("billing button");
    }
    
    @FXML
    private void orderRefillButton(ActionEvent event) {
        event.consume();
        System.out.println("order refill button");
    }
    
    @FXML
    private void notificationButton(ActionEvent event) {
        event.consume();
        System.out.println("notifications button");
    }
    
    @FXML
    private void appointmentButton(ActionEvent event) throws Exception {
        event.consume();
        System.out.println("appointment button");
        loadScene("/FXML/patient_appointment_view.fxml", event);
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
    
    //-------------------------------
    
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
