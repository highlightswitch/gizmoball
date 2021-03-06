package model.gizmo;

import model.*;
import physics.Circle;
import physics.Vect;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class Ball extends Gizmo implements Tickable, TileIndependentGizmo {

    private final double radius = 0.25;

    private Model model;

    private double cx, cy;

    private boolean isAbsorbed;
    private boolean justFired;

    // x, y coordinates and x,y velocity
    public Ball(Model model, double cx, double cy, Map<GizmoPropertyType, String> properties) {
        super(properties);
        this.model = model;

        this.cx = cx;
        this.cy = cy;

        justFired = false;
        isAbsorbed = false;

		type = GizmoType.BALL;

	}

    public void setCx(double val){ cx = val;}

    public void setCy(double val){ cy = val;}

    public GizmoType getType(){return type;}

    private Vect getVelocity() {
        return new Vect(Double.valueOf(getProperty(GizmoPropertyType.VEL_X)), Double.valueOf(getProperty(GizmoPropertyType.VEL_Y)));
    }

    private void setVelocity(double xv, double yv) {
        try {
            setProperty(GizmoPropertyType.VEL_X, String.valueOf(xv));
            setProperty(GizmoPropertyType.VEL_Y, String.valueOf(yv));
        } catch (GizmoPropertyException ignored) {
            //This should never happen
        }
    }

    @Override
    public Tile[] findAnnexedTiles(Tile anchorTile) {
        return new Tile[0];
    }

    @Override
    public double[] getPosition() {
        return new double[]{cx - 0.5, cy - 0.5};
    }

    @Override
    public void moveTo(double x, double y) {
        cx = x + 0.5;
        cy = y + 0.5;
    }

    @Override
    public boolean isTilePlacable() {
        return false;
    }

    void fire(Absorber firedFrom) {
        justFired = true;
        isAbsorbed = false;
        moveBall(firedFrom);
    }

    private void moveBall(Absorber absorber) {

        double lengthOfTick = 0.025; // 0.025 = 40 times per second as per Gizmoball

        //Apply Friction and Gravity
        double[] newVel = getNewVelocities(new double[]{getVelocity().x(), getVelocity().y()}, justFired, lengthOfTick);
        setVelocity(newVel[0], newVel[1]);

        //Get time until collision
        CollisionDetails cd = timeUntilCollision(absorber);
        double tuc = cd.getTuc(); //ie Time Until Collision
        double[] velAfterCollisionXY = new double[]{cd.getVelocity().x(), cd.getVelocity().y()};
        boolean isCollidingWithAbsorberNext = cd.getAbsorber() != null; // True if next thing ball is colliding with is an absorber
        Absorber collidedAbsorber = cd.getAbsorber(); //Usually null unless ball is colliding with absorber.

        boolean isCollidingThisTick = tuc < lengthOfTick;
        double timeMoving = isCollidingThisTick ? tuc : lengthOfTick;

        //Set the balls new velocity and move the ball for the correct amount of time
        setVelocity(newVel[0], newVel[1]);
        moveBallForTime(timeMoving);

        if (isCollidingThisTick) {
            //At this point the ball has collided with something

            //If the collision is with an absorber, absorb the ball.
            if (isCollidingWithAbsorberNext) {
                isAbsorbed = true;
                collidedAbsorber.setAbsorbedBall(this);
                cx = cd.getAbsorber().getPosition()[0] + Double.valueOf(cd.getAbsorber().getProperty(GizmoPropertyType.WIDTH)) - 0.5;
                cy = cd.getAbsorber().getPosition()[1] + Double.valueOf(cd.getAbsorber().getProperty(GizmoPropertyType.HEIGHT)) / 2;
                cd.getCollidingWith().collide(); // need to collide after absorbing the ball
            } else {
                //If we get to here, the ball is colliding this tick, and it is not with an absorber
                //So we set the post-collision velocity, then move the ball for the remaining time with its new velocity

                cd.getCollidingWith().collide();

                newVel = getNewVelocities(velAfterCollisionXY, justFired, lengthOfTick - tuc);

                setVelocity(newVel[0], newVel[1]);
                moveBallForTime(lengthOfTick - tuc);
            }


        }

        //If the ball has just been fired, one tick later, it has not just been fired
        if (justFired)
            justFired = false;

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
        double[] frictionArr = model.getFrictionConstants();
        return new double[]{
                velXY[0] * (1 - frictionArr[0] * timeMoving - frictionArr[1] * Math.abs(velXY[0]) * timeMoving),
                velXY[1] * (1 - frictionArr[0] * timeMoving - frictionArr[1] * Math.abs(velXY[1]) * timeMoving)
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

        //This collision will never happen.
        CollisionDetails nextCollision = new CollisionDetails(Double.MAX_VALUE, new Vect(0,0));

        for (Collidable co : collidable) {
            if (co.equals(absorber)) {
                continue;
            }
            CollisionDetails cd = co.timeUntilCollisionWithBall(this.getGameObject(), this.getVelocity());
            cd.setCollidingWith(co);
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

    public double getRadius(){
        return radius;
    }

    public double[] getCentre(){
        return new double[]{cx,cy};
    }

    @Override
    public GameObject getPrototypeGameObject() {
        Circle[] circles = { new Circle(0.5,0.5, radius) };
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
        data.setColour(getProperty(GizmoPropertyType.CURRENT_COLOUR));
        return data;
    }

    @Override
    public void doAction() {
    }

    @Override
    public void tick() {
        if (!isAbsorbed) {
            moveBall(null);
        }

    }
}
