package model.gizmo;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import physics.Angle;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BumperTest {

    private Gizmo circle;
    private Gizmo square;
    private Gizmo triangle;
    private Tile anchorTileCircle;
    private Tile anchorTileSquare;
    private Tile anchorTileTriangle;


    private Model model;

    @BeforeEach
    void setUp() throws GizmoPlacementNotValidException {
        model = new Model();

        anchorTileCircle = model.getTileAt(5, 6);
        anchorTileSquare = model.getTileAt(6, 8);
        anchorTileTriangle = model.getTileAt(10, 8);

        circle = model.placeGizmo(GizmoType.CIRCLE_BUMPER, anchorTileCircle, null);
        square = model.placeGizmo(GizmoType.SQUARE_BUMPER, anchorTileSquare, null);
        triangle = model.placeGizmo(GizmoType.TRIANGLE_BUMPER, anchorTileTriangle, null);
    }

    @Test
    void findAnnexedTiles() {
    }

    @Test
    void getGameObject() {
        circle.setAnchorTile(anchorTileCircle);
        triangle.setAnchorTile(anchorTileTriangle);
        square.setAnchorTile(anchorTileSquare);

        GameObject gameObject = circle.getPrototypeGameObject();

        GameObject go = circle.getGameObject();
        GameObject go1 = gameObject.rotateAroundPointByAngle( new Vect(0.5,0.5), new Angle(Math.toRadians(Double.valueOf(circle.getProperty(GizmoPropertyType.ROTATION_DEG))))).translate(circle.getPosition());

        for(int i = 0; i < go.getCircles().length; i++)
            assertTrue(go.getCircles()[i].equals(go1.getCircles()[i]));

        gameObject = triangle.getPrototypeGameObject();

        go = triangle.getGameObject();
        go1 = gameObject.rotateAroundPointByAngle( new Vect(0.5,0.5), new Angle(Math.toRadians(Double.valueOf(triangle.getProperty(GizmoPropertyType.ROTATION_DEG))))).translate(triangle.getPosition());

        for(int i = 0; i < go.getLines().length; i++)
            assertTrue(go.getLines()[i].equals(go1.getLines()[i]));

        for(int i = 0; i < go.getCircles().length; i++)
            assertTrue(go.getCircles()[i].equals(go1.getCircles()[i]));

        gameObject = square.getPrototypeGameObject();

        go = square.getGameObject();
        go1 = gameObject.rotateAroundPointByAngle( new Vect(0.5,0.5), new Angle(Math.toRadians(Double.valueOf(square.getProperty(GizmoPropertyType.ROTATION_DEG))))).translate(square.getPosition());

        for(int i = 0; i < go.getLines().length; i++)
            assertTrue(go.getLines()[i].equals(go1.getLines()[i]));

        for(int i = 0; i < go.getCircles().length; i++)
            assertTrue(go.getCircles()[i].equals(go1.getCircles()[i]));
    }

    @Test
    void isAbsorber() {
        assertFalse(circle.isAbsorber());
        assertFalse(square.isAbsorber());
        assertFalse(triangle.isAbsorber());
    }

    @Test
    void getPrototypeGameObject() {
        GameObject gameObject = circle.getPrototypeGameObject();

        Circle[] circles = {new Circle(0.5, 0.5, 0.5)};

        for(int i = 0; i < gameObject.getCircles().length; i++)
            assertTrue(gameObject.getCircles()[i].equals(circles[i]));

        GameObject gameObject1 = square.getPrototypeGameObject();

        LineSegment[] lines1 = {
                new LineSegment(0, 0, 1, 0), //North
                new LineSegment(1, 0, 1, 1), //West
                new LineSegment(1, 1, 0, 1), //South
                new LineSegment(0, 1, 0, 0) //East
        };

        // These are the circles with radius 0 to help with collisions at the ends of LineSegments.
        Circle[] circles1 = {
                new Circle(0, 0, 0), //NE corner
                new Circle(1, 0, 0), //NW corner
                new Circle(0, 1, 0), //SE corner
                new Circle(1, 1, 0) //SW corner
        };

        for(int i = 0; i < gameObject1.getLines().length; i++)
            assertTrue(gameObject1.getLines()[i].equals(lines1[i]));

        for(int i = 0; i < gameObject1.getCircles().length; i++)
            assertTrue(gameObject1.getCircles()[i].equals(circles1[i]));

        GameObject gameObject2 = triangle.getPrototypeGameObject();

        LineSegment[] lines2 = {
                new LineSegment(0, 0, 0, 1), //East
                new LineSegment(0, 0, 1, 0), //North
                new LineSegment(0, 1, 1, 0) //Diagonal
        };

        for(int i = 0; i < gameObject2.getLines().length; i++)
            assertTrue(gameObject2.getLines()[i].equals(lines2[i]));
    }

    @Test
    void getGizmoDrawingData() {
        DrawingData data = circle.getGizmoDrawingData();
        ArrayList<Double[]> circle = new ArrayList<>();

        circle.add(new Double[]{0.5, 0.5, 0.5});

        for(int i = 0; i < data.getCirclesData().size(); i++) {
            assertEquals(data.getCirclesData().get(i)[0], (circle.get(i)[0]));
            assertEquals(data.getCirclesData().get(i)[1], (circle.get(i)[1]));
            assertEquals(data.getCirclesData().get(i)[2], (circle.get(i)[2]));
        }

        data = square.getGizmoDrawingData();
        ArrayList<Double[]> squarePoly = new ArrayList<>();

        squarePoly.add(new Double[]{0.0,0.0}); //NE
        squarePoly.add(new Double[]{1.0,0.0}); //NW
        squarePoly.add(new Double[]{1.0,1.0}); //SW
        squarePoly.add(new Double[]{0.0,1.0}); //SE0.0 , 2.0}); //SE

        for(int i = 0; i < data.getPolygonsData().get(0).size(); i++) {

            assertEquals(data.getPolygonsData().get(0).get(i)[0],(squarePoly.get(i)[0]));
            assertEquals(data.getPolygonsData().get(0).get(i)[1],(squarePoly.get(i)[1]));
        }

        data = triangle.getGizmoDrawingData();
        ArrayList<Double[]> trianglePoly = new ArrayList<>();

        trianglePoly.add(new Double[]{0.0,0.0}); //NE
        trianglePoly.add(new Double[]{1.0,0.0}); //SW
        trianglePoly.add(new Double[]{0.0,1.0}); //SE

        for(int i = 0; i < data.getPolygonsData().get(0).size(); i++) {

            assertEquals(data.getPolygonsData().get(0).get(i)[0],(trianglePoly.get(i)[0]));
            assertEquals(data.getPolygonsData().get(0).get(i)[1],(trianglePoly.get(i)[1]));
        }
    }
}