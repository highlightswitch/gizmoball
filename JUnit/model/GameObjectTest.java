package model;

import model.gizmo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import physics.Angle;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameObjectTest {

    GameObject gameObject;
    GameObject gameObject1;

    private Absorber absorber1;
    private Bumper square;
    private Tile anchorTile1;
    private Tile anchorTile2;
    private Model model;

    @BeforeEach
    void setUp() throws TileCoordinatesNotValid, GizmoPlacementNotValidException {

        model = new Model();
        anchorTile1 = model.getTileAt(9, 10);
        absorber1 = (Absorber) model.placeGizmo(GizmoType.ABSORBER, anchorTile1, new String[]{ "1", "6", "2", "[r=255,g=255,b=255]", "[r=255,g=255,b=255]", "[r=16,g=219,b=139]"});

        anchorTile2 = model.getTileAt(12, 15);
        square = (Bumper) model.placeGizmo(GizmoType.SQUARE_BUMPER, anchorTile2, null);

        gameObject = absorber1.getGameObject();
        gameObject1 = square.getGameObject();
    }

    @Test
    void getLines() {
        LineSegment[] lines = {
                new LineSegment(9.0, 10.0, 9.0, 12.0),
                new LineSegment(9.0, 12.0, 15.0, 12.0),
                new LineSegment(15.0, 12.0, 15.0, 10.0),
                new LineSegment(9.0, 10.0, 15.0, 10.0)
        };

        for(int i = 0; i < gameObject.getLines().length; i++)
            assertTrue(gameObject.getLines()[i].equals(lines[i]));
    }

    @Test
    void getCircles() {
        Circle[] circles = {
                new Circle(9.0,10.0, 0),
                new Circle(9.0, 12.0, 0),
                new Circle(15.0, 10.0, 0),
                new Circle(15.0, 12.0, 0)
        };

        for(int i = 0; i < gameObject.getCircles().length; i++)
            assertTrue(gameObject.getCircles()[i].equals(circles[i]));
    }

    @Test
    void translate() {
        gameObject.translate(new double[]{1.0, 3.0});

        LineSegment[] lines = {
                new LineSegment(10.0, 13.0, 10.0, 15.0),
                new LineSegment(10.0, 15.0, 16.0, 15.0),
                new LineSegment(16.0, 15.0, 16.0, 13.0),
                new LineSegment(10.0, 13.0, 16.0, 13.0)
        };

        for(int i = 0; i < gameObject.getLines().length; i++)
            assertTrue(gameObject.getLines()[i].equals(lines[i]));

        Circle[] circles = {
                new Circle(10.0,13.0, 0),
                new Circle(10.0, 15.0, 0),
                new Circle(16.0, 13.0, 0),
                new Circle(16.0, 15.0, 0)
        };

        for(int i = 0; i < gameObject.getCircles().length; i++)
            assertTrue(gameObject.getCircles()[i].equals(circles[i]));
    }

    @Test
    void translate1() {
        gameObject.translate(new int[]{1, 3});

        LineSegment[] lines = {
                new LineSegment(10.0, 13.0, 10.0, 15.0),
                new LineSegment(10.0, 15.0, 16.0, 15.0),
                new LineSegment(16.0, 15.0, 16.0, 13.0),
                new LineSegment(10.0, 13.0, 16.0, 13.0)
        };

        for(int i = 0; i < gameObject.getLines().length; i++)
            assertTrue(gameObject.getLines()[i].equals(lines[i]));

        Circle[] circles = {
                new Circle(10.0,13.0, 0),
                new Circle(10.0, 15.0, 0),
                new Circle(16.0, 13.0, 0),
                new Circle(16.0, 15.0, 0)
        };

        for(int i = 0; i < gameObject.getCircles().length; i++)
            assertTrue(gameObject.getCircles()[i].equals(circles[i]));
    }

    @Test
    void rotateAroundPointByAngle() {

        LineSegment[] lines = {
                new LineSegment(15, -12, 15, -13), //North
                new LineSegment(15, -13, 16, -13), //West
                new LineSegment(16, -13, 16, -12), //South
                new LineSegment(16, -12, 15, -12) //East
        };

        // These are the circles with radius 0 to help with collisions at the ends of LineSegments.
        Circle[] circles = {
                new Circle(15, -12, 0), //NE corner
                new Circle(15, -13, 0), //NW corner
                new Circle(16, -12, 0), //SE corner
                new Circle(16, -13, 0) //SW corner
        };


        gameObject1.rotateAroundPointByAngle(new Vect(0, 0), new Angle(Math.toRadians(-90)));

        for(int i = 0; i < gameObject1.getCircles().length; i++)
            assertTrue(gameObject1.getLines()[i].equals(lines[i]));

        for(int i = 0; i < gameObject1.getCircles().length; i++)
            assertTrue(gameObject1.getCircles()[i].equals(circles[i]));
    }

    @Test
    void getDrawingData() {
        DrawingData data = absorber1.getDrawingData();
        ArrayList<Double[]> squarePoly = new ArrayList<>();

        squarePoly.add(new Double[]{9.0, 10.0}); //NE
        squarePoly.add(new Double[]{15.0, 10.0}); //NW
        squarePoly.add(new Double[]{15.0, 12.0}); //SW
        squarePoly.add(new Double[]{9.0 , 12.0}); //SE

        for(int i = 0; i < data.getPolygonsData().get(0).size(); i++) {
            assertEquals(data.getPolygonsData().get(0).get(i)[0],(squarePoly.get(i)[0]));
            assertEquals(data.getPolygonsData().get(0).get(i)[1],(squarePoly.get(i)[1]));
        }
    }

    @Test
    void timeUntilCollisionWithBall() throws TileCoordinatesNotValid, GizmoPlacementNotValidException {
        Gizmo ball = model.placeGizmo(GizmoType.BALL, model.getTileAt(15, 1), null);

        GameObject ballGO = ball.getGameObject();
        Vect velocity = new Vect(0, 1.0);

        gameObject.timeUntilCollisionWithBall(ballGO, velocity);

        Circle ballCircle = ballGO.getCircles()[0];
        double shortestTime = Double.MAX_VALUE;
        double time;
        Vect newVelocity = new Vect(0, 0);

        for(LineSegment line : gameObject.getLines()) {
            time = gameObject.timeUntilLineCollision(line, ballCircle, velocity);
            if(time < shortestTime) {
                shortestTime = time;
                newVelocity = gameObject.velocityOfLineCollision(line, ballCircle, velocity);
            }
        }

        for(Circle circle: gameObject.getCircles()){
            time = gameObject.timeUntilCircleCollision(circle, ballCircle, velocity);
            if(time < shortestTime) {
                shortestTime = time;
                newVelocity = gameObject.velocityOfCircleCollision(circle, ballCircle, velocity);
            }
        }

        assertEquals(gameObject.timeUntilCollisionWithBall(ballGO, velocity).getTuc(), new CollisionDetails(shortestTime, newVelocity).getTuc());
        assertEquals(gameObject.timeUntilCollisionWithBall(ballGO, velocity).getVelocity(), new CollisionDetails(shortestTime, newVelocity).getVelocity());
    }
}