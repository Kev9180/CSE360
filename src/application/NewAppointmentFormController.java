package application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;

public class NewAppointmentFormController {    
	@FXML private ComboBox<String> requestedProviderCB;
	@FXML private ComboBox<String> requestedTimeCB;
	@FXML private DatePicker requestedDatePicker;
	@FXML private TextArea healthConcernsTA;
	
	private Patient patient;
	private Visit visit;
	
	// Identifies the user within a list of patients and updates the UI for further scheduling 
	public void initialize() {
		User currentUser = UserManager.getInstance().getCurrentUser();
		String username = currentUser.getUsername();
		
		List<Patient> patientList = DatabaseUtil.getPatients();
		
		for (Patient patient : patientList) {
			if (username.equals(patient.getUsername()))
				this.patient = patient;
		}
		
		populateProviderCB();
		populateRequestedTimeCB();
	}
	
	// Method to populate the requested provider combo box with doctors
	public void populateProviderCB() {
		List<User> nurseAndDoctorList = DatabaseUtil.getNursesAndDoctors();
		ObservableList<String> doctors = FXCollections.observableArrayList();
		
		// Iterate through the list of doctors and nurses
		for (User user : nurseAndDoctorList) {
			String roleStr = user.getRole().toString();
			String role = "";
			
			// Only extract the doctors from the list - add them to the observable list
			if (roleStr.equals("DOCTOR")) {
				role = "Dr. ";
				String formattedName = role + user.getFirstName() + " " + user.getLastName() + " | Username: " + user.getUsername();
				doctors.add(formattedName);
			}
		}
		
		// Add the items to the combo box
		requestedProviderCB.setItems(doctors);
	}
	
	// Method to populate the requestedTimeCB with times in 30 minute increments from 9am to 3pm
	public void populateRequestedTimeCB() {
	    ObservableList<String> times = FXCollections.observableArrayList();
	    LocalTime startTime = LocalTime.of(9, 0); //Start at 9am
	    LocalTime endTime = LocalTime.of(15, 30); //End at 3pm, use 30 min increments
	    
	    // Add each of the 30 minute increments to the observable list
	    while (startTime.isBefore(endTime)) {
	        times.add(startTime.format(DateTimeFormatter.ofPattern("hh:mm a")));
	        startTime = startTime.plusMinutes(30);
	    }
	    
	    // Add the items to the combo box
	    requestedTimeCB.setItems(times);
	}
   
	// Method to submit the appointment request - adds a blank appointment to the users visit history for their requested date
	@FXML	
	private void submitAppointmentRequest(ActionEvent event) throws Exception {
		if (!validateFields()) {
			return;
		}
		
		LocalDate visitDate = requestedDatePicker.getValue();							//Extract the date value into a LocalDate variable
	    LocalDateTime visitDateTime = null;										
	    String healthConcerns = healthConcernsTA.getText();								//Extract the health concerns into a String variable
	    String selectedTimeStr = requestedTimeCB.getSelectionModel().getSelectedItem();	//Extract the requested time into a String variable
	    
	    // Format the requested date and time into a LocalDateTime variable
	    if (visitDate != null && selectedTimeStr != null && !selectedTimeStr.isEmpty()) {
	        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
	        LocalTime selectedTime = LocalTime.parse(selectedTimeStr, timeFormatter);
	        visitDateTime = LocalDateTime.of(visitDate, selectedTime);
	    }
	    
	    // fetch the requested doctor
	    String requestedProviderUsername = requestedProviderCB.getValue().split(" \\| Username: ")[1];
	    User requestedProvider = DatabaseUtil.getUserByUsername(requestedProviderUsername);
	    
	    // Create a new visit for the patient, which will represent the patient's requested visit
	    // On the date of the visit, the nurse/doctor will choose this visit from the patient's visit history list, and then fill in the information
	    visit = new Visit(healthConcerns, visitDateTime, visitDate, requestedProvider);
	    
	    //A dd the visit to the patient's visit history
	    VisitHistoryManager.storeVisit(patient, visit);
	    
	    System.out.println("Appointment requested and added to patient's visit history.");
	    
	    Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Success!");
		alert.setHeaderText("You're appointment has been requested.");
		alert.setContentText("A staff member will call you to confirm your appointment at the phone number you have listed (" + patient.getPhoneNumber() + "). Once confirmed, please arrive at the clinic 15 minutes prior to your scheduled appointment date/time. Thank you!");
		alert.showAndWait();
		
		goBack(event);
	}
  
	// Method to validate fields to make sure none of them are blank
	private boolean validateFields() {
		if (requestedProviderCB.getSelectionModel().isEmpty()) {
	        showAlert("Provider Not Selected", "Please select a provider.");
	        return false;
	    }
	    
	    if (requestedTimeCB.getSelectionModel().isEmpty()) {
	        showAlert("Time Not Selected", "Please select a time.");
	        return false;
	    }
	    
	    if (requestedDatePicker.getValue() == null) {
	        showAlert("Date Not Selected", "Please select a date.");
	        return false;
	    }
	    
	    if (healthConcernsTA.getText().trim().isEmpty()) {
	        showAlert("Health Concerns Not Specified", "Please enter your health concerns.");
	        return false;
	    }
	    
	    return true;
	}
	
	// Method to show an alert window with a specified prompt
	public void showAlert(String header, String content) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Validation Error");
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
	
	// Method to take the user back to patient view
    @FXML	
    private void goBack(ActionEvent event) throws Exception {
        event.consume();
        SceneManager.loadScene(getClass(), "/FXML/patient_view.fxml", event);
    }
}
