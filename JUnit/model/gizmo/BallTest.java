package model.gizmo;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import physics.Circle;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BallTest {

    private Ball ball;
    private Tile anchorTile;
    private Model model;
    private Absorber absorber;
    private Map<GizmoPropertyType, String> properties;

    @BeforeEach
    void setUp() throws GizmoPlacementNotValidException, TileCoordinatesNotValid {

        model = new Model();
        anchorTile = model.getTileAt(15, 1);
        ball = (Ball) model.placeGizmo(GizmoType.BALL, anchorTile, new String[]{ "Ball", "0", "0.5", "[r=255,g=255,b=255]", "[r=255,g=255,b=255]", "[r=16,g=219,b=139]" });
        absorber = (Absorber) model.placeGizmo(GizmoType.ABSORBER, model.getTileAt(3, 15), new String[]{ "1", "6", "2", "[r=255,g=255,b=255]", "[r=255,g=255,b=255]", "[r=16,g=219,b=139]" });
    }

    @Test
    void findAnnexedTiles() throws Exception {
    }

    @Test
    public void getPosition() throws Exception {
        double[] position = {15, 1};
        assertTrue(ball.getPosition()[0] == position[0]);
        assertTrue(ball.getPosition()[1] == position[1]);
    }

    @Test
    public void moveTo() throws  Exception {
        ball.moveTo(10, 11);
        double[] position = {10, 11};
        assertTrue(ball.getPosition()[0] == position[0]);
        assertTrue(ball.getPosition()[1] == position[1]);
    }

    @Test
    public void isTilePlacable() throws Exception {
        assertFalse(ball.isTilePlacable());
    }

    @Test
    public void fire_moveBall() throws Exception {
    }

    @Test
    public void moveBall() throws Exception {

    }

    @Test
    public void getRadius() throws  Exception {
        assertEquals(ball.getRadius(), 0.25);
    }

    @Test
    public void getCentre() throws  Exception{
        double[] v = new double[]{15.5, 1.5};
        double[] v1 = ball.getCentre();
        assertEquals(v[0], v1[0]);
        assertEquals(v[1], v1[1]);

    }

    @Test
    public void getPrototypeGameObject() throws Exception {
        GameObject gameObject = ball.getPrototypeGameObject();

        Circle[] circles = { new Circle(0.5,0.5, 0.25) };

        for(int i = 0; i < gameObject.getCircles().length; i++)
            assertTrue(gameObject.getCircles()[i].equals(circles[i]));
    }

    @Test
    public void getGameObject() throws Exception {
        ball.setAnchorTile(anchorTile);
        GameObject gameObject = absorber.getPrototypeGameObject();

        GameObject go = absorber.getGameObject();
        GameObject go1 = gameObject.translate(absorber.getPosition());

        for(int i = 0; i < go.getCircles().length; i++)
            assertTrue(go.getCircles()[i].equals(go1.getCircles()[i]));
    }

    @Test
    public void isAbsorber() throws Exception {
        assertFalse(ball.isAbsorber());
    }

    @Test
    public void getGizmoDrawingData() throws Exception {
        DrawingData data = ball.getGizmoDrawingData();
        ArrayList<Double[]> circle = new ArrayList<>();

        circle.add(new Double[]{0.5, 0.5, 0.25});

        for(int i = 0; i < data.getCirclesData().size(); i++) {
            assertEquals(data.getCirclesData().get(i)[0], (circle.get(i)[0]));
            assertEquals(data.getCirclesData().get(i)[1], (circle.get(i)[1]));
            assertEquals(data.getCirclesData().get(i)[2], (circle.get(i)[2]));
        }
    }
}