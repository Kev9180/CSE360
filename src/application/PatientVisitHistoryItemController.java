package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class PatientVisitHistoryItemController {

    @FXML private Label concernsLabel;
    @FXML private Label dateLabel;
    @FXML private Label examLabel;
    @FXML private Label prescriptionLabel; 
    @FXML private StackPane visitEntry;
    
    private Visit visit;
    private Patient patient;
    private PatientVisitHistoryItemListener parentController;
    
    public void setLabels(Visit visit) {
    	this.visit = visit;
    	
    	String dateStr = visit.getVisitDate() == null ? "Never" : visit.getVisitDate().toString();
    	dateLabel.setText(dateStr);
    	
    	String examStr = visit.getPhysicalExamNotes() == "" ? "N/A" : visit.getPhysicalExamNotes();
    	examLabel.setText(examStr);
    	
    	String concernsStr = visit.getHealthConcerns() == "" ? "N/A" : visit.getHealthConcerns();
    	concernsLabel.setText(concernsStr);
    	
    	String prescriptionStr = (visit.getPrescribedMedication() == null || visit.getPrescribedMedication().isEmpty()) ? "None" : visit.getPrescribedMedication().getLast();
    	prescriptionLabel.setText(prescriptionStr);
    }
    
    public void setPatient(Patient patient) {
    	this.patient = patient;
    }
    
    @FXML
    void handleListItemClick(MouseEvent event) {
    	visitEntry.getStyleClass().add("hover");
    }

    @FXML
    void handleMouseEnter(MouseEvent event) {
    	visitEntry.getStyleClass().remove("hover");
    	visitEntry.getStyleClass().remove("pressed");
    }

    @FXML
    void handleMouseExit(MouseEvent event) {
    	visitEntry.getStyleClass().add("pressed");
    }

    @FXML
    void handleMousePress(MouseEvent event) {
    	if (parentController != null)
    		parentController.onItemClick(patient, visit);
    }
    
    // pass parent controller to nurse/doctor patient list view
    public void setParentController(PatientVisitHistoryItemListener parentController) {
        this.parentController = parentController;
    }

}
