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
    ArrayList<Collidable> c =  new ArrayList<>();
    ArrayList<Tickable> ti = new ArrayList<>();
    ArrayList<Drawable> d = new ArrayList<>();
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
    void deleteGizmoCollidable() throws GizmoNotFoundException {
        for(Drawable dr : draw){
            d.add((Drawable) dr.clone());
        }
        for (Collidable col : collide){
            c.add((Collidable) col.clone());
        }

        for (Tickable t : tick){
            ti.add((Tickable) t.clone());
        }

       m.deleteGizmo("Flipper");

       assertEquals(c.size() - 1, collide.size());
       assertThrows(GizmoNotFoundException.class, () -> m.getGizmoByName("Flipper"));
    }

    @Test
    void deleteGizmoDrawable(){
        assertEquals(d.size() - 1, draw.size());
    }

    @Test
    void deleteGizmoTickable(){
        assertEquals(ti.size() - 1, tick.size());
    }

    @Test
    void moveGizmo() throws GizmoNotFoundException {
        double oldX = m.getGizmoByName("Ball").getPosition()[0];
        double OldY = m.getGizmoByName("Ball").getPosition()[1];

        m.moveGizmo("Ball", 2, 5);
        double x = m.getGizmoByName("Ball").getPosition()[0];
        double y = m.getGizmoByName("Ball").getPosition()[1];

        assertEquals(2,x);
        assertEquals(5,y);
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