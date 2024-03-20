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
		try {		
			Parent root = FXMLLoader.load(getClass().getResource("role_selection.fxml"));
	        primaryStage.setTitle("Role Selection");
	        primaryStage.setScene(new Scene(root, 800, 600));
	        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
