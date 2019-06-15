package model;

import org.junit.jupiter.api.Test;
import model.Cell;
import model.ConfigReader;
import model.GameOfLifeGrid;
import model.State;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameOfLifeGridTest {

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

    void setupNext(String simType) {
        var reader = new ConfigReader(simType);
        var numCols = reader.getNumCols();
        var numRows = reader.getNumRows();
        var gridData = reader.getGridData();
        var cellShape = reader.getCellShape();
        var possibleStateMap = reader.getMyStateMap();
        var arr = getCellArrayFromList(numCols, numRows, gridData, possibleStateMap);

        myGrid = new GameOfLifeGrid(numCols, numRows, arr, possibleStateMap, 1, 0, 0);
    }

    @Test
    void nextGridRandomTest() {
        setupNext("gameOfLifeRandom");
        var expected = "0,0,0,0,0\n" +
                "0,0,0,0,0\n" +
                "0,0,0,0,0\n" +
                "0,0,0,0,0\n" +
                "0,0,0,0,0\n" +
                "0,0,0,0,0\n" +
                "0,0,0,0,0";
        for (int i = 0; i <= 30; i++) {
            myGrid.createNextGrid();
            myGrid.setCurrentGrid(myGrid.getNextGrid());
        }
        var actual = myGrid.getCellsString(myGrid.getNextGrid());
        assertEquals(expected, actual, "Next Grid created must equal the expected value");
    }

    @Test
    void nextGridGliderTest() {
        setupNext("gameOfLifeGlider");
        var expected = "0,0,0,0,0,0,0,0,0,0\n" +
                "0,0,0,0,0,0,0,0,0,0\n" +
                "0,0,0,1,0,0,0,0,0,0\n" +
                "0,1,0,1,0,0,0,0,0,0\n" +
                "0,0,1,1,0,0,0,0,0,0\n" +
                "0,0,0,0,0,0,0,0,0,0\n" +
                "0,0,0,0,0,0,0,0,0,0\n" +
                "0,0,0,0,0,0,0,0,0,0\n" +
                "0,0,0,0,0,0,0,0,0,0\n" +
                "0,0,0,0,0,0,0,0,0,0";
        myGrid.createNextGrid();
        var actual = myGrid.getCellsString(myGrid.getNextGrid());
        assertEquals(expected, actual, "Next Grid created must equal the expected value");
    }

    @Test
    void nextGridBeaconTest() {
        setupNext("gameOfLifeBeacon");
        var expected = "0,0,0,0,0,0\n" +
                "0,1,1,0,0,0\n" +
                "0,1,0,0,0,0\n" +
                "0,0,0,0,1,0\n" +
                "0,0,0,1,1,0\n" +
                "0,0,0,0,0,0";
        myGrid.createNextGrid();
        var actual = myGrid.getCellsString(myGrid.getNextGrid());
        assertEquals(expected, actual, "Next Grid created must equal the expected value");
    }

    @Test
    void nextGridPulsarTest() {
        setupNext("gameOfLifePulsar");
        var expected = "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0\n" +
                "0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0\n" +
                "0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0\n" +
                "0,0,0,0,0,1,1,0,0,0,1,1,0,0,0,0,0\n" +
                "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0\n" +
                "0,1,1,1,0,0,1,1,0,1,1,0,0,1,1,1,0\n" +
                "0,0,0,1,0,1,0,1,0,1,0,1,0,1,0,0,0\n" +
                "0,0,0,0,0,1,1,0,0,0,1,1,0,0,0,0,0\n" +
                "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0\n" +
                "0,0,0,0,0,1,1,0,0,0,1,1,0,0,0,0,0\n" +
                "0,0,0,1,0,1,0,1,0,1,0,1,0,1,0,0,0\n" +
                "0,1,1,1,0,0,1,1,0,1,1,0,0,1,1,1,0\n" +
                "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0\n" +
                "0,0,0,0,0,1,1,0,0,0,1,1,0,0,0,0,0\n" +
                "0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0\n" +
                "0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0\n" +
                "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";
        myGrid.createNextGrid();
        var actual = myGrid.getCellsString(myGrid.getNextGrid());
        assertEquals(expected, actual, "Next Grid created must equal the expected value");
    }
}