package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ResourceBundle;

/**
 * @author Samuel Chan, William Francis, Sahil Gupta
 * @purpose Sets up buttons, splash screen, and the main scene itself
 * @assumptions SimulationViewer actually creates an instance of the class in the beginning to set up the simulation and allow user interaxction
 */

public class SceneMaker {

    private final ResourceBundle myResources;
    private static final String configFile = "config";
    private final SimulationViewer viewer;
    private static final String STYLESHEET = "styles.css";
    private final int vboxVar;

    public SceneMaker(SimulationViewer view,ResourceBundle resourceBundle) {
        myResources = resourceBundle;
        viewer = view;
        vboxVar = Integer.parseInt(myResources.getString("VBOX_VAR"));
    }

    /**
     * Sets up all the buttons, routing, and passing in of parameters to make the scene be displayed to the user and allow user interaction
     * @param width
     * @param height
     * @param background
     * @return the Scene itself to be displayed
     */
    public Scene setupSplashScreen(int width, int height, Paint background) {
        VBox vb = new VBox(vboxVar);
        vb.setAlignment(Pos.CENTER);
        var splashScene = new Scene(vb, width, height, background);

        var setupWidth = Integer.parseInt(myResources.getString("SETUPWIDTH"));
        var setupHeight = Integer.parseInt(myResources.getString("SETUPHEIGHT"));

        Label simIntro = new Label(myResources.getString("WELCOME"));
        simIntro.setFont(Font.font(myResources.getString("WELCOME_FONT"), FontWeight.BOLD, 15));
        Button b1 = new Button(myResources.getString("PERC_CLICK"));
        Button b2 = new Button(myResources.getString("GAME_OF_LIFE_CLICK"));
        Button b3 = new Button(myResources.getString("PRED_PREY_CLICK"));
        Button b4 = new Button(myResources.getString("SEGREG_CLICK"));
        Button b5 = new Button(myResources.getString("RPS_CLICK"));
        Button b6 = new Button(myResources.getString("FIRE_CLICK"));
        Button b7 = new Button(myResources.getString("LOAD_BTN"));

        b1.setOnAction(e -> viewer.setScene(setupPercolationOptions(setupWidth, setupHeight, background)));
        b2.setOnAction(e -> viewer.setScene(setupGameOfLifeOptions(setupWidth, setupHeight, background)));
        b3.setOnAction(e -> viewer.setScene(setupPredatorPreyOptions(setupWidth, setupHeight, background)));
        b4.setOnAction(e -> viewer.setScene(setupSegregationOptions(setupWidth, setupHeight, background)));
        b5.setOnAction(e -> viewer.setScene(setupRPSOptions(setupWidth, setupHeight, background)));
        b6.setOnAction(e -> viewer.setScene(setupFireOptions(setupWidth, setupHeight, background)));
        b7.setOnAction(e -> loadFromConfig());

        vb.getChildren().addAll(simIntro, b1, b2, b3, b4, b5, b6, b7);
        return splashScene;
    }

    /**
     * @purpose Used to load properties from config and load the simulation corresponding to the CSV_DATA_FILE when the corresponding button on the
     * homepage is clicked on
     */
    public void loadFromConfig() {
        viewer.loadProperties();
        ResourceBundle loadResource = ResourceBundle.getBundle(configFile);
        String loadFilename = loadResource.getString("CSV_DATA_FILE");
        viewer.setScene(viewer.setupSimulation(loadFilename));
    }

