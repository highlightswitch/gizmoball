package model.gizmo;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import physics.Vect;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GizmoTest {

    private Gizmo gizmo;
    private Gizmo gizmo1;
    private Model model;
    private Tile anchorTile;
    private Tile anchorTile1;

    @BeforeEach
    void setUp() throws TileCoordinatesNotValid, GizmoPlacementNotValidException {
        model = new Model();
        anchorTile = model.getTileAt(3, 15);
        anchorTile1 = model.getTileAt(5, 6);
        gizmo =  model.placeGizmo(GizmoType.ABSORBER, anchorTile, new String[]{"1", "6", "1", "[r=255,g=255,b=255]", "[r=255,g=255,b=255]", "[r=16,g=219,b=139]"});
        gizmo1 = model.placeGizmo(GizmoType.CIRCLE_BUMPER, anchorTile1, null);
    }

//    @Test
//    void getColour() {
//        assertEquals(gizmo.getColour(), "[r=255,g=255,b=255]");
//    }

    @Test
    void getType() {
        assertEquals(gizmo.getType(), GizmoType.ABSORBER);
    }

    @Test
    void getProperty() {
        assertEquals(gizmo.getProperty(GizmoPropertyType.NAME), "1");
        assertEquals(gizmo.getProperty(GizmoPropertyType.WIDTH), "6");
        assertEquals(gizmo.getProperty(GizmoPropertyType.HEIGHT), "1");
        assertEquals(gizmo.getProperty(GizmoPropertyType.CURRENT_COLOUR), "[r=255,g=255,b=255]");
        assertEquals(gizmo.getProperty(GizmoPropertyType.DEFAULT_COLOUR), "[r=255,g=255,b=255]");
        assertEquals(gizmo.getProperty(GizmoPropertyType.ALT_COLOUR), "[r=16,g=219,b=139]");
    }

    @Test
    void removeTileGetPosition() {
        gizmo.removeTile();
        assertEquals(gizmo.getPosition(), null);
    }

    @Test
    void getPosition() {
        assertEquals(gizmo.getPosition()[0], anchorTile.getPosition()[0]);
        assertEquals(gizmo.getPosition()[1], anchorTile.getPosition()[1]);
    }

    @Test
    void isTilePlacable() {
    }

    @Test
    void getCurrentRotationInRadians() throws TileCoordinatesNotValid, GizmoPlacementNotValidException {
        assertEquals(gizmo1.getCurrentRotationInRadians(), Math.toRadians(Double.valueOf(gizmo1.getProperty(GizmoPropertyType.ROTATION_DEG))));
    }

    @Test
    void rotateBy_Deg() throws GizmoPropertyException {
        double rotation = Double.valueOf(gizmo1.getProperty(GizmoPropertyType.ROTATION_DEG));
        rotation = (rotation + 90) % 360;
        gizmo1.setProperty(GizmoPropertyType.ROTATION_DEG, String.valueOf(90));

        gizmo1.rotateBy_Deg(90);

        assertEquals(gizmo1.getProperty(GizmoPropertyType.ROTATION_DEG), "180.0");
    }

    @Test
    void rotateTo_Deg() throws GizmoPropertyException {
        gizmo1.rotateTo_Deg(180);
        assertEquals(gizmo1.getProperty(GizmoPropertyType.ROTATION_DEG), "180.0");

    }

    @Test
    void getDrawingData() {

        DrawingData data = gizmo1.getGizmoDrawingData().translate(gizmo1.getPosition());
        DrawingData data1 = gizmo1.getDrawingData();

        for(int i = 0; i < data.getCirclesData().size(); i++) {
            assertEquals(data.getCirclesData().get(i)[0], (data1.getCirclesData().get(i)[0]));
            assertEquals(data.getCirclesData().get(i)[1], (data1.getCirclesData().get(i)[1]));
            assertEquals(data.getCirclesData().get(i)[2], (data1.getCirclesData().get(i)[2]));
        }
    }

    @Test
    void isAbsorber() {
        assertTrue(gizmo.isAbsorber());
        assertFalse(gizmo1.isAbsorber());
    }

    @Test
    void timeUntilCollisionWithBall() throws TileCoordinatesNotValid, GizmoPlacementNotValidException {
        Gizmo ball = model.placeGizmo(GizmoType.BALL, model.getTileAt(15, 1),  new String[]{ "Ball", "0", "0.5", "[r=255,g=255,b=255]", "[r=255,g=255,b=255]", "[r=16,g=219,b=139]" });
        GameObject ballGO = ball.getGameObject();
        Vect vel = new Vect(0, 0.5);

        CollisionDetails cd = gizmo1.getGameObject().timeUntilCollisionWithBall(ballGO, vel);
        cd.setCollidingWith(gizmo1);

        assertEquals(cd.getTuc(), gizmo1.timeUntilCollisionWithBall(ballGO, vel).getTuc());
        assertEquals(cd.getVelocity(), gizmo1.timeUntilCollisionWithBall(ballGO, vel).getVelocity());

    }

    @Test
    void getPropertyDefaults() {
        List<String> usedNames = new ArrayList<String>();

        usedNames.add("circleBumper_0");
        usedNames.add("absorber_0");

        assertEquals(gizmo.getPropertyDefaults(GizmoType.ABSORBER, usedNames)[0], "absorber_1");
        assertEquals(gizmo1.getPropertyDefaults(GizmoType.CIRCLE_BUMPER, usedNames)[0], "circleBumper_1");

        usedNames.add("circleBumper_1");
        usedNames.add("absorber_1");

        assertEquals(gizmo.getPropertyDefaults(GizmoType.ABSORBER, usedNames)[0], "absorber_2");
        assertEquals(gizmo1.getPropertyDefaults(GizmoType.CIRCLE_BUMPER, usedNames)[0], "circleBumper_2");
    }

}