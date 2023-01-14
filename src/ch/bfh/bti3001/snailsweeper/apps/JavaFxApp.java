package ch.bfh.bti3001.snailsweeper.apps;

import ch.bfh.bti3001.snailsweeper.apps.Grid;
import ch.bfh.bti3001.snailsweeper.apps.GridSerializer;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class JavaFxApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Create a grid pane to hold the game board
        GridPane gridPane = new GridPane();

        // Add elements to the grid pane to display the game board
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Button button = new Button();
                button.setOnMouseClicked(event -> {
                    if (event.isControlDown()) {
                    	Grid.toggleFlag(i, j);
                    } else {
                        Grid.uncover(i, j);
                    }
                });
                gridPane.add(button, i, j);
            }
        }

        // Create a scene and set it as the scene for the primary stage
        Scene scene = new Scene(gridPane, 600, 400);
        stage.setScene(scene);

        // Set the title of the primary stage
        stage.setTitle("SnailSweeper");

        // Show the primary stage
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
