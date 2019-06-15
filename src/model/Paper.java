package model;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Paper cell for Rock Paper Scissors Simulation
 */
public class Paper extends RPSCell {

    /** standard constructor
     *
     * @param pos cell's x and y coords
     * @param s State of cell
     */
    public Paper(Point pos, State s){
        super(pos, s);
    }

    /** special constructor used to create Paper from generic Cell
     * @param cell
     */
    public Paper(Cell cell) {
        super(cell);
    }

    /**
     * @param stateMap
     * @return returns a new Cell with a state that defeats Paper (Scissors)
     */
    @Override
    public RPSCell getDefeater(Map<Integer, State> stateMap) {
        return new Scissors(getPosition(), stateMap.get(2));
    }

    /**
     * @return returns a new Paper that is a copy of the current Rock
     */
    @Override
    public RPSCell copy() {
        return new Paper(this.getPosition(), this.getCurrState());
    }
}
