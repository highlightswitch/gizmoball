package model.gizmo;

import model.*;
import physics.Circle;
import physics.Vect;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class Ball extends Gizmo implements Drawable, Tickable {

    private final double newRadius = 0.25;

    private double xPos, yPos;
	private Vect velocity;
	private double gravity;
	private double friction;
	private boolean isAbsorbed;
	private boolean justFired;
	private String tag;

	private Model model;

	// x, y coordinates and x,y velocity
	public Ball(Model model, Color colour, String name,  double xPos, double yPos, double xv, double yv, double g, double f) {
        super(colour);
        this.xPos = xPos;
        this.yPos = yPos;
		velocity = new Vect(xv, yv);
		justFired = false;
        gravity = g;
        friction = f;
		this.model = model;
		tag = name;
	}

    public void setVelocity(double xv, double xy){
	    velocity = new Vect(xv, xy);
    }

	public void fire(Absorber firedFrom) {
	    moveBall(firedFrom);
	    justFired = true;
	    isAbsorbed = false;
    }

    public void moveBall(Absorber absorber) {

	    //A lot of definitions
        double lengthOfTick = 0.05; // 0.05 = 20 times per second as per Gizmoball
        CollisionDetails cd = timeUntilCollision(absorber);
        double tuc = cd.getTuc(); //ie Time Until Collision
        Vect velAfterCollision = cd.getVelocity();
        boolean isCollidingWithAbsorberNext = cd.getAbsorber() != null; // True if next thing ball is colliding with is an absorber
        Absorber collidedAbsorber = cd.getAbsorber(); //Usually null unless ball is colliding with absorber.


        // If the ball has not collided with something or if it has, but it is an absorber
        if(tuc != 0 || isCollidingWithAbsorberNext) {

            boolean isCollidingThisTick = tuc < lengthOfTick;
            double timeMoving = isCollidingThisTick ? tuc : lengthOfTick;

            //Get the new XY velocities of the ball during
            double[] newVel = getNewVelocities(justFired, timeMoving);

            //If the ball has just been fired, one tick later, it has not just been fired
            if(justFired) justFired = false;

            //Set the balls new velocity and move the ball for the correct amount of time
            velocity = new Vect(newVel[0], newVel[1]);
            moveBallForTime(timeMoving);

            if(isCollidingThisTick){
                //At this point the ball has collided with something

				// Set the velocity of the ball to its new post-collision velocity
                velocity = velAfterCollision;

                //If the collision is with an absorber, absorb the ball.
                if (isCollidingWithAbsorberNext) {
                    isAbsorbed = true;
                    collidedAbsorber.setAbsorbedBall(this);
                    xPos = 19.5;
                    yPos = 19.5;
                } else {
                	//If we get to here, the ball is colliding this tick, and it is not with an absorber
					//So we move the ball for the remaining time with its new velocity
                	moveBallForTime(lengthOfTick - tuc);
				}


            }
        }
    }

    private double[] getNewVelocities(boolean justFired, double timeMoving){

        //Set the initial new XY velocities
        //If the ball is just fired, it has a new velocity being fired upwards.
        //If not, then the initial velocity is it's velocity in the last tick (ie its current velocity)
        double[] velXY = justFired ? new double[]{0,-50} : new double[]{velocity.x(),velocity.y()};

        //Apply friction and gravity to the velocities
        velXY = applyFrictionToVelocities(velXY, timeMoving);
        velXY = applyGravityToVelocities(velXY, timeMoving);

        //This is the new velocity of the ball (before a collision if there is one).
        return velXY;
    }

    private double[] applyFrictionToVelocities(double[] velXY, double timeMoving){
        return new double[]{
                velXY[0] * (1 - friction * timeMoving - friction * Math.abs(velXY[0] * timeMoving)),
                velXY[1] * (1 - friction * timeMoving - friction * Math.abs(velXY[1] * timeMoving))
        };
    }

    private double[] applyGravityToVelocities(double[] velXY, double timeMoving){
        return new double[]{
                velXY[0],
                velXY[1] + (gravity * timeMoving)
        };
    }

    private void moveBallForTime(double time) {
        double xVel = velocity.x();
        double yVel = velocity.y();
        xPos = xPos + (xVel * time);
        yPos = yPos + (yVel * time);
    }

    private CollisionDetails timeUntilCollision( Absorber absorber) {
        // Find Time Until Collision and also, if there is a collision, the new speed vector.

        //Create a list of all collidable objects in the game.
        ArrayList<Collidable> collidable = model.getCollidable();

        // Create a new GameObject, move it to where the ball is the get the physics.Circle component.
        Circle ballCircle = getPrototypeGameObject().translate(new double[]{ xPos, yPos}).getCircles()[0];

//        if (justFired) {
//            velocity = new Vect(0, -50);
//            justFired = false;
//        }
        Vect ballVelocity = velocity;

        //This collision will never happen.
        CollisionDetails nextCollision = new CollisionDetails(Double.MAX_VALUE, new Vect(0,0));

        for (Collidable co : collidable) {
            if (co.equals(absorber)) {
                continue;
            }
            CollisionDetails cd = co.getGameObject().timeUntilGameObjectCollision(ballCircle, ballVelocity);
            if (co.isAbsorber()) {
                cd.setAbsorber((Absorber) co);
            }
            if (cd.getTuc() < nextCollision.getTuc()) {
                nextCollision = cd;
            }
        }

        // Check again but move GameObject to where the ball will be under the effect of gravity

        return nextCollision;
    }

    @Override
    public GameObject getPrototypeGameObject() {
        Circle[] circles = { new Circle(0,0, newRadius) };
        return new MovingGameObject(null, circles, 1.0);
    }

    @Override
    public GameObject getGameObject() {
        return null;
    }

    @Override
    public void rotate() {

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
        if (!isAbsorbed) {
            moveBall(null);
        }

    }

    @Override
    public GameObject getShapeToDraw() {
        return getPrototypeGameObject().translate(new double[]{ xPos, yPos });
    }

    public boolean isAbsorber() {return false;}
}