    private Scene setupFireOptions(int width, int height, Paint background) {
        VBox vb = new VBox(vboxVar);
        vb.setAlignment(Pos.CENTER);
        var fireScene = new Scene(vb, width, height, background);
        Button b0 = new Button(myResources.getString("FIRE_TEST_BTN"));
        b0.setOnAction(e -> {
            var simulationFile = myResources.getString("FIRE_TEST_FILE");
            viewer.setProbCatch(Double.parseDouble(myResources.getString("FIRE_TEST_PROB")));
            viewer.setScene(viewer.setupSimulation(simulationFile));
        });
        Button b1 = new Button(myResources.getString("FIRE_SMALLPROB_BTN"));
        b1.setOnAction(e -> {
            var simulationFile = myResources.getString("FIRE_SMALLPROB_FILE");
            viewer.setProbCatch(Double.parseDouble(myResources.getString("FIRE_SMALL_PROB")));
            viewer.setScene(viewer.setupSimulation(simulationFile));
        });
        Button b2 = new Button(myResources.getString("FIRE_LARGEPROB_BTN"));
        b2.setOnAction(e -> {
            var simulationFile = myResources.getString("FIRE_LARGEPROB_FILE");
            viewer.setProbCatch(Double.parseDouble(myResources.getString("FIRE_LARGE_PROB")));
            viewer.setScene(viewer.setupSimulation(simulationFile));
        });
        Button b3 = new Button(myResources.getString("FIRE_HEX_BTN"));
        b3.setOnAction(e -> {
            var simulationFile = myResources.getString("FIRE_HEX_FILE");
            viewer.setProbCatch(Double.parseDouble(myResources.getString("FIRE_SMALL_PROB")));
            viewer.setScene(viewer.setupSimulation(simulationFile));
        });
        Button b4 = new Button(myResources.getString("FIRE_TRIANGLE_BTN"));
        b4.setOnAction(e -> {
            var simulationFile = myResources.getString("FIRE_TRIANGLE_FILE");
            viewer.setProbCatch(Double.parseDouble(myResources.getString("FIRE_LARGE_PROB")));
            viewer.setScene(viewer.setupSimulation(simulationFile));
        });
        vb.getChildren().addAll(b0, b1, b2, b3, b4);
        fireScene.getStylesheets().add(STYLESHEET);
        fireScene.setOnKeyPressed(e -> viewer.splashScreenHandleKey(e.getCode()));
        return fireScene;
    }

    private Scene setupRPSOptions(int width, int height, Paint background) {
        VBox vb = new VBox(vboxVar);
        vb.setAlignment(Pos.CENTER);
        var rpsScene = new Scene(vb, width, height, background);
        Button b0 = new Button(myResources.getString("RPS_SQUARE_BTN"));
        b0.setOnAction(e -> {
            var simulationFile = myResources.getString("RPS_SQUARE_FILE");
            viewer.setScene(viewer.setupSimulation(simulationFile));
        });
        Button b1 = new Button(myResources.getString("RPS_HEX_BTN"));
        b1.setOnAction(e -> {
            var simulationFile = myResources.getString("RPS_HEX_FILE");
            viewer.setScene(viewer.setupSimulation(simulationFile));
        });
        Button b2 = new Button(myResources.getString("RPS_TRIANGLE_BTN"));
        b2.setOnAction(e -> {
            var simulationFile = myResources.getString("RPS_TRIANGLE_FILE");
            viewer.setScene(viewer.setupSimulation(simulationFile));
        });
        vb.getChildren().addAll(b0,b1,b2);
        rpsScene.getStylesheets().add(STYLESHEET);
        rpsScene.setOnKeyPressed(e -> viewer.splashScreenHandleKey(e.getCode()));

        return rpsScene;
    }

