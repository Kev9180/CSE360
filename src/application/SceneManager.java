package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

public class SceneManager {
	// used if triggered by button
	
	// example use case:
	// SceneManager.loadScene(getClass(), "/FXML/page.fxml", event);
    public static void loadScene(Class<?> clazz, String fxmlFile, ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(clazz.getResource(fxmlFile));
        Parent root = loader.load();
        loader.getController();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(clazz.getResource("/CSS/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    
    // used if you want to move to another scene, but don't have an actionEvent to pass in
    // instead, pass in any object in your scene
    // this is needed so that javafx can identify which window it needs to perform the method on
    
	// example use case:
	// SceneManager.loadScene(getClass(), "/FXML/page.fxml", backButton);
    public static void loadScene(Class<?> clazz, String fxmlFile, Node node) throws Exception {
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(clazz.getResource(fxmlFile));
        Parent root = loader.load();
        loader.getController();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(clazz.getResource("/CSS/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    
    // in complex scenes, replace just the right side of the scene, leaving left navbar the same
    // returns a controller object, please cast to your Controller filename
    // for example: 
    // NurseVisitHistoryController controller = (NurseVisitHistoryController) replaceRHS(getClass(), containerToReplace, fxmlString);
    // controller.initialize();
    public static Object replaceRHS(Class<?> clazz, Pane containerToReplace, String fxmlString) {
    	try {
	    	FXMLLoader loader = new FXMLLoader(clazz.getResource(fxmlString));
	    	Pane parentContainer = (Pane) containerToReplace.getParent();
	    	int indexToReplace = parentContainer.getChildren().indexOf(containerToReplace);
	    	parentContainer.getChildren().remove(indexToReplace);
	    	parentContainer.getChildren().add(indexToReplace, loader.load());
	    	Object controller = loader.getController();
	    	return controller;
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    // if your containerToReplace has no parent, you'll need to pass in a parent manually:
    public static Object replaceRHS(Class<?> clazz, Pane parentContainer, Node containerToReplace, String fxmlString) {
    	try {
	    	FXMLLoader loader = new FXMLLoader(clazz.getResource(fxmlString));
	    	int indexToReplace = parentContainer.getChildren().indexOf(containerToReplace);
	    	parentContainer.getChildren().remove(indexToReplace);
	    	parentContainer.getChildren().add(indexToReplace, loader.load());
	    	Object controller = loader.getController();
	    	return controller;
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }
}