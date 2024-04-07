package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class NurseVisitHistoryController implements Initializable, PatientVisitHistoryItemListener{

    @FXML private Label name;
    @FXML private VBox patientList;
    @FXML private HBox parentContainer; // parent VBox that will get replaced when switching scenes
    
    private Patient patient;
    private Visit visit;
    private NurseViewController parentController;

	public void initialize(Patient patient, NurseViewController parentController) {
		this.parentController = parentController;
		this.patient = patient;
		//this.parentContainer = parentContainer;
		// set name at top to be patient's name
		name.setText(patient.getName());
		// TODO: create new patient_visitHistory_item.fxml and load it	
	}
	
	// pass this back to parent controller (NurseViewController) to process
	@Override
	public void onItemClick(Patient patient, Visit visit) {
		parentController.onItemClick(patient, visit, parentContainer);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	// pass this back to parent controller (NurseViewController) to process
    @FXML
    void handleNewVisitClicked(MouseEvent event) {
    	parentController.onNewVisitClicked(patient, parentContainer);
    }
	
   
}
