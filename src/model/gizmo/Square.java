package model.gizmo;

import model.*;
import physics.Circle;
import physics.LineSegment;

import java.awt.*;

public class Square extends Gizmo implements Drawable, Tickable  {
    private Model model;
    private String name;

    private float xPos;
    private float yPos;

    private final double length = 2;
    private final double width = 2;

    public Square(Model model, Color colour, String name, float xPos, float yPos){
        super(colour);
        this.xPos = xPos;
        this.yPos = yPos;
        this.name = name;
        this.model = model;
    }

    @Override
    public  GameObject getPrototypeGameObject(){

        LineSegment[] lines = {
                new LineSegment(0, 0, 0,  length),
                new LineSegment(width, 0, width, length),
                new LineSegment(0, 0, 0,  length),
                new LineSegment(width, 0, width, length)
        };

        Circle[] circles = {
                new Circle(width/2d,0,width/2d),
                new Circle(width/2d, length, width/2d),
                new Circle(0,0, 0),
                new Circle(width, 0, 0),
                new Circle(0, length, 0),
                new Circle(width, length, 0),
                new Circle(0, length, 0),
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
    public void tick() {

    }

    @Override
    public GameObject getShapeToDraw() {
        return getPrototypeGameObject().translate(new double[]{ xPos, yPos });
    }

    public GameObject getGameObject() {
        return null;
    }
}
