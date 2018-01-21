package model;

import physics.Circle;
import physics.LineSegment;

/**
 * A GameObject is a collection of LineSegments and Circles that
 * represent the physical properties an object in the game.
 */
public class GameObject {

    private LineSegment[] lines;
    private Circle[] circles;

    public GameObject(LineSegment[] lines, Circle[] circles){
        this.lines = lines;
        this.circles = circles;
    }

    public LineSegment[] getLines() {
        return lines;
    }

    public Circle[] getCircles() {
        return circles;
    }

    /**
     * This method moves increases the x-y positions of each component of the object by the given amount.
     *
     * e.g. this.translate( {10, -4} ); will increase the x value by ten and
     * decrease the y value by 4 for every component of this object.
     *
     * @param translation An array of two doubles that represent changes in the x and y directions.
     */
    public void translate(double[] translation) {
        for(int i = 0; i < lines.length; i++){
            double[] newX = {lines[i].p1().x() + translation[0], lines[i].p2().x() + translation[0]};
            double[] newY = {lines[i].p1().y() + translation[1], lines[i].p2().y() + translation[1]};
            lines[i] = new LineSegment(newX[0], newY[0], newX[1], newY[1]);
        }
    }

    public void translate(int[] translation) {
        translate(new double[]{translation[0], translation[1]});
    }

}
