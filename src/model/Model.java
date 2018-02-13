package model;

import model.gizmo.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class Model extends Observable {

    private final int width = 20;
    private final int height = 20;

    private Tile[][] tiles;

	private Ball ball;
	private Walls walls;

    private Map<Integer, GizmoEventListener> keyEventTriggerMap;

	private ArrayList<Tickable> tickable;
    private ArrayList<Collidable> collidable;

    public Model() {

        tiles = new Tile[width][height];

	    for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                tiles[x][y] = new Tile(x,y);
            }
        }

		walls = new Walls(0, 0, 20, 20);

		tickable = new ArrayList<>();

		collidable = new ArrayList<>();
		collidable.add(walls);

	}

    public void setUpActionMap(Flipper flipper) {
        keyEventTriggerMap = new HashMap<>();
        keyEventTriggerMap.put(70, flipper); //Key code 70 = F
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

	public ArrayList<Drawable> getDrawables(){
        ArrayList<Drawable> list = new ArrayList<>();
        for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++)
                list.add(tiles[x][y]);

        list.add(ball);
        return list;
    }

    public Gizmo placeGizmo(GizmoType gizmoType, String name, Tile tile){
        Gizmo gizmo = null;

	    switch(gizmoType){
	        case FLIPPER:
	            gizmo = new Flipper(null, true);
	            tile.placeGizmo(gizmo);
	            tickable.add((Flipper) gizmo);
                collidable.add((Flipper) gizmo);
                break;
            case BALL:
                gizmo = new Ball(this, Color.black, name, tile.getX(), tile.getY(), 3, 4);
                ball = (Ball) gizmo;
                tickable.add((Ball) gizmo);
                break;
        }

        // Notify observers ... redraw updated view
        this.setChanged();
        this.notifyObservers();

        return gizmo;

    }

    public void addBall(String name, float xc, float yc, float xv, float yv){
        Ball ball = new Ball(this, Color.black, name, xc, yc, xv, yv);
        this.ball = (Ball) ball;
        tickable.add((Ball) ball);
    }

    public void addSquare(String name, float xc, float yc){
        Square square = new Square(this, Color.black, name, xc, yc);
        Tile tile = getTileAt(xc, yc);
                tile.placeGizmo(square);
        tickable.add(square);
        collidable.add(square);
    }

    public void addTriangle(String name, float xc, float yc){
        Triangle triangle = new Triangle(this, Color.black, name, xc, yc);
        Tile tile = getTileAt(xc, yc);
        tile.placeGizmo(triangle);
        tickable.add(triangle);
        collidable.add(triangle);
    }

    public void addCircle(String name, float xc, float yc){
        CircleGizmo circle = new CircleGizmo(this, Color.black, name, xc, yc);
        Tile tile = getTileAt(xc, yc);
        tile.placeGizmo(circle);
        tickable.add(circle);
        collidable.add(circle);
    }

    public void keyEventTriggered(int keyCode, TriggerType trigger) {

        if(keyEventTriggerMap.containsKey(keyCode)){
            GizmoEventListener eventListener = keyEventTriggerMap.get(keyCode);
            switch(trigger){
                case KEY_DOWN:
                    eventListener.keyDown();
                    break;
                case KEY_UP:
                    eventListener.keyUp();
                    break;
                case GENERIC:
                    eventListener.genericTrigger();
                    break;
            }
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

    public ArrayList<Collidable> getCollidable() {
        return collidable;
    }

    public Ball getBall() {
		return ball;
	}
}
