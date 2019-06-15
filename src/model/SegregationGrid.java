package model;


import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Class representing the functionality of the Segregation Simulation
 */

/**
 * Design Choices
 *
 * If there are more cells that want to relocate than there are empty spots, as many cells possible try to relocate and the rest
 * stay unsatisfied.
 *
 * When a cell relocates its new location is chosen at random.
 */

public class SegregationGrid extends AbstractShapeGrid {
    private double threshold;
    private State emptyState;

    /**
     * constructor for a segregation grid
     * @param width the width of the grid
     * @param height the height of the grid
     * @param gridData the 2d array of cells of the grid
     * @param possibleStateMap the possible states of the cells in a map
     * @param edgePolicy the edge policy of the cells
     * @param neighborPolicy the neighbor policy of the cells
     * @param shape the shape of the cells in the grid
     * @param threshold the threshold determining whether a cell is satisfied or not
     */

    public SegregationGrid(int width, int height, Cell[][] gridData, Map<Integer, State> possibleStateMap, int edgePolicy, int neighborPolicy, int shape, double threshold) {
        super(width, height, gridData, possibleStateMap, edgePolicy, neighborPolicy, shape);
        this.threshold = threshold;
        var stateMap = getPossibleStateMap();
        emptyState = stateMap.get(0);
    }

    private List<Point> findEmptyCells() {
        Cell[][] currentGrid = getCurrentGrid();
        List<Point> emptyPoints = new ArrayList<>();

        for(int i = 0; i < currentGrid.length; i++) {
            for (int j = 0; j < currentGrid[0].length; j++) {
                Cell currCell = currentGrid[i][j];
                if(currCell.getCurrState().getType() == 0) {
                    emptyPoints.add(new Point(i, j));
                }
            }
        }
        Collections.shuffle(emptyPoints);
        return emptyPoints;
    }

    /**
     * method that goes through the current grid and makes the new grid based on neighbors
     */

    public void createNextGrid() {
        Cell[][] currentGrid = getCurrentGrid();
        Cell[][] nextGrid = new Cell[currentGrid.length][currentGrid[0].length];
        List<Point> emptyPoints = findEmptyCells();

        for(int i = 0; i < currentGrid.length; i++) {
            for (int j = 0; j < currentGrid[0].length; j++) {
                double numNeighbors = 0;
                double amountSatisfied = 0;
                Cell currCell = currentGrid[i][j];
                if(currCell.getCurrState().getType() != 0) {
                    for(Cell c : currCell.getNeighbors()) {
                        if (c.getCurrState().getType() != 0) {
                            numNeighbors += 1;
                        }
                        if(c.getCurrState().getType() == currCell.getCurrState().getType()) {
                            amountSatisfied += 1;
                        }
                    }
                    if(numNeighbors == 0.0) {
                        nextGrid[i][j] = new Cell(currCell.getPosition(), currCell.getCurrState());
                    }
                    else if((amountSatisfied / numNeighbors < threshold) && !emptyPoints.isEmpty()) {
                        Point relocate = emptyPoints.remove(0);
                        int newX = relocate.x;
                        int newY = relocate.y;
                        nextGrid[newX][newY] = new Cell(currCell.getPosition(), currCell.getCurrState());
                    }
                    else {
                        nextGrid[i][j] = new Cell(currCell.getPosition(), currCell.getCurrState());
                    }
                }
            }
        }

        for(int i = 0; i < nextGrid.length; i++) {
            for (int j = 0; j < nextGrid[0].length; j++) {
                if(nextGrid[i][j] == null) {
                    nextGrid[i][j] = new Cell(new Point(i, j), emptyState);
                }
            }
        }
        setNextGrid(nextGrid);
    }

    /**
     * update the threshold at which cells are satisfied
     * @param newThresh the new threshold
     */

    public void updateThresh(double newThresh) {
        this.threshold = newThresh;
    }
}
