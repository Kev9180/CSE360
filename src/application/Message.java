package application;

// a Message that exists only within MessageThreads. 
// contains info about who sent the message. 
public class Message {
	public int id;
	public int senderId;
	public int recipientId;
	public String subject;
	public String body;
	public String timestamp;
	public int readStatus;
	public int threadId;
	
	public Message(int id, int senderId, int recipientId, String subject, String body, String timestamp, int readStatus, int threadId) {
		this.id = id;
		this.senderId = senderId;
		this.recipientId = recipientId;
		this.subject = subject;
		this.body = body;
		this.timestamp = timestamp;
		this.readStatus = readStatus;
		this.threadId = threadId;
	}
	
	// Getters
    public int getId() {
        return id;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getReadStatus() {
        return readStatus;
    }

    public int getThreadId() {
        return threadId;
    }
	
//	private String message;
//	private LocalDate date;
//	private User sender;
//	public Message(String message, User sender) {
//		this.message = message;
//		this.sender = sender;
//		this.date = LocalDate.now();
//	}
//	
//	public String getMessage() {
//		return message;
//	}
//
//	public LocalDate getDate() {
//		return date;
//	}
}
