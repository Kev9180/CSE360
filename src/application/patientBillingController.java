package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class patientBillingController {

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
    private void mainButton(ActionEvent event) throws Exception {
        event.consume();
        System.out.println("main button");
        loadScene("/FXML/patient_view.fxml", event);
    }
    
    @FXML
    private void messageButton(ActionEvent event) throws Exception {
        event.consume();
        System.out.println("message button");
        loadScene("/FXML/pateint_meesage_board.fxml", event);
    }
    
    @FXML
    private void changePasswordButton(ActionEvent event) throws Exception {
        event.consume();
        System.out.println("billing button");
        loadScene("/FXML/forgot_password.fxml", event);
    }
    
    @FXML
    private void backButton(ActionEvent event) throws Exception {
        event.consume();
        loadScene("/FXML/patient_setting_view.fxml", event);
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
