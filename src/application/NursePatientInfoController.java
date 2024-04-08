package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class NursePatientInfoController {
	
	@FXML private Label name;
	@FXML private TextField bloodPressureTF;
	@FXML private TextField heightTF;
	@FXML private TextField tempTF;
	@FXML private TextField vaccineDateTF;
	@FXML private TextField vaccineNameTF;
	@FXML private TextField weightTF;
	@FXML private TextArea allergiesTA;
	@FXML private TextArea healthConcernsTA;
	@FXML private VBox prescriptionList;
	@FXML private VBox vaccineList;
	@FXML private Button saveButton;
	
	private Patient patient;
	private Visit visit;
	private String mode;
	
	// sets labels
	// @Params: patient contains info about which patient was clicked on
	// visit (optional) allows us to modify a previous visit
	// mode <"Edit", "View", "New"> changes if text is editable, as well as action upon clicking save
	public void initialize(Patient patient, Visit visit, String mode) {
		this.patient = patient;
		this.visit = visit;
		this.mode = mode;
		
		String patientName = patient.getName();
		String labelStr = mode + " Visit for " + patientName;
		name.setText(labelStr);
		
		// Disable save button
		if (mode == "View") {
			saveButton.setDisable(true);
		}
	}

    @FXML
    void handleAddVaccine(MouseEvent event) {
    	
    }

    @FXML
    void handleSaveVisit(MouseEvent event) throws IOException {
    	Visit newVisit = createVisit();
    	// TODO: update (or create) visit in the database
    	VisitHistoryManager.storeVisit(patient, newVisit);
    	System.out.println("Visit created");
    }
    
    public Visit createVisit() {
    	try {
    		String height = heightTF.getText();
    		String weight = weightTF.getText();
    		String temperature = tempTF.getText();
    		String bloodPressure = bloodPressureTF.getText();
	    	List<String> immunizations = new ArrayList<>(); // TODO
	    	List<String> allergies = new ArrayList<>(); // TODO
	    	List<String> prescribedMedication = new ArrayList<>(); // TODO
	    	String healthConcerns = healthConcernsTA.getText();
	    	String dosage = "0"; // TODO
	    	String location = ""; // for doctor
	    	String physicalExamNotes = "";
	    	String medicationNotes = "";
	    	Visit visit = new Visit(height, weight, temperature, bloodPressure, immunizations, allergies, prescribedMedication, healthConcerns, dosage, location, physicalExamNotes, medicationNotes);
	    	return visit;
    	} catch (NumberFormatException e) {
    		e.printStackTrace();
    	}
    	return null;
    }

}
