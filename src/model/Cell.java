package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * class representing a Cell
 */

public class Cell {

    private List<Cell> neighbors;
    private State currState;
    private Point position;

    /**
     * a constructor for a cell
     * @param gridPos - the position of the cell in the grid
     * @param state - the State of the cell
     */

    public Cell(Point gridPos, State state) {
        currState = state;
        position = gridPos;
        neighbors = new ArrayList<>();
    }

    /**
     * a constructor for a cell if the state is unknown
     * @param gridPos - the position of the cell in the grid
     */

    public Cell(Point gridPos) {
        position = gridPos;
        neighbors = new ArrayList<>();
    }

    /**
     * a constructor a cell given another cell
     * @param cell - the cell to duplicate
     */

    public Cell(Cell cell) {
        currState = cell.getCurrState();
        position = cell.getPosition();
        neighbors = cell.getNeighbors();
    }

    /**
     * getter method for the list of neighbors of a cell
     * @return a List of cells that are the neighbors of that cell
     */

    public List<Cell> getNeighbors() {
        List<Cell> newNeighbors = neighbors;
        return newNeighbors;
    }

    /**
     * adds a neighbor to a cell
     * @param neighbor - the neighbor to add to the cell's neighbor
     */

    public void addNeighbor(Cell neighbor) {
        this.neighbors.add(neighbor);
    }

    /**
     * set a list of neighbors to a cell's neighbors
     * @param list
     */

    public void addAllNeighbors(List<Cell> list) {this.neighbors = list;}

    /**
     * getter method to get the current State of a cell
     * @return - the State of a cell
     */

    public State getCurrState() {
        return currState;
    }

    /**
     * getter method to return the position of a cell
     * @return - the position of the cell
     */

    public Point getPosition() {
        return position;
    }

    /**
     * set the current state of the cell
     * @param s - the new state of the cell
     */

    public void setCurrState(State s) {
        this.currState = s;
    }

    /**
     * returns a string of the position and current state of a cell (for error checking)
     * @return a string that says the position and current state of a cell
     */

    public String toString() {
        return "Position: " + position.toString() + " Current State: " + currState;
    }

}
