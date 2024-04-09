package application;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class PatientVisitInfoController implements PatientImmunizationItemListener {
	
	@FXML private Label name;
	@FXML private TextField bloodPressureTF;
	@FXML private TextField heightTF;
	@FXML private TextField tempTF;
    @FXML private DatePicker vaccineDateDP;
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
	private List<PatientImmunizationItemController> immunizationListItemControllers;
	private List<String> temporaryImmunizations = new ArrayList<>();;
	
	// sets labels
	// @Params: patient contains info about which patient was clicked on
	// visit (optional) allows us to modify a previous visit
	// mode <"Edit", "View", "New"> changes if text is editable, as well as action upon clicking save
	public void initialize(Patient patient, Visit visit, String mode) {
		this.patient = patient;
		this.visit = visit != null ? visit : new Visit();	//Make sure to check if visit if null and make a new visit if so
		this.mode = mode;
		
		String patientName = patient.getName();
		String labelStr = mode + " Visit for " + patientName;
		name.setText(labelStr);
		
		// Disable save button
		if (mode == "View") {
			saveButton.setDisable(true);
		}
		
		if (mode != "New") {
			temporaryImmunizations = visit.getImmunizations();
		}
		
		updateLabels();
		updateImmunizationsList();
	}

    @FXML
    void handleAddVaccine(MouseEvent event) {
    	String immunization = vaccineNameTF.getText() + "|" + vaccineDateDP.getValue();
    	temporaryImmunizations.add(immunization);
    	vaccineNameTF.clear();
    	vaccineDateDP.setValue(LocalDate.now());
    	updateImmunizationsList();
    }

    @FXML
    void handleSaveVisit(MouseEvent event) throws IOException {
    	Visit newVisit = createVisit();
    	if (mode == "New") {
    		patient.addVisit(newVisit);
    		System.out.println("Visit created");
    	}
    	else {
    		//TODO: this is printing true right here, so may be an issue with how the old visit object is being generated or passed in
    		LocalDateTime time = visit.getVisitDateFormatted();
    		boolean dateFormattedNull = time == null;
    		System.out.println("Old visit date formatted is null: " + dateFormattedNull);

    		
    		// pass in the old visit, so we know which one to change
    		newVisit.setVisitDate(visit.getVisitDate());
    		newVisit.setVisitDateFormatted(visit.getVisitDateFormatted());
    		patient.addVisit(newVisit);
    		System.out.println("Visit Updated");
    	}
    }
    
    public Visit createVisit() {
    	try {
    		String height = heightTF.getText();
    		String weight = weightTF.getText();
    		String temperature = tempTF.getText();
    		String bloodPressure = bloodPressureTF.getText();
	    	List<String> immunizations = getImmunizationsList();
	    	List<String> allergies = Arrays.asList(allergiesTA.getText().split(",")); 
	    	List<String> prescribedMedication = visit.getPrescribedMedication(); // for doctor
	    	String healthConcerns = healthConcernsTA.getText();
	    	List<String> dosages = visit.getDosages(); // TODO
	    	String location = visit.getLocation(); // for doctor
	    	String physicalExamNotes = visit.getPhysicalExamNotes();
	    	String medicationNotes = visit.getMedicationNotes();
	    	Visit visit = new Visit(height, weight, temperature, bloodPressure, immunizations, allergies, prescribedMedication, healthConcerns, dosages, location, physicalExamNotes, medicationNotes);
	    	return visit;
    	} catch (NumberFormatException e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    public void updateLabels() {
		// update all the text fields and lists to show visit data
		if (visit != null) {
	    	bloodPressureTF.setText(visit.getBloodPressure());
			heightTF.setText(visit.getHeight());
			tempTF.setText(visit.getTemperature());
			// TODO: update vaccinelist, prescriptions list
			weightTF.setText(visit.getWeight());
			allergiesTA.setText(String.join(", ", visit.getAllergies()));
			healthConcernsTA.setText(visit.getHealthConcerns());
		} else {
			bloodPressureTF.clear();
			heightTF.clear();
			tempTF.clear();
			weightTF.clear();
			allergiesTA.clear();
			healthConcernsTA.clear();
		}
    }
    
    public void updateImmunizationsList() {
    	if (temporaryImmunizations.size() < 1)
    		return;
		// create patientListItems
        // Load list items dynamically
    	immunizationListItemControllers = new ArrayList<>();
    	
    	// delete all list items (except the header)
    	ObservableList<Node> children = vaccineList.getChildren();
    	int numChildren = children.size();

    	for (int i = 1; i < numChildren; i++) {
    	    children.remove(1); // Remove the child at index indexToRemoveFrom
    	}
    	
        for (int i = 0; i < temporaryImmunizations.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/patient_immunization_item.fxml"));
                //controllers.add(loader.getController());
                vaccineList.getChildren().add(loader.load());
                PatientImmunizationItemController listItemController = loader.getController();
                listItemController.setListener(this);
                listItemController.setLabels(temporaryImmunizations, i);
                immunizationListItemControllers.add(listItemController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    // fetch the immunizations from the current state of the table
    public List<String> getImmunizationsList() {
    	List<String> output = new ArrayList<>();
    	for (PatientImmunizationItemController listItemController : immunizationListItemControllers) {
    		String immunization = listItemController.getLabels();
    		output.add(immunization);
    	}
    	System.out.println(output);
    	return output;
    }

	@Override
	public void onDeleteItem(int index) {
		temporaryImmunizations.remove(index);
		updateImmunizationsList();
	}

}
