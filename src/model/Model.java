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

	public static final double FRICTION_CONSTANT = 0.025;
	public static final double GRAVITY_CONSTANT = 25;

	private final int width = 20;
    private final int height = 20;

    private Tile[][] tiles;

	private Ball ball;
	private Walls walls;

    private Map<Integer, GizmoEventListener> keyEventTriggerMap =  new HashMap<>();

	private ArrayList<Tickable> tickable;
    private ArrayList<Collidable> collidable;
    private ArrayList<Drawable> drawables;
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
		drawables = new ArrayList<>();
        gizmos = new ArrayList<>();

		collidable = new ArrayList<>();
		collidable.add(walls);

	}

	public ArrayList<Drawable> getDrawables() {
        return drawables;
    }

	public ArrayList<Collidable> getCollidable() {
		return collidable;
	}

	public Ball getBall() {
		return ball;
	}

	public Tile[][] getTiles(){
        return tiles;
    }

	Gizmo getGizmoByName(String name) throws GizmoNotFoundException {
		for (Gizmo gizmo : gizmos) {
			if (gizmo.getProperty(GizmoPropertyType.NAME).equals(name)) {
				return gizmo;
			}
		}
		throw new GizmoNotFoundException("Cannot find gizmo with name: " + name);
    }

    public void deleteGizmo(String name) throws GizmoNotFoundException {
    	Gizmo gizmo = getGizmoByName(name);
        Tile tile = getTileAt(gizmo.getPosition()[0], gizmo.getPosition()[1]);
        tile.removeGizmo();
    	tickable.remove(gizmo);
    	collidable.remove(gizmo);
    	drawables.remove(gizmo);
    	gizmos.remove(gizmo);
	}

	public void moveGizmo(String name, int x, int y) throws GizmoNotFoundException {
		Gizmo gizmo = getGizmoByName(name);
		Tile tile = getTileAt(x, y);
		Tile oldTile = getTileAt(gizmo.getPosition()[0], gizmo.getPosition()[1]);
		oldTile.removeGizmo();
		tile.placeGizmo(gizmo);
		gizmo.setTile(tile);
	}

	boolean checkName(String name){
		for (Gizmo gizmo : gizmos) {
			if (gizmo.getProperty(GizmoPropertyType.NAME).equals(name)) {
				return true;
			}
		}
        return false;
    }

    public void setUpActionMap(Flipper l, Flipper r) {
        keyEventTriggerMap.put(70, l); //Key code 70 = F
        keyEventTriggerMap.put(71, r); //Key code 71 = G
    }

    public void setUpActionMap(Absorber absorber) {
        keyEventTriggerMap.put(32, absorber); //Key code 32 = space
    }

    public Tile getTileAt(int x, int y){
	    return tiles[x][y];
    }

    /**
     * This returns the tile under the pixel coordinates. eg tile (3,5) is under the coords (3.25, 5.89).
     * This can be used for learning where the mouse was clicked.
     * @param xPos The x coordinate
     * @param yPos The y coordinate
     * @return The tile at coordinates (x,y)
     */
    Tile getTileAt(double xPos, double yPos){
	    return getTileAt((int) xPos, (int) yPos);
    }

    public Gizmo placeGizmo(GizmoType gizmoType, Tile tile, String[] propertyValues){

    	//If propertyValues is null, set them to the default values
    	if(propertyValues == null){
    		propertyValues = Gizmo.getPropertyDefaults(gizmoType);
		}

    	//Ensure propertyValues's size matches the number of properties this gizmo has.
        ArrayList<GizmoPropertyType> propertyTypes = gizmoType.getPropertyTypes();
        assert propertyTypes.size() == propertyValues.length :
				"Length of propertyValues array (" + propertyValues.length + ") " +
						"does not equal the number of " + gizmoType + "'s properties (" + propertyTypes.size() + ").";

        //Create a property to value map
        Map<GizmoPropertyType, String> properties = new HashMap<>();
        for(int i = 0; i < propertyTypes.size(); i++){
        	properties.put(propertyTypes.get(i), propertyValues[i]);
		}

        Gizmo gizmo = null;
		switch(gizmoType){
			case FLIPPER:
				gizmo = new Flipper(null, properties);
				tile.placeGizmo(gizmo);
				tickable.add((Flipper) gizmo);
				collidable.add(gizmo);
				break;
			case BALL:
				gizmo = new Ball(this, Color.black, tile.getX(), tile.getY(), properties);
				ball = (Ball) gizmo;
				tickable.add((Ball) gizmo);
				break;
			case ABSORBER:
				gizmo = new Absorber(Color.BLACK, properties);
				collidable.add(gizmo);
				tile.placeGizmo(gizmo);
				break;
			case CIRCLE_BUMPER:
				gizmo = addBumper(GizmoType.CIRCLE_BUMPER, tile, properties);
				break;
			case SQUARE_BUMPER:
				gizmo = addBumper(GizmoType.SQUARE_BUMPER, tile, properties);
				break;
			case TRIANGLE_BUMPER:
				gizmo = addBumper(GizmoType.TRIANGLE_BUMPER, tile, properties);
				break;
		}

		gizmos.add(gizmo);
		drawables.add(gizmo);

        // Notify observers ... redraw updated view
        this.setChanged();
        this.notifyObservers();

        return gizmo;

    }

	private Gizmo addBumper(GizmoType gizmoType, Tile t, Map<GizmoPropertyType, String> properties) {
		Bumper bumper = new Bumper(Color.black, gizmoType, properties);
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

}
