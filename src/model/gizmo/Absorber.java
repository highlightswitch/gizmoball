package model.gizmo;

import model.Collidable;
import model.GameObject;
import model.StaticGameObject;
import physics.Angle;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.awt.*;

public class Absorber extends Gizmo implements Tickable, Collidable {

    private double length;
    private double width;
    private String name;

    public Absorber(Color colour, String name, double x1, double y1, double x2, double y2){
        super(colour);
        this.name = name;
        length = x2 - x1;
        width = y2 - y1;
    }

    @Override
    public void tick(){

    }

    @Override
    public GameObject getPrototypeGameObject() {

        LineSegment[] lines = {
                new LineSegment(0, 0, width,  0),
                new LineSegment(0, 0, 0,  length),
                new LineSegment(width, length, width, 0),
                new LineSegment(width, length, 0, length)
        };

        Circle[] circles = {
                new Circle(0,width,0),
                new Circle(0, width, 0),
                new Circle(length,0, 0),
                new Circle(length, 0, 0),
                new Circle(0, 0, 0),
                new Circle(0, 0, 0),
                new Circle(width, length, 0),
                new Circle(width, length, 0)
        };

        GameObject gameObject = new StaticGameObject(lines, circles, 0.95);

        return gameObject;

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

    @Override
    public void rotate() {

    }

    @Override
    public GameObject getGameObject() {
        return getPrototypeGameObject().translate(tile.getPosition());
    }
}
