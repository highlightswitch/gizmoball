package model;

import physics.LineSegment;
import physics.Vect;

import java.util.ArrayList;

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
		ArrayList<LineSegment> ls = new ArrayList<>();
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

	@Override
	public CollisionDetails timeUntilCollisionWithBall(GameObject ballGO, Vect ballVelocity) {
		return this.getGameObject().timeUntilCollisionWithBall(ballGO, ballVelocity);
	}

	@Override
	public void collide(){

	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Walls walls = (Walls) obj;
		return xpos1 == walls.xpos1 && xpos2 == walls.xpos2 && ypos1 == walls.ypos1 && ypos2 == walls.ypos2;
	}
}
