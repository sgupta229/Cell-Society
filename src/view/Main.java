package view;

import javafx.application.Application;
import javafx.stage.Stage;
import java.util.ResourceBundle;

public class Main extends Application {

    // Simulation instance variables
    private Stage primaryStage;

    // Properties
    private ResourceBundle myResources;
    private static final String propFile = "resources";

    /**
     * Starts the game, sets the resources file, creates the scene and launches the stage
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        myResources = ResourceBundle.getBundle(propFile);
        var viewer = new SimulationViewer(myResources, primaryStage);
        primaryStage.setScene(viewer.getSplashScene());
        primaryStage.setTitle(myResources.getString("TITLE"));
        primaryStage.show();
    }


    /**
     * Launches the game
     * @param args
     */
    public static void main (String[] args) {
        launch(args);
    }
}
