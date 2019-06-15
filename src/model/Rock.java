package model;


import java.awt.Point;
import java.util.Map;

/**
 * Rock cell for Rock Paper Scissors Simulation
 */
public class Rock extends RPSCell {

    /** standard constructor
     *
     * @param pos cell's x and y coords
     * @param s State of cell
     */
    public Rock(Point pos, State s){
        super(pos, s);
    }

    /** special constructor used to create Rock from generic Cell
     * @param cell
     */
    public Rock(Cell cell) {
        super(cell);
    }

    /**
     * @param stateMap
     * @return returns a new Cell with a state that defeats Rock (Paper)
     */
    @Override
    public RPSCell getDefeater(Map<Integer, State> stateMap) {
        return new Paper(getPosition(), stateMap.get(1));
    }

    /**
     * @return returns a new Rock that is a copy of the current Rock
     */
    @Override
    public RPSCell copy() {
        return new Rock(this.getPosition(), this.getCurrState());
    }
}
