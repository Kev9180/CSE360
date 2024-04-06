package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
}