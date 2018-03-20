package model;

import model.gizmo.Gizmo;
import model.gizmo.GizmoType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DrawingDataTest {

    DrawingData data;
    ArrayList<Double[]> squarePoly;
    Double[] circles;

    @BeforeEach
    void setUp() throws TileCoordinatesNotValid, GizmoPlacementNotValidException {
        data = new DrawingData();

        squarePoly = new ArrayList<>();

        squarePoly.add(new Double[]{0.0,0.0}); //NE
        squarePoly.add(new Double[]{1.0,0.0}); //NW
        squarePoly.add(new Double[]{1.0,1.0}); //SW
        squarePoly.add(new Double[]{0.0,1.0}); //SE0.0 , 2.0}); //SE

        data.addPolygon(squarePoly);

        circles = new Double[]{0.5, 0.5, 0.5};

        data.addCircle(circles);

    }

    @Test
    void addPolygonGetPolygonsData() {

        assertEquals(data.getPolygonsData().get(0).size(), 4);

        for(int i = 0; i < data.getPolygonsData().get(0).size(); i++) {

            assertEquals(data.getPolygonsData().get(0).get(i)[0],(squarePoly.get(i)[0]));
            assertEquals(data.getPolygonsData().get(0).get(i)[1],(squarePoly.get(i)[1]));
        }

        data.addPolygon(squarePoly);

        assertEquals(data.getPolygonsData().get(0).size(), 4);
    }

    @Test
    void addCircleGetPoligonsData() {

        assertEquals(data.getCirclesData().get(0).length, 3);

        for(int i = 0; i < data.getCirclesData().size(); i++) {
            assertEquals(data.getCirclesData().get(i)[0], (circles[0]));
            assertEquals(data.getCirclesData().get(i)[1], (circles[1]));
            assertEquals(data.getCirclesData().get(i)[2], (circles[2]));
        }

        data.addCircle(circles);

        assertEquals(data.getCirclesData().get(0).length, 3);
    }

    @Test
    void getRedValue() {
        data.setColour("[r=16,g=219,b=139]");

        assertEquals(data.getRedValue(), 16);
    }

    @Test
    void getGreenValue() {
        data.setColour("[r=16,g=219,b=139]");

        assertEquals(data.getGreenValue(), 219);
    }

    @Test
    void getBlueValue() {
        data.setColour("[r=16,g=219,b=139]");

        assertEquals(data.getBlueValue(), 139);
    }

    @Test
    void translate() {
        data.translate(new double[]{1.0, 2.0});

        ArrayList<Double[]> newPolygonsPoints = new ArrayList<>();

        newPolygonsPoints.add(new Double[]{1.0,2.0}); //NE
        newPolygonsPoints.add(new Double[]{2.0,2.0}); //NW
        newPolygonsPoints.add(new Double[]{2.0,3.0}); //SW
        newPolygonsPoints.add(new Double[]{1.0,3.0}); //SE0.0 , 2.0}); //SE

        for(int i = 0; i < data.getPolygonsData().get(0).size(); i++) {

            assertEquals(data.getPolygonsData().get(0).get(i)[0],(newPolygonsPoints.get(i)[0]));
            assertEquals(data.getPolygonsData().get(0).get(i)[1],(newPolygonsPoints.get(i)[1]));
        }

        Double[] circlesData = new Double[]{1.5, 2.5, 0.5};

        for(int i = 0; i < data.getCirclesData().size(); i++) {
            assertEquals(data.getCirclesData().get(i)[0], (circlesData[0]));
            assertEquals(data.getCirclesData().get(i)[1], (circlesData[1]));
            assertEquals(data.getCirclesData().get(i)[2], (circlesData[2]));
        }
    }

    @Test
    void translate1() {
        data.translate(new double[]{1, 2});

        ArrayList<Double[]> newPolygonsPoints = new ArrayList<>();

        newPolygonsPoints.add(new Double[]{1.0,2.0}); //NE
        newPolygonsPoints.add(new Double[]{2.0,2.0}); //NW
        newPolygonsPoints.add(new Double[]{2.0,3.0}); //SW
        newPolygonsPoints.add(new Double[]{1.0,3.0}); //SE0.0 , 2.0}); //SE

        for(int i = 0; i < data.getPolygonsData().get(0).size(); i++) {

            assertEquals(data.getPolygonsData().get(0).get(i)[0],(newPolygonsPoints.get(i)[0]));
            assertEquals(data.getPolygonsData().get(0).get(i)[1],(newPolygonsPoints.get(i)[1]));
        }

        Double[] circlesData = new Double[]{1.5, 2.5, 0.5};

        for(int i = 0; i < data.getCirclesData().size(); i++) {
            assertEquals(data.getCirclesData().get(i)[0], (circlesData[0]));
            assertEquals(data.getCirclesData().get(i)[1], (circlesData[1]));
            assertEquals(data.getCirclesData().get(i)[2], (circlesData[2]));
        }
    }

    @Test
    void rotateAroundPivotByRadians() {
        data.rotateAroundPivotByRadians(new double[]{1.0, 1.0},  Math.toRadians(90));

        ArrayList<Double[]> newPolygonsPoints = new ArrayList<>();

        newPolygonsPoints.add(new Double[]{2.0,0.0}); //NE
        newPolygonsPoints.add(new Double[]{2.0,1.0}); //NW
        newPolygonsPoints.add(new Double[]{1.0,1.0}); //SW
        newPolygonsPoints.add(new Double[]{1.0,0.0}); //SE0.0 , 2.0}); //SE

        for(int i = 0; i < data.getPolygonsData().get(0).size(); i++) {

            assertEquals(data.getPolygonsData().get(0).get(i)[0],(newPolygonsPoints.get(i)[0]));
            assertEquals(data.getPolygonsData().get(0).get(i)[1],(newPolygonsPoints.get(i)[1]));
        }

        Double[] circlesData = new Double[]{1.5, 0.5, 0.5};

        for(int i = 0; i < data.getCirclesData().size(); i++) {
            assertEquals(data.getCirclesData().get(i)[0], (circlesData[0]));
            assertEquals(data.getCirclesData().get(i)[1], (circlesData[1]));
            assertEquals(data.getCirclesData().get(i)[2], (circlesData[2]));
        }
    }

    @Test
    void rotateAroundPivotByRadians1() {
        data.rotateAroundPivotByRadians(new int[]{1, 1},  Math.toRadians(90));

        ArrayList<Double[]> newPolygonsPoints = new ArrayList<>();

        newPolygonsPoints.add(new Double[]{2.0,0.0}); //NE
        newPolygonsPoints.add(new Double[]{2.0,1.0}); //NW
        newPolygonsPoints.add(new Double[]{1.0,1.0}); //SW
        newPolygonsPoints.add(new Double[]{1.0,0.0}); //SE0.0 , 2.0}); //SE

        for(int i = 0; i < data.getPolygonsData().get(0).size(); i++) {

            assertEquals(data.getPolygonsData().get(0).get(i)[0],(newPolygonsPoints.get(i)[0]));
            assertEquals(data.getPolygonsData().get(0).get(i)[1],(newPolygonsPoints.get(i)[1]));
        }

        Double[] circlesData = new Double[]{1.5, 0.5, 0.5};

        for(int i = 0; i < data.getCirclesData().size(); i++) {
            assertEquals(data.getCirclesData().get(i)[0], (circlesData[0]));
            assertEquals(data.getCirclesData().get(i)[1], (circlesData[1]));
            assertEquals(data.getCirclesData().get(i)[2], (circlesData[2]));
        }
    }
}