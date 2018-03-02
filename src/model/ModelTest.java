package model;

import model.gizmo.Ball;
import model.gizmo.Flipper;
import model.gizmo.Tickable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {
    Model m;
    ArrayList<Collidable> collide;
    ArrayList<Drawable> draw;
    ArrayList<Tickable> tick;
    Ball b;
    Tile[][] t;
    Flipper f;

    @BeforeEach
    void setUp() {
        m =  new Model();
        collide = m.getCollidable();
        draw = m.getDrawables();
        b = m.getBall();
        t = m.getTiles();
    }

    @Test
    void getGizmoByName() throws GizmoNotFoundException {
        //when retreving by name what do you if there is multiple gizmos with the same name
        // is the gizmo equals method overridden
       assertThrows(GizmoNotFoundException.class, () -> m.getGizmoByName("Blue"));
    }

    @Test
    void deleteGizmo() throws GizmoNotFoundException {
        ArrayList<Collidable> c =  new ArrayList<>();
        ArrayList<Drawable> d = new ArrayList<>();
        ArrayList<Tickable> t = new ArrayList<>();

        for (Collidable col : collide){
            c.add(col.clone());
        }

        for(Drawable dr : draw){
            d.add(dr.clone());
        }

        for (Tickable ti : tick){
            t.add(ti.clone());
        }

       m.deleteGizmo("Flipper");

       assertEquals(c.size() - 1, collide.size());
       assertEquals(d.size() - 1, draw.size());
       assertEquals(t.size() - 1, tick.size());
       assertThrows(GizmoNotFoundException.class, () -> m.getGizmoByName("Flipper"));
    }

    @Test
    void moveGizmo() {
    }

    @Test
    void checkName() {
    }

    @Test
    void setUpActionMap() {
    }

    @Test
    void setUpActionMap1() {
    }

    @Test
    void getTileAt() {
    }

    @Test
    void getTileAt1() {
    }

    @Test
    void placeGizmo() {
    }

    @Test
    void keyEventTriggered() {
    }

    @Test
    void tick() {
    }

}