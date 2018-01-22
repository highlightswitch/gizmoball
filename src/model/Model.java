package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import model.gizmo.*;
import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class Model extends Observable {

    private final int width = 20;
    private final int height = 20;

    private Tile[][] tiles;

	private ArrayList<HorizontalLine> lines;
	private Ball ball;
	private Walls gws;

	private Map<Integer, Tile> keyboardActionMap;
	private ArrayList<Tickable> tickable;

    public Model() {

        tiles = new Tile[width][height];

	    for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                tiles[x][y] = new Tile(x,y);
            }
        }

		// Ball position (25, 25) in pixels. Ball velocity (100, 100) pixels per tick
		ball = new Ball(25, 25, 100, 100);

		// Wall size 500 x 500 pixels
		gws = new Walls(0, 0, 500, 500);

		// Lines added in Main
		lines = new ArrayList<HorizontalLine>();

		tickable = new ArrayList<>();

	}

    public void setUpActionMap() {
        keyboardActionMap = new HashMap<>();
        keyboardActionMap.put(70, tiles[10][10]); //Key code 70 = F
    }

    public Tile getTileAt(int x, int y){
	    return tiles[x][y];
    }

    /**
     * This returns the tile under the pixel coordinates. eg tile (3,5) is under the coords (3.25, 5.89).
     * This can be used for learning where the mouse was clicked.
     * @param xPos
     * @param yPos
     * @return
     */
    public Tile getTileAt(double xPos, double yPos){
	    return getTileAt((int) xPos, (int) yPos);
    }

	public ArrayList<Drawable> getDrawableTiles(){
        ArrayList<Drawable> list = new ArrayList<>();
        for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++)
                list.add(tiles[x][y]);
        return list;
    }

    public void placeGizmo(GizmoType gizmoType, Tile tile){
	    switch(gizmoType){
	        case FLIPPER:
	            Flipper flipper = new Flipper(null, true);
	            tile.placeGizmo(flipper);
	            tickable.add(flipper);
                break;
            default:
                System.out.println("Looking for gizmo that does not exist");
                break;
        }

        // Notify observers ... redraw updated view
        this.setChanged();
        this.notifyObservers();

    }

    public void keyEventTriggered(int keyCode) {
        if(keyboardActionMap.containsKey(keyCode)){
            keyboardActionMap.get(keyCode).doAction();
        }
    }

	public void tick(){

	    for(Tickable t : tickable){
	        t.tick();
        }

        // Notify observers ... redraw updated view
        this.setChanged();
        this.notifyObservers();
	}

	public void moveBall() {

		double moveTime = 0.05; // 0.05 = 20 times per second as per Gizmoball

		if (ball != null && !ball.stopped()) {

			CollisionDetails cd = timeUntilCollision();
			double tuc = cd.getTuc();
			if (tuc > moveTime) {
				// No collision ...
				ball = movelBallForTime(ball, moveTime);
			} else {
				// We've got a collision in tuc
				ball = movelBallForTime(ball, tuc);
				// Post collision velocity ...
				ball.setVelo(cd.getVelo());
			}

			// Notify observers ... redraw updated view
			this.setChanged();
			this.notifyObservers();
		}

	}

	private Ball movelBallForTime(Ball ball, double time) {

		double newX = 0.0;
		double newY = 0.0;
		double xVel = ball.getVelo().x();
		double yVel = ball.getVelo().y();
		newX = ball.getExactX() + (xVel * time);
		newY = ball.getExactY() + (yVel * time);
		ball.setExactX(newX);
		ball.setExactY(newY);
		return ball;
	}

	private CollisionDetails timeUntilCollision() {
		// Find Time Until Collision and also, if there is a collision, the new speed vector.
		// Create a physics.Circle from Ball
		Circle ballCircle = ball.getCircle();
		Vect ballVelocity = ball.getVelo();
		Vect newVelo = new Vect(0, 0);

		// Now find shortest time to hit a vertical line or a wall line
		double shortestTime = Double.MAX_VALUE;
		double time = 0.0;

		// Time to collide with 4 walls
		ArrayList<LineSegment> lss = gws.getLineSegments();
		for (LineSegment line : lss) {
			time = Geometry.timeUntilWallCollision(line, ballCircle, ballVelocity);
			if (time < shortestTime) {
				shortestTime = time;
				newVelo = Geometry.reflectWall(line, ball.getVelo(), 1.0);
			}
		}

		// Time to collide with any vertical lines
		for (HorizontalLine line : lines) {
			LineSegment ls = line.getLineSeg();
			time = Geometry.timeUntilWallCollision(ls, ballCircle, ballVelocity);
			if (time < shortestTime) {
				shortestTime = time;
				newVelo = Geometry.reflectWall(ls, ball.getVelo(), 1.0);
			}
		}
		return new CollisionDetails(shortestTime, newVelo);
	}

	public Ball getBall() {
		return ball;
	}

	public ArrayList<HorizontalLine> getLines() {
		return lines;
	}

	public void addLine(HorizontalLine l) {
		lines.add(l);
	}

	public void setBallSpeed(int x, int y) {
		ball.setVelo(new Vect(x, y));
	}

}
