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
    private ArrayList<Gizmo> gizmos;

    public Model() {

        tiles = new Tile[width][height];

	    for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                tiles[x][y] = new Tile(x,y);
            }
        }

		walls = new Walls(0, 0, 20, 20);

		tickable = new ArrayList<>();
        gizmos = new ArrayList<>();
		collidable = new ArrayList<>();
		collidable.add(walls);

	}

	public Tile[][] getTiles(){
        return tiles;
    }

	public void rotateGizmo(String name){
        Gizmo gizmo = searchForGizmo(name);
        gizmo.rotate();
    }

	public Gizmo searchForGizmo(String name){
        for(int i = 0; i < gizmos.size(); i++){
            if(gizmos.get(i).getName().equals(name)){
                return gizmos.get(i);
            }
        }
        return null;
    }

    public boolean checkName(String name){
        for(int i = 0; i < gizmos.size(); i++){
          //  System.out.println(gizmos.size());
            if(gizmos.get(i).getName().equals(name)){
                return true;
            }
        }
        return false;
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

        //        list.add(ball);
        return list;
    }

    public Gizmo placeGizmo(GizmoType gizmoType, String name, Tile tile){
        Gizmo gizmo = null;

		switch(gizmoType){
			case LEFT_FLIPPER:
				gizmo = new Flipper(null, name, true);
				tile.placeGizmo(gizmo);
				tickable.add((Flipper) gizmo);
				collidable.add(gizmo);
				gizmos.add(gizmo);
				break;
			case RIGHT_FLIPPER:
				gizmo = new Flipper(null, name, false);
				tile.placeGizmo(gizmo);
				tickable.add((Flipper) gizmo);
				collidable.add(gizmo);
                gizmos.add(gizmo);
				break;
			case BALL:
				gizmo = new Ball(this, Color.black, name, tile.getX(), tile.getY(), 3, 4);
				ball = (Ball) gizmo;
				tickable.add((Ball) gizmo);
                gizmos.add(gizmo);
				break;
			case CIRCLE_BUMPER:
				gizmo = addBumper(GizmoType.CIRCLE_BUMPER, name, tile);
                gizmos.add(gizmo);
				break;
			case SQUARE_BUMPER:
				gizmo = addBumper(GizmoType.SQUARE_BUMPER,name, tile);
                gizmos.add(gizmo);
				break;
			case TRIANGLE_BUMPER:
				gizmo = addBumper(GizmoType.TRIANGLE_BUMPER,name, tile);
                gizmos.add(gizmo);
				break;
		}

        // Notify observers ... redraw updated view
        this.setChanged();
        this.notifyObservers();

        return gizmo;

    }

    public Gizmo addAbsorber(String name, double x1, double y1, double x2, double y2){
        Absorber absorber = new Absorber(Color.BLACK, name, x1, y1, x2, y2);
        tickable.add(absorber);
        collidable.add(absorber);
        gizmos.add(absorber);
        Tile t = getTileAt(x1, y1);
        t.placeGizmo(absorber);
        return absorber;
    }

	private Gizmo addBumper(GizmoType gt, String name, Tile t) {
		Bumper bumper = new Bumper(Color.black, name, gt);
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
