package model;

import model.gizmo.Absorber;
import model.gizmo.GizmoPropertyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import physics.Vect;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CollisionDetailsTest {
    CollisionDetails cd;
    Vect v;
    Absorber a;

    @BeforeEach
    void setUp() {
        v = new Vect(4,4);
        a  = new Absorber(Color.black, new HashMap<GizmoPropertyType, String>());
        cd = new CollisionDetails(0.5, v);
        cd.setAbsorber(a);
    }

    @Test
    void getTuc() {
        assertEquals(0.5, cd.getTuc());
    }

    @Test
    void getVelocity() {
        assertEquals(v, cd.getVelocity());
    }

    @Test
    void getAbsorber() {
        assertEquals(a, cd.getAbsorber());
    }

}