    private Scene setupPercolationOptions(int width, int height, Paint background) {
        VBox vb = new VBox(vboxVar);
        vb.setAlignment(Pos.CENTER);
        var percolationScene = new Scene(vb, width, height, background);
        Button b0 = new Button(myResources.getString("PERC_TRIANGLE_BTN"));
        b0.setOnAction(e -> {
            var simulationFile = myResources.getString("PERC_TRIANGLE_FILE");
            viewer.setScene(viewer.setupSimulation(simulationFile));
        });
        Button b1 = new Button(myResources.getString("PERC_ONEPATH_BTN"));
        b1.setOnAction(e -> {
            var simulationFile = myResources.getString("PERC_ONEPATH_FILE");
            viewer.setScene(viewer.setupSimulation(simulationFile));
        });
        Button b2 = new Button(myResources.getString("PERC_TOR_BTN"));
        b2.setOnAction(e -> {
            var simulationFile = myResources.getString("PERC_TOR_FILE");
            viewer.setScene(viewer.setupSimulation(simulationFile));
        });
        Button b3 = new Button(myResources.getString("PERC_HEX_BTN"));
        b3.setOnAction(e -> {
            var simulationFile = myResources.getString("PERC_HEX_FILE");
            viewer.setScene(viewer.setupSimulation(simulationFile));
        });
        vb.getChildren().addAll(b0, b1, b2, b3);

        percolationScene.getStylesheets().add(STYLESHEET);
        percolationScene.setOnKeyPressed(e -> viewer.splashScreenHandleKey(e.getCode()));

        return percolationScene;
    }

    private Scene setupGameOfLifeOptions(int width, int height, Paint background) {
        VBox vb = new VBox(vboxVar);
        vb.setAlignment(Pos.CENTER);
        var gameOfLifeScene = new Scene(vb, width, height, background);
        Button b0 = new Button(myResources.getString("GOL_TRIANGLE_BTN"));
        Button b1 = new Button(myResources.getString("GOL_GLIDER_BTN"));
        Button b2 = new Button(myResources.getString("GOL_BEACON_BTN"));
        Button b3 = new Button(myResources.getString("GOL_PULSAR_BTN"));
        Button b4 = new Button(myResources.getString("GOL_TEST_BTN"));
        Button b5 = new Button(myResources.getString("GOL_HEX_BTN"));
        b0.setOnAction(e -> {
            var simulationFile = myResources.getString("GOL_TRIANGLE_FILE");
            viewer.setScene(viewer.setupSimulation(simulationFile));
        });
        b1.setOnAction(e -> {
            var simulationFile = myResources.getString("GOL_GLIDER_FILE");
            viewer.setScene(viewer.setupSimulation(simulationFile));
        });
        b2.setOnAction(e -> {
            var simulationFile = myResources.getString("GOL_BEACON_FILE");
            viewer.setScene(viewer.setupSimulation(simulationFile));
        });
        b3.setOnAction(e -> {
            var simulationFile = myResources.getString("GOL_PULSAR_FILE");
            viewer.setScene(viewer.setupSimulation(simulationFile));
        });
        b4.setOnAction(e -> {
            var simulationFile = myResources.getString("GOL_TEST_FILE");
            viewer.setScene(viewer.setupSimulation(simulationFile));
        });
        b5.setOnAction(e -> {
            var simulationFile = myResources.getString("GOL_HEX_FILE");
            viewer.setScene(viewer.setupSimulation(simulationFile));
        });

        vb.getChildren().addAll(b0, b1, b2, b3, b4, b5);
        gameOfLifeScene.getStylesheets().add(STYLESHEET);
        gameOfLifeScene.setOnKeyPressed(e -> viewer.splashScreenHandleKey(e.getCode()));

        return gameOfLifeScene;
    }

