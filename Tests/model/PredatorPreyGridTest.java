package model;

import javafx.scene.paint.Color;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.awt.*;
import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PredatorPreyGridTest {
    PredatorPreyGrid p;


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
        int edgePolicy = 1;
        int neighborPolicy = 1;
        int shape = 0;
        int maxEnergy = 5;
        int energyFood = 2;
        int sharkSpawn = 5;
        int fishSpawn = 3;

        p = new PredatorPreyGrid(numCols, numRows, arr, possibleStateMap, edgePolicy, neighborPolicy, shape, maxEnergy, energyFood, sharkSpawn, fishSpawn);

    }

    @Test
    void TestSharkEat(){
        setupNext("predatorPreySharkEat");
        var expected = "0,0,0\n" +
                "0,0,2\n" +
                "0,0,0";
        p.createNextGrid();
        var actual = p.getCellsString(p.getNextGrid());
        assertEquals(expected, actual);
    }

    @Test
    void TestSharkEatThenDie(){
        setupNext("predatorPreySharkEat");
        var expected = "0,0,0\n" +
                "0,0,0\n" +
                "0,0,0";
        int i = 0;
        while (i<6){
            p.createNextGrid();
            p.setCurrentGrid(p.getNextGrid());
            i++;
        }
        var actual = p.getCellsString(p.getNextGrid());
        assertEquals(expected, actual);

    }

    @Test
    void TestFishReproduces(){
        setupNext("predatorPreyLoneFish");
        var expected = 2;
        int i = 0;
        while (i<3){
            p.createNextGrid();
            p.setCurrentGrid(p.getNextGrid());
            i++;
        }

        int actualCount = 0;
        var cells = p.getNextGrid();
        for (Cell[] row: cells){
            for (Cell c: row){
                if (c.getCurrState().getType()==1)
                    actualCount++;
            }
        }

        assertEquals(expected, actualCount);
    }

}