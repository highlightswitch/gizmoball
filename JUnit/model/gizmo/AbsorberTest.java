package model.gizmo;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import physics.Circle;
import physics.LineSegment;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AbsorberTest {

    private Absorber absorber;
    private Ball ball;
    private Model model;


    private Color colour;
    private Tile anchorTile;
    private Tile[] annexedTiles;

    protected GizmoType type;

    private Map<GizmoPropertyType, String> properties;

    private GameObject gameObject;
    DrawingData data;
    ArrayList<Double[]> squarePoly;

    @BeforeEach
    void setUp() {
        properties = new HashMap<GizmoPropertyType, String>();
        properties.put(GizmoPropertyType.NAME, "Absorber");
        properties.put(GizmoPropertyType.WIDTH, "20");
        properties.put(GizmoPropertyType.HEIGHT, "1");

        absorber = new Absorber(Color.white, properties);
        model = new Model();
        ball = model.getBall();

        LineSegment[] lines = {
                new LineSegment(0, 0, 0, 1),
                new LineSegment(0, 1, 20, 20),
                new LineSegment(20, 1, 20, 0),
                new LineSegment(0, 0, 20, 0)
        };

        Circle[] circles = {
                new Circle(0,0, 0),
                new Circle(0, 1, 0),
                new Circle(20, 0, 0),
                new Circle(20, 1, 0)
        };
        gameObject = new StaticGameObject(lines, circles, 1);

        data = new DrawingData();
        squarePoly = new ArrayList<>();
    }

    @Test
    void setAbsorbedBall() {
    }

    @Test
    void findAnnexedTiles() {
    }

    @Test
    void getPrototypeGameObject() {
        assertEquals(absorber.getPrototypeGameObject(), gameObject);
    }

    @Test
    void getGameObject() {
        assertEquals(absorber.getGameObject(), gameObject.translate(absorber.getPosition()));
    }

    @Test
    void isAbsorber() {
        assertTrue(absorber.isAbsorber());
    }

    @Test
    void getGizmoDrawingData() {
        squarePoly.add(new Double[]{0.0, 0.0}); //NE
        squarePoly.add(new Double[]{20.0, 0.0}); //NW
        squarePoly.add(new Double[]{20.0, 1.0}); //SW
        squarePoly.add(new Double[]{0.0 , 1.0}); //SE
        data.addPolygon(squarePoly);

        assertEquals(absorber.getGizmoDrawingData(), data);
    }

    @Test
    void genericTrigger() {
    }

    @Test
    void keyDown() {
    }

    @Test
    void keyUp() {
    }

}