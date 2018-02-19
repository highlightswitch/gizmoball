package model.gizmo;

import model.Collidable;
import model.GameObject;
import model.StaticGameObject;
import physics.Angle;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.awt.*;

public class Absorber extends Gizmo implements Collidable {

    private final double length = 20;
    private final double width = 1;
    private Ball absorbedBall;

    public Absorber(Color colour) {
        super(colour);
    }

    @Override
    public GameObject getPrototypeGameObject() {
        LineSegment[] lines = {
                new LineSegment(0, 0, 0,  width),
                new LineSegment(0, width, length, width),
                new LineSegment(length, width, length, 0),
                new LineSegment(0, 0, length, 0)
        };

        // These are the circles at either end of the flipper, and also the circles with
        // radius 0 to help with collisions at the ends of LineSegments.
        Circle[] circles = {
                new Circle(0,0, 0),
                new Circle(0, width, 0),
                new Circle(length, 0, 0),
                new Circle(length, width, 0)
        };

        GameObject gameObject = new StaticGameObject(lines, circles, 1);


        return gameObject;
    }

    public void setAbsorbedBall (Ball ball) {
        absorbedBall = ball;
    }

    @Override
    public void genericTrigger() {
        //Empty...
    }

    @Override
    public void keyDown() {
    	if(absorbedBall != null) {
			absorbedBall.fire(this);
			absorbedBall = null;
		}
    }

    @Override
    public void keyUp() {
        //Empty...
    }

    public GameObject getGameObject(){return getPrototypeGameObject().translate(tile.getPosition());}

   public boolean isAbsorber() {return true;}
}
