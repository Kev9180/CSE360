package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NurseDoctorVisitHistoryController implements Initializable, PatientVisitHistoryItemListener{

    @FXML private Label name;
    @FXML private VBox visitList;
    @FXML private HBox parentContainer; // Parent VBox that will get replaced when switching scenes
    @FXML private Button newVisitButton;
    
    private Patient patient;
    private List<Visit> visitHistory;
    private NurseDoctorPatientVisitController parentController;

	public void initialize(Patient patient, NurseDoctorPatientVisitController parentController) {
		this.parentController = parentController;
		this.patient = patient;
		this.visitHistory = patient.getVisitHistory();
		// Set name at top to be patient's name
		name.setText(patient.getName());
		
		// Sort visitHistory
        Collections.sort(visitHistory, new Comparator<Visit>() {
            @Override
            public int compare(Visit visit1, Visit visit2) {
            	// Get the visit dates from patients            	
                LocalDate d1 = visit1.getVisitDate();
                LocalDate d2 = visit2.getVisitDate();

                // Handle null cases
                if (d1 == null && d2 == null)	return 0; // Both dates are null, consider them equal
                else if (d1 == null)  			return 1; // Null dates should come after non-null dates
                else if (d2 == null)  			return -1; // Null dates should come after non-null dates
                
                return d2.compareTo(d1);
            }
        });
        
        updateVisitList();
		
		// Disable new visit button if is doctor
        if(UserManager.getInstance().getCurrentUserRole() == Role.DOCTOR) {
        	newVisitButton.setVisible(false);
        }
	}
	
    // Update patient list method
    public void updateVisitList() {
    	
		// Create patientListItems
        // Load list items dynamically
        for (int i = 0; i < visitHistory.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/patient_visit_item.fxml"));
                // Controllers.add(loader.getController());
                visitList.getChildren().add(loader.load());
                PatientVisitHistoryItemController listItemController = loader.getController();
                listItemController.setParentController(this); // Pass reference to parent controller
                listItemController.setLabels(visitHistory.get(i));
                listItemController.setPatient(patient);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
	// Pass this back to parent controller (NurseViewController) to process
	@Override
	public void onItemClick(Patient patient, Visit visit) {
		parentController.onItemClick(patient, visit, parentContainer);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	// Pass this back to parent controller (NurseViewController) to process
    @FXML
    void handleNewVisitClicked(MouseEvent event) {
    	parentController.onNewVisitClicked(patient, parentContainer);
    }
    
    @FXML
    void previousScene(ActionEvent event) {
    	// return back to patientList
    	SceneManager.loadScene(getClass(), "/FXML/nurse_doctor_patient_list.fxml", event);
    }
	
   
}
