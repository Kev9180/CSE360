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
			System.out.println("Message sent successfully.");
			clearForm();
		} else {
			System.out.println("Failed to send the message. Invalid recipient or sender.");
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
		
		String role = parts[0];
		String firstName = parts[1];
		String lastName = parts[2];
		
		return DatabaseUtil.getUserIdByNameAndRole(firstName, lastName, role);
	}
	
	public void goBack(ActionEvent event) throws Exception {
		System.out.println("Back button pressed");
		
		String fxmlFile = "/FXML/patient_message_board.fxml";
		SceneManager.loadScene(getClass(), fxmlFile, event);
		
	}
	
	private void loadRecipientList() {
		ObservableList<String> recipients = FXCollections.observableArrayList();
		
		List<User> userList = DatabaseUtil.getNursesAndDoctors();
		
		for (User user : userList) {
			String formattedName = user.getRole() + " " + user.getFirstName() + " " + user.getLastName();
			recipients.add(formattedName);
		}
		
		recipientCB.setItems(recipients);
	}

}
