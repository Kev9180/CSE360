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

public class PatientViewController {

    @FXML private Button categoryAllButton;
    @FXML private Button categoryCurrentButton;
    
    
	//Handle back button (goes home)
    public void previousScene(ActionEvent event) throws Exception {
        loadScene("/FXML/role_selection.fxml", event);
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
    private void appointmentButton(ActionEvent event) {
        event.consume();
        System.out.println("appointment button");
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
