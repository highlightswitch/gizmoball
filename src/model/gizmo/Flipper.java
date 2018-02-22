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
    private String name;
    private GizmoType type;
    Angle rotation = Angle.RAD_PI_OVER_TWO;

    public Flipper(Color colour, String name, boolean isLeft){
        super(colour);
        if(isLeft) {
            type = GizmoType.LEFT_FLIPPER;
        } else {
            type = GizmoType.RIGHT_FLIPPER;
        }
        flipPos = 0;
        currentMovement = 0;
        this.name = name;

        setIsLeft(isLeft);
    }

    public GizmoType getGizmoType(){return type;}

    public String getName(){
        return name;
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

        GameObject testCircle = new StaticGameObject(null, new Circle[]{new Circle(10, 10, 0)}, 1.0).translate( tile.getPosition());
        System.out.println(testCircle.getCircles()[0].getCenter() + " - " + testCircle.getCircles()[0].getRadius());

        LineSegment[] lines;
        Circle[] circles;

        if(isLeftFlipper) {

            lines = new LineSegment[] {
                    new LineSegment(0, width / 2, 0, length - width / 2),
                    new LineSegment(width, width / 2, width, length - width / 2)
            };

            // These are the circles at either end of the flipper, and also the circles with
            // radius 0 to help with collisions at the ends of LineSegments.
            circles = new Circle[] {
//                    new Circle(width / 2, width / 2, width / 2),
//                    new Circle(width / 2, length - width / 2, width / 2),
                    new Circle(0, width/2, 0),
                    new Circle(0, length - width/2, 0),
                    new Circle(width, width/2, 0),
                    new Circle(width, length - width/2, 0)
            };
        } else{

            lines = new LineSegment[] {
                    new LineSegment(2 - width, width / 2, 2 - width, length - width / 2),
                    new LineSegment(2, width / 2, 2, length - width / 2)
            };

            // These are the circles at either end of the flipper, and also the circles with
            // radius 0 to help with collisions at the ends of LineSegments.
            circles = new Circle[] {
//                    new Circle(2 - width / 2, width / 2, width / 2),
//                    new Circle( 2 - width / 2, length - width / 2, width / 2),
                    new Circle(2 - width, width/2, 0),
                    new Circle(2 - width, length - width/2, 0),
                    new Circle(2, width/2, 0),
                    new Circle(2, length - width/2, 0)
            };

        }

        GameObject gameObject = new StaticGameObject(lines, circles, 0.95);

        //TODO: Get Rotating Game Objects working with the ball.
//        double angularVelocity = 0.0;
//        if(flipPos > 0.0 && flipPos < 1.0)
//            angularVelocity = currentMovement < 0 ? Math.toRadians(180) : -1 * Math.toRadians(180);
//        GameObject gameObject = new RotatingGameObject(lines, circles, 0.95, new Vect(width/2, 0), angularVelocity );

        gameObject.rotateAround(new Vect(width/2, 0), new Angle(Math.toRadians(-90 * flipPos)));

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

    public void rotate() {
        rotation = rotation.plus(Angle.DEG_90);
    }

    @Override
    public GameObject getGameObject() {
        return getPrototypeGameObject(). /* rotateAround( new Vect(0,0), rotation ) . */ translate( tile.getPosition());
    }

    @Override
    public boolean isAbsorber() {
        return false;
    }
}
