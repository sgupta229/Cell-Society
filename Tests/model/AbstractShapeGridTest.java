package model;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AbstractShapeGridTest {

    private GameOfLifeGrid myGrid;

    private Cell[][] getCellArrayFromList(int numCols, int numRows, List<Integer> gridData, Map<Integer, State> possibleStateMap) {
        Cell[][] cells = new Cell[numRows][numCols];
        int r = 0;
        int c = 0;
        for (Integer val: gridData){
            if (c >= numCols){
                c = 0;
                r++;
            }
            State s = possibleStateMap.get(val);
            cells[r][c] = new Cell(new Point(c, r), s);
            c++;
        }
        return cells;
    }

    void setupNext(String simType, int edgePolicy) {
        var reader = new ConfigReader(simType);
        var numCols = reader.getNumCols();
        var numRows = reader.getNumRows();
        var gridData = reader.getGridData();
        var cellShape = reader.getCellShape();
        var neighborPolicy = reader.getNeighborPolicy();
        var possibleStateMap = reader.getMyStateMap();
        var arr = getCellArrayFromList(numCols, numRows, gridData, possibleStateMap);

        myGrid = new GameOfLifeGrid(numCols, numRows, arr, possibleStateMap, edgePolicy, neighborPolicy, cellShape);
    }

    @Test
    public void testSetNeighborsCompleteBoundedSquare() {
        setupNext("squareNeighborTest", 0);
        var currGrid = myGrid.getCurrentGrid();
        var cell = currGrid[0][0];
        var neighbors = cell.getNeighbors();
        var actual = neighbors.get(0).getPosition().equals(new Point(0,1));
        actual &= neighbors.get(1).getPosition().equals(new Point(1,0));
        actual &= neighbors.get(2).getPosition().equals(new Point(1,1));
        int actualNumNeighbors = neighbors.size();
        int expectedNumNeighbors = 3;
        assertTrue(actual && (actualNumNeighbors == expectedNumNeighbors));
    }

    @Test
    public void testSetNeighborsCompleteToroidalSquare() {
        setupNext("squareNeighborTest", 1);
        var currGrid = myGrid.getCurrentGrid();
        var cell = currGrid[0][0];
        var neighbors = cell.getNeighbors();
        var actual = neighbors.get(0).getPosition().equals(new Point(0,1));
        actual &= neighbors.get(1).getPosition().equals(new Point(0,4));
        actual &= neighbors.get(2).getPosition().equals(new Point(4,0));
        actual &= neighbors.get(3).getPosition().equals(new Point(1,0));
        actual &= neighbors.get(4).getPosition().equals(new Point(4,1));
        actual &= neighbors.get(5).getPosition().equals(new Point(1,1));
        actual &= neighbors.get(6).getPosition().equals(new Point(4,4));
        actual &= neighbors.get(7).getPosition().equals(new Point(1,4));
        int actualNumNeighbors = neighbors.size();
        int expectedNumNeighbors = 8;
        assertTrue(actual && (actualNumNeighbors == expectedNumNeighbors));
    }

    @Test
    void testSetNeighborsToroidalHex() {
        setupNext("gameOfLifeHex", 1);
        var currGrid = myGrid.getCurrentGrid();
        var cell = currGrid[0][0];
        var neighbors = cell.getNeighbors();
        var actual = neighbors.get(0).getPosition().equals(new Point(2,0));
        actual &= neighbors.get(1).getPosition().equals(new Point(1,0));
        actual &= neighbors.get(2).getPosition().equals(new Point(0,1));
        actual &= neighbors.get(3).getPosition().equals(new Point(2,1));
        actual &= neighbors.get(4).getPosition().equals(new Point(0,2));
        actual &= neighbors.get(5).getPosition().equals(new Point(2,2));
        assertTrue(actual);

        cell = currGrid[1][0];
        neighbors = cell.getNeighbors();
        actual = neighbors.get(0).getPosition().equals(new Point(2,1));
        actual &= neighbors.get(1).getPosition().equals(new Point(1,1));
        actual &= neighbors.get(2).getPosition().equals(new Point(0,2));
        actual &= neighbors.get(3).getPosition().equals(new Point(1,2));
        actual &= neighbors.get(4).getPosition().equals(new Point(0,0));
        actual &= neighbors.get(5).getPosition().equals(new Point(1,0));
        assertTrue(actual);

    }

    @Test
    void testSetNeighborsKleinBottle() {
        setupNext("gameOfLifeHex", 2);
        var currGrid = myGrid.getCurrentGrid();
        var cell = currGrid[0][0];
        var neighbors = cell.getNeighbors();
        var actual = neighbors.get(0).getPosition().equals(new Point(2,0));
        actual &= neighbors.get(1).getPosition().equals(new Point(1,0));
        actual &= neighbors.get(2).getPosition().equals(new Point(0,1));
        actual &= neighbors.get(3).getPosition().equals(new Point(2,1));
        actual &= neighbors.get(4).getPosition().equals(new Point(2,2));
        actual &= neighbors.get(5).getPosition().equals(new Point(2,2));
        assertTrue(actual);
    }

    @Test
    public void testSetNeighborsCompleteBoundedTriangle() {
        setupNext("triangleNeighborTest", 0);
        var currGrid = myGrid.getCurrentGrid();
        var cell = currGrid[0][0];
        var neighbors = cell.getNeighbors();
        var actual = neighbors.get(0).getPosition().equals(new Point(1,0));
        actual &= neighbors.get(1).getPosition().equals(new Point(0,1));
        actual &= neighbors.get(2).getPosition().equals(new Point(1,1));
        int actualNumNeighbors = neighbors.size();
        int expectedNumNeighbors = 5;
        assertTrue(actual && (actualNumNeighbors == expectedNumNeighbors));
    }

    @Test
    public void testSetNeighborsCompleteToroidalTriangle() {
        setupNext("triangleNeighborTest", 1);
        var currGrid = myGrid.getCurrentGrid();
        var cell = currGrid[0][0];
        var neighbors = cell.getNeighbors();
        var actual = neighbors.get(0).getPosition().equals(new Point(4,0));
        actual &= neighbors.get(1).getPosition().equals(new Point(1,0));
        actual &= neighbors.get(2).getPosition().equals(new Point(0,1));
        actual &= neighbors.get(3).getPosition().equals(new Point(4,4));
        actual &= neighbors.get(4).getPosition().equals(new Point(1,4));
        actual &= neighbors.get(5).getPosition().equals(new Point(4,1));
        actual &= neighbors.get(6).getPosition().equals(new Point(1,1));
        actual &= neighbors.get(7).getPosition().equals(new Point(3,0));
        actual &= neighbors.get(8).getPosition().equals(new Point(2,0));
        actual &= neighbors.get(9).getPosition().equals(new Point(0,4));
        actual &= neighbors.get(10).getPosition().equals(new Point(3,1));
        actual &= neighbors.get(11).getPosition().equals(new Point(2,1));
        assertTrue(actual);
    }

}