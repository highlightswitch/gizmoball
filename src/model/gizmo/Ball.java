package model.gizmo;

import model.*;
import physics.Angle;
import physics.Circle;
import physics.Vect;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class Ball extends Gizmo implements Drawable, Tickable {

    private final double newRadius = 0.5;

    private double xPos, yPos;
	private Vect velocity;
	private Vect gravity;
	private double friction;
	private boolean isStopped;
	private boolean isAbsorbed;

	private Model model;

	// x, y coordinates and x,y velocity
	public Ball(Model model, Color colour, double xPos, double yPos, double xv, double yv, double g, double f) {
        super(colour);
        this.xPos = xPos;
        this.yPos = yPos;
		velocity = new Vect(xv, yv);
		isStopped = false;
        gravity = new Vect(Angle.DEG_90, g);
        friction = f;
		this.model = model;
	}

	public void fire (Absorber firedFrom) {
	    moveBall(firedFrom);
	    isAbsorbed = false;
    }

    public void moveBall(Absorber absorber) {
        System.out.println("Current Velocity: " + velocity.toString());
        double moveTime = 0.05; // 0.05 = 20 times per second as per Gizmoball

        if (!isStopped) {
           /* if(velocity.x() < 0.1 && velocity.y() < 0.1){
                velocity = Vect.ZERO;
            }*/

            CollisionDetails cd = timeUntilCollision(absorber);
            double tuc = cd.getTuc();

            if (tuc > moveTime) {
                // No collision ...
                double newVX = velocity.x() * ((1 * 0.025 * friction) - (0.025* velocity.x() * friction));
                double newVY = velocity.y() * ((1 * 0.025 * friction) - (0.025* velocity.y() * friction));
                velocity = new Vect(newVX, newVY);
                velocity = velocity.plus(gravity);
                moveBallForTime(moveTime);

            } else {
                // We've got a collision in tuc
                moveBallForTime(tuc);
                if (cd.getAbsorber()!=null) {
                    isAbsorbed = true;
                    cd.getAbsorber().setAbsorbedBall(this);
                    velocity = new Vect (0, -50);
                    xPos = 19.5;
                    yPos = 19.5;
                }
                // Post collision velocity ...
                velocity = cd.getVelocity();
            }

        }

        else {
            moveBallToAbsorber();
        }

    }

    private void moveBallForTime(double time) {
        double xVel = velocity.x();
        double yVel = velocity.y();
        xPos = xPos + (xVel * time);
        yPos = yPos + (yVel * time);
    }

    private void moveBallToAbsorber() {
	    xPos = 18;
	    yPos = 18;
    }

    private CollisionDetails timeUntilCollision( Absorber absorber) {
        // Find Time Until Collision and also, if there is a collision, the new speed vector.

        //Create a list of all collidable game objects in the game.
        ArrayList<Collidable> collidable = model.getCollidable();
        ArrayList<GameObject> gameObjects = new ArrayList<>();
        for(Collidable col : collidable){
            gameObjects.add(col.getGameObject());
        }

        // Create a new GameObject, move it to where the ball is the get the physics.Circle component.
        Circle ballCircle = getPrototypeGameObject().translate(new double[]{ xPos, yPos}).getCircles()[0];

        Vect ballVelocity;
        double newVX = velocity.x() * ((1 * 0.025 * friction) - (0.025* velocity.x() * friction));
        double newVY = velocity.y() * ((1 * 0.025 * friction) - (0.025* velocity.y() * friction));
        ballVelocity = new Vect(newVX, newVY);
        ballVelocity = ballVelocity.plus(gravity);

        //This collision will never happen.
        CollisionDetails nextCollision = new CollisionDetails(Double.MAX_VALUE, new Vect(0,0));

        for (Collidable co : collidable) {
            if (co.equals((Collidable) absorber)) {
                continue;
            }
            CollisionDetails cd = co.getGameObject().timeUntilGameObjectCollision(ballCircle, ballVelocity);
            if (co.isAbsorber())
            {
                //System.out.println("HGFHGCH");
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
