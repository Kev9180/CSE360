package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ViewMessageController {
	@FXML private Button replyBtn;
	@FXML private Button backBtn;
	@FXML private TextField senderTF;
	@FXML private TextField subjectTF;
	@FXML private TextArea messageText;
	
	@FXML
	public void initialize() {
		
	}
	
	@FXML
	private void replyToMessage(ActionEvent event) throws Exception {
		System.out.println("Send Message button pressed");
		String senderName = senderTF.getText();
		String subject = subjectTF.getText();
		String messageBody = messageText.getText();
		
		int recipientId = getUserIdFromSenderName(senderName);
		int senderId = DatabaseUtil.getCurrentUserId();
		
		if (recipientId != -1 && senderId != -1) {
			DatabaseUtil.sendMessage(senderId, recipientId, subject, messageBody);
			goBack(event);
		}
	}
	
	public void setMessageDetails(String sender, String subject, String messageBody) {
		senderTF.setText(sender);
		senderTF.setEditable(false);
		subjectTF.setText(subject);
		messageText.clear();
		messageText.setText(messageBody);
		messageText.positionCaret(0);
	}
	
	@FXML
	private void goBack(ActionEvent event) throws Exception {
		String fxmlFile = "";
		Role currentUserRole = UserManager.getInstance().getCurrentUserRole();
		
		if (currentUserRole.equals(Role.PATIENT))
			fxmlFile = "/FXML/patient_message_board.fxml";
		else if (currentUserRole.equals(Role.NURSE) || currentUserRole.equals(Role.DOCTOR))
			fxmlFile = "/FXML/nurse_doctor_message_board.fxml";
		else
			fxmlFile = "/FXML/role_selection.fxml";
		
		SceneManager.loadScene(getClass(), fxmlFile, event);
	}
	
	private int getUserIdFromSenderName(String senderName) {
		String[] parts = senderName.split(" ");
		
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
	
	public void clearMessageView() {
		messageText.clear();
	}
}
