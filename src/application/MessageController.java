package application;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MessageController {
	@FXML private Button sendMessageBtn;
	@FXML private Button backBtn;
	
	@FXML private ComboBox<String> recipientCB;
	
	@FXML private TextField subjectTF;
	@FXML private TextArea messageText;
	
	@FXML
	public void initialize() {
		loadRecipientList();
	}
	
	// Method for formatting and sending a message by retrieving the recipient and sender ids
	public void sendMessage() {
		System.out.println("Send Message button pressed");
		String recipientName = recipientCB.getValue();
		String subject = subjectTF.getText();
		String messageBody = messageText.getText();
		
		int recipientId = getUserIdFromRecipientName(recipientName);
		int senderId = DatabaseUtil.getCurrentUserId();
		
		// If valid the message will be sent and the form will be cleared in preparation for a new message
		if (recipientId != -1 && senderId != -1) {
			DatabaseUtil.sendMessage(senderId, recipientId, subject, messageBody);
			clearForm();
		}
	}
	
	// Method resets the recipient ComboBox, the subject field, and the message field
	public void clearForm() {
		recipientCB.setValue(null);
		subjectTF.setText("");
		messageText.setText("");
	}
	
	// Method uses a string to extract a users role, first and last name, and searches the database to return the corresponding user
	private int getUserIdFromRecipientName(String recipientName) {
		String[] parts = recipientName.split(" ");
		
		if (parts.length < 3) {
			System.err.println("Invalid recipient format.");
			return -1;
		}
		
		String roleStr = parts[0];
		String role = "";
		
		if (roleStr.equals("Dr."))
			role = "DOCTOR";
		else if (roleStr.equals("Nurse"))
			role = "NURSE";
		else if (roleStr.equals("Patient"))
			role = "PATIENT";
		else
			role = "ERROR";
		
		String firstName = parts[1];
		String lastName = parts[2];
		
		return DatabaseUtil.getUserIdByNameAndRole(firstName, lastName, role);
	}
	
	// Depending on the users role, the available recipients are displayed in a selection control
	private void loadRecipientList() {
		List<User> userList = null;
		ObservableList<String> recipients = FXCollections.observableArrayList();
		Role currentUserRole = UserManager.getInstance().getCurrentUserRole();
		
		if (currentUserRole == Role.PATIENT)
			userList = DatabaseUtil.getNursesAndDoctors();
		if (currentUserRole == Role.NURSE || currentUserRole == Role.DOCTOR)
			userList = DatabaseUtil.getAllUsers();
		
		for (User user : userList) {
			String roleStr = user.getRole().toString();
			String role = "";
			
			if (roleStr.equals("DOCTOR"))
				role = "Dr.";
			else if (roleStr.equals("NURSE"))
				role = "Nurse";
			else if (roleStr.equals("PATIENT"))
				role = "Patient";
			else
				role = "ERROR";
			
			
			String formattedName = role + " " + user.getFirstName() + " " + user.getLastName() + " | Username: " + user.getUsername();
			recipients.add(formattedName);
		}
		
		recipientCB.setItems(recipients);
	}
	
	// Preselects a default for the recipient
	public void setDefaultOption(String option) {
		recipientCB.setValue(option);
	}
	
	@FXML
	public void goBack(ActionEvent event) throws Exception {
		String fxmlFile = "/FXML/patient_view.fxml";
		SceneManager.loadScene(getClass(), fxmlFile, event);
	}
}
