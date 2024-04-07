package application;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PatientMessageBoardController {
	@FXML private VBox messageContainer;

    @FXML private Button newMessageBtn;
    
    @FXML private TableView<MessageThread> messagesTable;
    @FXML private TableColumn<MessageThread, String> senderColumn;
    @FXML private TableColumn<MessageThread, String> subjectColumn;
    
    @FXML private Label allCount;
    @FXML private Label unreadCount;
    @FXML private Label readCount;
    
    @FXML
    public void initialize() {
    	initializeMessagesTable();
    }
    
    // Method to initialize the messages table
    private void initializeMessagesTable() {
    	senderColumn.setCellValueFactory(cellData -> cellData.getValue().senderProperty());
    	subjectColumn.setCellValueFactory(cellData -> cellData.getValue().subjectProperty());
    	
    	loadMessages();
    	
    	messagesTable.setPlaceholder(new Label("No messages!"));
    }
    
    //
    public void composeNewMessage() {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/compose_message.fxml"));
    		Parent composeMessageRoot = loader.load();
    		
    		messageContainer.getChildren().clear();
    		messageContainer.getChildren().add(composeMessageRoot);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	System.out.println("New Message button pressed");
    }
    
    // Method to load the messages into the table
    private void loadMessages() {
    	UserManager userManager = UserManager.getInstance();
    	User currentUser = userManager.getCurrentUser();
    	int currentUserId = DatabaseUtil.getUserId(currentUser.getUsername());
    	
    	Map<Integer, List<Message>> threads = DatabaseUtil.getMessagesForUser(currentUserId);
    	
    	ObservableList<MessageThread> messageThreads = FXCollections.observableArrayList();
    	
    	threads.forEach((threadId, messages) -> {
    		if (!messages.isEmpty()) {
    			Message lastMessage = messages.get(0);
    			String sender = DatabaseUtil.getUsernameById(lastMessage.getSenderId());
    			String subject = lastMessage.getSubject();
    			
    			messageThreads.add(new MessageThread(sender, subject, threadId));
    		}
    	});
    	
    	messagesTable.setItems(messageThreads);
    }
    
	//Handle back button (goes home)
    public void previousScene(ActionEvent event) throws Exception {
    	loadScene("/FXML/patient_view.fxml", event);
    }
    
	//Handle logout button 
    public void logout(ActionEvent event) throws Exception {
        loadScene("/FXML/role_selection.fxml", event);
    }
    
    @FXML
    private void appointmentButton(ActionEvent event) {
        event.consume();
        System.out.println("appointment button");
    }
    
    @FXML
    private void aboutUsButton(ActionEvent event) {
        event.consume();
        System.out.println("about us button");
    }
    
    @FXML
    private void refillButton(ActionEvent event) {
        event.consume();
        System.out.println("refill button");
    }
    
    @FXML
    private void settingButton(ActionEvent event) {
        event.consume();
        System.out.println("setting button");
    }
    
    @FXML
    private void mainButton(ActionEvent event) throws Exception {
        event.consume();
        System.out.println("main button");
        loadScene("/FXML/patient_view.fxml", event);
    }
    	
    //Method to load the scene
    private void loadScene(String fxmlFile, ActionEvent event) throws Exception {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
    	Parent root = loader.load();
    	loader.getController();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/CSS/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
