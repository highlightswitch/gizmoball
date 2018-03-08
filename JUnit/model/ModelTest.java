package model;

import model.gizmo.Gizmo;
import model.gizmo.GizmoType;
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
    ArrayList<Tickable> t = new ArrayList<>();
    ArrayList<Drawable> d = new ArrayList<>();
    Tile[][] tile;
    Gizmo flipper;
    Gizmo circle;
    Gizmo ball;
    String[] prop = {"Flipper", "90", "true"};
    String[] prop2 = null;
    String[] prop3 = {"Ball", "0", "3"};

    @BeforeEach
    void setUp() throws GizmoPlacementNotValidException {
        m =  new Model();
        flipper = m.placeGizmo(GizmoType.FLIPPER, new Tile(m, 5, 4), prop);
        circle = m.placeGizmo(GizmoType.CIRCLE_BUMPER, new Tile(m, 5, 9), prop2);
        ball = m.placeGizmo(GizmoType.BALL, new Tile(m, 0, 1), prop3);
        collide = m.getCollidable();
        draw = m.getDrawables();
        tile = m.getTiles();
    }

    @Test
    void getGizmoByName() throws GizmoNotFoundException {
        //when retreving by name what do you if there is multiple gizmos with the same name
        // is the gizmo equals method overridden
       assertThrows(GizmoNotFoundException.class, () -> m.getGizmoByName("Blue"));
    }

    @Test
    void getGizmoByName2 () throws GizmoNotFoundException {
        assertEquals(flipper, m.getGizmoByName("Flipper"));
    }

    @Test
    void deleteGizmoCollidable() throws GizmoNotFoundException {
        for(Drawable dr : draw){
            d.add((Drawable) dr.clone());
        }
        for (Collidable col : collide){
            c.add((Collidable) col.clone());
        }

        for (Tickable ti : tick){
            t.add((Tickable) ti.clone());
        }

       m.deleteGizmo("Flipper");

       assertEquals((c.size()-1), collide.size());
       assertThrows(GizmoNotFoundException.class, () -> m.getGizmoByName("Flipper"));
    }

    @Test
    void deleteGizmoDrawable(){
        assertEquals((d.size()-1), draw.size());
    }

    @Test
    void deleteGizmoTickable(){
        assertEquals((t.size()-1), tick.size());
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

    @Test
    void getDrawables() {
    }

    @Test
    void getCollidable() {
    }

    @Test
    void getFrictionConstant() {
    }

    @Test
    void getGravityConstant() {
    }

    @Test
    void setFrictionConstant() {
    }

    @Test
    void setGravityConstant() {
    }

    @Test
    void getBall() {
    }

    @Test
    void getTiles() {
    }

    @Test
    void checkName() {
    }
}