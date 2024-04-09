package application;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class PatientImmunizationItemController {

    @FXML
    private Label dateAdministered;

    @FXML
    private Label immunizationName;
    
    @FXML
    private HBox itemContainer;
    
    private PatientImmunizationItemListener listener;
    
    private List<String> immunizations;
    private int listIndex;
    
    public void setLabels(List<String> immunizations, int listIndex) {
    	this.immunizations = immunizations;
    	this.listIndex = listIndex;
    	String immunization = immunizations.get(listIndex);
		String[] nameAndDate = immunization.split("\\|");
		immunizationName.setText(nameAndDate[0]);
		dateAdministered.setText(nameAndDate[1]);
    }
    
    public void setListener(PatientImmunizationItemListener listener) {
        this.listener = listener;
    }
    
    public String getLabels() {
    	return immunizationName.getText() + "|" + dateAdministered.getText();
    }

    @FXML
    void handleDeleteItem(ActionEvent event) {
    	listener.onDeleteItem(listIndex);
    }

}
