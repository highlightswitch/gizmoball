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

    private GizmoType type;
    Angle rotation = Angle.RAD_PI_OVER_TWO;
    private String name;

    public Bumper(Color colour, String name, GizmoType type){
        super(colour);
        this.type = type;
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void rotate() {
        rotation = rotation.plus(Angle.DEG_90);
    }

    @Override
    public GameObject getGameObject() {
        return getPrototypeGameObject().rotateAround( new Vect(0.5,0.5), rotation ).translate( tile.getPosition() );
    }

    public GizmoType getGizmoType(){return type;}

    @Override
    public GameObject getPrototypeGameObject() {
        switch (type) {
            case SQUARE_BUMPER:
                return getSquareGameObject();
            case TRIANGLE_BUMPER:
                return getTriangleGameObject();
            case CIRCLE_BUMPER:
                return getCircleGameObject();
            default:
                return null;
        }
    }

    private GameObject getSquareGameObject(){
        LineSegment[] lines = {
                new LineSegment(0, 0, 0, 1), //East
                new LineSegment(0, 0, 1, 0), //North
                new LineSegment(0, 1, 1, 1), //South
                new LineSegment(1, 0, 1, 1) //West
        };

        // These are the circles with radius 0 to help with collisions at the ends of LineSegments.
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

        // These are the circles with radius 0 to help with collisions at the ends of LineSegments.
        Circle[] circles = {
                new Circle(0, 0, 0), //NE corner
                new Circle(0, 1, 0), //SE corner
                new Circle(1, 1, 0) // SW corner
        };

        return new StaticGameObject(lines, circles, 1.0);
    }

    private GameObject getCircleGameObject() {
        Circle[] circles = {
                new Circle(0.5, 0.5, 0.5)
        };
        return new StaticGameObject(null, circles,1.0);
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
