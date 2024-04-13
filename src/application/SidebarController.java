package application;
import java.util.List;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

/* 
 * To use in your scene, first:
 * - make sure you have a container you want to put the navbar into
 * 
 * - add it into your scene:
 * 		' SidebarController controller = (SidebarController) 
 * 		  SceneManager.addContainerElement(getClass, parentContainer, indexToAdd, "/FXML/sidebar.fxml" '
 * 
 * - make a list of sidebar enums which tells the sidebar which buttons to display:
 * 		' List<Sidebar> buttons = {Sidebar.PATIENTLIST, Sidebar... etc}; '
 * 
 * - call the initializer in this class:
 * 		' controller.setButtons(buttons); '
 * 
 * - ensure your class implements SidebarListener, and implement the needed methods
 */

public class SidebarController {

	@FXML private VBox container;
	
	@FXML private Button patientListB;
	@FXML private Button patientResultsB;
	@FXML private Button messagingB;
    @FXML private Button appointmentsB;
    @FXML private Button myVisitsB;
    @FXML private Button myInfoB;
    
    // controller class
    SidebarListener listener;
    
    // deletes unnecessary elements, then sets appropriate buttons given the input list
    public void setButtons(List<Sidebar> buttons) {
    	
    	// clear sidebar of unecessary items
    	if (!buttons.contains(Sidebar.PATIENTLIST)) {
    		container.getChildren().remove(patientListB);
    	}
    	if (!buttons.contains(Sidebar.PATIENTRESULTS)) {
    		container.getChildren().remove(patientResultsB);
    	}
    	if (!buttons.contains(Sidebar.MESSAGES)) {
    		container.getChildren().remove(messagingB);
    	}
    	if (!buttons.contains(Sidebar.APPOINTMENTS)) {
    		container.getChildren().remove(appointmentsB);
    	}
    	if (!buttons.contains(Sidebar.MYVISITS)) {
    		container.getChildren().remove(myVisitsB);
    	}
    	if (!buttons.contains(Sidebar.MYINFO)) {
    		container.getChildren().remove(myInfoB);
    	}
    	
    	// defer until after rendering, or else children will be undefined
    	Platform.runLater(() -> {
    		setSelected(0);
    	});
    	
    }
    
    public void setSelected(int currentlySelected) {
    	for (int i = 0; i < container.getChildren().size(); i ++) {
    		Button button = (Button) container.getChildren().get(i);
    		if (currentlySelected == i) {
    			setButtonSelect(button);
    		}
    		else {
    			setButtonDeselect(button);
    		}
    	}
    }
    
    private void setButtonDeselect(Button button) {
    	// get references to things we need to change: icon, label text
    	HBox hbox = (HBox) button.getChildrenUnmodifiable().get(0);
    	StackPane sp = (StackPane) hbox.getChildren().get(0);
    	SVGPath icon = (SVGPath) sp.getChildren().get(0);
    	Label label = (Label) hbox.getChildren().get(1);
    	
    	icon.setFill(Color.web("#777777"));
    	label.setTextFill(Color.web("#666666"));
    	button.setEffect(null);
    	button.setId("unselectedbutton");
    }
    
    private void setButtonSelect(Button button) {
    	// get references to things we need to change: icon, label text
    	HBox hbox = (HBox) button.getChildrenUnmodifiable().get(0);
    	StackPane sp = (StackPane) hbox.getChildren().get(0);
    	SVGPath icon = (SVGPath) sp.getChildren().get(0);
    	Label label = (Label) hbox.getChildren().get(1);
    	
    	icon.setFill(Color.web("#6039d2"));
    	label.setTextFill(Color.web("#222222"));
    	
    	DropShadow shadow = new DropShadow();
    	shadow.setBlurType(BlurType.GAUSSIAN);
    	shadow.setWidth(16);
    	shadow.setHeight(16);
    	shadow.setRadius(7.5);
    	shadow.setColor(Color.web("rgba(0, 0, 0, 0.05)"));
    	
    	button.setEffect(shadow);
    	button.setId("selectedbutton");
    }

    @FXML
    void handlePatientListClicked(ActionEvent event) {
    	// select button
    	setSelected(container.getChildren().indexOf(patientListB));
    	
    	// call listener method
    	listener.handleClick(Sidebar.PATIENTLIST, event);    	
    }
    
    @FXML
    void handlePatientResultsClicked(ActionEvent event) {
    	// select button
    	setSelected(container.getChildren().indexOf(patientResultsB));
    	
    	// call listener method
    	listener.handleClick(Sidebar.PATIENTRESULTS, event);
    }

    @FXML
    void handleMessagingClicked(ActionEvent event) {
    	// select button
    	setSelected(container.getChildren().indexOf(messagingB));
    	
    	// call listener method
    	listener.handleClick(Sidebar.MESSAGES, event);
    }
    
    @FXML
    void handleAppointmentsClicked(ActionEvent event) {
    	// select button
    	setSelected(container.getChildren().indexOf(appointmentsB));
    	
    	// call listener method
    	listener.handleClick(Sidebar.APPOINTMENTS, event);
    }

    @FXML
    void handleMyVisitsClicked(ActionEvent event) {
    	// select button
    	setSelected(container.getChildren().indexOf(myVisitsB));
    	
    	// call listener method
    	listener.handleClick(Sidebar.MYVISITS, event);
    }
    
    @FXML
    void handleMyInformationClicked(ActionEvent event) {
    	// select button
    	setSelected(container.getChildren().indexOf(myInfoB));
    	
    	// call listener method
    	listener.handleClick(Sidebar.MYINFO, event);
    }

    @FXML
    void logout(ActionEvent event) {
    	listener.logout(event);
    }
    
    public void setListener(SidebarListener listener) {
    	this.listener = listener;
    }

}
