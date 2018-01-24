package model;

public class CollisionDetails {

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class CollisionDetails {
	private double tuc;
	private Vect velocity;

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

	@Override
	public String toString(){
		return "CollisionDetails <" + tuc + "," + velocity + ">";
	}
}
