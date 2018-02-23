package model.gizmo;

import model.Collidable;
import model.DrawingData;
import model.GameObject;
import model.StaticGameObject;
import physics.Angle;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.awt.*;
import java.util.ArrayList;

public class Flipper extends Gizmo implements Tickable, Collidable {

    private double flipSpeed;

    private final double length = 2;
    private final double width = 0.5;

    private boolean isLeftFlipper;
    private double currentMovement;
    private double flipPos;
    private String name;
    Angle rotation = Angle.RAD_PI_OVER_TWO;

    public Flipper(Color colour, String name, boolean isLeft){
        super(colour);

        this.name = name;
        setIsLeft(isLeft);

        flipPos = 0;
        currentMovement = 0;

    }

    private void setIsLeft(boolean isLeft){
        isLeftFlipper = isLeft;
        flipSpeed = isLeftFlipper ? 0.1 : -0.1;
    }

    private void moveFlipper(){
        flipPos = isLeftFlipper ?
                clamp(flipPos + currentMovement, isLeftFlipper ? 0 : -1, isLeftFlipper ? 1 : 0)
                :
                clamp(flipPos + currentMovement, -1, 0);
    }


    private double clamp(double val, double min, double max){
        if(val < min) return min;
        if(val > max) return max;
        return val;
    }

    @Override
    public GameObject getPrototypeGameObject() {

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
                    new Circle(width / 2, width / 2, width / 2),
                    new Circle(width / 2, length - width / 2, width / 2),
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
                    new Circle(2 - width / 2, width / 2, width / 2),
                    new Circle( 2 - width / 2, length - width / 2, width / 2),
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

        gameObject.rotateAroundPointByAngle(
                isLeftFlipper ? new Vect(width/2, width/2) : new Vect(2 - width/2, width/2),
                new Angle(Math.toRadians(-90 * flipPos))
        );

        return gameObject;
    }

    @Override
    public GameObject getGameObject() {
        return getPrototypeGameObject(). /* rotateAroundPointByAngle( new Vect(0,0), rotation ) . */ translate(getPosition());
    }

    @Override
    public boolean isAbsorber() {
        return false;
    }

    public void rotate() {
        rotation = rotation.plus(Angle.DEG_90);
    }

    @Override
    public DrawingData getGizmoDrawingData() {
        DrawingData data = new DrawingData();

        if(isLeftFlipper) {
            ArrayList<Double[]> poly = new ArrayList<>();
            poly.add(new Double[]{0.0, width / 2}); // NW
            poly.add(new Double[]{0.0, length - width / 2}); // SW
            poly.add(new Double[]{width, length - width / 2}); // SE
            poly.add(new Double[]{width, width / 2}); // NE
            data.addPolygon(poly);

            data.addCircle(new Double[]{width / 2, width / 2, width / 2});
            data.addCircle(new Double[]{width / 2, length - width / 2, width / 2});

            data.rotateAroundPivotByRadians(new double[]{width/2, width/2}, Math.toRadians(-90 * flipPos));
        } else {
            ArrayList<Double[]> poly = new ArrayList<>();
            poly.add(new Double[]{2.0, width / 2}); // NW
            poly.add(new Double[]{2.0, length - width / 2}); // SW
            poly.add(new Double[]{2 - width, length - width / 2}); // SE
            poly.add(new Double[]{2 - width, width / 2}); // NE
            data.addPolygon(poly);

            data.addCircle(new Double[]{2 - width / 2, width / 2, width / 2});
            data.addCircle(new Double[]{2 - width / 2, length - width / 2, width / 2});

            data.rotateAroundPivotByRadians(new double[]{2 - width/2, width/2}, Math.toRadians(-90 * flipPos));
        }

        return data;
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
}
