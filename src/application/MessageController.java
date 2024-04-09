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
	
	public void sendMessage() {
		System.out.println("Send Message button pressed");
		String recipientName = recipientCB.getValue();
		String subject = subjectTF.getText();
		String messageBody = messageText.getText();
		
		int recipientId = getUserIdFromRecipientName(recipientName);
		int senderId = DatabaseUtil.getCurrentUserId();
		
		if (recipientId != -1 && senderId != -1) {
			DatabaseUtil.sendMessage(senderId, recipientId, subject, messageBody);
			clearForm();
		}
	}
	
	public void clearForm() {
		recipientCB.setValue(null);
		subjectTF.setText("");
		messageText.setText("");
	}
	
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
	
	public void setDefaultOption(String option) {
		recipientCB.setValue(option);
	}
	
	@FXML
	public void goBack(ActionEvent event) throws Exception {
		String fxmlFile = "/FXML/patient_view.fxml";
		SceneManager.loadScene(getClass(), fxmlFile, event);
	}
}
