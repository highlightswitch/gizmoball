package model.gizmo;

import model.*;
import physics.Circle;
import physics.Vect;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class Ball extends Gizmo implements Tickable {

    private final double newRadius = 0.25;

	private Model model;

    private double cx, cy;

	private boolean isAbsorbed;
	private boolean justFired;


	// x, y coordinates and x,y velocity
	public Ball(Model model, Color colour, double xTile, double yTile, Map<GizmoPropertyType, String> properties) {
        super(colour, properties);
		this.model = model;

        this.cx = xTile + 0.5;
        this.cy = yTile + 0.5;

		justFired = false;
		isAbsorbed = false;

	}

	private Vect getVelocity(){
	    return new Vect(Double.valueOf(getProperty(GizmoPropertyType.VEL_X)), Double.valueOf(getProperty(GizmoPropertyType.VEL_Y)));
    }

    private void setVelocity(double xv, double yv){
        try {
            setProperty(GizmoPropertyType.VEL_X, String.valueOf(xv));
            setProperty(GizmoPropertyType.VEL_Y, String.valueOf(yv));
        } catch (GizmoPropertyException e) {
            //This should never be thrown anyway...
            e.printStackTrace();
        }
    }

    @Override
    public Tile[] findAnnexedTiles(Tile anchorTile) {
        return new Tile[0];
    }

    @Override
    public double[] getPosition() {
        return new double[] { cx - 0.5, cy - 0.5 };
    }

    void fire(Absorber firedFrom) {
	    justFired = true;
	    isAbsorbed = false;
	    moveBall(firedFrom);
    }

    public void moveBall(Absorber absorber) {

        //=====================
	    //Start of Definitions
        // \/ \/ \/ \/ \/ \/ \/

        double lengthOfTick = 0.05; // 0.05 = 20 times per second as per Gizmoball

        double[] currentVelXY = new double[]{getVelocity().x(), getVelocity().y()}; // Balls velocity in the last tick

        CollisionDetails cd = timeUntilCollision(absorber);
        double tuc = cd.getTuc(); //ie Time Until Collision
        double[] velAfterCollisionXY = new double[]{cd.getVelocity().x(), cd.getVelocity().y()};
        boolean isCollidingWithAbsorberNext = cd.getAbsorber() != null; // True if next thing ball is colliding with is an absorber
        Absorber collidedAbsorber = cd.getAbsorber(); //Usually null unless ball is colliding with absorber.

        boolean isCollidingThisTick = tuc < lengthOfTick;
        double timeMoving = isCollidingThisTick ? tuc : lengthOfTick;

        // /\ /\ /\ /\ /\ /\ /\
        //End of Definitions
        //=====================

        //=====================
        //Start of Calculation
        // \/ \/ \/ \/ \/ \/ \/

        //Get the new XY velocities of the ball during
        double[] newVel = getNewVelocities(currentVelXY, justFired, timeMoving);

        //Set the balls new velocity and move the ball for the correct amount of time
        setVelocity(newVel[0], newVel[1]);
        moveBallForTime(timeMoving);

        if(isCollidingThisTick){
            //At this point the ball has collided with something

            //If the collision is with an absorber, absorb the ball.
            if (isCollidingWithAbsorberNext) {
                isAbsorbed = true;
                collidedAbsorber.setAbsorbedBall(this);
                cx = 19.5;
                cy = 19.5;
            } else {
                //If we get to here, the ball is colliding this tick, and it is not with an absorber
                //So we set the post-collision velocity, then move the ball for the remaining time with its new velocity

                newVel = getNewVelocities(velAfterCollisionXY, justFired, lengthOfTick - tuc);

                setVelocity(newVel[0], newVel[1]);
                moveBallForTime(lengthOfTick - tuc);
            }


        }

        //If the ball has just been fired, one tick later, it has not just been fired
        if(justFired)
            justFired = false;

        // /\ /\ /\ /\ /\ /\ /\
        //End of Calculations
        //=====================
    }

    private double[] getNewVelocities(double[] currentVelXY, boolean justFired, double timeMoving){
        //If the ball is just fired,
        // return a representation of the ball being fired upwards.
        //Else
        // return the currentVelocity with friction and gravity applied to it.
        return justFired ?
                new double[]{0,-50}
                :
                applyGravityToVelocities(
                        applyFrictionToVelocities(currentVelXY, timeMoving),
                        timeMoving
                );
    }

    private double[] applyFrictionToVelocities(double[] velXY, double timeMoving){
        double friction = model.getFrictionConstant();
        return new double[]{
                velXY[0] * (1 - friction * timeMoving - friction * Math.abs(velXY[0]) * timeMoving),
                velXY[1] * (1 - friction * timeMoving - friction * Math.abs(velXY[1]) * timeMoving)
        };
    }

    private double[] applyGravityToVelocities(double[] velXY, double timeMoving){
        double gravity = model.getGravityConstant();
        return new double[]{
                velXY[0],
                velXY[1] + (gravity * timeMoving)
        };
    }

    private CollisionDetails timeUntilCollision( Absorber absorber) {
        // Find Time Until Collision and also, if there is a collision, the new speed vector.

        //Create a list of all collidable objects in the game.
        ArrayList<Collidable> collidable = model.getCollidable();

        // Create a new GameObject, move it to where the ball is the get the physics.Circle component.
        Circle ballCircle = getGameObject().getCircles()[0];
        Vect ballVelocity = getVelocity();

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

        return nextCollision;
    }

    private void moveBallForTime(double time) {
        double xVel = getVelocity().x();
        double yVel = getVelocity().y();
        cx = cx + (xVel * time);
        cy = cy + (yVel * time);
    }

    @Override
    public GameObject getPrototypeGameObject() {
        Circle[] circles = { new Circle(0.5,0.5, newRadius) };
        return new MovingGameObject(null, circles, 1.0);
    }

    @Override
    public GameObject getGameObject() {
        return getPrototypeGameObject().translate(getPosition());
    }

    @Override
    public Object clone() {
        return super.clone();
    }

    public boolean isAbsorber() {return false;}

    @Override
    protected DrawingData getGizmoDrawingData() {
        DrawingData data = new DrawingData();
        data.addCircle(new Double[]{0.5, 0.5, 0.25});
        return data;
    }

    @Override
    public void tick() {
        if (!isAbsorbed) {
            moveBall(null);
        }

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

}
