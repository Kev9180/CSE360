package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class NurseViewController {

    @FXML private Button categoryAllButton;
    @FXML private Button categoryCurrentButton;
    @FXML private Button categoryPreviousButton;
    
    @FXML private Label allCount;
    @FXML private Label currentCount;
    @FXML private Label previousCount;
    
	//Handle back button (goes home)
    public void previousScene(ActionEvent event) throws Exception {
        loadScene("/FXML/role_selection.fxml", event);
    }
    
	//Handle logout button 
    public void logout(ActionEvent event) throws Exception {
        loadScene("/FXML/role_selection.fxml", event);
    }
    
    public void messageButton(ActionEvent event) throws Exception {
        loadScene("/FXML/nurse_doctor_message_board.fxml", event);
    }
    
    public void selectPatients(ActionEvent event) throws Exception {
    	
    }
    
    public void selectMessages(ActionEvent event) throws Exception {
    	
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
    	currentCount.setTextFill(Color.web("#666666"));
    	
    	// deactivate categoryPrevious button
    	categoryPreviousButton.setId("unselectedoption");
    	BorderPane previousBP = (BorderPane) categoryPreviousButton.getGraphic();
    	Label previousLabel = (Label) previousBP.getLeft();
    	previousLabel.setTextFill(Color.web("#666666"));
    	previousCount.setTextFill(Color.web("#666666"));
    }
    
    public void categoryCurrent(ActionEvent event) throws Exception {
    	// activate categoryCurrent button
    	categoryCurrentButton.setId("selectedoption");
    	BorderPane currentBP = (BorderPane) categoryCurrentButton.getGraphic();
    	Label currentLabel = (Label) currentBP.getLeft();
    	currentLabel.setTextFill(Color.web("#6039d2"));
    	currentCount.setTextFill(Color.web("#6039d2"));
    	
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
    	previousCount.setTextFill(Color.web("#666666"));
   
    }
    
    public void categoryPrevious(ActionEvent event) throws Exception {
    	// activate categoryPrevious button
    	categoryPreviousButton.setId("selectedoption");
    	BorderPane previousBP = (BorderPane) categoryPreviousButton.getGraphic();
    	Label previousLabel = (Label) previousBP.getLeft();
    	previousLabel.setTextFill(Color.web("#6039d2"));
    	previousCount.setTextFill(Color.web("#6039d2"));
    	
    	// deactivate categoryCurrent button
    	categoryCurrentButton.setId("unselectedoption");
    	BorderPane currentBP = (BorderPane) categoryCurrentButton.getGraphic();
    	Label currentLabel = (Label) currentBP.getLeft();
    	currentLabel.setTextFill(Color.web("#666666"));
    	currentCount.setTextFill(Color.web("#666666"));
    	
    	// deactivate categoryAll button
    	categoryAllButton.setId("unselectedoption");
    	BorderPane allBP = (BorderPane) categoryAllButton.getGraphic();
    	Label allLabel = (Label) allBP.getLeft();
    	allLabel.setTextFill(Color.web("#666666"));
    	allCount.setTextFill(Color.web("#666666"));
    }
	
    //Method to load the scene
    private void loadScene(String fxmlFile, ActionEvent event) throws Exception {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
    	Parent root = loader.load();
    	loader.getController();
    	stage.setScene(new Scene(root, 800, 600));
    	stage.show();
    }
}
