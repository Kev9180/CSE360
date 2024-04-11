package application;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PatientAppointmentViewController {
	@FXML private Pane screenContainer;
	@FXML private Button backBtn;
	@FXML private Button newAppointmentBtn;
	@FXML private ComboBox<LocalDateTime> visitDateCB;
	@FXML private VBox visitDisplayBox;
	@FXML private TextField heightTF;
	@FXML private TextField weightTF;
	@FXML private TextField tempTF;
	@FXML private TextField bloodPressureTF;
	@FXML private TextField immunizationsTF;
	@FXML private TextField allergiesTF;
	@FXML private TextField medicationsTF;
	@FXML private TextField healthConcernsTF;
	@FXML private TextField dosageTF;
	@FXML private TextField medicationNotesTF;
	@FXML private TextField physicalExamNotesTF;
	@FXML private TextField visitDateTF;
	
	private Patient patient;
	
	// Update the patient, set the table column value, populate table, and make the rows clickable
	public void initialize() {
		User currentUser = UserManager.getInstance().getCurrentUser();
		String username = currentUser.getUsername();
		
		List<Patient> patientList = DatabaseUtil.getPatients();
		
		// Find the patient in the patientList
		for (Patient patient : patientList) {
			if (username.equals(patient.getUsername()))
				this.patient = patient;
		}
		
		populateVisitDates();
		setupComboBoxListener();
		setupComboBoxDisplay();
	}
	
	// Populate all of the patient's visit dates into the combo box
	private void populateVisitDates() {
		String username = patient.getUsername();
		List<Visit> visits = VisitHistoryManager.getVisitsForPatient(username);
		
		for (Visit visit : visits) {
			visitDateCB.getItems().add(visit.getVisitDateFormatted());
		}
	}
	
	// Setup the listener for the combo box to update info when user selects a different date from the combo box
	private void setupComboBoxListener() {
		visitDateCB.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                loadVisitDetails(newValue); //newValue is already a LocalDateTime
            }
        });	
	}
	
	// Setup the display to display a readable date format instead of the regular LocalDateTime
	private void setupComboBoxDisplay() {
        visitDateCB.setCellFactory(cell -> new ListCell<LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))); //Customize as needed
                }
            }
        });

        visitDateCB.setButtonCell(new ListCell<LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))); //Ensure button cell matches list cells
                }
            }
        });
    }
	
	// Load the specifics of the visit into each of the corresponding textfields 
	private void loadVisitDetails(LocalDateTime dateTime) {
		List<Visit> visits = VisitHistoryManager.getVisitsForPatient(patient.getUsername());
		
		// Iterate through the visits in the list to find the matching appointment
	    for (Visit visit : visits) {
	    	// If the LocalDateTime values match, update the display values
	        if (visit.getVisitDateFormatted().equals(dateTime)) {
	            heightTF.setText(visit.getHeight());
	            weightTF.setText(visit.getWeight());
	            tempTF.setText(visit.getTemperature());
	            bloodPressureTF.setText(visit.getBloodPressure());
	            immunizationsTF.setText(String.join(", ", visit.getImmunizations()));
	            allergiesTF.setText(String.join(", ", visit.getAllergies()));
	            medicationsTF.setText(String.join(", ", visit.getPrescribedMedication()));
	            healthConcernsTF.setText(visit.getHealthConcerns());
	            dosageTF.setText(String.join(", ", visit.getDosages()));
	            medicationNotesTF.setText(visit.getMedicationNotes());
	            physicalExamNotesTF.setText(visit.getPhysicalExamNotes());
	            visitDateTF.setText(dateTime.toString());
	            break;
	        }
	    }
	    
	    // Set all of the fields to non-editable
	    heightTF.setEditable(false);
        weightTF.setEditable(false);
        tempTF.setEditable(false);
        bloodPressureTF.setEditable(false);
        immunizationsTF.setEditable(false);
        allergiesTF.setEditable(false);
        medicationsTF.setEditable(false);
        healthConcernsTF.setEditable(false);
        dosageTF.setEditable(false);
        medicationNotesTF.setEditable(false);
        physicalExamNotesTF.setEditable(false);
        visitDateTF.setEditable(false);
	}
	
	// Method to go back to the patient view screen
	@FXML
	public void goBack(ActionEvent event) throws Exception {
		String fxmlFile = "/FXML/patient_view.fxml";
		SceneManager.loadScene(getClass(), fxmlFile, event);
	}
	
	// Displays a new appointment form, clears the current content, then adds the loaded form to the container
	@FXML
    private void newAppointmentScreen(ActionEvent event) throws Exception {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/new_appointment_form.fxml"));
    		Parent settingsRoot = loader.load();
    		
    		screenContainer.getChildren().clear();
    		screenContainer.getChildren().add(settingsRoot);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
	
	
	
	
}
