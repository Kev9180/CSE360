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
    
    public void setLabels(List<String> prescriptions, List<String> dosages, int listIndex) {
    	this.prescriptions = prescriptions;
    	this.dosages = dosages;
    	this.listIndex = listIndex;
    	
    	name.setText(prescriptions.get(listIndex));
    	dosage.setText(dosages.get(listIndex));
    }
    
    public void setListener(PatientPrescriptionItemListener listener) {
    	this.listener = listener;
    }
    
    public String[] getLabels() {
    	String[] output = {name.getText(), dosage.getText()};
    	return output;
    }

    @FXML
    void handleDeleteItem(ActionEvent event) {
    	listener.onDeleteItem(listIndex);
    }

}
