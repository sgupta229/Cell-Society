package model;


import java.awt.Point;
import java.util.Map;

/**
 * class defines the implementation of the game of life grid
 */

public class GameOfLifeGrid extends AbstractShapeGrid {

    /**
     * game of life grid constructor
     * @param width the width of the grid
     * @param height the height of the grid
     * @param gridData a 2d array of cells of the grid
     * @param possibleStateMap a map representing the possible states of cells
     * @param edgePolicy the edge policy for the grid
     * @param neighborPolicy the neighbor policy for the grid
     * @param shape the shape of the cells in the grid
     */

    public GameOfLifeGrid(int width, int height, Cell[][] gridData, Map<Integer, State> possibleStateMap, int edgePolicy, int neighborPolicy, int shape) {
        super(width, height, gridData, possibleStateMap, edgePolicy, neighborPolicy, shape);
    }

    /**
     * method that defines how to find the next grid given the current one
     */

    public void createNextGrid() {

        Map<Integer, State> stateMap = getPossibleStateMap();
        Cell[][] currentGrid = getCurrentGrid();
        Cell[][] nextGrid = new Cell[currentGrid.length][currentGrid[0].length];

        for(int i = 0; i < currentGrid.length; i++) {
            for(int j = 0; j < currentGrid[0].length; j++) {
                Cell currCell = currentGrid[i][j];

                int aliveNeighbors = countAliveNeighbors(currCell);

                if(aliveNeighbors <= 2) {
                    nextGrid[i][j] = new Cell(new Point(j, i), stateMap.get(0));
                }
                if((aliveNeighbors == 2 || aliveNeighbors == 3) && currentGrid[i][j].getCurrState().getType() == 1) {
                    nextGrid[i][j] = new Cell(new Point(j, i), stateMap.get(1));
                }
                if(aliveNeighbors == 3 && currentGrid[i][j].getCurrState().getType() == 0) {
                    nextGrid[i][j] = new Cell(new Point(j, i), stateMap.get(1));
                }
                if(aliveNeighbors > 3) {
                    nextGrid[i][j] = new Cell(new Point(j, i), stateMap.get(0));
                }
            }
        }

        setNextGrid(nextGrid);
    }

    private int countAliveNeighbors(Cell currCell) {
        int aliveNeighbors = 0;
        for(Cell c : currCell.getNeighbors()) {
            if(c.getCurrState().getType() == 1) {
                aliveNeighbors++;
            }
        }
        return aliveNeighbors;
    }
}