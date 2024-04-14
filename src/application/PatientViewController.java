package application;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PatientViewController implements SidebarListener, Initializable {
	
	@FXML private HBox parentContainer; // holds the sidebar + screen
	@FXML private VBox screenContainer; // holds the actual screen contents
    @FXML private Button categoryAllButton;
    @FXML private Button categoryCurrentButton;
    
    // handles the sidebar buttons
	@Override
	public void handleClick(Sidebar action, ActionEvent event) {
		switch (action) {
		case DASHBOARD:
			SceneManager.loadScene(getClass(), "/FXML/patient_view.fxml", event);
			break;
		case MESSAGES:
			messageButton(event);
			break;
		case APPOINTMENTS:
			newAppointmentButton(event);
			break;
		case MYINFO: 
			settingButton(event);
			break;
		case MYVISITS:
			visitHistoryButton(event);
			break;
		default:
			break;
		}
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// set sidebar
        SidebarController controller = (SidebarController) SceneManager.addContainerElement(getClass(), parentContainer, 0, "/FXML/sidebar.fxml");
		controller.setListener(this);
		
		Sidebar[] buttons_array = {Sidebar.DASHBOARD, Sidebar.MESSAGES, Sidebar.APPOINTMENTS, Sidebar.MYINFO, Sidebar.MYVISITS};
		List<Sidebar> buttons = Arrays.asList(buttons_array);
		controller.setButtons(buttons);
	}
    
    // Responds to the message button click and displays the patient message board screen
    // Clears the existing content in a designated container and adds the newly loaded message board to the container
    @FXML
    private void messageButton(ActionEvent event) {
   		SceneManager.setContainerElement(getClass(), screenContainer, "/FXML/patient_message_board.fxml");
    }
    
    // Switches the application view to settings when triggered
    @FXML
    private void settingButton(ActionEvent event) {
    	SceneManager.setContainerElement(getClass(), screenContainer, "/FXML/patient_setting_view.fxml");
    }
   
    // Switches the application view to display a new appointment form when triggered
    @FXML
    private void newAppointmentButton(ActionEvent event) {
    	SceneManager.setContainerElement(getClass(), screenContainer, "/FXML/new_appointment_form.fxml");
    }
    
    // Switches the application view to display a patients visit history
    @FXML
    private void visitHistoryButton(ActionEvent event) {
		SceneManager.setContainerElement(getClass(), screenContainer, "/FXML/patient_appointment_view.fxml");
    }
    
    // Switches the application view to display a patients appointments
    @FXML
    private void appointmentButton(ActionEvent event) {
    	SceneManager.setContainerElement(getClass(), screenContainer, "/FXML/patient_appointment_view.fxml");
    }

	@Override
	public void logout(ActionEvent event) {
    	logoutPatient();
    	SceneManager.loadScene(getClass(), "/FXML/role_selection.fxml", event);
	}
	
    // Method to logout the patient before going back to the previous screen
    public void logoutPatient() {
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
    }
   
}
