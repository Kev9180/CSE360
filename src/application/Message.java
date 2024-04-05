package application;

import java.time.LocalDate;

// a Message that exists only within MessageThreads. 
// contains info about who sent the message. 
public class Message {
	private String message;
	private LocalDate date;
	private User sender;
	public Message(String message, User sender) {
		this.message = message;
		this.sender = sender;
		this.date = LocalDate.now();
	}
	
	public String getMessage() {
		return message;
	}

	public LocalDate getDate() {
		return date;
	}
}
