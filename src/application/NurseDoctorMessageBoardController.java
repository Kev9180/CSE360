package application;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class NurseDoctorMessageBoardController {
	@FXML private Pane messageContainer;

    @FXML private Button newMessageBtn;
    
    @FXML private TableView<MessageThread> messagesTable;
    @FXML private TableColumn<MessageThread, String> senderColumn;
    @FXML private TableColumn<MessageThread, String> subjectColumn;
    @FXML private TableColumn<MessageThread, Void> actionsColumn;
    
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
    	
    	actionsColumn.setCellFactory(param -> new TableCell<>() {
    		private final Button openBtn = new Button("Open");
    		private final Button deleteBtn = new Button("Delete");
    		private final HBox pane = new HBox(5, openBtn, deleteBtn);
    		
    		{
    			openBtn.setOnAction(event -> {
    				MessageThread thread = getTableView().getItems().get(getIndex());
    				openMessageThread(thread);
    			});
    			
    			deleteBtn.setOnAction(event -> {
    				MessageThread thread = getTableView().getItems().get(getIndex());
    				deleteMessageThread(thread);
    				getTableView().getItems().remove(thread);
    			});
    		}
    		
    		@Override
    		protected void updateItem(Void item, boolean empty) {
    			super.updateItem(item, empty);
    			setGraphic(empty ? null : pane);
    		}
    	});
    	
    	loadMessages();
    	
    	messagesTable.setPlaceholder(new Label("No messages!"));
    }
	
	// Loads and displays the gui for a new message to be written
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
    
    // Checks if a message thread already exists within messages, if found return true
    private boolean isDuplicateMessage(ObservableList<MessageThread> messageThreads, int threadId) {
    	return messageThreads.stream().anyMatch(thread -> thread.getThreadId() == threadId);
    	//return false;
    }
    
    // Method to load the messages into the table
    private void loadMessages() {
    	UserManager userManager = UserManager.getInstance();
    	User currentUser = userManager.getCurrentUser();
    	int currentUserId = DatabaseUtil.getUserId(currentUser.getUsername());
    	
    	Map<Integer, List<Message>> threads = DatabaseUtil.getMessagesForUser(currentUserId);
    	
    	ObservableList<MessageThread> messageThreads = FXCollections.observableArrayList();
    	
    	threads.forEach((threadId, messages) -> {
    		if (!messages.isEmpty() && !isDuplicateMessage(messageThreads, threadId)) {
    			Message lastMessage = messages.get(0);
    			String senderUsername = DatabaseUtil.getUsernameById(lastMessage.getSenderId());
    			User msgSender = DatabaseUtil.getUserByUsername(senderUsername);
    			
    			String senderRole = "";
    			
    			if (msgSender.getRole().equals(Role.DOCTOR))
    				senderRole = "Dr. ";
    			else if (msgSender.getRole().equals(Role.NURSE))
    				senderRole = "Nurse ";
    			else if (msgSender.getRole().equals(Role.PATIENT))
    				senderRole = "Patient ";
    			else
    				senderRole = "ERROR ";
    			
    			String senderName = msgSender.getFullName();
    			String subject = lastMessage.getSubject();
    			
    			String formattedSender = senderRole + senderName;
    			
    			messageThreads.add(0, new MessageThread(formattedSender, subject, threadId));
    		}
    	});
    	
    	messagesTable.setItems(messageThreads);
    }
    
	// Handle back button (goes home)
    public void previousScene(ActionEvent event) throws Exception {
    	UserManager userManager = UserManager.getInstance();
    	userManager.logout();
    	SceneManager.loadScene(getClass(), "/FXML/role_selection.fxml", event);
    }
    
	// Handle logout button 
    public void logout(ActionEvent event) throws Exception {
    	UserManager userManager = UserManager.getInstance();
    	userManager.logout();
    	SceneManager.loadScene(getClass(), "/FXML/role_selection.fxml", event);
    }
    
    // Prevents further processing of a button click, logs a message, then navigates to a new screen using a scene manager
    @FXML
    private void patientListButton(ActionEvent event) throws Exception {
        event.consume();
        System.out.println("patient button");
        SceneManager.loadScene(getClass(), "/FXML/role_selection.fxml", event);
    }
    
    // Method to open the message thread, load all messages from the thread, and concat them all together
    private void openMessageThread(MessageThread thread) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/view_message.fxml"));
    		Parent composeMessageRoot = loader.load();
    		
    		ViewMessageController controller = loader.getController();
    		    		
    		List<Message> messages = DatabaseUtil.getMessagesByThreadId(thread.getThreadId());
    		
    		messages.sort((m1, m2) -> m2.getTimestamp().compareTo(m1.getTimestamp()));
    		
    		StringBuilder fullThreadText = new StringBuilder();
    		
    		for (Message message : messages) {
    			String senderUsername = DatabaseUtil.getUsernameById(message.getSenderId());
    			User senderUser = DatabaseUtil.getUserByUsername(senderUsername);
    			String role = "";
    			
    			switch (senderUser.getRole()) {
    			case DOCTOR:
    				role = "Dr. ";
    				break;
    			case NURSE:
    				role = "Nurse ";
    				break;
    			case PATIENT:
    				role = "Patient ";
    				break;
    			}
    			
    			String formattedName = role + senderUser.getFullName();
    			String messageFormat = String.format("\n\n---------------------------------------------------------\nFrom: %s\nAt: %s\n%s\n\n", formattedName, message.getTimestamp(), message.getBody());
    			fullThreadText.append(messageFormat);
    		}
    		
    		if (!messages.isEmpty()) {
    			Message lastMessage = messages.get(messages.size() - 1);
    			
    			String senderUsername = DatabaseUtil.getUsernameById(lastMessage.getSenderId());
    			User senderUser = DatabaseUtil.getUserByUsername(senderUsername);
    			String role = "";
    			
    			switch (senderUser.getRole()) {
    			case DOCTOR:
    				role = "Dr. ";
    				break;
    			case NURSE:
    				role = "Nurse ";
    				break;
    			case PATIENT:
    				role = "Patient ";
    				break;
    			}
    			
    			String formattedName = role + senderUser.getFullName();
    			
    			controller.setMessageDetails(formattedName, messages.get(0).getSubject(), fullThreadText.toString());
    		}
    		
    		messageContainer.getChildren().clear();
    		messageContainer.getChildren().add(composeMessageRoot);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    // Method to delete a message thread
    private void deleteMessageThread(MessageThread thread) {
    	int threadId = thread.getThreadId();
    	
    	DatabaseUtil.deleteMessageThread(threadId);
    	
    	loadMessages();
    }
}
