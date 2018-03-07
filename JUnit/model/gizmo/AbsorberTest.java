package model.gizmo;

import model.*;
import org.junit.Before;
import org.junit.Test;
import physics.Circle;
import physics.LineSegment;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class AbsorberTest {

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

    @Before
    public void setUp() throws Exception {

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
    public void findAnnexedTiles() throws Exception {
    }

    @Test
    public void getPrototypeGameObject() throws Exception {
        assertEquals(absorber.getPrototypeGameObject(), gameObject);
    }

    @Test
    public void getGameObject() throws Exception {
        assertEquals(absorber.getGameObject(), gameObject.translate(absorber.getPosition()));
    }

    @Test
    public void isAbsorber() throws Exception {
        assertTrue(absorber.isAbsorber());
    }

    @Test
    public void getGizmoDrawingData() throws Exception {
        squarePoly.add(new Double[]{0.0, 0.0}); //NE
        squarePoly.add(new Double[]{20.0, 0.0}); //NW
        squarePoly.add(new Double[]{20.0, 1.0}); //SW
        squarePoly.add(new Double[]{0.0 , 1.0}); //SE
        data.addPolygon(squarePoly);

        assertEquals(absorber.getGizmoDrawingData(), data);
    }

    @Test
    public void keyDown() throws Exception {
    }
}