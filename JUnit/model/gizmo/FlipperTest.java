package model.gizmo;

import model.*;
import model.util.GizmoUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import physics.Angle;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FlipperTest {

    Flipper leftFlipper;
    Flipper rightFlipper;
    Tile anchorTile1;
    Tile anchorTile2;
    Model model;

    String[] prop = {"Flipper", "90", "true", "[r=255,g=255,b=255]", "[r=255,g=255,b=255]", "[r=16,g=219,b=139]"};
    String[] rFProp = {"rf_0", "90", "false", "[r=255,g=255,b=255]", "[r=255,g=255,b=255]", "[r=16,g=219,b=139]"};


    @BeforeEach
    void setUp() throws GizmoPlacementNotValidException, TileCoordinatesNotValid {
        model = new Model();
        anchorTile1 = model.getTileAt(9, 10);
        leftFlipper = (Flipper) model.placeGizmo(GizmoType.FLIPPER, anchorTile1, prop);

        anchorTile2 = model.getTileAt(15, 3);
        rightFlipper = (Flipper) model.placeGizmo(GizmoType.FLIPPER, anchorTile2, rFProp);
        
    }

    @Test
    void getType() {
        GizmoType type = GizmoType.FLIPPER;
        //assertTrue(type.equals(leftFlipper.getType()));
        //assertEquals(type, leftFlipper.getType());
    }

    @Test
    void findAnnexedTiles() throws TileCoordinatesNotValid {
        ArrayList<Tile> tiles = new ArrayList<>();

        tiles.add(anchorTile1.getNeighbour(0, 1));
        tiles.add(anchorTile1.getNeighbour(1, 0));
        tiles.add(anchorTile1.getNeighbour(1, 1));

        Tile[] arr = new Tile[tiles.size()];
        arr = tiles.toArray(arr);

        Tile[] arr1 = leftFlipper.findAnnexedTiles(anchorTile1);

        for (int i = 0; i < arr1.length; i++)
            assertTrue(arr[i].equals(arr1[i]));
    }

    @Test
    void getPrototypeGameObjectLeft() {
        double length = 2;
        double width = 0.5;
        double flipPos = 0;

        GameObject gameObject = leftFlipper.getPrototypeGameObject();

        LineSegment[] lines = new LineSegment[] {
                new LineSegment(0, width / 2, 0, length - width / 2),
                new LineSegment(width, width / 2, width, length - width / 2)
        };

        Circle[] circles = new Circle[] {
                new Circle(width / 2, width / 2, width / 2),
                new Circle(width / 2, length - width / 2, width / 2),
                new Circle(0, width/2, 0),
                new Circle(0, length - width/2, 0),
                new Circle(width, width/2, 0),
                new Circle(width, length - width/2, 0)
        };

        double angularVelocity = -1 * Math.toRadians(180);

        GameObject go = new RotatingGameObject(
                lines,
                circles,
                0.95,
                new Vect(width/2, width/2),
                angularVelocity
        );

        go.rotateAroundPointByAngle(
                new Vect(width/2, width/2),
                new Angle(Math.toRadians(-90 * flipPos))
        );

        for(int i = 0; i < go.getLines().length; i++)
            assertTrue(gameObject.getLines()[i].equals(go.getLines()[i]));

        for(int i = 0; i < go.getCircles().length; i++)
            assertTrue(gameObject.getCircles()[i].equals(go.getCircles()[i]));
    }

    @Test
    void getPrototypeGameObjectRight() {
        double length = 2;
        double width = 0.5;
        double flipPos = 0;

        GameObject gameObject = rightFlipper.getPrototypeGameObject();

        LineSegment[] lines = new LineSegment[] {
                new LineSegment(2 - width, width / 2, 2 - width, length - width / 2),
                new LineSegment(2, width / 2, 2, length - width / 2)
        };

        Circle[] circles = new Circle[] {
                new Circle(2 - width / 2, width / 2, width / 2),
                new Circle( 2 - width / 2, length - width / 2, width / 2),
                new Circle(2 - width, width/2, 0),
                new Circle(2 - width, length - width/2, 0),
                new Circle(2, width/2, 0),
                new Circle(2, length - width/2, 0)
        };

        double angularVelocity = -1 * Math.toRadians(180);

        GameObject go = new RotatingGameObject(
                lines,
                circles,
                0.95,
                new Vect(width/2, width/2),
                angularVelocity
        );

        go.rotateAroundPointByAngle(
                new Vect(2 - width/2, width/2),
                new Angle( Math.toRadians(90 * flipPos))
        );

        for(int i = 0; i < go.getLines().length; i++) {
            assertTrue(gameObject.getLines()[i].equals(go.getLines()[i]));
        }

        for(int i = 0; i < go.getCircles().length; i++)
            assertTrue(gameObject.getCircles()[i].equals(go.getCircles()[i]));
    }

    @Test
    void getGameObject() throws TileCoordinatesNotValid {
        leftFlipper.setAnchorTile(anchorTile1);
        GameObject gameObject = leftFlipper.getPrototypeGameObject();

        GameObject go = leftFlipper.getGameObject();
        GameObject go1 = gameObject.rotateAroundPointByAngle( new Vect(1,1),  new Angle(leftFlipper.getCurrentRotationInRadians())).translate(leftFlipper.getPosition());

        for(int i = 0; i < go.getLines().length; i++)
            assertTrue(go.getLines()[i].equals(go1.getLines()[i]));

        for(int i = 0; i < go.getCircles().length; i++)
            assertTrue(go.getCircles()[i].equals(go1.getCircles()[i]));

        rightFlipper.setAnchorTile(anchorTile2);
        gameObject = rightFlipper.getPrototypeGameObject();

        go = rightFlipper.getGameObject();
        go1 = gameObject.rotateAroundPointByAngle( new Vect(1,1),  new Angle(rightFlipper.getCurrentRotationInRadians())).translate(rightFlipper.getPosition());

        for(int i = 0; i < go.getLines().length; i++)
            assertTrue(go.getLines()[i].equals(go1.getLines()[i]));

        for(int i = 0; i < go.getCircles().length; i++)
            assertTrue(go.getCircles()[i].equals(go1.getCircles()[i]));
    }

    @Test
    void isAbsorber() {
        assertFalse(leftFlipper.isAbsorber());
    }

    @Test
    void getGizmoDrawingDataLeft() {
        double length = 2;
        double width = 0.5;
        double flipPos = 0;

        DrawingData data1 = new DrawingData();
        ArrayList<Double[]> poly = new ArrayList<>();
        poly.add(new Double[]{0.0, width / 2}); // NW
        poly.add(new Double[]{0.0, length - width / 2}); // SW
        poly.add(new Double[]{width, length - width / 2}); // SE
        poly.add(new Double[]{width, width / 2}); // NE
        data1.addPolygon(poly);

        data1.addCircle(new Double[]{width / 2, width / 2, width / 2});
        data1.addCircle(new Double[]{width / 2, length - width / 2, width / 2});

        data1.rotateAroundPivotByRadians(new double[]{width/2, width/2}, Math.toRadians(-90 * flipPos));
        data1.rotateAroundPivotByRadians(new int[]{1,1}, leftFlipper.getCurrentRotationInRadians());

        DrawingData data = leftFlipper.getGizmoDrawingData();

        for(int i = 0; i < data.getPolygonsData().get(0).size(); i++) {

            assertEquals(data.getPolygonsData().get(0).get(i)[0],(data1.getPolygonsData().get(0).get(i)[0]));
            assertEquals(data.getPolygonsData().get(0).get(i)[1],(data1.getPolygonsData().get(0).get(i)[1]));
        }

        for(int i = 0; i < data.getCirclesData().size(); i++) {
            assertEquals(data.getCirclesData().get(i)[0], (data1.getCirclesData().get(i)[0]));
            assertEquals(data.getCirclesData().get(i)[1], (data1.getCirclesData().get(i)[1]));
        }
    }

    @Test
    void getGizmoDrawingDataRight() {
        double length = 2;
        double width = 0.5;
        double flipPos = 0;

        DrawingData data1 = new DrawingData();
        ArrayList<Double[]> poly = new ArrayList<>();
        poly.add(new Double[]{2.0, width / 2}); // NW
        poly.add(new Double[]{2.0, length - width / 2}); // SW
        poly.add(new Double[]{2 - width, length - width / 2}); // SE
        poly.add(new Double[]{2 - width, width / 2}); // NE
        data1.addPolygon(poly);

        data1.addCircle(new Double[]{2 - width / 2, width / 2, width / 2});
        data1.addCircle(new Double[]{2 - width / 2, length - width / 2, width / 2});

        data1.rotateAroundPivotByRadians(new double[]{2 - width/2, width/2}, Math.toRadians(90 * flipPos));
        data1.rotateAroundPivotByRadians(new int[]{1,1}, rightFlipper.getCurrentRotationInRadians());

        DrawingData data = rightFlipper.getGizmoDrawingData();

        for(int i = 0; i < data.getPolygonsData().get(0).size(); i++) {

            assertEquals(data.getPolygonsData().get(0).get(i)[0],(data1.getPolygonsData().get(0).get(i)[0]));
            assertEquals(data.getPolygonsData().get(0).get(i)[1],(data1.getPolygonsData().get(0).get(i)[1]));
        }

        for(int i = 0; i < data.getCirclesData().size(); i++) {
            assertEquals(data.getCirclesData().get(i)[0], (data1.getCirclesData().get(i)[0]));
            assertEquals(data.getCirclesData().get(i)[1], (data1.getCirclesData().get(i)[1]));
        }
    }
}