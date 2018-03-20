package model;

import model.gizmo.Gizmo;
import model.gizmo.GizmoType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import static org.junit.jupiter.api.Assertions.*;

class MovingGameObjectTest {

    MovingGameObject m;
    Model model;
    Gizmo ball;
    LineSegment[] lines;
    Circle[] circles;
    double length = 2;
    double width = 0.5;

    String[] prop3 = {"Ball", "0", "3", "[r=255,g=255,b=255]", "[r=255,g=255,b=255]", "[r=16,g=219,b=139]"};


    @BeforeEach
    void setUp() throws TileCoordinatesNotValid, GizmoPlacementNotValidException {
        model = new Model();

        lines = new LineSegment[] {
                new LineSegment(0, width / 2, 0, length - width / 2),
                new LineSegment(width, width / 2, width, length - width / 2)
        };

        circles = new Circle[] {
                new Circle(width / 2, width / 2, width / 2),
                new Circle(width / 2, length - width / 2, width / 2),
                new Circle(0, width/2, 0),
                new Circle(0, length - width/2, 0),
                new Circle(width, width/2, 0),
                new Circle(width, length - width/2, 0)
        };
        m = new MovingGameObject(lines, circles, 1);
        ball = model.placeGizmo(GizmoType.BALL, model.getTileAt(0, 1), prop3);
    }

    @Test
    void timeUntilLineCollision() {
        GameObject ballGO = ball.getGameObject();
        Vect velocity = new Vect(0, 1.0);

        Circle ballCircle = ballGO.getCircles()[0];
        assertEquals(m.timeUntilLineCollision(lines[0], ballCircle, velocity), 0);
    }

    @Test
    void velocityOfLineCollision() {
        GameObject ballGO = ball.getGameObject();
        Vect velocity = new Vect(0, 1.0);

        Circle ballCircle = ballGO.getCircles()[0];
        assertNull(m.velocityOfLineCollision(lines[0], ballCircle, velocity));
    }

    @Test
    void timeUntilCircleCollision() {
        GameObject ballGO = ball.getGameObject();
        Vect velocity = new Vect(0, 1.0);

        Circle ballCircle = ballGO.getCircles()[0];
        assertEquals(m.timeUntilCircleCollision(circles[0], ballCircle, velocity), 0);
    }

    @Test
    void velocityOfCircleCollision() {
        GameObject ballGO = ball.getGameObject();
        Vect velocity = new Vect(0, 1.0);

        Circle ballCircle = ballGO.getCircles()[0];
        assertNull(m.velocityOfCircleCollision(circles[0], ballCircle, velocity));
    }
}