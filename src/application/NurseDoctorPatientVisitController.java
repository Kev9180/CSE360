package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class NurseDoctorPatientVisitController implements PatientListItemListener, Initializable{

    @FXML private Button categoryAllButton;
    @FXML private Button categoryCurrentButton;
    @FXML private Button categoryPreviousButton;
    
    @FXML private Label allCount;
    @FXML private Label currentCount;
    @FXML private Label previousCount;
    
    @FXML private VBox patientList;
    
    @FXML private HBox parentContainer; // holds everything
  
    
    private List<Patient> patients;
    
    private List<PatientListItemController> controllers;
    
    // tell the table what the columns should consist of
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// mark initial vbox to be the one to be replaced
		
		patients = PatientManager.getInstance().getPatients();
		// initially sort by most recent visit date
        Collections.sort(patients, new Comparator<Patient>() {
            @Override
            public int compare(Patient o1, Patient o2) {

            	// Get the visit dates from patients            	
            	List<Visit> visit1 = o1.getVisitHistory();
            	List<Visit> visit2 = o2.getVisitHistory();

            	// Get the visit dates from patients
                LocalDate d1 = (o1.getVisitHistory().isEmpty() || o1.getVisitHistory().getLast() == null) ? null : o1.getVisitHistory().getLast().getVisitDate();
                LocalDate d2 = (o2.getVisitHistory().isEmpty() || o2.getVisitHistory().getLast() == null) ? null : o2.getVisitHistory().getLast().getVisitDate();

                // Handle null cases
                if (d1 == null && d2 == null)	return 0; // Both dates are null, consider them equal
                else if (d1 == null)  			return 1; // Null dates should come after non-null dates
                else if (d2 == null)  			return -1; // Null dates should come after non-null dates
                
                return d2.compareTo(d1);
            }
        });
        
        updatePatientList();
        
        // also update patient count
        allCount.setText("" + patients.size());
		
	}
    
	
	/* PATIENT LIST VIEW HANDLERS */
	
	
    
	//Handle back button (goes home)
    public void previousScene(ActionEvent event) throws Exception {
    	logoutStaff();
    	SceneManager.loadScene(getClass(), "/FXML/role_selection.fxml", event);
    }
    
	//Handle logout button 
    public void logout(ActionEvent event) throws Exception {
    	logoutStaff();
    	SceneManager.loadScene(getClass(), "/FXML/role_selection.fxml", event);
    }
    
    public void messageButton(ActionEvent event) throws Exception {
    	event.consume();
    	NurseDoctorMessageBoardController controller = (NurseDoctorMessageBoardController) SceneManager.replaceContainerElement(getClass(), parentContainer, 1, "/FXML/nurse_doctor_message_board.fxml");
    }
    
    public void selectPatients(ActionEvent event) throws Exception {
    	event.consume();
    	SceneManager.loadScene(getClass(), "/FXML/nurse_doctor_patient_list.fxml", event);
    }
    
    public void selectMessages(ActionEvent event) throws Exception {
    	NurseDoctorMessageBoardController controller = (NurseDoctorMessageBoardController) SceneManager.replaceContainerElement(getClass(), parentContainer, 1, "/FXML/nurse_doctor_message_board.fxml");
    	
    }
    
    @Override
    public void onMessageButtonClick(Patient patient) {
    	// jump to the messaging scene, and select the patient from the drop-down menu
    	MessageController controller = (MessageController) SceneManager.replaceContainerElement(getClass(), parentContainer, 1, "/FXML/compose_message.fxml");
    	
    	// find correct patient in the combo box
    	String formattedName = "Patient " + patient.getFirstName() + " " + patient.getLastName() + " | Username: " + patient.getUsername();
    	controller.setDefaultOption(formattedName);
    }
    
    @Override
    public void onListItemClick(Patient patient) {
    	NurseDoctorVisitHistoryController controller = (NurseDoctorVisitHistoryController) SceneManager.replaceContainerElement(getClass(), parentContainer, 1,  "/FXML/nurse_doctor_visit_history.fxml");
    	controller.initialize(patient, this);
    }
    
    @Override
    public void onViewInfoButtonClick(Patient patient) {
    	// jump to the edit patient info scene
    	
    	
    }
    
    
    /*  VISIT HISTORY VIEW HANDLERS */
    
    
    
    // go to patient info edit screen for the patient's visit
    public void onItemClick(Patient patient, Visit visit, Pane container) {
    	PatientVisitInfoController controller = (PatientVisitInfoController) SceneManager.replaceContainerElement(getClass(), parentContainer, 1, "/FXML/patient_visit_info.fxml");
		controller.initialize(patient, visit, "Edit");
		System.out.println("Edit Patient Info Form for Patient" + patient.getName() + " on " + visit.getVisitDate().toString());
    }
    
    // go to patient info creation screen and initialize it for the patient
    public void onNewVisitClicked(Patient patient, Pane container) {
    	PatientVisitInfoController controller = (PatientVisitInfoController) SceneManager.replaceContainerElement(getClass(), parentContainer, 1, "/FXML/patient_visit_info.fxml");
    	controller.initialize(patient, null, "New");
    	System.out.println("New Patient Info Form for Patient" + patient.getName());
    }
    
    // update patient list method
    public void updatePatientList() {
		controllers = new ArrayList<>();
    	
		// create patientListItems
        // Load list items dynamically
        for (int i = 0; i < patients.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/patient_list_item.fxml"));
                controllers.add(loader.getController());
                patientList.getChildren().add(loader.load());
                PatientListItemController listItemController = loader.getController();
                listItemController.setParentController(this); // Pass reference to parent controller
                listItemController.setLabels(patients.get(i));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /* im sorry i know this is awful */
    
    public void categoryAll(ActionEvent event) throws Exception {
    	// activate categoryAll button
    	categoryAllButton.setId("selectedoption");
    	BorderPane allBP = (BorderPane) categoryAllButton.getGraphic();
    	Label allLabel = (Label) allBP.getLeft();
    	allLabel.setTextFill(Color.web("#6039d2"));
    	allCount.setTextFill(Color.web("#6039d2"));
    	
    	// deactivate categoryCurrent button
    	categoryCurrentButton.setId("unselectedoption");
    	BorderPane currentBP = (BorderPane) categoryCurrentButton.getGraphic();
    	Label currentLabel = (Label) currentBP.getLeft();
    	currentLabel.setTextFill(Color.web("#666666"));
    	currentCount.setTextFill(Color.web("#666666"));
    	
    	// deactivate categoryPrevious button
    	categoryPreviousButton.setId("unselectedoption");
    	BorderPane previousBP = (BorderPane) categoryPreviousButton.getGraphic();
    	Label previousLabel = (Label) previousBP.getLeft();
    	previousLabel.setTextFill(Color.web("#666666"));
    	previousCount.setTextFill(Color.web("#666666"));
    }
    
    public void categoryCurrent(ActionEvent event) throws Exception {
    	// activate categoryCurrent button
    	categoryCurrentButton.setId("selectedoption");
    	BorderPane currentBP = (BorderPane) categoryCurrentButton.getGraphic();
    	Label currentLabel = (Label) currentBP.getLeft();
    	currentLabel.setTextFill(Color.web("#6039d2"));
    	currentCount.setTextFill(Color.web("#6039d2"));
    	
    	// deactivate categoryAll button
    	categoryAllButton.setId("unselectedoption");
    	BorderPane allBP = (BorderPane) categoryAllButton.getGraphic();
    	Label allLabel = (Label) allBP.getLeft();
    	allLabel.setTextFill(Color.web("#666666"));
    	allCount.setTextFill(Color.web("#666666"));
    	
    	// deactivate categoryPrevious button
    	categoryPreviousButton.setId("unselectedoption");
    	BorderPane previousBP = (BorderPane) categoryPreviousButton.getGraphic();
    	Label previousLabel = (Label) previousBP.getLeft();
    	previousLabel.setTextFill(Color.web("#666666"));
    	previousCount.setTextFill(Color.web("#666666"));
   
    }
    
    public void categoryPrevious(ActionEvent event) throws Exception {
    	// activate categoryPrevious button
    	categoryPreviousButton.setId("selectedoption");
    	BorderPane previousBP = (BorderPane) categoryPreviousButton.getGraphic();
    	Label previousLabel = (Label) previousBP.getLeft();
    	previousLabel.setTextFill(Color.web("#6039d2"));
    	previousCount.setTextFill(Color.web("#6039d2"));
    	
    	// deactivate categoryCurrent button
    	categoryCurrentButton.setId("unselectedoption");
    	BorderPane currentBP = (BorderPane) categoryCurrentButton.getGraphic();
    	Label currentLabel = (Label) currentBP.getLeft();
    	currentLabel.setTextFill(Color.web("#666666"));
    	currentCount.setTextFill(Color.web("#666666"));
    	
    	// deactivate categoryAll button
    	categoryAllButton.setId("unselectedoption");
    	BorderPane allBP = (BorderPane) categoryAllButton.getGraphic();
    	Label allLabel = (Label) allBP.getLeft();
    	allLabel.setTextFill(Color.web("#666666"));
    	allCount.setTextFill(Color.web("#666666"));
    }

    //Method to logout the patient before going back to the previous screen
    public void logoutStaff() {
    	UserManager userManager = UserManager.getInstance();
    	
    	//Get the current logged in user
    	User currentUser = userManager.getCurrentUser();
    	
    	//If currentUser is not null, log the user out
    	if (currentUser != null) {
    		System.out.println("Current user: " + currentUser.getUsername() + " logged out.");
    		userManager.logout();
    	} else {
    		System.out.println("No user currently logged in.");
    	}
    }

}
