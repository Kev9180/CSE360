package application;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class PatientImmunizationItemController {
	
    @FXML private Label dateAdministered;
    @FXML private Label immunizationName;
    @FXML private HBox itemContainer;
    
    private PatientImmunizationItemListener listener;
    
    private List<String> immunizations;
    private int listIndex;
    
    // Updates the information being displayed, assigns a list of immunizations and an index to variables
    // Extracts specific records using the index and splits the record into name and date components
    // Displays the name and date of the immunization
    public void setLabels(List<String> immunizations, int listIndex) {
    	this.immunizations = immunizations;
    	this.listIndex = listIndex;
    	String immunization = immunizations.get(listIndex);
		String[] nameAndDate = immunization.split("\\|");
		immunizationName.setText(nameAndDate[0]);
		dateAdministered.setText(nameAndDate[1]);
    }
    
    // Method allows the object to listen for events related to an immunization item
    public void setListener(PatientImmunizationItemListener listener) {
        this.listener = listener;
    }
    
    // Combines the text of the name and date of immunization, separated with a delimiter and returns the string
    public String getLabels() {
    	return immunizationName.getText() + "|" + dateAdministered.getText();
    }

    // Triggers the deletion of an item by notifying a registered listener and calling the method with item index
    @FXML
    void handleDeleteItem(ActionEvent event) {
    	listener.onDeleteItem(listIndex);
    }

}
