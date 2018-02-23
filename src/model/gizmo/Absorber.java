package model.gizmo;

import model.*;
import physics.Circle;
import physics.LineSegment;

import java.awt.*;
import java.util.ArrayList;

public class Absorber extends Gizmo implements Collidable {

    private double length;
    private double width;
    private String name;
    private GizmoType type;
    private Ball absorbedBall;

    public Absorber(Color colour, String name){
        super(colour);
        this.name = name;
        length = 20;
        width = 1;
        type = GizmoType.ABSORBER;
    }

    public GizmoType getGizmoType(){return type;}

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

    public GameObject getGameObject(){return getPrototypeGameObject().translate(getPosition());}

    @Override
    public DrawingData getGizmoDrawingData() {
        DrawingData data = new DrawingData();
        ArrayList<Double[]> squarePoly = new ArrayList<>();
        squarePoly.add(new Double[]{0.0, 0.0}); //NE
        squarePoly.add(new Double[]{length, 0.0}); //NW
        squarePoly.add(new Double[]{length ,width}); //SW
        squarePoly.add(new Double[]{0.0 ,width}); //SE
        data.addPolygon(squarePoly);

        return data;
    }

    public boolean isAbsorber() {return true;}

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

}
