package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

// Controller class for viewing and replying to messages in the UI
public class ViewMessageController {
    @FXML private Button replyBtn; // Button for replying to the message
    @FXML private Button backBtn; // Button for navigating back to the previous view
    @FXML private TextField senderTF; // Text field displaying the sender of the message
    @FXML private TextField subjectTF; // Text field displaying the subject of the message
    @FXML private TextArea messageText; // Text area displaying the body of the message
    
    // Method called during initialization of the controller
    @FXML
    public void initialize() {
        
    }
    
    // Method to handle the action of replying to the message
    @FXML
    private void replyToMessage(ActionEvent event) throws Exception {
        System.out.println("Send Message button pressed");
        String senderName = senderTF.getText();
        String subject = subjectTF.getText();
        String messageBody = messageText.getText();
        
        // Get the recipient's user ID based on the sender's name
        int recipientId = getUserIdFromSenderName(senderName);
        int senderId = DatabaseUtil.getCurrentUserId();
        
        // If both sender and recipient IDs are valid, send the message and navigate back
        if (recipientId != -1 && senderId != -1) {
            DatabaseUtil.sendMessage(senderId, recipientId, subject, messageBody);
            goBack(event);
        }
    }
    
    // Method to set the details of the message being viewed
    public void setMessageDetails(String sender, String subject, String messageBody) {
        senderTF.setText(sender);
        senderTF.setEditable(false);
        subjectTF.setText(subject);
        messageText.clear();
        messageText.setText(messageBody);
        messageText.positionCaret(0);
    }
    
    // Method to navigate back to the previous view
    public void goBack(ActionEvent event) throws Exception {
        String fxmlFile = "";
        Role currentUserRole = UserManager.getInstance().getCurrentUserRole();
        
        // Determine the appropriate FXML file based on the current user's role
        if (currentUserRole.equals(Role.PATIENT))
            fxmlFile = "/FXML/patient_view.fxml";
        else if (currentUserRole.equals(Role.NURSE) || currentUserRole.equals(Role.DOCTOR)) {
            NurseDoctorPatientVisitController controller = (NurseDoctorPatientVisitController) SceneManager.loadScene(getClass(), "/FXML/nurse_doctor_patient_list.fxml", event);
            controller.messageButton(new ActionEvent());
            return;
        }
        else
            fxmlFile = "/FXML/role_selection.fxml";
        
        // Load the appropriate scene
        SceneManager.loadScene(getClass(), fxmlFile, event);
    }
    
    // Method to retrieve the user ID of the recipient based on the sender's name
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
    
    // Method to clear the message view
    public void clearMessageView() {
        messageText.clear();
    }
}
