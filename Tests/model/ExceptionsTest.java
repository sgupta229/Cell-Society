package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExceptionsTest {

    @Test
    public void testFileNotFound(){

        var brokenFile = "brokenTests/nonexistentfile";

        Exception thrown =
                assertThrows(IllegalArgumentException.class, () -> {new ConfigReader(brokenFile);},
                        "ConfigReader should throw error for non existent file");

        assertTrue(thrown.getMessage().contains("cannot be found"));
    }

    @Test
    public void testBrokenSimtype(){

        var brokenFile = "brokenTests/brokenSimtype";

        Exception thrown =
                assertThrows(IllegalArgumentException.class, () -> {new ConfigReader(brokenFile);},
                        "ConfigReader should throw error for broken sim type");

        assertTrue(thrown.getMessage().equalsIgnoreCase("Simulation type given is invalid"));
    }


    @Test
    public void testBrokenShape(){

        var brokenFile = "brokenTests/brokenShape";

        Exception thrown =
                assertThrows(IllegalArgumentException.class, () -> {new ConfigReader(brokenFile);},
                        "ConfigReader should throw error for broken shape type");

        assertTrue(thrown.getMessage().contains("Cell Shape given is invalid"));
    }

    @Test
    public void testBrokenNeighbor(){

        var brokenFile = "brokenTests/brokenNeighbor";

        Exception thrown =
                assertThrows(IllegalArgumentException.class, () -> {new ConfigReader(brokenFile);},
                        "ConfigReader should throw error for broken neighbor type");

        assertTrue(thrown.getMessage().contains("Neighbor policy given is invalid"));
    }

    @Test
    public void testBrokenEdge(){

        var brokenFile = "brokenTests/brokenEdge";

        Exception thrown =
                assertThrows(IllegalArgumentException.class, () -> {new ConfigReader(brokenFile);},
                        "ConfigReader should throw error for broken edge type");

        assertTrue(thrown.getMessage().contains("Edge policy given is invalid"));
    }

    @Test
    public void testBrokenOutlined(){

        var brokenFile = "brokenTests/brokenOutlined";

        Exception thrown =
                assertThrows(IllegalArgumentException.class, () -> {new ConfigReader(brokenFile);},
                        "ConfigReader should throw error for broken outlined type");

        assertTrue(thrown.getMessage().contains("Outlined policy given is invalid"));
    }

    @Test
    public void testBrokenInitialConfig(){

        var brokenFile = "brokenTests/brokenInitialConfig";

        Exception thrown =
                assertThrows(IllegalArgumentException.class, () -> {new ConfigReader(brokenFile);},
                        "ConfigReader should throw error for broken initial config type");

        System.out.println(thrown.getMessage());
        assertTrue(thrown.getMessage().contains("Illegal way of generating grid"));
    }

    @Test
    public void testBrokenNumStates(){

        var brokenFile = "brokenTests/brokenNumStates";

        Exception thrown =
                assertThrows(IllegalArgumentException.class, () -> {new ConfigReader(brokenFile);},
                        "ConfigReader should throw error for invalid number of states");

        assertTrue(thrown.getMessage().contains("Num States is not formatted correctly"));
    }

    @Test
    public void testBrokenStateInt(){

        var brokenFile = "brokenTests/brokenStateInt";

        Exception thrown =
                assertThrows(IllegalArgumentException.class, () -> {new ConfigReader(brokenFile);},
                        "ConfigReader should throw error for invalid parsing of int for state");

        assertTrue(thrown.getMessage().contains("State value is not int correctly"));
    }

    @Test
    public void testBrokenColor(){

        var brokenFile = "brokenTests/brokenColor";

        Exception thrown =
                assertThrows(IllegalArgumentException.class, () -> {new ConfigReader(brokenFile);},
                        "ConfigReader should throw error for invalid color type");

        assertTrue(thrown.getMessage().contains("State color is not formatted correctly"));
    }

    @Test
    public void testBrokenInitialConfigProb(){

        var brokenFile = "brokenTests/brokenInitialConfigProb";

        Exception thrown =
                assertThrows(IllegalArgumentException.class, () -> {new ConfigReader(brokenFile);},
                        "ConfigReader should throw error for probabilities that don't sum to 1");

        assertTrue(thrown.getMessage().contains("Probabilities don't sum to 1"));
    }

    @Test
    public void testBrokenInitialConfigTotalSum(){

        var brokenFile = "brokenTests/brokenInitialConfigTotalSum";

        Exception thrown =
                assertThrows(IllegalArgumentException.class, () -> {new ConfigReader(brokenFile);},
                        "ConfigReader should throw error for total values that don't sum to number of cells");

        assertTrue(thrown.getMessage().contains("Probabilities don't sum to total number of Cells"));
    }

    @Test
    public void testBrokenRowCol(){

        var brokenFile = "brokenTests/brokenRowCol";

        Exception thrown =
                assertThrows(IllegalArgumentException.class, () -> {new ConfigReader(brokenFile);},
                        "ConfigReader should throw error for broken row/col int types");

        assertTrue(thrown.getMessage().contains("is not formatted correctly as an int"));
    }


    @Test
    public void testBrokenDetValues(){

        var brokenFile = "brokenTests/brokenDetValues";

        Exception thrown =
                assertThrows(IllegalArgumentException.class, () -> {new ConfigReader(brokenFile);},
                        "ConfigReader should throw error for total number of cell values that don't equal to the number of number of cell");

        assertTrue(thrown.getMessage().contains("Size of grid created is not equal to total number of cells"));
    }



}