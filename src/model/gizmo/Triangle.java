package model.gizmo;

import model.Collidable;
import model.GameObject;
import model.Model;
import model.StaticGameObject;
import physics.Circle;
import physics.LineSegment;

import java.awt.*;

public class Triangle extends Gizmo implements Tickable, Collidable {

    private Model model;
    private String name;

    private float xPos;
    private float yPos;

    private final double length = 1;
    private final double width = 1;

    public Triangle(Model model, Color colour, String name, float xPos, float yPos){
        super(colour);
        this.xPos = xPos;
        this.yPos = yPos;
        this.name = name;
        this.model = model;
    }

    @Override
    public GameObject getPrototypeGameObject(){

        LineSegment[] lines = {
                new LineSegment(0, 0, 0,  length),
                new LineSegment(0, 0, width,  0),
                new LineSegment(width, 0, 0, length)
        };

        Circle[] circles = {
                new Circle(0,0,0),
                new Circle(0, 0, 0),
                new Circle(width,0, 0),
                new Circle(width, 0, 0),
                new Circle(0, length, 0),
                new Circle(0, length, 0)
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
    public void tick() {

    }

    public GameObject getGameObject() {
        return getPrototypeGameObject().translate(tile.getPosition());
    }
}
