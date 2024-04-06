package application;
import java.time.LocalDate;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class PatientListItemController {
    @FXML private Label dateLabel;
    @FXML private Label nameLabel;
    @FXML private Label dobLabel;
    @FXML private StackPane patientEntry;
    
    private Patient patient;
    private PatientListItemListener parentController;
    
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
    
    public Patient getPatient() {
    	return patient;
    }
    
    @FXML
    public void handleMessageButtonClick() {
    	if (parentController != null)
    		parentController.onMessageButtonClick(patient);
    	System.out.println("Message Button Clicked");
    }
    
    @FXML
    public void handleViewInfoButtonClick() {
    	if (parentController != null)
    		parentController.onViewInfoButtonClick(patient);
    	System.out.println("Patient Info Edit Clicked");
    }
    
    @FXML
    public void handleListItemClick() {
    	if (parentController != null)
    		parentController.onListItemClick(patient);
    	System.out.println("Item Clicked");
    }
    
    @FXML
    void handleMouseEnter(MouseEvent event) {
    	patientEntry.getStyleClass().add("hover");
    }

    @FXML
    void handleMouseExit(MouseEvent event) {
    	patientEntry.getStyleClass().remove("hover");
    	patientEntry.getStyleClass().remove("pressed");
    }

    @FXML
    void handleMousePress(MouseEvent event) {
    	patientEntry.getStyleClass().add("pressed");
    }
    
    // pass parent controller to nurse/doctor patient list view
    public void setParentController(PatientListItemListener parentController) {
        this.parentController = parentController;
    }
}
