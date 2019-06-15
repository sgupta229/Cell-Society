package model;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Scissor cell for Rock Paper Scissors Simulation
 */
public class Scissors extends RPSCell {

    /** standard constructor
     *
     * @param pos cell's x and y coords
     * @param s State of cell
     */
    public Scissors(Point pos, State s){
        super(pos, s);
    }

    /** special constructor used to create Scissors from generic Cell
     * @param cell
     */
    public Scissors(Cell cell) {
        super(cell);
    }

    /**
     * @param stateMap
     * @return returns a new Cell with a state that defeats Scissors (Rock)
     */
    @Override
    public RPSCell getDefeater(Map<Integer, State> stateMap) {
        return new Rock(getPosition(), stateMap.get(0));
    }

    /**
     * @return returns a new Scissor that is a copy of the current Scissor
     */
    @Override
    public RPSCell copy() {
        return new Scissors(this.getPosition(), this.getCurrState());
    }
}
