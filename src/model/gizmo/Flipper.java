package model.gizmo;

import model.Collidable;

import model.GameObject;
import physics.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class Flipper extends Gizmo implements Tickable, Collidable {

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

    private void moveFlipper(){
        flipPos = clamp(flipPos + currentMovement, 0, 1);
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
        Circle[] circles = {
                new Circle(width/2d,0,width/2d),
                new Circle(width/2d, length, width/2d)
        };

        GameObject gameObject = new GameObject(lines, circles);
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
