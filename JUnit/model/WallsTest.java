package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WallsTest {

    Walls w;
    Model m;

    @Before
    public void setUp() throws Exception {
        m = new Model();
        w  = new Walls(1,1,10,2);
    }

    @Test
    public void getGameObject() throws Exception {
    }

    @Test
    public void isAbsorber() throws Exception {
    }

}