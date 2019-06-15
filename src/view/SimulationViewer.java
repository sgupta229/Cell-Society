package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Samuel Chan, William Francis, Sahil Gupta
 * @purpose The main class of the entire program, is in charge of setting up the simulation and all its properties, calling configreader to
 * parse the given simulation files, setup grid and display the simulation, along with passing all necessary inputs into each specific grid and update
 * all buttons, visuals, and cells themselves
 * @assumptions We must assume the config files, user inputs, resource files, etc. are passed in correctly/the right format.
 * @dependencies Initially called in Main, requires each individual grids, the simulation files themselves, the resource/css files, the CellChart/SceneMaker/
 * Shapemaker/Viewerhelper to help display the simulation/splash screens, the cells/states
 * @usage Called and pass in the Resourcebundle and the stage itself
 */

public class SimulationViewer {

    private final SceneMaker sceneMaker;
    private final ViewerHelper helper;
    // Instance variables to be specified by setup resources properties
    private int framesPerSecond;
    private double millisecondDelay;
    private int setupWidth;
    private int setupHeight;
    private int gridWidth;
    private int gridHeight;
    private int gameWidth;
    private int gameHeight;

    private Paint BACKGROUND;

    // Simulation instance variables
    private Stage primaryStage;
    private Timeline animation;
    private Grid myGrid;
    private LineChart lineChart;
    private CellChart cellChart = new CellChart();
    //private SceneSetter sceneSetter = new SceneSetter();
    private ShapeMaker shapeMaker;
    private Scene myScene;
    private String typeOfSim;
    private ConfigReader reader;
    private BorderPane bp;

    private double segregationThreshold;
    private int rpsThreshold;
    private int rpsRandom;
    private double probCatch;
    private int maxEnergy;
    private int energyFood;
    private int sharkSpawn;
    private int fishSpawn;
    private boolean paused = false;
    private boolean outlinedGrid;

    // Properties
    private ResourceBundle myResources;

    public static final String STYLESHEET = "styles.css";
    public static final String GAME_STYLESHEET = "gameStyles.css";
    public static final int mouseXOffset = 2;
    public static final double mouseYOffset = 1.18;
    public static final int milliSecondDelayInitial = 1000;
    public static final int addFrameRate = 5;
    public static final int subFrameRate = -5;
    public static final int maxFrameRate = 50;
    public static final int minFrameRate = 5;

    private int myCellShape;
    private int myCellDiameter;
    private Pane myRoot;
    private Map<Integer, State> possibleStateMap;

    public SimulationViewer(ResourceBundle resources, Stage stage) {
        primaryStage = stage;
        myResources = resources;
        sceneMaker = new SceneMaker(this,  myResources);
        helper = new ViewerHelper(this, myResources);
        loadProperties();
    }

    /**
     * Called to generate the splash scene/routing
     * @return Scene
     */
    public Scene getSplashScene(){
        myScene = sceneMaker.setupSplashScreen(setupWidth, setupHeight, BACKGROUND);
        myScene.getStylesheets().add(STYLESHEET);
        myScene.setOnKeyPressed(e -> splashScreenHandleKey(e.getCode()));
        return myScene;
    }

    /**
     * Load certain properties from config and set values of private instance variables used in the simulation settings
     */
    public void loadProperties() {
        framesPerSecond = Integer.parseInt(myResources.getString("FRAMES_PER_SECOND"));
        millisecondDelay = (double) milliSecondDelayInitial / framesPerSecond;
        setupWidth = Integer.parseInt(myResources.getString("SETUPWIDTH"));
        setupHeight = Integer.parseInt(myResources.getString("SETUPHEIGHT"));
        gridWidth = Integer.parseInt(myResources.getString("GRIDWIDTH"));
        gridHeight = Integer.parseInt(myResources.getString("GRIDHEIGHT"));
        gameHeight = Integer.parseInt(myResources.getString("GAMEHEIGHT"));
        gameWidth = Integer.parseInt(myResources.getString("GAMEWIDTH"));
        BACKGROUND = Paint.valueOf(myResources.getString("BACKGROUND"));

    }

