package application;

import javafx.beans.property.SimpleStringProperty;

public class MessageThread {
	private final SimpleStringProperty sender;
	private final SimpleStringProperty subject;
	private final int threadId;
	
	// Setters
	public MessageThread(String sender, String subject, int threadId) {
		this.sender = new SimpleStringProperty(sender);
		this.subject = new SimpleStringProperty(subject);
		this.threadId = threadId;
	}
	
	// Getters
	public String getSender() {
		return sender.get();
	}
	
	public SimpleStringProperty senderProperty() {
		return sender;
	}
	
	public String getSubject() {
		return subject.get();
	}
	
	public SimpleStringProperty subjectProperty() {
		return subject;
	}
	
	public int getThreadId() {
		return this.threadId;
	}
}
