package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WallsTest {

    Walls w;
    Model m;

    @BeforeEach
    void setUp() {
        m = new Model();
        w  = new Walls(1,1,10,2);
    }

    @Test
    void getGameObject() {
    }

    @Test
    void isAbsorber() {
    }

}