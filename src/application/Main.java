package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		DatabaseUtil.initializeDatabase();
		DatabaseUtil.initializeMessageDatabase();
		// DatabaseUtil.preloadUsers();
		
		// Gets role selection fxml file and sets as root screen to be the opener
		try {		
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/role_selection.fxml"));
	        primaryStage.setTitle("Role Selection");
	        Scene scene = new Scene(root, 800, 600);
	        scene.getStylesheets().add(getClass().getResource("/CSS/styles.css").toExternalForm());
	        primaryStage.setScene(scene);
	        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
