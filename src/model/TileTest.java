package model;

import model.gizmo.Bumper;
import model.gizmo.Flipper;
import model.gizmo.Gizmo;
import model.gizmo.GizmoType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {
    Tile t;
    Model m;
    Gizmo g;

    @BeforeEach
    void setUp() {
        m = new Model();
        t = new Tile(m,7,8);
    }

    @Test
    void getX() {
        assertEquals(7,t.getX());
    }

    @Test
    void getY() {
        assertEquals(8,t.getY());
    }

    @Test
    void getPosition() {
        int[] pos = {7,8};
        assertEquals(pos[0], t.getPosition()[0]);
        assertEquals(pos[1], t.getPosition()[1]);
    }

    @Test
    void getNeighbour() {
        assertEquals(10, t.getNeighbour(3,0).getX());
    }

    @Test
    void isOccupied() {
        assertEquals(false, t.isOccupied());
    }

    @Test
    void setOccupied() {
        g  = new Bumper(Color.WHITE, GizmoType.TRIANGLE_BUMPER, new HashMap<>());
        t.placeGizmo(g);
        assertEquals(true, t.isOccupied());
    }

    @Test
    void removeGizmo() {
        g  = new Bumper(Color.WHITE, GizmoType.TRIANGLE_BUMPER, new HashMap<>());
        t.placeGizmo(g);
        t.removeGizmo();
        assertEquals(false,t.isOccupied());
    }

    @Test
    void placeGizmo() {
        g = new Bumper(Color.ORANGE, GizmoType.CIRCLE_BUMPER, new HashMap<>());
        t.placeGizmo(g);
        assertEquals(GizmoType.CIRCLE_BUMPER, t.getGizmo().getType());
    }

    @Test
    void placeGizmoOccupied() {
        g = new Bumper(Color.ORANGE, GizmoType.CIRCLE_BUMPER, new HashMap<>());
        t.placeGizmo(g);
        assertThrows(Exception.class, () -> t.placeGizmo(g));
    }

    @Test
    void getGizmo() {
        assertEquals(g, t.getGizmo());
    }
}