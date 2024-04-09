package application;

import java.time.LocalDate;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class PatientAppointmentViewController {
	@FXML private Button backBtn;
	@FXML private Button newAppointmentBtn;
	@FXML private ComboBox<LocalDate> visitDateCB;
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
		
		for (Patient patient : patientList) {
			if (username.equals(patient.getUsername()))
				this.patient = patient;
		}
		
		populateVisitDates();
		setupComboBoxListener();
	}
	
	// Populate all of the patient's visit dates into the combo box
	private void populateVisitDates() {
		String username = patient.getUsername();
		List<Visit> visits = VisitHistoryManager.getVisitsForPatient(username);
		
		for (Visit visit : visits) {
			visitDateCB.getItems().add(visit.getVisitDate());
		}
	}
	
	// Setup the listener for the combo box to update info when user selects a different date from the combo box
	private void setupComboBoxListener() {
		visitDateCB.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<LocalDate>() {
			@Override
	        public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
	            if (newValue != null) {
	                // When a new date is selected, load the visit details
	                loadVisitDetails(newValue);
	            }
	        }
		});
	}
	
	//
	private void loadVisitDetails(LocalDate date) {
		List<Visit> visits = VisitHistoryManager.getVisitsForPatient(patient.getUsername());
	    for (Visit visit : visits) {
	        if (visit.getVisitDate().equals(date)) {
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
	            visitDateTF.setText(date.toString());
	            break;
	        }
	    }
	    
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
	
	@FXML	// Method to go back to the patient view screen
	public void goBack(ActionEvent event) throws Exception {
		String fxmlFile = "/FXML/patient_view.fxml";
		SceneManager.loadScene(getClass(), fxmlFile, event);
	}
	
	
	
	
}
