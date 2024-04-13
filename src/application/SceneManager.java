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
	// returns controller, you must cast it:
	// Controller myController = (Controller) SceneManager.loadScene(...);
    public static Object loadScene(Class<?> clazz, String fxmlFile, ActionEvent event) {
		try {
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        FXMLLoader loader = new FXMLLoader(clazz.getResource(fxmlFile));
	        Parent root;
			root = loader.load();
	        loader.getController();
	        Scene scene = new Scene(root, 800, 600);
	        scene.getStylesheets().add(clazz.getResource("/CSS/styles.css").toExternalForm());
	        stage.setScene(scene);
	        stage.show();
	        return loader.getController();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    
    // used if you want to move to another scene, but don't have an actionEvent to pass in
    // instead, pass in any object in your scene
    // this is needed so that javafx can identify which window it needs to perform the method on
    
	// example use case:
	// SceneManager.loadScene(getClass(), "/FXML/page.fxml", backButton);
    public static Object loadScene(Class<?> clazz, String fxmlFile, Node node) {
		try {
	        Stage stage = (Stage) node.getScene().getWindow();
	        FXMLLoader loader = new FXMLLoader(clazz.getResource(fxmlFile));
	        Parent root;
			root = loader.load();
	        loader.getController();
	        Scene scene = new Scene(root, 800, 600);
	        scene.getStylesheets().add(clazz.getResource("/CSS/styles.css").toExternalForm());
	        stage.setScene(scene);
	        stage.show();
	        return loader.getController();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    
    // swaps out a part of a scene for another scene
    // useful to keep the left bar constant, while only swapping the actual content
    // @param Pane parentContainer: the container which you want the children elements replaced
    // @param indexToReplace: index of the children to replace
    // SceneManager.replaceContainerElement(getClass(), parentContainer, 2, "/FXML/scene_to_swap_to.fxml");
    public static Object replaceContainerElement(Class<?> clazz, Pane parentContainer, int indexToReplace, String fxmlString) {
    	try {
    		FXMLLoader loader = new FXMLLoader(clazz.getResource(fxmlString));
	    	parentContainer.getChildren().remove(indexToReplace);
	    	parentContainer.getChildren().add(indexToReplace, loader.load());
	    	Object controller = loader.getController();
	    	return controller;
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    // adds an element to a container at an index (useful for lists)
    public static Object addContainerElement(Class<?> clazz, Pane parentContainer, int indexToAdd, String fxmlString) {
    	try {
    		FXMLLoader loader = new FXMLLoader(clazz.getResource(fxmlString));
	    	parentContainer.getChildren().add(indexToAdd, loader.load());
	    	Object controller = loader.getController();
	    	return controller;
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    // empties out a container completely and inserts a new container
    public static Object setContainerElement(Class<?> clazz, Pane parentContainer, String fxmlString) {
    	try {
    		FXMLLoader loader = new FXMLLoader(clazz.getResource(fxmlString));
    		parentContainer.getChildren().clear();
	    	parentContainer.getChildren().add(loader.load());
	    	Object controller = loader.getController();
	    	return controller;
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }

}