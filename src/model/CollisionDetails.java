package model;

import model.gizmo.Absorber;
import physics.Vect;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class CollisionDetails {

	private double tuc;
	private Vect velocity;
	private Absorber absorber;
	private Collidable collidingWith;

	public CollisionDetails(double t, Vect v) {
		tuc = t;
		velocity = v;
	}

	public double getTuc() {
		return tuc;
	}
	
	public Vect getVelocity() {
		return velocity;
	}

	public Absorber getAbsorber (){
		return absorber;
	}

	public void setAbsorber (Absorber a)
	{
		absorber = a;
	}

	@Override
	public String toString(){
		return "CollisionDetails <" + tuc + "," + velocity + ">";
	}

	public void setCollidingWith(Collidable collidable) {
		this.collidingWith = collidable;
	}

	public Collidable getCollidingWith() {
		return collidingWith;
	}
}
