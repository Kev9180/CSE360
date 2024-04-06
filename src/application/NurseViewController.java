package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

public class NurseViewController implements PatientListItemListener, Initializable{

    @FXML private Button categoryAllButton;
    @FXML private Button categoryCurrentButton;
    @FXML private Button categoryPreviousButton;
    
    @FXML private Label allCount;
    @FXML private Label currentCount;
    @FXML private Label previousCount;
    
    @FXML private VBox patientList;
    
    @FXML private HBox parentContainer; // holds everything
    @FXML private VBox replaceVBox; // right side that we can replace with another scene
    
    private List<Patient> patients;
    
    private List<PatientListItemController> controllers;
    
    // tell the table what the columns should consist of
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		patients = PatientManager.getInstance().getPatients();
		// initially sort by most recent visit date
        Collections.sort(patients, new Comparator<Patient>() {
            @Override
            public int compare(Patient o1, Patient o2) {
            	// Get the visit dates from patients
                LocalDate d1 = (o1.getVisitHistory().isEmpty() || o1.getVisitHistory().getLast() == null) ? null : o1.getVisitHistory().getLast().getVisitDate();
                LocalDate d2 = (o2.getVisitHistory().isEmpty() || o2.getVisitHistory().getLast() == null) ? null : o2.getVisitHistory().getLast().getVisitDate();

                // Handle null cases
                if (d1 == null && d2 == null) {
                    return 0; // Both dates are null, consider them equal
                } else if (d1 == null) {
                    return -1; // Null dates should come before non-null dates
                } else if (d2 == null) {
                    return 1; // Null dates should come before non-null dates
                }

                // Compare non-null dates
                return d1.compareTo(d2);
            }
        });
        
        updatePatientList();
        
        // also update patient count
        allCount.setText("" + patients.size());
		
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
    	SceneManager.loadScene(getClass(), "/FXML/role_selection.fxml", event);
    }
    
    public void selectPatients(ActionEvent event) throws Exception {
    	
    }
    
    public void selectMessages(ActionEvent event) throws Exception {
    	
    }
    
    @Override
    public void onMessageButtonClick(Patient patient) {
    	// TODO Auto-generated method stub
    	
    }
    
    @Override
    public void onListItemClick(Patient patient) {
    	replaceRHS("/FXML/nurse_visit_history.fxml", patient);
    }
    
    @Override
    public void onViewInfoButtonClick(Patient patient) {
    	// TODO Auto-generated method stub
    	
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
    
    public void replaceRHS(String fxmlString, Patient patient) {
    	try {
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlString));
	    	int indexToReplace = parentContainer.getChildren().indexOf(replaceVBox);
	    	parentContainer.getChildren().remove(indexToReplace);
	    	parentContainer.getChildren().add(indexToReplace, loader.load());
	    	NurseVisitHistoryController controller = loader.getController();
	    	controller.initialize(patient);
		} catch (IOException e) {
			e.printStackTrace();
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



}
