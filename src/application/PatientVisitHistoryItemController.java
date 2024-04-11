package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

// Controller class for each individual visit entry in the patient's visit history
public class PatientVisitHistoryItemController {

    // FXML annotations to inject UI elements from the FXML file
    @FXML private Label concernsLabel; // Label displaying health concerns noted during the visit
    @FXML private Label dateLabel; // Label displaying the date of the visit
    @FXML private Label examLabel; // Label displaying notes from the physical examination during the visit
    @FXML private Label prescriptionLabel; // Label displaying the prescribed medication during the visit
    @FXML private StackPane visitEntry; // StackPane containing the visit entry
    
    private Visit visit; // Reference to the visit associated with this entry
    private Patient patient; // Reference to the patient associated with this visit
    private PatientVisitHistoryItemListener parentController; // Listener interface for handling actions on the visit entry
    
    // Method to set the labels of the UI elements based on the provided visit information
    public void setLabels(Visit visit) {
        this.visit = visit;
        
        // Set visit date
        String dateStr = visit.getVisitDate() == null ? "Never" : visit.getVisitDate().toString();
        dateLabel.setText(dateStr);
        
        // Set physical exam notes
        String examStr = visit.getPhysicalExamNotes() == "" ? "N/A" : visit.getPhysicalExamNotes();
        examLabel.setText(examStr);
        
        // Set health concerns
        String concernsStr = visit.getHealthConcerns() == "" ? "N/A" : visit.getHealthConcerns();
        concernsLabel.setText(concernsStr);
        
        // Set prescribed medication
        String prescriptionStr = (visit.getPrescribedMedication() == null || visit.getPrescribedMedication().isEmpty()) ? "None" : visit.getPrescribedMedication().getLast();
        prescriptionLabel.setText(prescriptionStr);
    }
    
    // Method to set the associated patient
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
    // Method to handle the action of clicking the visit entry
    @FXML
    void handleListItemClick(MouseEvent event) {
        visitEntry.getStyleClass().add("hover");
    }

    // Method to handle mouse entering the visit entry
    @FXML
    void handleMouseEnter(MouseEvent event) {
        visitEntry.getStyleClass().remove("hover");
        visitEntry.getStyleClass().remove("pressed");
    }

    // Method to handle mouse exiting the visit entry
    @FXML
    void handleMouseExit(MouseEvent event) {
        visitEntry.getStyleClass().add("pressed");
    }

    // Method to handle mouse pressing on the visit entry
    @FXML
    void handleMousePress(MouseEvent event) {
        if (parentController != null)
            parentController.onItemClick(patient, visit);
    }
    
    // Method to set the parent controller for handling actions on the visit entry
    public void setParentController(PatientVisitHistoryItemListener parentController) {
        this.parentController = parentController;
    }

}
