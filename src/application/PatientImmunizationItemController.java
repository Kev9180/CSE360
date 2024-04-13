package application;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
//Controller class for the individual items representing patient immunizations in the UI
public class PatientImmunizationItemController {
	
    @FXML private Label dateAdministered;
    @FXML private Label immunizationName;
    @FXML private HBox itemContainer;
    
    private PatientImmunizationItemListener listener;
    
    private List<String> immunizations;
    private int listIndex;
    
    // Method to set the labels of the UI elements based on the provided immunization information
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
    
    // Method to set the listener for handling actions on the item
    public void setListener(PatientImmunizationItemListener listener) {
        this.listener = listener;
    }
  
    // Method to get the labels of the UI elements as a concatenated string
    public String getLabels() {
    	return immunizationName.getText() + "|" + dateAdministered.getText();
    }
    
    @FXML   // Triggers the deletion of an item by notifying a registered listener and calling the method with item index
    void handleDeleteItem(ActionEvent event) {
    	listener.onDeleteItem(listIndex);
    }
}
