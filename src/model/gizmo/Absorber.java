package model.gizmo;

import model.*;
import physics.Circle;
import physics.LineSegment;

import java.awt.*;
import java.util.ArrayList;

public class Absorber extends Gizmo implements Collidable {

    private double width;
    private double height;

    private Ball absorbedBall;

    public Absorber(Color colour, String name){
        super(name, colour);
        width = 20;
        height = 1;
    }

    public double getWidth(){
        return width;
    }

    public double getHeight(){
        return height;
    }

    void setAbsorbedBall (Ball ball) {
        absorbedBall = ball;
    }

    @Override
    public GameObject getPrototypeGameObject() {
        LineSegment[] lines = {
                new LineSegment(0, 0, 0, height),
                new LineSegment(0, height, width, height),
                new LineSegment(width, height, width, 0),
                new LineSegment(0, 0, width, 0)
        };

        // These are the circles at either end of the flipper, and also the circles with
        // radius 0 to help with collisions at the ends of LineSegments.
        Circle[] circles = {
                new Circle(0,0, 0),
                new Circle(0, height, 0),
                new Circle(width, 0, 0),
                new Circle(width, height, 0)
        };

        GameObject gameObject = new StaticGameObject(lines, circles, 1);

        return gameObject;
    }

    public GameObject getGameObject(){return getPrototypeGameObject().translate(getPosition());}

    public boolean isAbsorber() {return true;}

    @Override
    public void rotate() {

    }

    @Override
    public DrawingData getGizmoDrawingData() {
        DrawingData data = new DrawingData();
        ArrayList<Double[]> squarePoly = new ArrayList<>();
        squarePoly.add(new Double[]{0.0, 0.0}); //NE
        squarePoly.add(new Double[]{width, 0.0}); //NW
        squarePoly.add(new Double[]{width, height}); //SW
        squarePoly.add(new Double[]{0.0 , height}); //SE
        data.addPolygon(squarePoly);

        return data;
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

}
