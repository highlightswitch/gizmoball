package model.gizmo;

import model.Collidable;
import model.GameObject;
import model.StaticGameObject;
import physics.Angle;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.awt.*;

public class Bumper extends Gizmo implements Collidable{

    private BumperType type;

    Angle angle = Angle.RAD_PI_OVER_TWO;

    private int rotate = 90;

    public Bumper(Color colour, BumperType type){
        super(colour);

        this.type = type;

    }

    @Override
    public GameObject getGameObject() {

        return getPrototypeGameObject().rotateAround(new Vect(0,0),angle).translate(tile.getPosition());
    }

    @Override
    public GameObject getPrototypeGameObject() {
        switch (type){
            case Square:
                return getSquareGameObject();
            case Triangle:
                return getTriangleGameObject();
            case Circle:
                return getCircleGameObject();
            default:
                return null;
        }
    }



    public GameObject getSquareGameObject(){
        LineSegment[] lines = {
                new LineSegment(0, 0, 0, 1), //East
                new LineSegment(0, 0, 1, 0), //North
                new LineSegment(0, 1, 1, 1), //South
                new LineSegment(1, 0, 1, 1) //West
        };

        // These are the circles at either end of the flipper, and also the circles with
        // radius 0 to help with collisions at the ends of LineSegments.
        Circle[] circles = {
                new Circle(0, 0, 0), //NE corner
                new Circle(1, 0, 0), //NW corner
                new Circle(0, 1, 0), //SE corner
                new Circle(1, 1, 0) //SW corner
        };

        return new StaticGameObject(lines, circles, 1.0);
    }

    private GameObject getTriangleGameObject() {
        LineSegment[] lines = {
                new LineSegment(0, 0, 0, 1), //East
                new LineSegment(0, 1, 1, 1), //South
                new LineSegment(0, 0, 1, 1) //Diagonal
        };

        // These are the circles at either end of the flipper, and also the circles with
        // radius 0 to help with collisions at the ends of LineSegments.
        Circle[] circles = {
                new Circle(0, 0, 0), //NE corner
                new Circle(0, 1, 0), //SE corner
                new Circle(1, 1, 0) // SW corner
        };

        return new StaticGameObject(lines, circles, 1.0);
    }

    private GameObject getCircleGameObject() {
        return null;
    }

    @Override
    public void keyDown() {

    }

    @Override
    public void keyUp() {

    }

    @Override
    public void genericTrigger() {

    }



}