    private Scene setupPredatorPreyOptions(int width, int height, Paint background) {
        VBox vb = new VBox(vboxVar);
        vb.setAlignment(Pos.CENTER);
        var predatoryPreyScene = new Scene(vb, width, height, background);
        viewer.setMaxEnergy(Integer.parseInt(myResources.getString("MAX_ENERGY")));
        viewer.setEnergyFood(Integer.parseInt(myResources.getString("ENERGY_FOOD")));
        viewer.setSharkSpawn(Integer.parseInt(myResources.getString("SHARK_SPAWN")));
        viewer.setFishSpawn(Integer.parseInt(myResources.getString("FISH_SPAWN")));
        Button b0 = new Button(myResources.getString("PREDPREY_SMALL_BTN"));
        b0.setOnAction(e -> {
            var simulationFile = myResources.getString("PREDPREY_SMALL_FILE");
            viewer.setScene(viewer.setupSimulation(simulationFile));
        });
        Button b1 = new Button(myResources.getString("PREDPREY_MED_BTN"));
        b1.setOnAction(e -> {
            var simulationFile = myResources.getString("PREDPREY_MED_FILE");
            viewer.setScene(viewer.setupSimulation(simulationFile));
        });
        Button b2 = new Button(myResources.getString("PREDPREY_LARGE_BTN"));
        b2.setOnAction(e -> {
            var simulationFile = myResources.getString("PREDPREY_LARGE_FILE");
            viewer.setScene(viewer.setupSimulation(simulationFile));
        });
        Button b3 = new Button(myResources.getString("PREDPREY_HEX_BTN"));
        b3.setOnAction(e -> {
            var simulationFile = myResources.getString("PREDPREY_HEX_FILE");
            viewer.setScene(viewer.setupSimulation(simulationFile));
        });
        Button b4 = new Button(myResources.getString("PREDPREY_TRIANGLE_BTN"));
        b4.setOnAction(e -> {
            var simulationFile = myResources.getString("PREDPREY_TRIANGLE_FILE");
            viewer.setScene(viewer.setupSimulation(simulationFile));
        });
        vb.getChildren().addAll(b0,b1,b2,b3,b4);
        predatoryPreyScene.getStylesheets().add(STYLESHEET);
        predatoryPreyScene.setOnKeyPressed(e -> viewer.splashScreenHandleKey(e.getCode()));


        return predatoryPreyScene;
    }

    private Scene setupSegregationOptions(int width, int height, Paint background) {
        VBox vb = new VBox(vboxVar);
        vb.setAlignment(Pos.CENTER);
        var segregationScene = new Scene(vb, width, height, background);
        Button b0 = new Button(myResources.getString("SEGREG_SMALL_BTN"));
        b0.setOnAction(e -> {
            viewer.setSegregationThreshold(Double.parseDouble(myResources.getString("SEGREG_THRESH_SMALL")));
            var simulationFile = myResources.getString("SEGREG_SMALL_FILE");
            viewer.setScene(viewer.setupSimulation(simulationFile));
        });
        Button b1 = new Button(myResources.getString("SEGREG_MED_BTN"));
        b1.setOnAction(e -> {
            viewer.setSegregationThreshold(Double.parseDouble(myResources.getString("SEGREG_THRESH_MED")));
            var simulationFile = myResources.getString("SEGREG_MED_FILE");
            viewer.setScene(viewer.setupSimulation(simulationFile));
        });
        Button b2 = new Button(myResources.getString("SEGREG_LARGE_BTN"));
        b2.setOnAction(e -> {
            viewer.setSegregationThreshold(Double.parseDouble(myResources.getString("SEGREG_THRESH_LARGE")));
            var simulationFile = myResources.getString("SEGREG_LARGE_FILE");
            viewer.setScene(viewer.setupSimulation(simulationFile));
        });
        Button b3 = new Button(myResources.getString("SEGREG_TRIANGLE_BTN"));
        b3.setOnAction(e -> {
            viewer.setSegregationThreshold(Double.parseDouble(myResources.getString("SEGREG_THRESH_LARGE")));
            var simulationFile = myResources.getString("SEGREG_TRIANGLE_FILE");
            viewer.setScene(viewer.setupSimulation(simulationFile));
        });
        Button b4 = new Button(myResources.getString("SEGREG_HEX_BTN"));
        b4.setOnAction(e -> {
            viewer.setSegregationThreshold(Double.parseDouble(myResources.getString("SEGREG_THRESH_LARGE")));
            var simulationFile = myResources.getString("SEGREG_HEX_FILE");
            viewer.setScene(viewer.setupSimulation(simulationFile));
        });

        vb.getChildren().addAll(b0,b1,b2,b3,b4);
        segregationScene.getStylesheets().add(STYLESHEET);
        segregationScene.setOnKeyPressed(e -> viewer.splashScreenHandleKey(e.getCode()));

        return segregationScene;
    }
}
