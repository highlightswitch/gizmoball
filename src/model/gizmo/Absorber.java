package model.gizmo;

import model.Collidable;
import model.DrawingData;
import model.GameObject;
import model.StaticGameObject;
import physics.Circle;
import physics.LineSegment;

import java.awt.*;

public class Absorber extends Gizmo implements Tickable, Collidable {

    private double length;
    private double width;
    private String name;
    private GizmoType type;
    private Ball absorbedBall;

    public Absorber(Color colour, String name, double x1, double y1, double x2, double y2){
        super(colour);
        this.name = name;
        length = x2 - x1;
        width = y2 - y1;
        type = GizmoType.ABSORBER;
    }

    public GizmoType getGizmoType(){return type;}


    @Override
    public void tick(){

    }

    public double getlength(){
        return length;
    }

    public double getWidth(){
        return width;
    }

    public String getName(){
        return name;
    }

    @Override
    public void rotate() {

    }

    @Override
    public GameObject getPrototypeGameObject() {
        LineSegment[] lines = {
                new LineSegment(0, 0, 0,  width),
                new LineSegment(0, width, length, width),
                new LineSegment(length, width, length, 0),
                new LineSegment(0, 0, length, 0)
        };

        // These are the circles at either end of the flipper, and also the circles with
        // radius 0 to help with collisions at the ends of LineSegments.
        Circle[] circles = {
                new Circle(0,0, 0),
                new Circle(0, width, 0),
                new Circle(length, 0, 0),
                new Circle(length, width, 0)
        };

        GameObject gameObject = new StaticGameObject(lines, circles, 1);


        return gameObject;
    }

    public void setAbsorbedBall (Ball ball) {
        absorbedBall = ball;
    }

    @Override
    public void genericTrigger() {
        //Empty...
    }

    @Override
    public void keyDown() {
    	if(absorbedBall != null) {
			absorbedBall.fire(this);
			absorbedBall = null;
		}
    }

    @Override
    public void keyUp() {
        //Empty...
    }

    public GameObject getGameObject(){return getPrototypeGameObject().translate(getPosition());}

    @Override
    public DrawingData getGizmoDrawingData() {
        return null;
    }

   public boolean isAbsorber() {return true;}
}
