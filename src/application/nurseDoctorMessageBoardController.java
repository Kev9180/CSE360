package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class nurseDoctorMessageBoardController {

    @FXML private Button categoryAllButton;
    @FXML private Button categoryCurrentButton;
    @FXML private Button categoryPreviousButton;
    
    @FXML private Label allCount;
    @FXML private Label unreadCount;
    @FXML private Label readCount;
    
	//Handle back button (goes home)
    public void previousScene(ActionEvent event) throws Exception {
    	SceneManager.loadScene(getClass(), "/FXML/role_selection.fxml", event);
    }
    
	//Handle logout button 
    public void logout(ActionEvent event) throws Exception {
    	SceneManager.loadScene(getClass(), "/FXML/role_selection.fxml", event);
    }
    
    
    @FXML
    private void patientListButton(ActionEvent event) throws Exception {
        event.consume();
        System.out.println("patient button");
        SceneManager.loadScene(getClass(), "/FXML/role_selection.fxml", event);
    }
    
    
    /* im sorry i know this is awful */
    
    public void categoryAll(ActionEvent event) throws Exception {
    	// activate categoryAll button
    	categoryAllButton.setId("selectedoption");
    	BorderPane allBP = (BorderPane) categoryAllButton.getGraphic();
    	Label allLabel = (Label) allBP.getLeft();
    	//currentLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
    	allLabel.setTextFill(Color.web("#6039d2"));
    	//currentCount.setFont(Font.font("System", FontWeight.BOLD, 12));
    	allCount.setTextFill(Color.web("#6039d2"));
    	
    	// deactivate categoryCurrent button
    	categoryCurrentButton.setId("unselectedoption");
    	BorderPane currentBP = (BorderPane) categoryCurrentButton.getGraphic();
    	Label currentLabel = (Label) currentBP.getLeft();
    	currentLabel.setTextFill(Color.web("#666666"));
    	unreadCount.setTextFill(Color.web("#666666"));
    	
    	// deactivate categoryPrevious button
    	categoryPreviousButton.setId("unselectedoption");
    	BorderPane previousBP = (BorderPane) categoryPreviousButton.getGraphic();
    	Label previousLabel = (Label) previousBP.getLeft();
    	previousLabel.setTextFill(Color.web("#666666"));
    	readCount.setTextFill(Color.web("#666666"));
    }
    
    public void categoryCurrent(ActionEvent event) throws Exception {
    	// activate categoryCurrent button
    	categoryCurrentButton.setId("selectedoption");
    	BorderPane currentBP = (BorderPane) categoryCurrentButton.getGraphic();
    	Label currentLabel = (Label) currentBP.getLeft();
    	currentLabel.setTextFill(Color.web("#6039d2"));
    	unreadCount.setTextFill(Color.web("#6039d2"));
    	
    	// deactivate categoryAll button
    	categoryAllButton.setId("unselectedoption");
    	BorderPane allBP = (BorderPane) categoryAllButton.getGraphic();
    	Label allLabel = (Label) allBP.getLeft();
    	allLabel.setTextFill(Color.web("#666666"));
    	allCount.setTextFill(Color.web("#666666"));
    	
    	// deactivate categoryPrevious button
    	categoryPreviousButton.setId("unselectedoption");
    	BorderPane previousBP = (BorderPane) categoryPreviousButton.getGraphic();
    	Label previousLabel = (Label) previousBP.getLeft();
    	previousLabel.setTextFill(Color.web("#666666"));
    	readCount.setTextFill(Color.web("#666666"));
   
    }
    
    public void categoryPrevious(ActionEvent event) throws Exception {
    	// activate categoryPrevious button
    	categoryPreviousButton.setId("selectedoption");
    	BorderPane previousBP = (BorderPane) categoryPreviousButton.getGraphic();
    	Label previousLabel = (Label) previousBP.getLeft();
    	previousLabel.setTextFill(Color.web("#6039d2"));
    	readCount.setTextFill(Color.web("#6039d2"));
    	
    	// deactivate categoryCurrent button
    	categoryCurrentButton.setId("unselectedoption");
    	BorderPane currentBP = (BorderPane) categoryCurrentButton.getGraphic();
    	Label currentLabel = (Label) currentBP.getLeft();
    	currentLabel.setTextFill(Color.web("#666666"));
    	unreadCount.setTextFill(Color.web("#666666"));
    	
    	// deactivate categoryAll button
    	categoryAllButton.setId("unselectedoption");
    	BorderPane allBP = (BorderPane) categoryAllButton.getGraphic();
    	Label allLabel = (Label) allBP.getLeft();
    	allLabel.setTextFill(Color.web("#666666"));
    	allCount.setTextFill(Color.web("#666666"));
    }
	

}
