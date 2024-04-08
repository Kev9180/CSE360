package application;

import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;

public class newAppointmentFormController {

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
    	loadScene("/FXML/patient_view.fxml", event);
    }
    
	//Handle logout button 
    public void logout(ActionEvent event) throws Exception {
        loadScene("/FXML/role_selection.fxml", event);
    }
    
    @FXML
    private void messageButton(ActionEvent event) throws Exception {
        event.consume();
        System.out.println("message button");
        loadScene("/FXML/pateint_meesage_board.fxml", event);
    }
    
    @FXML
    private void mainButton(ActionEvent event) throws Exception {
        event.consume();
        System.out.println("main button");
        loadScene("/FXML/patient_view.fxml", event);
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
        loadScene("/FXML/patient_setting_view.fxml", event);
    }
    
    @FXML
    private void finishButton(ActionEvent event) throws Exception {
        event.consume();
        System.out.println("setting button");
        loadScene("/FXML/patient_appointment_view.fxml", event);
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
