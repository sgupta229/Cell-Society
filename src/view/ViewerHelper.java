package view;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import model.Cell;
import model.Grid;
import model.PercolationGrid;
import model.State;
import model.GameOfLifeGrid;
import model.SegregationGrid;
import model.FireGrid;
import model.RockPaperScissorsGrid;
import model.PredatorPreyGrid;

import java.awt.Point;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Samuel Chan, William Francis, Sahil Gupta
 * @purpose A helper class for simulationviewer
 * @assumptions Called by simulationviewer for basic utilities, such as acquiring the grid, updating colors, acquiring grid values
 * @dependencies Resources file, Grid, SimulationViewer
 */
public class ViewerHelper {

    private final ResourceBundle myResources;
    private final SimulationViewer viewer;

    public ViewerHelper(SimulationViewer view, ResourceBundle resourceBundle){
        myResources = resourceBundle;
        viewer = view;
    }

    /**
     * Based on the type of Grid provided (which is known from the variable simType, creates the specific type of grid that is specified by simType
     * In the future, this is probably not recommended as it is somewhat redundant and requires downcasting, leading to less model-view separation.
     * @param numCols
     * @param numRows
     * @param arr
     * @param simType
     * @param possibleStateMap
     * @param edgePolicy
     * @param neighborPolicy
     * @param shape
     * @return Grid
     */
    public Grid getGrid(int numCols, int numRows, Cell[][] arr, String simType,
                        Map<Integer, State> possibleStateMap, int edgePolicy, int neighborPolicy, int shape) {
        Grid grid;

        //to add new Simulations add an additional if statement with that sim's constructor
        if (simType.equalsIgnoreCase(myResources.getString("PERCSIM"))){
            grid = new PercolationGrid(numCols, numRows, arr, possibleStateMap, edgePolicy, neighborPolicy, shape);
        }
        else if (simType.equalsIgnoreCase(myResources.getString("GOLSIM"))){
            grid = new GameOfLifeGrid(numCols, numRows, arr, possibleStateMap, edgePolicy, neighborPolicy, shape);
        }
        else if (simType.equalsIgnoreCase(myResources.getString("PREDPREYSIM"))){
            grid = new PredatorPreyGrid(numCols, numRows, arr, possibleStateMap, edgePolicy, neighborPolicy, shape, viewer.getMaxEnergy(), viewer.getEnergyFood(), viewer.getSharkSpawn(), viewer.getFishSpawn());
            //grid = new PredatorPreyGrid(numCols, numRows, arr, possibleStateMap, edgePolicy, neighborPolicy, shape);

        }
        else if (simType.equalsIgnoreCase(myResources.getString("SEGREGSIM"))){
            grid = new SegregationGrid(numCols, numRows, arr, possibleStateMap, edgePolicy, neighborPolicy, shape, viewer.getSegregationThreshold());
        }
        else if (simType.equalsIgnoreCase(myResources.getString("RPSSIM"))){
            viewer.setRPSThreshold(Integer.parseInt(myResources.getString("RPS_THRESHOLD")));
            viewer.setRPSRandom(Integer.parseInt(myResources.getString("RPS_RANDOM")));
            grid = new RockPaperScissorsGrid(numCols, numRows, arr, possibleStateMap, edgePolicy, neighborPolicy, shape, viewer.getRPSThreshold(), viewer.getRPSRandom());
        }
        else if (simType.equalsIgnoreCase(myResources.getString("FIRESIM"))) {
            grid = new FireGrid(numCols, numRows, arr, possibleStateMap, edgePolicy, neighborPolicy, shape, viewer.getProbCatch());
        }
        else {
            grid = null;
        }
        return grid;
    }

    /**
     * Calling ShapeMaker, it loops through the cells and visually constructs the grid, filling each Cell in with either a solid color (provided in config .csv file)
     * or with the provided image.
     * @param cells
     */
    public void populateRoot(Cell[][] cells) {
        for (int r = 0; r < cells.length; r++){
            for (int c = 0; c < cells[r].length; c++){
                var cell = cells[r][c];
                Polygon p = viewer.getShapeMaker().makeShape(r,c);
                if(viewer.getOutlinedGrid()) {
                    p.setStyle("-fx-stroke: grey; -fx-stroke-width: 1;");
                }
                var image = cell.getCurrState().getImage();
                if(!image.equals("NONE")) {
                    Image img = null;
                    try {
                        img = new Image(getClass()
                                .getResourceAsStream("/img/" + image));
                    } catch (IllegalArgumentException e) {
                        viewer.showError("Image not found");
                    }
                    if(img != null) {
                        p.setFill(new ImagePattern(img));
                    }
                }
                else {
                    p.setFill(cell.getCurrState().getColor());
                }
                viewer.getRoot().getChildren().add(p);
            }
        }
    }

    /**
     * Looping through the list, it generates a 2x2 array of Cells that is further passed into the model for query processing and updating of the grid.
     * The 2x2 array is the base data structure that contains the states for each cell.
     * @param numCols
     * @param numRows
     * @param gridData
     * @param possibleStateMap
     * @return
     */
    public Cell[][] getCellArrayFromList(int numCols, int numRows, List<Integer> gridData, Map<Integer, State> possibleStateMap) {
        Cell[][] cells = new Cell[numRows][numCols];
        int r = 0;
        int c = 0;
        for (Integer val: gridData){
            if (c >= numCols){
                c = 0;
                r++;
            }
            State s = possibleStateMap.get(val);
            cells[r][c] = new Cell(new Point(c, r), s);
            c++;
        }
        return cells;
    }
}
