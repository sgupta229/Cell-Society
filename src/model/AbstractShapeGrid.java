package model;


import model.neighborConstruction.HexagonNeighborConstructor;
import model.neighborConstruction.NeighborConstructor;
import model.neighborConstruction.SquareNeighborConstructor;
import model.neighborConstruction.TriangleNeighborConstructor;

import java.util.Map;

/**
 * This class is the abstract shape grid class that all grid types extend. It defines the basic properties and
 * functionalities that all grids have.
 */

public abstract class AbstractShapeGrid implements Grid {
    private int numRows;
    private int numCols;
    private Cell[][] currGrid;
    private Cell[][] nextGrid;
    private Map<Integer, State> possibleStateMap;
    private final int edgePolicy;
    private final int neighborPolicy;
    private final int shape;

    /**
     * The constructor for an abstract shape grid
     * @param numCols - the number of columns the grid has
     * @param numRows - the number of rows the grid has
     * @param gridData - a 2D array of cells representing the current state of the grid
     * @param possibleStateMap - a map with keys as integers and values as States.
     *                         Contains all possible cell states for the specific type of grid
     * @param edgePolicy - defines the edge policy of the cells (bounded, toroidal, klein)
     * @param neighborPolicy - defines the neighbor policy of the cells (cardinal, complete, diagonal)
     * @param shape - which shape the grid consists of (triangle, square, hexagons)
     */

    public AbstractShapeGrid(int numCols, int numRows, Cell[][] gridData, Map<Integer, State> possibleStateMap, int edgePolicy, int neighborPolicy, int shape) {
        this.numCols = numCols;
        this.numRows = numRows;
        this.currGrid = gridData;
        this.possibleStateMap = possibleStateMap;
        this.edgePolicy = edgePolicy;
        this.neighborPolicy = neighborPolicy;
        this.shape = shape;
        setNeighbors(this.shape);
    }

    /**
     * This method calls a neighbor constructor depending on what shape the grid is. The specific constructor will set
     * all neighbors for the grid.
     * @param shape - the shape used in the grid
     */

    public void setNeighbors(int shape){
        NeighborConstructor n;
        if (shape == 0) {
            n = new SquareNeighborConstructor(this);
        }
        else if (shape == 1) {
            n = new HexagonNeighborConstructor(this);
        }
        else if (shape == 2) {
            n = new TriangleNeighborConstructor(this);
        }
    }

    /**
     * implemented by each simulation grid. this method defines howw the next grid should be calculated
     */

    public abstract void createNextGrid();

    //////////////////////////////////////////
    // Getter and Setters
    //////////////////////////////////////////

    /**
     * getter method to get the next state of the grid
     * @return - the next grid
     */

    public Cell[][] getNextGrid() {
        return nextGrid;
    }

    /**
     * set the next state of the grid to next grid
     * @param next - the next state of the grid
     */

    public void setNextGrid(Cell[][] next) { this.nextGrid = next; }

    /**
     * getter method for the grid of cells
     * @return a 2d array of cell
     */

    public Cell[][] getCurrentGrid() {
        return currGrid;
    }

    /**
     * setter method to set the current grid to a different 2d array of cell
     * @param curr - set currGrid to curr
     */

    public void setCurrentGrid(Cell[][] curr) { this.currGrid = curr; }

    /**
     * getter method for the number of rows
     * @return the number of rows
     */

    public int getNumRows() {
        return numRows;
    }

    /**
     * getter method for the number of columns
     * @return the number of columns
     */

    public int getNumCols() {
        return numCols;
    }

    /**
     *getter method for the possible state map
     * @return returns a map mapping integer keys to values that are possible states for the cell
     */

    public Map<Integer, State> getPossibleStateMap() {
        return possibleStateMap;
    }

    /**
     * getter method to return the edge policy
     * @return an int representing the edge policy (bounded - 0, toroidal - 1, klein - 2)
     */

    public int getEdgePolicy() { return edgePolicy; }

    /**
     * getter method to return the neighbor policy
     * @return an int representing the neighbor policy (0 - complete, 1 - cardinal, 2 - diagonal)
     */

    public int getNeighborPolicy() {
        return neighborPolicy;
    }

    /**
     * method to return a string representation of the 2d array
     * @param cells - 2d array of cells
     * @return - returns a string representing the 2d array of cells
     */

    public String getCellsString(Cell[][] cells){
        var str = new StringBuilder();
        for (Cell[] row: cells){
            for (Cell cell: row){
                str.append(cell.getCurrState().getType() + ",");
            }
            str.deleteCharAt(str.length()-1);
            str.append("\n");
        }
        return str.toString().trim();
    }

    /**
     * Getter method for the shape used in the grid
     * @return - the shape
     */

    public int getShape() {
        return shape;
    }
}
