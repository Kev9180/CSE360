package application;

import java.util.ArrayList;
import java.util.List;

public class MessageThread {
	
	private String subject;
	private User recipient;
	private User sender;
	private List<Message> messages = new ArrayList<>();
	
	public MessageThread(String subject, User recipient, User sender, Message message) {
		this.subject = subject;
		this.recipient = recipient;
		this.sender = sender;
		addMessage(message);
	}
	
	public void addMessage(Message message) {
		messages.add(message);
	}
	
	public String getSubject() {
		return subject;
	}
	
	public User getRecipient() {
		return recipient;
	}
	
	public List<Message> getMessages() {
		return messages;
	}
}
