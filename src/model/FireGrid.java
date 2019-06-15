package model;

import java.util.Map;

/**
 * class that defines the implementation of the fire grid
 */

public class FireGrid extends AbstractShapeGrid {

    private double probCatch;
    private State emptyState;
    private State burningState;
    private State treeState;

    /**
     * fire grid constructor
     * @param width the width of the grid
     * @param height the height of the grid
     * @param gridData the 2d array of cells of the grid
     * @param possibleStateMap - the possible states the cells can take
     * @param edgePolicy - the edge policy to determine future neighbors
     * @param neighborPolicy - the neighbor policy to determine future neighbors
     * @param shape - the shape of cells in the grid
     * @param probCatch - the probability that a cell catches on fire
     */

    public FireGrid(int width, int height, Cell[][] gridData, Map<Integer, State> possibleStateMap, int edgePolicy, int neighborPolicy, int shape, double probCatch) {
        super(width, height, gridData, possibleStateMap, edgePolicy, neighborPolicy, shape);
        var stateMap = getPossibleStateMap();
        emptyState = stateMap.get(0);
        burningState = stateMap.get(2);
        treeState = stateMap.get(1);
        this.probCatch = probCatch;
    }

    /**
     * method that determines the next grid given the current one
     */

    public void createNextGrid() {
        Cell[][] currentGrid = getCurrentGrid();
        Cell[][] nextGrid = new Cell[currentGrid.length][currentGrid[0].length];

        for(int i = 0; i < currentGrid.length; i++) {
            for (int j = 0; j < currentGrid[0].length; j++) {
                Cell currCell = currentGrid[i][j];
                if(currCell.getCurrState().getType() == 1) {
                    for(Cell c : currCell.getNeighbors()) {
                        if(c.getCurrState().getType() == 2 && Math.random() <= probCatch) {
                            nextGrid[i][j] = new Cell(currCell.getPosition(), burningState);
                            break;
                        }
                        nextGrid[i][j] = new Cell(currCell.getPosition(), treeState);
                    }
                }
                else {
                    nextGrid[i][j] = new Cell(currCell.getPosition(), emptyState);
                }
            }
        }
        setNextGrid(nextGrid);
    }

    /**
     * method to update the probability of catching fire
     * @param newProb - probabilty (decimal) of catching fire
     */

    public void updateProb(double newProb) {
        this.probCatch = newProb;
    }
}
