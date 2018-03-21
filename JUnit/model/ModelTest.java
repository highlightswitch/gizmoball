package model;

import model.gizmo.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

class ModelTest {
    private Model m;
    private ArrayList<Collidable> collide;
    private ArrayList<Drawable> draw;
    private ArrayList<Collidable> c = new ArrayList<>();
    private ArrayList<Drawable> d = new ArrayList<>();
    private Tile[][] tile;
    private Gizmo flipper;
    private Gizmo circle;
    private Gizmo ball;
    private String[] prop = {"Flipper", "90", "true", "[r=255,g=255,b=255]", "[r=255,g=255,b=255]", "[r=16,g=219,b=139]"};
    private String[] prop2 = null;
    private String[] prop3 = {"Ball", "0", "3", "[r=255,g=255,b=255]", "[r=255,g=255,b=255]", "[r=16,g=219,b=139]"};

    @BeforeEach
    void setUp() throws GizmoPlacementNotValidException, TileCoordinatesNotValid {
        m = new Model();
        flipper = m.placeGizmo(GizmoType.FLIPPER, m.getTileAt(5, 4), prop);
        circle = m.placeGizmo(GizmoType.CIRCLE_BUMPER, m.getTileAt(5, 9), prop2);
        ball = m.placeGizmo(GizmoType.BALL, m.getTileAt(0, 1), prop3);
        collide = m.getCollidable();
        draw = m.getDrawables();
        tile = m.getTiles();
    }

    @Test
    void getBallName() {
        //assertEquals(m.getBallName(), "Ball");
    }

    @Test
    void getCollidable() {
        Walls walls = new Walls(0, 0, 20, 20);
        ArrayList<Collidable> collidable = new ArrayList<>();
        collidable.add(walls);

        assertTrue(m.getCollidable().get(0).equals(collidable.get(0)));
    }

    @Test
    void getBall() {
        assertEquals(m.getBall(), ball);
    }

