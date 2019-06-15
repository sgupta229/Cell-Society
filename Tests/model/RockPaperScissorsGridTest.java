package model;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RockPaperScissorsGridTest {

    RockPaperScissorsGrid rps;


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

    void setupNext(String s) {
        var reader = new ConfigReader(s);
        var numCols = reader.getNumCols();
        var numRows = reader.getNumRows();
        var gridData = reader.getGridData();
        var possibleStateMap = reader.getMyStateMap();
        var arr = getCellArrayFromList(numCols, numRows, gridData, possibleStateMap);

        rps = new RockPaperScissorsGrid(numCols, numRows, arr, possibleStateMap, 1, 1, 0, 3, 2);
    }

    @Test
    void cellLoses(){
        setupNext("rpsPaperLoses");
        rps.createNextGrid();
        var g = rps.getNextGrid();
        var cell = g[1][1];
        var expected = 2;
        var actual = cell.getCurrState().getType();
        assertEquals(expected,actual);

    }

    @Test
    void cellWins(){
        setupNext("rpsRockWins");
        rps.createNextGrid();
        var g = rps.getNextGrid();
        var cell = g[1][1];
        var expected = 0;
        var actual = cell.getCurrState().getType();
        assertEquals(expected,actual);

    }

}