package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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

public class NurseDoctorPatientVisitController implements PatientListItemListener, SidebarListener, Initializable{

    @FXML private Button categoryAllButton;
    @FXML private Button categoryCurrentButton;
    @FXML private Button categoryPreviousButton;
    
    @FXML private Label allCount;
    @FXML private Label currentCount;
    @FXML private Label previousCount;
    
    @FXML private VBox patientList;
    
    @FXML private HBox parentContainer; // Holds everything
  
    
    private List<Patient> patients;
    
    private List<PatientListItemController> controllers;
    
    // Tell the table what the columns should consist of
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// initially sort patients by date
		patients = PatientManager.getInstance().getPatients();
        sortPatientsByDate();
        
        // Also update patient count
        allCount.setText("" + patients.size());
        
        // set sidebar
        SidebarController controller = (SidebarController) SceneManager.addContainerElement(getClass(), parentContainer, 0, "/FXML/sidebar.fxml");
		controller.setListener(this);
		
		Sidebar[] buttons_array = {Sidebar.PATIENTLIST, Sidebar.MESSAGES};
		List<Sidebar> buttons = Arrays.asList(buttons_array);
		controller.setButtons(buttons);
	}
	
	public void sortPatientsByDate() {
		// Initially sort by most recent visit date
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
	}
	
	public void sortPatientsByFirstName() {
		// Initially sort by most recent visit date
        Collections.sort(patients, new Comparator<Patient>() {
            public int compare(Patient o1, Patient o2) {
                return o1.getFirstName().compareTo(o2.getFirstName());
            }
        });
        
        updatePatientList();
	}
    
	
	/* PATIENT LIST VIEW HANDLERS */
	
	
    
	// Handle back button (goes home)
    public void previousScene(ActionEvent event) throws Exception {
		logout(event);
		SceneManager.loadScene(getClass(), "/FXML/role_selection.fxml", event);
    }
    
    // Handle patients/messaging button
	@Override
	public void handleClick(Sidebar action, ActionEvent event) {
		switch (action) {
		case PATIENTLIST:
			event.consume();
			SceneManager.loadScene(getClass(), "/FXML/nurse_doctor_patient_list.fxml", event);
			break;
		case MESSAGES:
	    	event.consume();
	    	NurseDoctorMessageBoardController controller = (NurseDoctorMessageBoardController) SceneManager.replaceContainerElement(getClass(), parentContainer, 1, "/FXML/nurse_doctor_message_board.fxml");
		}
		
	}
    
    public void messageButton(ActionEvent event) throws Exception {
    	event.consume();
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
    
    
    
    // Go to patient info edit screen for the patient's visit
    public void onItemClick(Patient patient, Visit visit, Pane container) {
    	if (UserManager.getInstance().getCurrentUserRole() == Role.NURSE) {
    		// if the role is nurse, send to the edit patient info page
    		PatientVisitInfoController controller = (PatientVisitInfoController) SceneManager.replaceContainerElement(getClass(), parentContainer, 1, "/FXML/patient_visit_info.fxml");
    		controller.initialize(patient, visit, "Edit");
    		System.out.println("Edit Patient Info Form for Patient" + patient.getName() + " on " + visit.getVisitDate().toString());
    	} else {
    		// if the role is doctor, send to the edit prescription/physical exam notes page
    		DoctorExaminationPrescriptionController controller = (DoctorExaminationPrescriptionController) SceneManager.replaceContainerElement(getClass(), parentContainer, 1, "/FXML/doctor_examination_prescription.fxml");
    		controller.initialize(patient, visit, "Edit");
    		System.out.println("Assign Prescription Medication and edit Physical Examination for " + patient.getName() + " on " + visit.getVisitDate().toString());
    	}
    }
    
    // Go to patient info creation screen and initialize it for the patient
    public void onNewVisitClicked(Patient patient, Pane container) {
    	PatientVisitInfoController controller = (PatientVisitInfoController) SceneManager.replaceContainerElement(getClass(), parentContainer, 1, "/FXML/patient_visit_info.fxml");
    	controller.initialize(patient, null, "New");
    	System.out.println("New Patient Info Form for Patient" + patient.getName());
    }
    
    // Update patient list method
    public void updatePatientList() {
		controllers = new ArrayList<>();
		
    	// delete preexisting list
		while (patientList.getChildren().size() > 1) {
			patientList.getChildren().remove(1);
		}
		
		// Create patientListItems
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
    
    // shows all patients
    public void categoryAll(ActionEvent event) throws Exception {
    	// Activate categoryAll button
    	setCategoryButton(categoryAllButton, true);
    	setCategoryButton(categoryCurrentButton, false);
    	setCategoryButton(categoryPreviousButton, false);
    	
    	// sort and show all patients by date
    	patients = PatientManager.getInstance().getPatients();
    	sortPatientsByDate();
    }
    
    // shows patients that do not yet have a visit
    public void categoryCurrent(ActionEvent event) throws Exception {
    	// Activate categoryCurrent button
    	setCategoryButton(categoryCurrentButton, true);
		setCategoryButton(categoryAllButton, false);
		setCategoryButton(categoryPreviousButton, false);
		
		// show and sort all patients by first name
    	List<Patient> tempList = PatientManager.getInstance().getPatients();
    	patients = new ArrayList<>();
    	for (int i = 0; i < tempList.size(); i ++) {
    		if (tempList.get(i).getVisitHistory().size() == 0) {
    			patients.add(tempList.get(i));
    		}
    	}

    	sortPatientsByFirstName();
    }
    
    // shows patients that do have a visit
    public void categoryPrevious(ActionEvent event) throws Exception {
    	// Activate categoryPrevious button
    	setCategoryButton(categoryPreviousButton, true);
		setCategoryButton(categoryCurrentButton, false);
		setCategoryButton(categoryAllButton, false);
		
    	// sort and show all patients by date
    	List<Patient> tempList = PatientManager.getInstance().getPatients();
    	patients = new ArrayList<>();
    	for (int i = 0; i < tempList.size(); i ++) {
    		if (tempList.get(i).getVisitHistory().size() > 0) {
    			patients.add(tempList.get(i));
    		}
    	}

    	sortPatientsByDate();
    }
    
    public void setCategoryButton(Button button, boolean isSelected) {
    	BorderPane container = (BorderPane) button.getGraphic();
    	Label leftLabel = (Label) container.getLeft(); // name of the category
    	Label rightLabel = (Label) container.getRight(); // displays the number of patients within category
    	if (isSelected) {
        	button.setId("selectedoption");
    		leftLabel.setTextFill(Color.web("#6039d2"));
    		rightLabel.setTextFill(Color.web("#6039d2"));
    	} else {
        	button.setId("unselectedoption");
    		leftLabel.setTextFill(Color.web("#888888"));
    		rightLabel.setTextFill(Color.web("#888888"));
    	}
    }


	@Override
	public void logout(ActionEvent event) {
		// TODO Auto-generated method stub
    	UserManager userManager = UserManager.getInstance();
    	
    	// Get the current logged in user
    	User currentUser = userManager.getCurrentUser();
    	
    	// If currentUser is not null, log the user out
    	if (currentUser != null) {
    		System.out.println("Current user: " + currentUser.getUsername() + " logged out.");
    		userManager.logout();
    	} else {
    		System.out.println("No user currently logged in.");
    	}
    	
    	SceneManager.loadScene(getClass(), "/FXML/role_selection.fxml", event);
	}
}
