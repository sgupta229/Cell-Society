package model;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StateTest {
    State s1;
    State s2;
    State s3;

    @BeforeEach
    void setup(){
        s1 = new State("EMPTY", 0, Color.BLACK, "NONE", 0.0, 0);
        s2 = new State("EMPTY", 0, Color.BLACK, "NONE",0.0, 0);
        s3 = new State("SHARK", 1, Color.WHITE, "shark.jpg",0.0, 0);
    }

    @Test
    void testEquals(){
        assertTrue(s1.equals(s2));
    }

    @Test
    void testNotEquals(){
        assertFalse(s1.equals(s3));
    }

}