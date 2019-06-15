package model;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Superclass for Rock Paper and Scissors
 */
public abstract class RPSCell extends Cell{

    /** standard constructor
     *
     * @param pos cell's x and y coords
     * @param s State of cell
     */
    public RPSCell(Point pos, State s){
        super(pos, s);
    }

    /** special constructor used to create specific RPSCell from generic Cell
     * @param cell
     */
    public RPSCell(Cell cell){
        super(cell.getPosition(), cell.getCurrState());
        addAllNeighbors(cell.getNeighbors());
    }

    /**
     * @param stateMap
     * @return returns a new Cell with a state that defeats this cell's state
     */
    public abstract RPSCell getDefeater(Map<Integer, State> stateMap);

    /**
     * @return returns a new RPSCell that is a copy of the current RPSCell
     */
    public abstract RPSCell copy();
}
