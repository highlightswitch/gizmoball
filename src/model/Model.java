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

    public void setUpActionMap(Flipper l, Flipper r) {
        keyEventTriggerMap = new HashMap<>();
        keyEventTriggerMap.put(70, l); //Key code 70 = F
        keyEventTriggerMap.put(71, r); //Key code 71 = G
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
			case LEFT_FLIPPER:
				gizmo = new Flipper(null, true);
				tile.placeGizmo(gizmo);
				tickable.add((Flipper) gizmo);
				collidable.add(gizmo);
				break;
			case RIGHT_FLIPPER:
				gizmo = new Flipper(null, false);
				tile.placeGizmo(gizmo);
				tickable.add((Flipper) gizmo);
				collidable.add(gizmo);
				break;
			case BALL:
				gizmo = new Ball(this, Color.black, tile.getX(), tile.getY(), 3, 4);
				ball = (Ball) gizmo;
				tickable.add((Ball) gizmo);
				break;
			case CIRCLE_BUMPER:
				gizmo = addBumper(GizmoType.CIRCLE_BUMPER,tile);
				break;
			case SQUARE_BUMPER:
				gizmo = addBumper(GizmoType.SQUARE_BUMPER,tile);
				break;
			case TRIANGLE_BUMPER:
				gizmo = addBumper(GizmoType.TRIANGLE_BUMPER,tile);
				break;

		}

        // Notify observers ... redraw updated view
        this.setChanged();
        this.notifyObservers();

        return gizmo;

    }

	private Gizmo addBumper(GizmoType gt,Tile t) {
		Bumper bumper = new Bumper(Color.black, gt);
		collidable.add(bumper);
		t.placeGizmo(bumper);
		return bumper;
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
