package model;

import physics.Angle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

import java.awt.*;

public class Flipper {

    private final double flipSpeed = 0.1;

    private double xPos;
    private double yPos;
    private int length;
    private int width;
    private Color colour;

    private boolean isLeftFlipper;
    private double currentMovement;
    private double flipPos;

    public Flipper(int x, int y, boolean isLeft){
        xPos = x;
        yPos = y;
        length = 80;
        width = 20;
        colour = Color.black;

        isLeftFlipper = isLeft;
        flipPos = 0;
        currentMovement = 0;
    }

    public void flipKeyPressed(){
        currentMovement = flipSpeed;
    }

    public void moveFlipper(){
        if(flipPos == 1)
            currentMovement = -1 * currentMovement;

        flipPos = clamp(flipPos + currentMovement, 0, 1);
    }

    private double clamp(double val, double min, double max){
        if(val < min) return min;
        if(val > max) return max;
        return val;
    }

    public Shape getShape(){
        LineSegment eastLS = new LineSegment(xPos, yPos, xPos, yPos+ length);
        LineSegment westLS = new LineSegment(xPos+width, yPos, xPos+width, yPos+ length);

        eastLS = Geometry.rotateAround(eastLS, new Vect(xPos, yPos), new Angle(Math.toRadians(-90 * flipPos)));
        westLS = Geometry.rotateAround(westLS, new Vect(xPos, yPos), new Angle(Math.toRadians(-90 * flipPos)));

        Polygon shape = new Polygon(
                new int[]{(int)eastLS.p1().x(), (int)eastLS.p2().x(), (int)westLS.p2().x(), (int)westLS.p1().x() },
                new int[]{(int)eastLS.p1().y(), (int)eastLS.p2().y(), (int)westLS.p2().y(), (int)westLS.p1().y()},
                4);

        return shape;
    }

    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
    }

    public int getLength() {
        return length;
    }

    public double getFlipPos() {
        return flipPos;
    }
}