    // Handle in game key press
    private void inGameHandleKey(KeyCode code) {
        if(code.equals(KeyCode.EQUALS) && framesPerSecond <= maxFrameRate) {
            changeKeyFrame(addFrameRate);
        }
        if(code.equals(KeyCode.MINUS) && framesPerSecond >= minFrameRate) {
            changeKeyFrame(subFrameRate);
        }
        if(code.equals(KeyCode.PERIOD)) {
            step();
        }
        if(code.equals(KeyCode.P)) {
            pauseGame();
        }
        if(code.equals(KeyCode.S)) {
            saveState();
        }
        if(code.equals(KeyCode.L)) {
            animation.stop();
            sceneMaker.loadFromConfig();
        }
        if(code.equals(KeyCode.R)) {
            animation.stop();
            restart();
        }
    }

    // Restart to main menu when necessary
    private void restart() {
        var main = new Main();
        main.start(primaryStage);
    }

    /**
     * Click the button R --> Restart
     * @param code
     */
    public void splashScreenHandleKey(KeyCode code) {
        if(code.equals(KeyCode.R)) {
            restart();
        }
    }

    // Save state to a file
    private void saveState() {

        File toWrite = new File("./data/currentState.csv");

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(toWrite);
        } catch (FileNotFoundException e) {
            showError(e.getMessage(), myResources.getString("WriteFileErrorTitle"));
        }

        StringBuilder builder = new StringBuilder();

        builder.append(reader.getSimType()+"\n");
        builder.append(reader.getCellShape()+"\n");
        builder.append(reader.getMyNumStates()+"\n");
        for(int i = 0; i < reader.getMyNumStates(); i++) {
            State state = reader.getMyStateMap().get(i);
            builder.append(state.getStateName()+","+state.getType()+","+state.getColor()+","+state.getImage()+"\n");
        }
        builder.append(reader.getNumCols()+",");
        builder.append(reader.getNumRows()+"\n");
        builder.append(myGrid.getCellsString(myGrid.getCurrentGrid()));

