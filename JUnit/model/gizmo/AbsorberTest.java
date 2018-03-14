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

    private Absorber absorber1;
    private Absorber absorber2;
    private Tile anchorTile1;
    private Tile anchorTile2;
    private Ball ball;
    private Model model;
   // private GameObject gameObject;


    @BeforeEach
    void setUp() throws GizmoPlacementNotValidException {
        model = new Model();
        anchorTile1 = model.getTileAt(9, 10);
        anchorTile2 = model.getTileAt(2, 17);
        absorber1 = (Absorber) model.placeGizmo(GizmoType.ABSORBER, anchorTile1, new String[]{ "1", "6", "2" });
        absorber2 = (Absorber) model.placeGizmo(GizmoType.ABSORBER, anchorTile2, new String[]{ "2", "10", "2" });
        ball = model.getBall();
    }

    @Test
    void findAnnexedTilesTrue() {
        ArrayList<Tile> tiles = new ArrayList<>();

        tiles.add(anchorTile1.getNeighbour(1, 1));
        tiles.add(anchorTile1.getNeighbour(2, 1));
        tiles.add(anchorTile1.getNeighbour(3, 1));
        tiles.add(anchorTile1.getNeighbour(4, 1));
        tiles.add(anchorTile1.getNeighbour(5, 1));

        Tile[] arr = new Tile[tiles.size()];
        arr = tiles.toArray(arr);

        Tile[] arr1 = absorber1.findAnnexedTiles(anchorTile1);

        for (int i = 0; i < arr.length; i++)
            assertTrue(arr[i].equals(arr1[i]));
    }

    @Test
    void findAnnexedTilesFalse() {
        ArrayList<Tile> tiles = new ArrayList<>();

        tiles.add(anchorTile1.getNeighbour(1, 1));
        tiles.add(anchorTile1.getNeighbour(2, 1));
        tiles.add(anchorTile1.getNeighbour(3, 1));
        tiles.add(anchorTile1.getNeighbour(4, 1));
        tiles.add(anchorTile1.getNeighbour(5, 1));
        tiles.add(anchorTile1.getNeighbour(6, 1));
        tiles.add(anchorTile1.getNeighbour(7, 1));
        tiles.add(anchorTile1.getNeighbour(8, 1));
        tiles.add(anchorTile1.getNeighbour(9, 1));

        Tile[] arr = new Tile[tiles.size()];
        arr = tiles.toArray(arr);

        Tile[] arr1 = absorber2.findAnnexedTiles(anchorTile2);

        for (int i = 0; i < arr.length; i++)
            assertFalse(arr[i].equals(arr1[i]));
    }

    @Test
    void getPrototypeGameObject() {
        GameObject gameObject = absorber1.getPrototypeGameObject();

        LineSegment[] lines = {
                new LineSegment(0, 0, 0, 2),
                new LineSegment(0, 2, 6, 2),
                new LineSegment(6, 2, 6, 0),
                new LineSegment(0, 0, 6, 0)
        };

        Circle[] circles = {
                new Circle(0,0, 0),
                new Circle(0, 2, 0),
                new Circle(6, 0, 0),
                new Circle(6, 2, 0)
        };

        for(int i = 0; i < gameObject.getLines().length; i++)
            assertTrue(gameObject.getLines()[i].equals(lines[i]));

        for(int i = 0; i < gameObject.getCircles().length; i++)
            assertTrue(gameObject.getCircles()[i].equals(circles[i]));
    }

    @Test
    void getGameObject() {
        absorber1.setAnchorTile(anchorTile1);
        GameObject gameObject = absorber1.getPrototypeGameObject();

        GameObject go = absorber1.getGameObject();
        GameObject go1 = gameObject.translate(absorber1.getPosition());

        for(int i = 0; i < go.getLines().length; i++)
            assertTrue(go.getLines()[i].equals(go1.getLines()[i]));

        for(int i = 0; i < go.getCircles().length; i++)
            assertTrue(go.getCircles()[i].equals(go1.getCircles()[i]));
    }

    @Test
    void isAbsorber() {
        assertTrue(absorber1.isAbsorber());
    }

    @Test
    void getGizmoDrawingData() {
        DrawingData data = absorber1.getGizmoDrawingData();
        ArrayList<Double[]> squarePoly = new ArrayList<>();

        squarePoly.add(new Double[]{0.0, 0.0}); //NE
        squarePoly.add(new Double[]{6.0, 0.0}); //NW
        squarePoly.add(new Double[]{6.0, 2.0}); //SW
        squarePoly.add(new Double[]{0.0 , 2.0}); //SE

        for(int i = 0; i < data.getPolygonsData().get(0).size(); i++) {

            assertEquals(data.getPolygonsData().get(0).get(i)[0],(squarePoly.get(i)[0]));
            assertEquals(data.getPolygonsData().get(0).get(i)[1],(squarePoly.get(i)[1]));
        }
    }
}