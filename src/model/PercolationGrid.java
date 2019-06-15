package model;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PercolationGrid extends AbstractShapeGrid {

    /**
     * Constructor for a percolation grid
     * @param width the width of the grid
     * @param height the height of the grid
     * @param gridData the 2d array of cells for the grid
     * @param possibleStateMap the possible states of the cells in a map
     * @param edgePolicy the edge policy for the cells
     * @param neighborPolicy the neighbor policy for the cells
     * @param shape the shape of the cells in the grid
     */

    public PercolationGrid(int width, int height, Cell[][] gridData, Map<Integer, State> possibleStateMap, int edgePolicy, int neighborPolicy, int shape) {
        super(width, height, gridData, possibleStateMap, edgePolicy, neighborPolicy, shape);
    }

    /**
     * method that defines creating the next grid given the current one
     */

    public void createNextGrid() {

        Map<Integer, State> stateMap = getPossibleStateMap();
        Cell[][] currentGrid = getCurrentGrid();
        Cell[][] nextGrid = new Cell[currentGrid.length][currentGrid[0].length];

        for(int i = 0; i < currentGrid.length; i++) {
            for(int j = 0; j < currentGrid[0].length; j++) {
                Cell current = currentGrid[i][j];

                boolean isFull = false;
                for(Cell c : current.getNeighbors()) {
                    // For making sure there's no back flow: current.getPosition().getX() >= c.getPosition().getX()
                    if(c.getCurrState().getType() == 2) {
                        isFull = true;
                        break;
                    }
                }

                if(current.getCurrState().getType() != 0 && isFull) {
                    nextGrid[i][j] = new Cell(new Point(i, j), stateMap.get(2));
                }
                else {
                    nextGrid[i][j] = new Cell(new Point(i, j), current.getCurrState());
                }
            }
        }

        setNextGrid(nextGrid);
    }

}
