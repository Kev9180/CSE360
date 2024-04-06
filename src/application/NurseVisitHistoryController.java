package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class NurseVisitHistoryController implements Initializable, PatientVisitHistoryItemListener{

    @FXML
    private Label name;

    @FXML
    private VBox patientList;

	public void initialize(Patient patient) {
		// set name at top to be patient's name
		name.setText(patient.getName());
		// TODO: create new patient_visitHistory_item.fxml and load it	
	}

	@Override
	public void onItemClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}
