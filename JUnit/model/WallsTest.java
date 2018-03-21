package model;

import model.gizmo.Gizmo;
import model.gizmo.GizmoType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WallsTest {

    Walls walls;
    Model model;
    Gizmo ball;
    String[] prop3 = {"Ball", "0", "3", "[r=255,g=255,b=255]", "[r=255,g=255,b=255]", "[r=16,g=219,b=139]"};


    @BeforeEach
    void setUp() throws TileCoordinatesNotValid, GizmoPlacementNotValidException {
        model = new Model();
        walls = new Walls(0, 0, 20, 20);

        ball = model.placeGizmo(GizmoType.BALL, model.getTileAt(0, 1), prop3);
    }

    @Test
    void getGameObject() {
        ArrayList<LineSegment> ls = new ArrayList<>();
        LineSegment l1 = new LineSegment(0, 0, 20, 0);
        LineSegment l2 = new LineSegment(0, 0, 0, 20);
        LineSegment l3 = new LineSegment(20, 0, 20, 20);
        LineSegment l4 = new LineSegment(0, 20, 20, 20);
        ls.add(l1);
        ls.add(l2);
        ls.add(l3);
        ls.add(l4);

        LineSegment[] lines = ls.toArray(new LineSegment[ls.size()]);

        GameObject sgo = walls.getGameObject();

        for(int i = 0; i < sgo.getLines().length; i++)
            assertTrue(sgo.getLines()[i].equals(lines[i]));
    }

    @Test
    void isAbsorber() {
        assertFalse(walls.isAbsorber());
    }

    @Test
    void timeUntilCollisionWithBall() {
        GameObject ballGO = ball.getGameObject();
        Vect velocity = new Vect(0, 1.0);

        System.out.println(walls.getGameObject().timeUntilCollisionWithBall(ballGO, velocity));
        System.out.println(walls.timeUntilCollisionWithBall(ballGO, velocity));


        assertEquals(walls.getGameObject().timeUntilCollisionWithBall(ballGO, velocity).getTuc(),
                walls.timeUntilCollisionWithBall(ballGO, velocity).getTuc());

        assertEquals(walls.getGameObject().timeUntilCollisionWithBall(ballGO, velocity).getVelocity(),
                walls.timeUntilCollisionWithBall(ballGO, velocity).getVelocity());
    }

}