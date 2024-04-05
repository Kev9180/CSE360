package application;

import java.util.ArrayList;
import java.util.List;

public class MessageBoard {
	// Holds all message threads for a user
	private List<Integer> threadIDs;
	private MessageManager myMessageManager;
	private User sender;
	
	public MessageBoard(User sender) {
		this.threadIDs = new ArrayList<>();
		this.myMessageManager  = MessageManager.getInstance();
		this.sender = sender;
	}
	
	public void composeThread(String subject, User recipient, String message) {
		myMessageManager.composeThread(subject, recipient, sender, message);
	}
	
	public void replyThread(int threadID, String message) {
		myMessageManager.replyThread(threadID, sender, message);
	}
	
	// Deletes the thread for the user
	// Does not delete the thread for the recipient
	public void deleteThread(int threadID) {
		threadIDs.remove(threadID);
	}
	
	public void subscribeThread(int threadID) {
		threadIDs.add(threadID);
	}
	
	// returns a list of all threads with user's threadIDs
	public List<MessageThread> getThreads() {
		List<MessageThread> result = new ArrayList<>();
		for (int threadID : threadIDs) {
			result.add(myMessageManager.getThread(threadID));
		}
		
		return result;
	}
}
