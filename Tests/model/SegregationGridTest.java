package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SegregationGridTest {
    private SegregationGrid myGrid;
    private double threshold = 0.5;

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

    private void setupNext(String s) {
        var reader = new ConfigReader(s);
        var numCols = reader.getNumCols();
        var numRows = reader.getNumRows();
        var gridData = reader.getGridData();
        var possibleStateMap = reader.getMyStateMap();
        var arr = getCellArrayFromList(numCols, numRows, gridData, possibleStateMap);

        myGrid = new SegregationGrid(numCols, numRows, arr, possibleStateMap, 1, 1, 0, threshold);
    }

    @Test
    public void noNeighbors() {
        setupNext("segregationNoNeighbors");
        var expected = "0,0,0\n" +
                "0,1,0\n" +
                "0,0,0";
        myGrid.createNextGrid();
        var actual = myGrid.getCellsString(myGrid.getNextGrid());
        assertEquals(expected, actual);
    }

    @Test
    public void moveIfNotSatisfied() {
        setupNext("segregationCheckSatisfied");
        var initial = "2,2,0\n" +
                "1,0,0\n" +
                "0,1,0";
        myGrid.createNextGrid();
        var next = myGrid.getCellsString(myGrid.getNextGrid());
        assertFalse(initial.equals(next));
    }

    @Test
    public void stayIfSatisfied() {
        setupNext("segregationCheckSatisfied");
        var initial = "2,2,0\n" +
                "1,0,0\n" +
                "0,1,0";
        myGrid.createNextGrid();
        String next = myGrid.getCellsString(myGrid.getNextGrid());
        //Loop through 25 iterations of the grid. Both cells with values 2
        //should remain in the same spot since they should never be dissatisfied
        for(int i = 0; i < 25; i++) {
            myGrid.createNextGrid();
            next = myGrid.getCellsString(myGrid.getNextGrid());
            myGrid.setCurrentGrid(myGrid.getNextGrid());
        }
        String expected = initial.substring(0, 3);
        String actual = next.substring(0, 3);
        assertEquals(expected, actual);
    }

    @Test
    public void dissatisfiedGreaterThanEmptySpots() {
        setupNext("segregationLittleEmptySpots");
        var expected1 = "2,2,2\n" +
                "2,2,1\n" +
                "2,0,1";
        var expected2 = "2,2,2\n" +
                "2,2,0\n" +
                "2,1,1";
        myGrid.createNextGrid();
        var actual = myGrid.getCellsString(myGrid.getNextGrid());
        assertTrue(actual.equals(expected1) || actual.equals(expected2));
    }
}