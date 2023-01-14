package ch.bfh.bti3001.snailsweeper.apps;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class JavaFxApp extends Application {

//	@Override
//	public void start(Stage stage) throws Exception {
//
//		Label label = new Label("HELLO WORLD");
//		label.setAlignment(Pos.CENTER);
//
//		Scene scene = new Scene();
//		stage.setScene(scene);
//		stage.setWidth(600);
//		stage.setHeight(400);
//
//		stage.setTitle("SnailSweeper");
//		stage.show();
//
//	}
	@Override
	public void start(Stage stage) throws Exception {

	    Label label = new Label("HELLO WORLD");
	    label.setAlignment(Pos.CENTER);

	    // Create a GridPane layout
	    GridPane root = new GridPane();
	    root.setAlignment(Pos.CENTER);
	    root.add(label, 0, 0); // add the label to the grid at (0, 0)

	    Scene scene = new Scene(root); // set the GridPane as the root of the scene
	    stage.setScene(scene);
	    stage.setWidth(600);
	    stage.setHeight(400);

	    stage.setTitle("SnailSweeper");
	    stage.show();
	}



	public static void main(String[] args) {
		launch();
	}
//	public static void main(String[] args) {
//	    launch(JavaFxApp.class);
//	}


}
