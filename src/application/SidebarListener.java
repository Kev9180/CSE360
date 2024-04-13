package application;

import javafx.event.ActionEvent;

public interface SidebarListener {
	// each class that uses the sidebar will implement their own way to handle each click 
	// param1: enum Sidebar that represents the button clicked
	public void handleClick(Sidebar action, ActionEvent event);
	public void logout(ActionEvent event);
}
