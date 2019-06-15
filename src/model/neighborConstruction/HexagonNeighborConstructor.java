package model.neighborConstruction;

import model.Cell;
import model.Grid;

/**
 * assigns neighbors in hexagon way
 */
public class HexagonNeighborConstructor extends NeighborConstructor {

    /** standard constructor, calls super
     *
     * @param grid current Grid
     */
    public HexagonNeighborConstructor(Grid grid) {
        super(grid);
    }

    /** add neighbors in way particular to hexagon
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
        addNeighborIfValid(cell, row, left, grid);
        addNeighborIfValid(cell, row, right, grid);

        if (row%2==0){
            addNeighborIfValid(cell, bottom, col, grid);
            addNeighborIfValid(cell, bottom, left, grid);
            addNeighborIfValid(cell, top, col, grid);
            addNeighborIfValid(cell, top, left, grid);
        }
        else{
            addNeighborIfValid(cell, bottom, col, grid);
            addNeighborIfValid(cell, bottom, right, grid);
            addNeighborIfValid(cell, top, col, grid);
            addNeighborIfValid(cell, top, right, grid);
        }
    }
}
