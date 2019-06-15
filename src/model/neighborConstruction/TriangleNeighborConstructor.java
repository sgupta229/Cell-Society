package model.neighborConstruction;

import model.Cell;
import model.Grid;

/**
 * assigns neighbors in triangle way
 */
public class TriangleNeighborConstructor extends NeighborConstructor {

    private static final int COMPLETE_NEIGHBORS = 0;
    private static final int CARDINAL_NEIGHBORS = 1;
    private static final int DIAGONAL_NEIGHBORS = 2;

    /** standard constructor, calls super
     *
     * @param grid current Grid
     */
    public TriangleNeighborConstructor(Grid grid) {
        super(grid);
    }

    private boolean checkUprightTriangle(int row, int col) {
        if(row % 2 == 0) {
            return (col % 2 == 0);
        }
        else {
            return !(col % 2 == 0);
        }
    }

    /** add neighbors in way particular to triangle
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
    @Override
    protected void addNeighbors(Cell cell, int row, int col, int top, int bottom, int left, int right, Cell[][] grid) {
        boolean isUprightTriangle = checkUprightTriangle(row, col);
        if(getMyGrid().getNeighborPolicy() != DIAGONAL_NEIGHBORS) {
            addNeighborIfValid(cell, row, left, grid);
            addNeighborIfValid(cell, row, right, grid);
            if(isUprightTriangle) {
                addNeighborIfValid(cell, bottom, col, grid);
            }
            else {
                addNeighborIfValid(cell, top, col, grid);
            }
        }
        if(getMyGrid().getNeighborPolicy() != CARDINAL_NEIGHBORS) {
            addNeighborIfValid(cell, top, left, grid);
            addNeighborIfValid(cell, top, right, grid);
            addNeighborIfValid(cell, bottom, left, grid);
            addNeighborIfValid(cell, bottom, right, grid);
        }
        if(getMyGrid().getNeighborPolicy() == COMPLETE_NEIGHBORS) {
            int twoLeft = left - 1;
            int twoRight = right + 1;
            if(twoLeft < 0) {
                if(getEdgePolicy() != 0) {
                    twoLeft = twoLeft + getMyGrid().getNumCols();
                }
            }
            if(twoRight >= getMyGrid().getNumCols()) {
                if(getEdgePolicy() != 0) {
                    twoRight = twoRight - getMyGrid().getNumCols();
                }
            }
            addNeighborIfValid(cell, row, twoLeft, grid);
            addNeighborIfValid(cell, row, twoRight, grid);
            if(isUprightTriangle) {
                addNeighborIfValid(cell, top, col, grid);
                addNeighborIfValid(cell, bottom, twoLeft, grid);
                addNeighborIfValid(cell, bottom, twoRight, grid);
            }
            else {
                addNeighborIfValid(cell, bottom, col, grid);
                addNeighborIfValid(cell, top, twoLeft, grid);
                addNeighborIfValid(cell, top, twoRight, grid);
            }
        }
    }
}
