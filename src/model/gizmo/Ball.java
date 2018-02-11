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

    private final double newRadius = 0.5;

    private double xPos, yPos;
	private Vect velocity;
	private String name;

	private boolean isStopped;

	private Model model;

	// x, y coordinates and x,y velocity
	public Ball(Model model, Color colour, String name, float xPos, float yPos, float xv, float yv) {
        super(colour);
        this.xPos = xPos;
        this.yPos = yPos;
		velocity = new Vect(xv, yv);
		isStopped = false;
		this.name = name;

		this.model = model;
	}



    public void moveBall() {

        double moveTime = 0.05; // 0.05 = 20 times per second as per Gizmoball

        if (!isStopped) {

            CollisionDetails cd = timeUntilCollision();
            double tuc = cd.getTuc();
            if (tuc > moveTime) {
                // No collision ...
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

        // Create a new GameObject, move it to where the ball is, the get the physics.Circle component.
        Circle ballCircle = getPrototypeGameObject().translate(new double[]{ xPos, yPos }).getCircles()[0];
        Vect ballVelocity = velocity;

        //This collision will never happen.
        CollisionDetails nextCollision = new CollisionDetails(Double.MAX_VALUE, new Vect(0,0));

        for (GameObject go : gameObjects) {
            CollisionDetails cd = go.timeUntilGameObjectCollision(ballCircle, ballVelocity);
            if (cd.getTuc() < nextCollision.getTuc()) {
                nextCollision = cd;
            }
        }

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