    @Test
    void getTiles() {
        Tile[][] tiles = new Tile[20][20];

        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 20; y++) {
                tiles[x][y] = new Tile(m, x, y);
                assertEquals(m.getTiles()[x][y], tiles[x][y]);
            }
        }
    }


    @Test
    void getGizmoByName2() throws GizmoNotFoundException {
        assertEquals(flipper, m.getGizmoByName("Flipper"));
    }

    @Test
    void checkName() {
        assertFalse(m.checkName("OuterWalls"));
        assertTrue(m.checkName("Flipper"));
        assertTrue(m.checkName("Ball"));
    }

    @Test
    void getTileAt() throws TileCoordinatesNotValid {
        assertEquals(m.getTileAt(2, 3), tile[2][3]);
    }

    @Test
    void getTileAtNear() throws TileCoordinatesNotValid {
        assertEquals(m.getTileNear(4.0, 5.0), m.getTileAt(4, 5));
    }

    @Test
    void deleteGizmo() throws GizmoNotFoundException, TileCoordinatesNotValid {
        for (Drawable dr : draw) {
            d.add(dr);
        }
        for (Collidable col : collide) {
            c.add(col);
        }

        m.deleteGizmo("Flipper");

        assertEquals((c.size() - 1), collide.size());
        assertEquals((d.size() - 1), draw.size());
        assertThrows(GizmoNotFoundException.class, () -> m.getGizmoByName("Flipper"));
    }

    @Test
    void moveGizmo() throws GizmoNotFoundException, TileCoordinatesNotValid, GizmoPlacementNotValidException {
        m.moveGizmo("Flipper", m.getTileAt(2, 5));
        double x = m.getGizmoByName("Flipper").getPosition()[0];
        double y = m.getGizmoByName("Flipper").getPosition()[1];

        assertEquals(2, x);
        assertEquals(5, y);


        m.moveGizmo("Ball", m.getTileAt(7, 10));
        
        x = m.getGizmoByName("Ball").getPosition()[0];
        y = m.getGizmoByName("Ball").getPosition()[1];

        assertEquals(7, x);
        assertEquals(10, y);
    }

    @Test
    void rotateGizmoBy_Deg() throws GizmoNotFoundException, GizmoPropertyException, TileCoordinatesNotValid, GizmoPlacementNotValidException, GizmoNotRotatableException {
        double val = 90.0;
        double rotation = Double.valueOf(flipper.getProperty(GizmoPropertyType.ROTATION_DEG));
        rotation = (rotation + val) % 360;
        flipper.setProperty(GizmoPropertyType.ROTATION_DEG, String.valueOf(rotation));

        Model m1 = new Model();
        Gizmo flipper1 = m1.placeGizmo(GizmoType.FLIPPER, m1.getTileAt(5, 4), prop);
        m1.rotateGizmoBy_Deg("Flipper", 90.0);

        assertEquals(flipper.getProperty(GizmoPropertyType.ROTATION_DEG), flipper1.getProperty(GizmoPropertyType.ROTATION_DEG));
    }

    @Test
    void getGizmoProperty() throws GizmoNotFoundException {
        assertEquals((m.getGizmoProperty("Flipper", GizmoPropertyType.ROTATION_DEG)), "90");
    }

    @Test
    void setGizmoProperty() throws GizmoNotFoundException, GizmoPropertyException {
        m.setGizmoProperty("Flipper", GizmoPropertyType.ROTATION_DEG, "180");
        assertEquals((m.getGizmoProperty("Flipper", GizmoPropertyType.ROTATION_DEG)), "180");
    }

    @Test
    void setAllProperties() throws GizmoNotFoundException {
        HashMap<GizmoPropertyType, String> properties = new HashMap<>();

        properties.put(GizmoPropertyType.NAME, "flipper");
        properties.put(GizmoPropertyType.ROTATION_DEG, "270");
        properties.put(GizmoPropertyType.IS_LEFT_ORIENTATED, "false");
        properties.put(GizmoPropertyType.CURRENT_COLOUR, "[r=16,g=219,b=139]");
        properties.put(GizmoPropertyType.DEFAULT_COLOUR, "[r=255,g=255,b=255]");
        properties.put(GizmoPropertyType.ALT_COLOUR, "[r=255,g=255,b=255]");

        m.setAllProperties("Flipper", properties);

        assertEquals(flipper.getProperty(GizmoPropertyType.NAME), "flipper");
        assertEquals(flipper.getProperty(GizmoPropertyType.ROTATION_DEG), "270");
        assertEquals(flipper.getProperty(GizmoPropertyType.IS_LEFT_ORIENTATED), "false");
        assertEquals(flipper.getProperty(GizmoPropertyType.CURRENT_COLOUR), "[r=16,g=219,b=139]");
        assertEquals(flipper.getProperty(GizmoPropertyType.DEFAULT_COLOUR), "[r=255,g=255,b=255]");
        assertEquals(flipper.getProperty(GizmoPropertyType.ALT_COLOUR), "[r=255,g=255,b=255]");

    }

    @Test
    void placeBall() throws GizmoPlacementNotValidException, TileCoordinatesNotValid {
        String[] prop = {"ball", "10", "13", "[r=255,g=255,b=255]", "[r=255,g=255,b=255]", "[r=16,g=219,b=139]"};
        m.placeBall(10, 13, prop);

        assertTrue(tile[10][13].isOccupied());
    }

    @Test
    void getFrictionConstants() {
        double[] v = new double[]{0.025, 0.025};
        assertEquals(m.getFrictionConstants()[0], v[0]);
        assertEquals(m.getFrictionConstants()[1], v[1]);
    }

    @Test
    void getGravityConstants() {
        assertEquals(m.getGravityConstant(), 25);

    }

    @Test
    void setFrictionConstants() throws ModelPropertyException {
        double[] v = new double[]{1, 1};
        m.setFrictionConstants(v);
        assertEquals(m.getFrictionConstants()[0], v[0]);
        assertEquals(m.getFrictionConstants()[1], v[1]);
    }

    @Test
    void getDrawables() {
        assertEquals(m.getDrawables().size(), 3);
        assertTrue(m.getDrawables().contains(flipper));
        assertTrue(m.getDrawables().contains(circle));
        assertTrue(m.getDrawables().contains(ball));
    }
}