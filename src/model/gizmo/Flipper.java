package model.gizmo;

import model.Collidable;

import model.GameObject;
import model.StaticGameObject;
import physics.*;

import java.awt.*;

public class Flipper extends Gizmo implements Tickable, Collidable {

    private double flipSpeed;

    private final double length = 2;
    private final double width = 0.5;

    private boolean isLeftFlipper;
    private double currentMovement;
    private double flipPos;

    public Flipper(Color colour, boolean isLeft){
        super(colour);

        flipPos = 0;
        currentMovement = 0;

        setIsLeft(isLeft);
    }

    private void moveFlipper(){
        flipPos = isLeftFlipper ?
                clamp(flipPos + currentMovement, isLeftFlipper ? 0 : -1, isLeftFlipper ? 1 : 0)
                :
                clamp(flipPos + currentMovement, -1, 0);
    }

    public void setIsLeft(boolean isLeft){
        isLeftFlipper = isLeft;
        flipSpeed = isLeftFlipper ? 0.1 : -0.1;
    }

    private double clamp(double val, double min, double max){
        if(val < min) return min;
        if(val > max) return max;
        return val;
    }

    @Override
    public GameObject getPrototypeGameObject() {

        LineSegment[] lines = {
                new LineSegment(0, 0, 0,  length),
                new LineSegment(width, 0, width, length)
        };

        // These are the circles at either end of the flipper, and also the circles with
        // radius 0 to help with collisions at the ends of LineSegments.
        Circle[] circles = {
                new Circle(width/2d,0,width/2d),
                new Circle(width/2d, length, width/2d),
                new Circle(0,0, 0),
                new Circle(width, 0, 0),
                new Circle(0, length, 0),
                new Circle(width, length, 0)
        };


        GameObject gameObject = new StaticGameObject(lines, circles, 0.95);

        //TODO: Get Rotating Game Objects working with the ball.
//        double angularVelocity = 0.0;
//        if(flipPos > 0.0 && flipPos < 1.0)
//            angularVelocity = currentMovement < 0 ? Math.toRadians(180) : -1 * Math.toRadians(180);
//        GameObject gameObject = new RotatingGameObject(lines, circles, 0.95, new Vect(width/2d, 0), angularVelocity );

        gameObject.rotateAround(new Vect(width/2d, 0), new Angle(Math.toRadians(-90 * flipPos)));

        return gameObject;
    }

    @Override
    public void tick() {
        moveFlipper();
    }

    @Override
    public void keyDown() {
        currentMovement = flipSpeed;
    }

    @Override
    public void keyUp() {
        currentMovement = -1 * flipSpeed;
    }

    @Override
    public void genericTrigger() {
        //Empty...
    }

    @Override
    public GameObject getGameObject() {
        return getPrototypeGameObject().translate(tile.getPosition());
    }
}
