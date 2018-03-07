package model;

import model.gizmo.Gizmo;
import model.gizmo.GizmoType;
import model.gizmo.Tickable;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.Assert.*;

public class ModelTest {

    Model m;
    ArrayList<Collidable> collide;
    ArrayList<Drawable> draw;
    ArrayList<Tickable> tick;
    ArrayList<Collidable> c =  new ArrayList<>();
    ArrayList<Tickable> ti = new ArrayList<>();
    ArrayList<Drawable> d = new ArrayList<>();
    Tile[][] t;
    Gizmo flipper;
    Gizmo circle;
    Gizmo ball;
    String[] prop = {};

    @Before
    public void setUp() throws GizmoPlacementNotValidException {
        m =  new Model();
        flipper = m.placeGizmo(GizmoType.FLIPPER, new Tile(m, 5, 4), prop);
        circle = m.placeGizmo(GizmoType.CIRCLE_BUMPER, new Tile(m, 5, 9), prop);
        ball = m.placeGizmo(GizmoType.BALL, new Tile(m, 0, 1), prop);
        collide = m.getCollidable();
        draw = m.getDrawables();
        t = m.getTiles();
    }

    @Test
    public void getDrawables() throws Exception {
    }

    @Test
    public void getCollidable() throws Exception {
    }

    @Test
    public void getFrictionConstant() throws Exception {
    }

    @Test
    public void getGravityConstant() throws Exception {
    }

    @Test
    public void setFrictionConstant() throws Exception {
    }

    @Test
    public void setGravityConstant() throws Exception {
    }

    @Test
    public void getBall() throws Exception {
    }

    @Test
    public void getTiles() throws Exception {
    }

    @Test
    public void getGizmoByName() throws GizmoNotFoundException {
        //when retreving by name what do you if there is multiple gizmos with the same name
        // is the gizmo equals method overridden
        assertThrows(GizmoNotFoundException.class, () -> m.getGizmoByName("Blue"));
    }

    @Test
    public void getGizmoByName2() throws GizmoNotFoundException {
        assertEquals(flipper, m.getGizmoByName("Flipper"));
    }

    @Test
    public void deleteGizmoCollidable() throws GizmoNotFoundException {
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
    public void deleteGizmoDrawable(){
        assertEquals(d.size() - 1, draw.size());
    }

    @Test
    public void deleteGizmoTickable(){
        assertEquals(ti.size() - 1, tick.size());
    }

    @Test
    public void moveGizmo() throws GizmoNotFoundException {
        double oldX = m.getGizmoByName("Ball").getPosition()[0];
        double OldY = m.getGizmoByName("Ball").getPosition()[1];

        m.moveGizmo("Ball", 2, 5);
        double x = m.getGizmoByName("Ball").getPosition()[0];
        double y = m.getGizmoByName("Ball").getPosition()[1];



        assertEquals(2, x, 0);
        assertEquals(5, y, 0);
    }

    @Test
    public void checkName() throws Exception {
    }

    @Test
    public void setUpActionMap() throws Exception {
    }

    @Test
    public void setUpActionMap1() throws Exception {
    }

    @Test
    public void getTileAt() throws Exception {
    }

    @Test
    public void getTileAt1() throws Exception {
    }

    @Test
    public void placeGizmo() throws Exception {
    }

    @Test
    public void keyEventTriggered() throws Exception {
    }

    @Test
    public void tick() throws Exception {
    }

}