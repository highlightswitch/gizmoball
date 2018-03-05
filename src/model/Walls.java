package model;

import java.util.ArrayList;

import physics.LineSegment;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class Walls implements Collidable {

	private int xpos1;
	private int ypos1;
	private int ypos2;
	private int xpos2;

	// Walls are the enclosing Rectangle - defined by top left corner and bottom
	// right
    Walls(int x1, int y1, int x2, int y2) {
		xpos1 = x1;
		ypos1 = y1;
		xpos2 = x2;
		ypos2 = y2;
	}

	private ArrayList<LineSegment> getLineSegments() {
		ArrayList<LineSegment> ls = new ArrayList<LineSegment>();
		LineSegment l1 = new LineSegment(xpos1, ypos1, xpos2, ypos1);
		LineSegment l2 = new LineSegment(xpos1, ypos1, xpos1, ypos2);
		LineSegment l3 = new LineSegment(xpos2, ypos1, xpos2, ypos2);
		LineSegment l4 = new LineSegment(xpos1, ypos2, xpos2, ypos2);
		ls.add(l1);
		ls.add(l2);
		ls.add(l3);
		ls.add(l4);
		return ls;
	}

	@Override
	public GameObject getGameObject() {
	    ArrayList<LineSegment> lineList = getLineSegments();
        LineSegment[] lines = lineList.toArray(new LineSegment[lineList.size()]);
        return new StaticGameObject(lines, null, 1.0);
	}

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean isAbsorber() {
		return false;
	}
}
