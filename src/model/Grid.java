package model;

import java.util.Map;

/**
 * interface for AbstractShapeGrid
 */

/**
 * This interface defines the methods that all grids should have
 */

public interface Grid {

    /**
     * sets the neighbors of a cell
     * @param shape - the shape of the cell in question
     */

    public void setNeighbors(int shape);

    /**
     * method to create the next grid
     */

    public void createNextGrid();

    //////////////////////////////////////////
    // Getter and Setters
    //////////////////////////////////////////

    /**
     * getter method to get the next grid
     * @return 2d array of cells of the next grid
     */

    public Cell[][] getNextGrid();

    /**
     * method to set the future grid
     * @param next
     */

    public void setNextGrid(Cell[][] next);

    /**
     * getter method to get the current grid
     * @return - the current 2d array of cell
     */

    public Cell[][] getCurrentGrid();

    /**
     * setter method to set the current 2d array of cells
     * @param curr the current 2d array of cells
     */

    public void setCurrentGrid(Cell[][] curr);

    /**
     * getter method to get the number of rows of a grid
     * @return the number of rows
     */

    public int getNumRows();

    /**
     * getter method to get the number of columns of a grid
     * @return the number of columns
     */

    public int getNumCols();

    /**
     * getter method to get the possible state map.
     * @return the map of possible states
     */

    public Map<Integer, State> getPossibleStateMap();

    /**
     * getter method for the edge policy
     * @return the edge policy represented by an int
     */

    public int getEdgePolicy();

    /**
     * getter method for the neighbor policy
     * @return the neighbor policy represented by an int
     */

    public int getNeighborPolicy();

    /**
     * getter method for the shape
     * @return the shape
     */

    public int getShape();

    /**
     * get the cells in a string format (for testing)
     * @param cells 2d array of cells
     * @return a string with the relevant information
     */

    public String getCellsString(Cell[][] cells);
}

