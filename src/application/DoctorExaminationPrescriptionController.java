package application;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class DoctorExaminationPrescriptionController implements PatientPrescriptionItemListener{

    @FXML private Spinner<?> dosageSP;
    @FXML private MenuButton frequencyMB;
	@FXML private TextField locationTF;
	@FXML private TextField medicationNameTF;
	@FXML private Label name;
	@FXML private TextArea physicalExamNotesTA;
	@FXML private VBox prescriptionList;
	@FXML private Button saveButton;
	@FXML private TextArea usageInstructionsTA;

	private Patient patient;
	private Visit visit;
	private String mode;
	private List<PatientPrescriptionItemController> prescriptionListItemControllers;
	private List<String> temporaryPrescriptions = new ArrayList<>();
	private List<String> temporaryDosages = new ArrayList<>();
	
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
			temporaryPrescriptions = visit.getPrescribedMedication();
			temporaryDosages = visit.getDosages();
		}
		
		updateLabels();
		updatePrescriptionsList();
	}
	
    @FXML
    void handleAddPrescription(MouseEvent event) {
    	String medication = medicationNameTF.getText();
    	String dosage = (String) dosageSP.getValue();
    	temporaryPrescriptions.add(medication);
    	temporaryDosages.add(dosage);
    	medicationNameTF.clear();
    	dosageSP.getValueFactory().setValue(null);
    	updatePrescriptionsList();
    }


    @FXML
    void handleSaveVisit(MouseEvent event) {
    	Visit newVisit = createVisit();

		//TODO: this is printing true right here, so may be an issue with how the old visit object is being generated or passed in
		LocalDateTime time = visit.getVisitDateFormatted();
		boolean dateFormattedNull = time == null;
		//System.out.println("Old visit date formatted is null: " + dateFormattedNull);

		// pass in the old visit, so we know which one to change
		newVisit.setVisitDate(visit.getVisitDate());
		newVisit.setVisitDateFormatted(visit.getVisitDateFormatted());
		patient.addVisit(newVisit);
		System.out.println("Visit Updated");
    	
    }
    
    public Visit createVisit() {
    	try {
    		String height = visit.getHeight();
    		String weight = visit.getWeight();
    		String temperature = visit.getTemperature();
    		String bloodPressure = visit.getBloodPressure();
	    	List<String> immunizations = visit.getImmunizations();
	    	List<String> allergies = visit.getAllergies();
	    	List<String> prescribedMedication = getPrescriptionsList();
	    	String healthConcerns = visit.getHealthConcerns();
	    	List<String> dosages = getDosagesList();
	    	String location = locationTF.getText();
	    	String physicalExamNotes = physicalExamNotesTA.getText();
	    	String medicationNotes = usageInstructionsTA.getText();
	    	Visit visit = new Visit(height, weight, temperature, bloodPressure, immunizations, allergies, prescribedMedication, healthConcerns, dosages, location, physicalExamNotes, medicationNotes);
	    	return visit;
    	}catch (NumberFormatException e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    public void updateLabels() {
    	if (mode != "New") {
    		locationTF.setText(visit.getLocation());
    		physicalExamNotesTA.setText(visit.getPhysicalExamNotes());
    		usageInstructionsTA.setText(visit.getMedicationNotes());
    	} else {
    		locationTF.clear();
    		physicalExamNotesTA.clear();
    		usageInstructionsTA.clear();
    	}
    }
    
    public void updatePrescriptionsList() {
    	if (temporaryPrescriptions.size() < 1)
    		return;
    	
    	prescriptionListItemControllers = new ArrayList<>();
    	ObservableList<Node> children = prescriptionList.getChildren();
    	int numChildren = children.size();

    	for (int i = 1; i < numChildren; i++) {
    	    children.remove(1); // Remove the child at index indexToRemoveFrom
    	}
    	
        for (int i = 0; i < temporaryPrescriptions.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/doctor_prescription_list_item.fxml"));
                //controllers.add(loader.getController());
                prescriptionList.getChildren().add(loader.load());
                PatientPrescriptionItemController listItemController = loader.getController();
                listItemController.setListener(this);
                listItemController.setLabels(temporaryPrescriptions, temporaryDosages, i);
                prescriptionListItemControllers.add(listItemController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    // fetch the immunizations from the current state of the table
    public List<String> getPrescriptionsList() {
    	List<String> output = new ArrayList<>();
    	for (PatientPrescriptionItemController listItemController : prescriptionListItemControllers) {
    		String[] prescription = listItemController.getLabels();
    		output.add(prescription[0]);
    	}
    	System.out.println(output);
    	return output;
    }
    
    public List<String> getDosagesList() {
    	List<String> output = new ArrayList<>();
    	for (PatientPrescriptionItemController listItemController : prescriptionListItemControllers) {
    		String[] prescription = listItemController.getLabels();
    		output.add(prescription[1]);
    	}
    	System.out.println(output);
    	return output;
    }

	@Override
	public void onDeleteItem(int index) {
		temporaryPrescriptions.remove(index);
		temporaryDosages.remove(index);
	}
//    @FXML
//    void handleDeleteItem(ActionEvent event) {
//
//    }

}
