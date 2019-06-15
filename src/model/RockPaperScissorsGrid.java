package model;

import java.util.Map;

/**
 * RockPaperScissor Simulation
 */
public class RockPaperScissorsGrid extends AbstractShapeGrid {

    private final State ROCK_STATE;
    private final State PAPER_STATE;
    private final State SCISSORS_STATE;
    private int THRESHOLD;
    private int RANDOM_UPPER_BOUND;
    private final Map<Integer, State> stateMap;


    /**
     *
     * @param width number of columns
     * @param height number of rows
     * @param gridData grid data structure
     * @param possibleStateMap map of possible States in this sim
     * @param edgePolicy int of edge policy (0-2)
     * @param neighborPolicy int of neighbor policy (0-2)
     * @param shape int of shape (0-2)
     * @param threshold threshold number for neighbors to beat current cell
     * @param randomAmount upperbound for randomizer to modify threshold amounts
     */
    public RockPaperScissorsGrid(int width, int height, Cell[][] gridData, Map<Integer, State> possibleStateMap, int edgePolicy, int neighborPolicy, int shape, int threshold, int randomAmount) {
        super(width, height, gridData, possibleStateMap, edgePolicy, neighborPolicy, shape);
        stateMap = getPossibleStateMap();
        ROCK_STATE = stateMap.get(0);
        PAPER_STATE = stateMap.get(1);
        SCISSORS_STATE = stateMap.get(2);
        populateGridWithRPSCells();
        THRESHOLD = threshold;
        RANDOM_UPPER_BOUND = randomAmount;
    }

    private void populateGridWithRPSCells() {
        var currentGrid = getCurrentGrid();
        for(int i = 0; i < currentGrid.length; i++) {
            for(int j = 0; j < currentGrid[0].length; j++) {
                var cell = currentGrid[i][j];
                if (cell.getCurrState().equals(ROCK_STATE)) {
                    currentGrid[i][j] = new Rock(cell);
                } else if (cell.getCurrState().equals(PAPER_STATE)) {
                    currentGrid[i][j] = new Paper(cell);
                } else if (cell.getCurrState().equals(SCISSORS_STATE)) {
                    currentGrid[i][j] = new Scissors(cell);
                }
            }
        }
    }

    /**
     * method to create the next state of Grid (the rules method)
     */
    @Override
    public void createNextGrid() {
        var currentGrid = getCurrentGrid();
        var nextGrid = new Cell[currentGrid.length][currentGrid[0].length];

        for (Cell[] row: currentGrid) {
            for (Cell cell : row) {
                var rpsCell = getRPSCell(cell);
                var pos = cell.getPosition();
                if (isDefeated(rpsCell)){
                    nextGrid[pos.y][pos.x] = rpsCell.getDefeater(stateMap);
                }
                else {
                    nextGrid[pos.y][pos.x] = rpsCell.copy();
                }
            }
        }

//        System.out.println("\n========\n" + getCellsString(currentGrid) + "\n========\n" + getCellsString(nextGrid));
        setNextGrid(nextGrid);
    }

    private RPSCell getRPSCell(Cell cell) {
        if (cell.getCurrState().equals(ROCK_STATE)) {
            return new Rock(cell);
        }
        if (cell.getCurrState().equals(PAPER_STATE)) {
            return new Paper(cell);
        }
        if (cell.getCurrState().equals(SCISSORS_STATE)) {
            return new Scissors(cell);
        }
        throw new IllegalArgumentException("State is not a recognized RPS State");
    }

    //in this implementation, our cell chooses one of its neighbors at random and plays RPS with that one neighbor
//    private boolean isDefeated(RPSCell cell) {
//        var defeaterState = cell.getDefeater(stateMap).getCurrState();
//        var neighbors = cell.getNeighbors();
//        var r = new Random();
//        var i = r.nextInt(neighbors.size());
//        if (neighbors.get(i).getCurrState().equals(defeaterState))
//            return true;
//
//        return false;
//    }

    //in this implementation, our cell compares itself to all of its neighbors, and if enough of its neighbors are
    //of the defeater type, we return true
    private boolean isDefeated(RPSCell cell) {
        var defeaterState = cell.getDefeater(stateMap).getCurrState();
        var neighbors = cell.getNeighbors();
        int count = 0;
        for (Cell other: neighbors){
            if (other.getCurrState().equals(defeaterState)) {
                count++;
            }
        }

        return (count >= THRESHOLD + getModifier());
    }

    private int getModifier() {
        if (RANDOM_UPPER_BOUND==0) {
            return 0;
        }
        return (int) (Math.random() * RANDOM_UPPER_BOUND);
    }

    /** updates threshold if button is pressed
     *
     * @param newThresh new threshold
     */
    public void updateThreshhold(int newThresh) {
        this.THRESHOLD = newThresh;
    }

    /** updates random if button is pressed
     *
     * @param newRandom new random upper bound
     */
    public void updateRandom(int newRandom) {
        this.RANDOM_UPPER_BOUND = newRandom;
    }
}
