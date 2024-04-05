package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

// this comment is a test to see if I setup git correctly! 

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		DatabaseUtil.initializeDatabase();
		//DatabaseUtil.preloadUsers();
		
		try {		
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/role_selection.fxml"));
	        primaryStage.setTitle("Role Selection");
	        Scene scene = new Scene(root, 800, 600);
	        //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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
