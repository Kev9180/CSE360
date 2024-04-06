package application;

import java.util.ArrayList;
import java.util.List;

/*
 * 		To get/send messages from a file other than this one:
 * 		MessageThread thread = MessageDatabase.getInstance().getThread(threadID);
 * 
 * 		Supported methods:
 * 		- composeThread(...): 
 * 			- creates a new email thread and subscribes involved users
 * 			- all threads are composed with one initial message
 * 
 * 		- replyThread(...):
 * 			- replies to a thread and resubscribes involved users
 * 			
 * 		- getThread(threadID): 
 * 			- returns thread at index threadID.
 * 			- threadID is used so a sender deleting a thread won't delete it for the recipient
 */

// Holds list of all threads
// Upon creation of message/thread, distribute to recipient/sender MessageBoard
public class MessageManager {
	private static MessageManager instance;
	private List<MessageThread> threads = new ArrayList<>();
	
	private MessageManager() {}
	
	public static synchronized MessageManager getInstance() {
		if (instance == null) {
			instance = new MessageManager();
		}
		
		return instance;
	}
	
	// creates a new thread and adds it to User's inboxes
	public void composeThread(String subject, User recipient, User sender, String message) {
		Message myMessage = new Message(message, sender);
		MessageThread myThread = new MessageThread(message, recipient, sender, myMessage);
		threads.add(myThread);
		
		int threadID = threads.indexOf(myThread);
		
		sender.getMessageBoard().subscribeThread(threadID);
		recipient.getMessageBoard().subscribeThread(threadID);
	}
	
	public void replyThread(int threadID, User sender, String message) {
		Message myMessage = new Message(message, sender);
		MessageThread myThread = getThread(threadID);
		myThread.addMessage(myMessage);
		
		// resubscribe users to thread
		sender.getMessageBoard().subscribeThread(threadID);
		
		User recipient = myThread.getRecipient();
		recipient.getMessageBoard().subscribeThread(threadID);
	}
	
	public MessageThread getThread(int threadID) {
		return threads.get(threadID);
	}
}
