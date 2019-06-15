package model.neighborConstruction;

import model.Cell;
import model.Grid;

/**
 * abstract superclass for the ShapeNeighborConstructors
 */
public abstract class NeighborConstructor {
    private int numRows;
    private int numCols;
    private int edgePolicy;
    private static final int BOUNDED_EDGE = 0;
    private static final int TOROIDAL_EDGE = 1;
    private static final int KLEIN_BOTTLE_EDGE = 2;
    private Grid myGrid;

    /** constructs class and calls setNeighbors()
     *
     * @param grid current Grid
     */
    public NeighborConstructor(Grid grid) {
        myGrid = grid;
        numRows = myGrid.getNumRows();
        numCols = myGrid.getNumCols();
        edgePolicy = myGrid.getEdgePolicy();
        setNeighbors();
    }

    /** for use in subclasses
     *
     * @return Grid
     */
    public Grid getMyGrid() {
        return myGrid;
    }

    private void setNeighbors(){
        var currGrid = myGrid.getCurrentGrid();
        for(int row = 0; row < numRows; row++) {
            for(int col = 0; col < numCols; col++) {
                Cell currCell = currGrid[row][col];
                if(edgePolicy ==KLEIN_BOTTLE_EDGE) {
                    addKleinBottleNeighbors(currCell, row, col);
                }
                else if(edgePolicy ==TOROIDAL_EDGE) {
                    addToroidalNeighbors(currCell, row, col, currGrid);
                }
                //so BOUNDED_EDGE
                else {
                    addNeighbors(currCell, row, col, row-1,row+1,col-1,col+1, currGrid);
                }
            }
        }
    }

    private void addToroidalNeighbors(Cell cell, int row, int col, Cell[][] grid) {
        var arr = toroidalHelper(row, col);
        addNeighbors(cell, row, col, arr[0], arr[1], arr[2], arr[3], grid);
    }

    private void addKleinBottleNeighbors(Cell cell, int row, int col) {
        addToroidalNeighbors(cell, row, col, reverseTopAndBottomRows());
    }

    /** add neighbors in way particular to each shape
     *
     * @param cell the cell in question
     * @param row cell's row
     * @param col cell's col
     * @param top coord 'above' the cell, may be different if toroidal or klein bottle
     * @param bottom coord 'below' the cell, may be different if toroidal or klein bottle
     * @param left coord 'left' the cell, may be different if toroidal or klein bottle
     * @param right coord 'right' the cell, may be different if toroidal or klein bottle
     * @param grid grid to work with, is different than myGrid's grid if klein bottle edge
     */
    protected abstract void addNeighbors(Cell cell, int row, int col, int top, int bottom, int left, int right, Cell[][] grid);


    //////////////////////////////////////////
    //setNeighbors() helper methods below
    //////////////////////////////////////////

    /** Adds neighbor if given coords are valid
     *
     * @param cell the cell in question
     * @param row cell's row
     * @param col cell's col
     * @param grid grid to work with, is different than myGrid's grid if klein bottle edge
     */
    protected void addNeighborIfValid(Cell cell, int row, int col, Cell[][] grid) {
        try {
            if (edgePolicy != BOUNDED_EDGE) {
                cell.addNeighbor(grid[row][col]);
            } else if (row >= 0 && row <= numRows - 1 && col >= 0 && col <= numCols - 1) {
                cell.addNeighbor(grid[row][col]);
            }
        } catch (Exception e){
            throw new IllegalArgumentException("Unable to add neighbor");
        }
    }

    private int[] toroidalHelper(int row, int col) {
        //for column
        int left = col - 1;
        int right = col + 1;
        //for row
        int top = row - 1;
        int bottom = row + 1;
        if(row == 0) {
            top = numRows - 1;
        }
        if(row == numRows - 1) {
            bottom = 0;
        }
        if(col == 0) {
            left = numCols - 1;
        }
        if(col == numCols - 1) {
            right = 0;
        }

        return new int[] {top, bottom, left, right};
    }

    private Cell[][] reverseTopAndBottomRows() {
        var curGrid = copyArr(myGrid.getCurrentGrid());

        var reversedTop = reverseArray(curGrid[0]);
        var reversedBottom = reverseArray(curGrid[numRows -1]);

        for(int r = 0; r < numRows; r++) {
            for(int c = 0; c < numCols; c++) {
                if (r == 0){
                    curGrid[r][c] = reversedTop[c];
                }
                else if (r == numRows -1){
                    curGrid[r][c] = reversedBottom[c];
                }
            }
        }
        return curGrid;
    }

    private Cell[][] copyArr(Cell[][] grid) {
        var copy = new Cell[grid.length][grid[0].length];
        for (int i =0;i<grid.length;i++){
            for (int j=0;j<grid[0].length;j++){
                var cell = grid[i][j];
                copy[i][j] = new Cell(cell.getPosition(), cell.getCurrState());
            }
        }
        return copy;
    }

    private <T> T[] reverseArray(T[] arr) {
        for (int i = 0; i<arr.length; i++){
            arr[i] = arr[arr.length - i - 1];
        }
        return arr;
    }

    /**
     *
     * @return returns edge policy as a number 0-2
     */
    public int getEdgePolicy() {
        return edgePolicy;
    }
}
