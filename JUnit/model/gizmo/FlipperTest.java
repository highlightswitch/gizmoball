package model.gizmo;

import model.GizmoPlacementNotValidException;
import model.Model;
import model.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FlipperTest {

    Flipper flipper;
    Tile anchorTile;
    Model model;

    @BeforeEach
    void setUp() throws GizmoPlacementNotValidException {
        model = new Model();
        anchorTile = model.getTileAt(9, 10);
        flipper = (Flipper) model.placeGizmo(GizmoType.FLIPPER, anchorTile, null);
    }

    @Test
    void findAnnexedTiles() {
        ArrayList<Tile> tiles = new ArrayList<>();

        tiles.add(anchorTile.getNeighbour(1, 1));

        Tile[] arr = new Tile[tiles.size()];
        arr = tiles.toArray(arr);

        Tile[] arr1 = flipper.findAnnexedTiles(anchorTile);

        for (int i = 0; i < arr.length; i++)
            assertTrue(arr[i].equals(arr1[i]));
    }

    @Test
    void getPrototypeGameObject() {
    }

    @Test
    void getGameObject() {
    }

    @Test
    void isAbsorber() {
        assertFalse(flipper.isAbsorber());
    }

    @Test
    void getGizmoDrawingData() {
    }
}