package model;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FireGridTest {
    private FireGrid myGrid;

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

    private void setupNext(String s, double probCatch) {
        var reader = new ConfigReader(s);
        var numCols = reader.getNumCols();
        var numRows = reader.getNumRows();
        var gridData = reader.getGridData();
        var possibleStateMap = reader.getMyStateMap();
        var arr = getCellArrayFromList(numCols, numRows, gridData, possibleStateMap);

        myGrid = new FireGrid(numCols, numRows, arr, possibleStateMap, 0, 1, 0, probCatch);
    }

    @Test
    public void checkProbCatch() {
        setupNext("fireBurnEverything", 1.0);
        var expected = "1,2,1\n" +
                "2,0,2\n" +
                "1,2,1";
        myGrid.createNextGrid();
        myGrid.setCurrentGrid(myGrid.getNextGrid());
        String actual = myGrid.getCellsString(myGrid.getNextGrid());
        assertEquals(expected, actual);
    }

    @Test
    public void noFire() {
        setupNext("fireNoFire", 1.0);
        var expected = "0,0,0\n" +
                "0,1,0\n" +
                "0,0,0";
        myGrid.createNextGrid();
        String actual = myGrid.getCellsString(myGrid.getNextGrid());
        assertEquals(expected, actual);
    }

    @Test
    public void emptySpotsDoNotBurn() {
        setupNext("fireEmptySpotsBurning", 1.0);
        var expected = "0,0,0\n" +
                "0,0,0\n" +
                "0,0,0";
        myGrid.createNextGrid();
        String actual = myGrid.getCellsString(myGrid.getNextGrid());
        assertEquals(expected, actual);
    }


}