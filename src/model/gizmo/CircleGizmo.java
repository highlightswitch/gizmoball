package model.gizmo;

import model.Collidable;
import model.GameObject;
import model.Model;
import model.StaticGameObject;
import physics.LineSegment;

import java.awt.*;

public class CircleGizmo extends Gizmo implements Tickable, Collidable {

    private Model model;
    private String name;

    private float xPos;
    private float yPos;

    private final double length = 1;
    private final double width = 1;

    public CircleGizmo(Model model, Color colour, String name, float xPos, float yPos){
        super(colour);
        this.xPos = xPos;
        this.yPos = yPos;
        this.name = name;
        this.model = model;
    }
    public void rotate(){

    }

    @Override
    public GameObject getPrototypeGameObject(){

        LineSegment[] lines = {
        };

        physics.Circle[] circles = {
                new physics.Circle(width/2, length/2, length/2)
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

    @Override
    public boolean isAbsorber() {
        return false;
    }
}
