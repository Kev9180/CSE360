package application;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
//Controller class for each individual patient entry in the patient list view
public class PatientListItemController {
	// FXML annotations to inject UI elements from the FXML file
    @FXML private Label dateLabel; // Label displaying the last visit date of the patient
    @FXML private Label nameLabel; // Label displaying the name of the patient
    @FXML private Label dobLabel; // Label displaying the date of birth of the patient
    @FXML private StackPane patientEntry; // StackPane containing the patient entry
    
    private Patient patient;
    private PatientListItemListener parentController;
    // Method to set the labels of the UI elements based on the provided patient information
    public void setLabels(Patient patient) {
    	this.patient = patient;
	    
	    // set patient visit date
	    List<Visit> visitHistory = patient.getVisitHistory();
	    
	    String dateStr = "Never";
	    if (visitHistory.size() > 0) {
	    	LocalDate date = visitHistory.get(visitHistory.size() - 1).getVisitDate();
	    	dateStr = date.toString();
	    }
	    
    	dateLabel.setText(dateStr);
    	
    	// set patient name 
    	nameLabel.setText(patient.getName());
    	
    	// set patient DOB
    	dobLabel.setText(patient.getDOB().toString());
    }
 // Method to retrieve the associated patient
    public Patient getPatient() {
    	return patient;
    }
    
 // Method to handle the action of clicking the message button
    @FXML
    public void handleMessageButtonClick() {
    	if (parentController != null)
    		parentController.onMessageButtonClick(patient);
    	System.out.println("Message Button Clicked");
    }
    
 // Method to handle the action of clicking the view info button
    @FXML
    public void handleViewInfoButtonClick() {
    	if (parentController != null)
    		parentController.onViewInfoButtonClick(patient);
    	System.out.println("Patient Info Edit Clicked");
    }
    
 // Method to handle the action of clicking the patient entry
    @FXML
    public void handleListItemClick() {
    	if (parentController != null)
    		parentController.onListItemClick(patient);
    	System.out.println("Item Clicked");
    }
    
 // Method to handle mouse entering the patient entry
    @FXML
    void handleMouseEnter(MouseEvent event) {
    	patientEntry.getStyleClass().add("hover");
    }

 // Method to handle mouse exiting the patient entry
    @FXML
    void handleMouseExit(MouseEvent event) {
    	patientEntry.getStyleClass().remove("hover");
    	patientEntry.getStyleClass().remove("pressed");
    }

 // Method to handle mouse pressing on the patient entry
    @FXML
    void handleMousePress(MouseEvent event) {
    	patientEntry.getStyleClass().add("pressed");
    }
    
    // pass parent controller to nurse/doctor patient list view
    public void setParentController(PatientListItemListener parentController) {
        this.parentController = parentController;
    }
}
