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
	private Vect friction;
	private boolean isStopped;

	private Model model;

	// x, y coordinates and x,y velocity
	public Ball(Model model, Color colour, double xPos, double yPos, double xv, double yv, double g, double f) {
        super(colour);
        this.xPos = xPos;
        this.yPos = yPos;
		velocity = new Vect(xv, yv);
		isStopped = false;
        gravity = new Vect(Angle.DEG_90, g);
        friction = new Vect(Angle.DEG_90, f);
		this.model = model;
	}

    public void moveBall() {

        double moveTime = 0.05; // 0.05 = 20 times per second as per Gizmoball

        if (!isStopped) {

            CollisionDetails cd = timeUntilCollision();
            double tuc = cd.getTuc();
            if (tuc > moveTime) {
                // No collision ...
                velocity =  velocity.plus(gravity).plus(friction);
                moveBallForTime(moveTime);
            } else {
                // We've got a collision in tuc
                moveBallForTime(tuc);
                // Post collision velocity ...
                velocity = cd.getVelocity();
            }

        }

    }

    private void moveBallForTime(double time) {
        double xVel = velocity.x();
        double yVel = velocity.y();
        xPos = xPos + (xVel * time);
        yPos = yPos + (yVel * time);
    }

    private CollisionDetails timeUntilCollision() {
        // Find Time Until Collision and also, if there is a collision, the new speed vector.

        //Create a list of all collidable game objects in the game.
        ArrayList<Collidable> collidable = model.getCollidable();
        ArrayList<GameObject> gameObjects = new ArrayList<>();
        for(Collidable col : collidable){
            gameObjects.add(col.getGameObject());
        }

        // Create a new GameObject, move it to where the ball is the get the physics.Circle component.
        Circle ballCircle = getPrototypeGameObject().translate(new double[]{ xPos, yPos}).getCircles()[0];

        if(velocity.y() < 0){
            friction.rotateBy(Angle.DEG_180);
        }

        Vect ballVelocity = velocity.plus(gravity).plus(friction);

        //This collision will never happen.
        CollisionDetails nextCollision = new CollisionDetails(Double.MAX_VALUE, new Vect(0,0));

        for (GameObject go : gameObjects) {
            CollisionDetails cd = go.timeUntilGameObjectCollision(ballCircle, ballVelocity);
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
        moveBall();
    }

    @Override
    public GameObject getShapeToDraw() {
        return getPrototypeGameObject().translate(new double[]{ xPos, yPos });
    }
}