        writer.print(builder.toString());
        writer.close();

    }

    // Pause animation/game
    private void pauseGame() {
        if(!paused) {
            this.animation.pause();
        }
        else {
            this.animation.play();
        }
        paused = !paused;
    }

    /**
     * Called to set up the simulation when needed, handles animation, shape creation, chart, buttons, routing, styling, etc.
     * @param simFile
     * @return
     */
    public Scene setupSimulation(String simFile) {
        var frame = new KeyFrame(Duration.millis(millisecondDelay), e -> step());
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();

        //read values from ConfigReader
        try {
            reader = new ConfigReader(simFile);
        } catch (Exception e) {
            showError(e.getMessage());
            restart();
        }
        var simType = reader.getSimType();
        typeOfSim = simType;
        var numCols = reader.getNumCols();
        var numRows = reader.getNumRows();
        var gridData = reader.getGridData();
        possibleStateMap = reader.getMyStateMap();
        var edgePolicy = reader.getEdgePolicy();
        var neighborPolicy = reader.getNeighborPolicy();
        myCellShape = reader.getCellShape();
        outlinedGrid = reader.getIsOutlined();
        myCellDiameter = gridWidth / numCols;

        var arr = helper.getCellArrayFromList(numCols, numRows, gridData, possibleStateMap);
        myGrid = helper.getGrid(numCols, numRows, arr, simType, possibleStateMap, edgePolicy, neighborPolicy, myCellShape);
        shapeMaker = new ShapeMaker(myResources, myGrid);

        myRoot = new Pane();
        myRoot.setPrefSize(gridWidth, gridHeight);
        helper.populateRoot(arr);

        bp = createButtons(simType);

        lineChart = cellChart.initChart(myGrid);

        bp.setCenter(lineChart);
        bp.setLeft(myRoot);

        myScene = new Scene(bp, gameWidth, gameHeight);

        myScene.setOnMouseClicked(e-> mouseClicked(e.getSceneX(), e.getSceneY()));
        myScene.setOnMouseDragged(e-> mouseClicked(e.getSceneX(), e.getSceneY()));

        myScene.setOnKeyPressed(e -> inGameHandleKey(e.getCode()));
        myScene.getStylesheets().add(GAME_STYLESHEET);
        return myScene;
    }

    private void changeKeyFrame(int amount) {
        framesPerSecond += amount;
        millisecondDelay = (((double) milliSecondDelayInitial)/framesPerSecond);

        animation.pause();
        animation.getKeyFrames().remove(0);
        var frame = new KeyFrame(Duration.millis(millisecondDelay), e -> step());
        animation.getKeyFrames().add(frame);
        animation.playFromStart();
    }

    private void mouseClicked(double mouseX, double mouseY) {
        if (myCellShape==2){
            mouseX*= mouseXOffset;
            mouseY*= mouseYOffset;
        }
        int x = (int) (mouseX/(myCellDiameter));
        int y = (int) (mouseY/(myCellDiameter) - 1);

        var grid  = myGrid.getCurrentGrid();
        if (y<grid.length && y>=0 && x<grid[0].length && x>=0) {
            var cell = grid[y][x];
            var s = cell.getCurrState();
            int i = s.getType() + 1;
            if (i >= possibleStateMap.size()) {
                i = 0;
            }
            cell.setCurrState(possibleStateMap.get(i));
            if (myGrid.getNextGrid()!=null) {
                //cell = myGrid.getNextGrid()[y][x];
                myGrid.getNextGrid()[y][x] = cell;
                myGrid.setCurrentGrid(myGrid.getNextGrid());
                //cell.setCurrState(possibleStateMap.get(i));
            }
        }
    }

    private BorderPane createButtons(String simType) {
        Button inc = new Button(myResources.getString("INC_FRAME_BTN"));
        inc.setOnAction(e -> {
            if(framesPerSecond <= Integer.parseInt(myResources.getString("FPSMax"))) {
                changeKeyFrame(addFrameRate);
            }
        });
        Button dec = new Button(myResources.getString("DEC_FRAME_BTN"));
        dec.setOnAction(e -> {
            if(framesPerSecond >= Integer.parseInt(myResources.getString("FPSMin"))) {
                changeKeyFrame(subFrameRate);
            }
        });
        ToolBar toolbar = new ToolBar(inc, dec);
        toolbar = addMoreButtons(simType, toolbar);
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(toolbar);
        borderPane.setAlignment(toolbar,Pos.CENTER);

        ToolBar tb = new ToolBar();
        Label label = new Label("FPS: " + framesPerSecond);
        tb.getItems().add(label);
        borderPane.setBottom(tb);

        return borderPane;
    }

    private ToolBar addMoreButtons(String st, ToolBar tb) {
        if (st.equalsIgnoreCase(myResources.getString("PERCSIM"))){
        }
        else if (st.equalsIgnoreCase(myResources.getString("GOLSIM"))){
        }
        else if (st.equalsIgnoreCase(myResources.getString("PREDPREYSIM"))){
            tb = predPreyButtons(tb);
        }
        else if (st.equalsIgnoreCase(myResources.getString("SEGREGSIM"))){
            tb = segregButtons(tb);
        }
        else if (st.equalsIgnoreCase(myResources.getString("RPSSIM"))){
            Button incthresh = new Button(myResources.getString("INC_RPSTHRESH_BTN"));
            incthresh.setOnAction(e -> {
                if(rpsThreshold <= Integer.parseInt(myResources.getString("rpsThreshMax"))) {
                    rpsThreshold++;
                }
            });
            Button decthresh = new Button(myResources.getString("DEC_RPSTHRESH_BTN"));
            decthresh.setOnAction(e -> {
                if(rpsThreshold >= Integer.parseInt(myResources.getString("rpsThreshMin"))) {
                    rpsThreshold--;
                }
            });
            Button incrandom = new Button(myResources.getString("INC_RPSRAND_BTN"));
            incrandom.setOnAction(e -> {
                if(rpsRandom <= Integer.parseInt(myResources.getString("rpsRandMax"))) {
                    rpsRandom++;
                }
            });
            Button decrandom = new Button(myResources.getString("DEC_RPSRAND_BTN"));
            decrandom.setOnAction(e -> {
                if(rpsRandom >= Integer.parseInt(myResources.getString("rpsRandMin"))) {
                    rpsRandom--;
                }
            });
            tb.getItems().addAll(incthresh, decthresh, incrandom, decrandom);
        }
        else if (st.equalsIgnoreCase(myResources.getString("FIRESIM"))) {
            Button inc = new Button(myResources.getString("INC_FIREPROB_BTN"));
            inc.setOnAction(e -> {
                if(probCatch <= Double.parseDouble(myResources.getString("probCatchMax"))) {
                    probCatch += Double.parseDouble(myResources.getString("probCatchInc"));
                }
            });
            Button dec = new Button(myResources.getString("DEC_FIREPROB_BTN"));
            dec.setOnAction(e -> {
                if(probCatch >= Double.parseDouble(myResources.getString("probCatchMin"))) {
                    probCatch -= Double.parseDouble(myResources.getString("probCatchInc"));
                }
            });
            tb.getItems().addAll(inc, dec);
        }

        return tb;
    }

    private ToolBar predPreyButtons(ToolBar tb) {
        Button b1 = new Button(myResources.getString("INC_FSPAWN_BTN"));
        b1.setOnAction(e -> {
            int spawnRate = Integer.parseInt(myResources.getString("fishSpawnMax"));
            if(fishSpawn <= spawnRate) {
                fishSpawn++;
            }
        });
        Button b2 = new Button(myResources.getString("DEC_FSPAWN_BTN"));
        b2.setOnAction(e -> {
            if(fishSpawn >= Integer.parseInt(myResources.getString("fishSpawnMin"))) {
                fishSpawn--;
            }
        });
        Button b3 = new Button(myResources.getString("INC_SSPAWN_BTN"));
        b3.setOnAction(e -> {
            if(sharkSpawn <= Integer.parseInt(myResources.getString("sharkSpawnMax"))) {
                sharkSpawn++;
            }
        });
        Button b4 = new Button(myResources.getString("DEC_SSPAWN_BTN"));
        b4.setOnAction(e -> {
            if(sharkSpawn >= Integer.parseInt(myResources.getString("sharkSpawnMin"))) {
                sharkSpawn--;
            }
        });
        Button b5 = new Button(myResources.getString("INC_MAXENERGY_BTN"));
        b5.setOnAction(e -> {
            if(maxEnergy <= Integer.parseInt(myResources.getString("maxEnergyHigh"))) {
                maxEnergy++;
            }
        });
        Button b6 = new Button(myResources.getString("DEC_MAXENERGY_BTN"));
        b6.setOnAction(e -> {
            if(maxEnergy >= Integer.parseInt(myResources.getString("maxEnergyLow"))) {
                maxEnergy--;
            }
        });
        Button b7 = new Button(myResources.getString("INC_ENERGYFOOD_BTN"));
        b7.setOnAction(e -> {
            if(energyFood <= Integer.parseInt(myResources.getString("energyFoodMax"))) {
                energyFood++;
            }
        });
        Button b8 = new Button(myResources.getString("DEC_ENERGYFOOD_BTN"));
        b8.setOnAction(e -> {
            if(energyFood >= Integer.parseInt(myResources.getString("energyFoodMin"))) {
                energyFood--;
            }
        });
        tb.getItems().addAll(b1, b2, b3, b4, b5, b6, b7, b8);
        return tb;
    }

    private ToolBar segregButtons(ToolBar tb) {
        Button inc = new Button(myResources.getString("INC_SEGREGTHRESH_BTN"));
        inc.setOnAction(e -> {
            if(segregationThreshold <= Double.parseDouble(myResources.getString("segThreshMax"))) {
                segregationThreshold += Double.parseDouble(myResources.getString("segThreshInc"));
            }
        });
        Button dec = new Button(myResources.getString("DEC_SEGREGTHRESH_BTN"));
        dec.setOnAction(e -> {
            if(segregationThreshold >= Double.parseDouble(myResources.getString("segThreshMin"))) {
                segregationThreshold -= Double.parseDouble(myResources.getString("segThreshInc"));
            }
        });
        tb.getItems().addAll(inc, dec);
        return tb;
    }

    private void step(){
        updateStatusBar();
        updateProperties();
        myGrid.createNextGrid();
        myGrid.setCurrentGrid(myGrid.getNextGrid());
        myGrid.setNeighbors(myGrid.getShape());
        myRoot.getChildren().clear();
        helper.populateRoot(myGrid.getCurrentGrid());
        cellChart.updateLineChart(myGrid, bp);
    }

    private void updateStatusBar() {
        for(Node b : bp.getChildren()) {
            if(b instanceof ToolBar) {
                for(Node n: ((ToolBar) b).getItems()) {
                    if(n instanceof  Label) {
                        ((Label) n).setText("FPS: " + String.valueOf(framesPerSecond));
                    }
                }
            }
        }
    }

    private void updateProperties() {
        if (typeOfSim.equalsIgnoreCase(myResources.getString("PERCSIM"))){
        }
        else if (typeOfSim.equalsIgnoreCase(myResources.getString("GOLSIM"))){
        }
        else if (typeOfSim.equalsIgnoreCase(myResources.getString("PREDPREYSIM"))){
            ((PredatorPreyGrid) myGrid).updateMaxEnergy(maxEnergy);
            ((PredatorPreyGrid) myGrid).updateEnergyFood(energyFood);
            ((PredatorPreyGrid) myGrid).updateSharkSpawn(sharkSpawn);
            ((PredatorPreyGrid) myGrid).updateFishSpawn(fishSpawn);
        }
        else if (typeOfSim.equalsIgnoreCase(myResources.getString("SEGREGSIM"))){
            ((SegregationGrid) myGrid).updateThresh(segregationThreshold);
        }
        else if (typeOfSim.equalsIgnoreCase(myResources.getString("RPSSIM"))){
            ((RockPaperScissorsGrid) myGrid).updateRandom(rpsRandom);
            ((RockPaperScissorsGrid) myGrid).updateThreshhold(rpsThreshold);
        }
        else if (typeOfSim.equalsIgnoreCase(myResources.getString("FIRESIM"))) {
            ((FireGrid) myGrid).updateProb(probCatch);
        }
    }

    /**
     * Show popup error window with the specified message when an error is encountered
     * @param message
     */
    public void showError(String message) {
        animation.stop();
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(myResources.getString("ErrorTitle"));
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message, String title) {
        animation.stop();
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Public getters/setters below for instance variables that are changed during the simulation and
     * sometimes specific for each type of simulation
     */

    public void setProbCatch(double prob) {
        probCatch = prob;
    }
    public void setSegregationThreshold(double segreg_thresh) {
        segregationThreshold = segreg_thresh;
    }
    public void setMaxEnergy(int max_energy) { maxEnergy = max_energy; }
    public void setEnergyFood(int energy_food) { energyFood = energy_food; }
    public void setSharkSpawn(int shark_spawn) { sharkSpawn = shark_spawn; }
    public void setFishSpawn(int fish_spawn) { fishSpawn = fish_spawn; }
    public void setScene(Scene scene) { primaryStage.setScene(scene); }
    public double getProbCatch() { return probCatch; }
    public int getRPSThreshold() { return rpsThreshold; }
    public int getRPSRandom() { return rpsRandom; }
    public double getSegregationThreshold() { return segregationThreshold; }
    public int getMaxEnergy() { return maxEnergy; }
    public int getEnergyFood() { return energyFood; }
    public int getSharkSpawn() { return sharkSpawn;}
    public int getFishSpawn() { return fishSpawn; }
    public void setRPSThreshold(int rps_threshold) { rpsThreshold = rps_threshold; }
    public void setRPSRandom(int rps_random) { rpsRandom = rps_random; }
    public ShapeMaker getShapeMaker() { return shapeMaker; }
    public boolean getOutlinedGrid() { return outlinedGrid; }
    public Pane getRoot() { return myRoot; }
}
