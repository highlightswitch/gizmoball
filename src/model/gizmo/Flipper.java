package model.gizmo;

import model.GameObject;
import physics.Angle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

import java.awt.*;
import java.awt.event.ActionEvent;

public class Flipper extends Gizmo implements Tickable {

    private final double flipSpeed = 0.1;

    private final double length = 2;
    private final double width = 0.5;

    private boolean isLeftFlipper;
    private double currentMovement;
    private double flipPos;

    public Flipper(Color colour, boolean isLeft){
        super(colour);

        isLeftFlipper = isLeft;
        flipPos = 0;
        currentMovement = 0;
    }

    public void moveFlipper(){
        flipPos = clamp(flipPos + currentMovement, 0, 1);
    }

    private double clamp(double val, double min, double max){
        if(val < min) return min;
        if(val > max) return max;
        return val;
    }

    @Override
    public GameObject getPhysicsObject() {
        LineSegment[] lines = {new LineSegment(0, 0, 0,  length), new LineSegment(width, 0, width, length)};

        lines[0] = Geometry.rotateAround(lines[0], new Vect(0, 0), new Angle(Math.toRadians(-90 * flipPos)));
        lines[1] = Geometry.rotateAround(lines[1], new Vect(0, 0), new Angle(Math.toRadians(-90 * flipPos)));

        return new GameObject(lines, null);
    }

    public double getLength() {
        return length;
    }

    public double getFlipPos() {
        return flipPos;
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
