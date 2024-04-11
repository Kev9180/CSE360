package application;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class PatientPrescriptionItemController {
	
    @FXML private HBox container;
    @FXML private Label dosage;
    @FXML private Label name;
    
    private PatientPrescriptionItemListener listener;
    
    private List<String> prescriptions;
    private List<String> dosages;
    private int listIndex;
    
    // Sets the list of prescriptions and dosages to the respective fields, updates the text to display the name and dosage of a prescription 
    public void setLabels(List<String> prescriptions, List<String> dosages, int listIndex) {
    	this.prescriptions = prescriptions;
    	this.dosages = dosages;
    	this.listIndex = listIndex;
    	
    	name.setText(prescriptions.get(listIndex));
    	dosage.setText(dosages.get(listIndex));
    }
    
    // Sets the listener to the current object to respond to item deletion
    public void setListener(PatientPrescriptionItemListener listener) {
    	this.listener = listener;
    }
    
    // Returns the name and dosage of a medication to a string array
    public String[] getLabels() {
    	String[] output = {name.getText(), dosage.getText()};
    	return output;
    }

    // Triggers the method of the listener to pass the index of the item to be deleted
    @FXML
    void handleDeleteItem(ActionEvent event) {
    	listener.onDeleteItem(listIndex);
    }

}
