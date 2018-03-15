package model;

import model.gizmo.*;
import model.util.DualKeyMap;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class Model extends Observable implements IModel {


	private final int width = 20;
    private final int height = 20;

	private double[] frictionConstants = { 0.025, 0.025 };
	private double gravityConstant = 25;

    private Tile[][] tiles;

	private Ball ball;

    private DualKeyMap<Integer, TriggerType, Set<KeyEventTriggerable>> keyEventTriggerMap;

	private ArrayList<Tickable> tickable;
    private ArrayList<Collidable> collidable;
    private ArrayList<Drawable> drawables;
    private ArrayList<Gizmo> gizmos;

    public Model() {

        tiles = new Tile[width][height];

	    for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                tiles[x][y] = new Tile(this, x,y);
            }
        }

		Walls walls = new Walls(0, 0, 20, 20);

        keyEventTriggerMap = new DualKeyMap<>();

		tickable = new ArrayList<>();
		drawables = new ArrayList<>();
        gizmos = new ArrayList<>();

		collidable = new ArrayList<>();
		collidable.add(walls);

	}

	public String getBallName(){
    	return ball.getProperty(GizmoPropertyType.NAME);
	}

	public String toString(){
        String game = "";
        for (Gizmo gizmo: gizmos) {
            game = game + gizmo.toString() + "\n";
            if(!(gizmo.getType() == GizmoType.BALL)) {
                if(!(gizmo.getType() == GizmoType.ABSORBER)) {
                    double rotation = Double.parseDouble(gizmo.getProperty(GizmoPropertyType.ROTATION_DEG));
                    rotation = rotation / 90;
                    for (int i = 0; i < rotation; i++) {
                        game = game + "Rotate" + " " + gizmo.getProperty(GizmoPropertyType.NAME) + "\n";
                    }
                }
            }
        }
        if(gizmos.size() != 0){
            for (Gizmo gizmo: gizmos) {
                game = game + gizmo.toString() + "\n";
            }
        }
        game = game + "Gravity " + gravityConstant + "\n";
        game = game + "Friction " + Arrays.toString(frictionConstants) + "\n";
        //need to add rotation
        return game;
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

	boolean checkName(String name){
    	if(name.equals("OuterWalls")){
    	    return false;
        }
		for (Gizmo gizmo : gizmos) {
			if (gizmo.getProperty(GizmoPropertyType.NAME).equals(name)) {
				return true;
			}
		}
        return false;
    }

    public void keyEventTriggered(int keyCode, TriggerType trigger) {

		if(keyEventTriggerMap.containsKey(keyCode, trigger)){
			Set<KeyEventTriggerable> set = keyEventTriggerMap.get(keyCode, trigger);
			for(KeyEventTriggerable triggerable : set) {
				switch (trigger) {
					case KEY_DOWN:
						triggerable.keyDown();
						break;
					case KEY_UP:
						triggerable.keyUp();
						break;
				}
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


	//==============================
	//		Validation Methods
	//==============================

	private void validateTileCoordiantes(double coordX, double coordY) throws TileCoordinatesNotValid {
		if(coordX < 0 || coordX >= width || coordY < 0 || coordY >= height)
			throw new TileCoordinatesNotValid("Invalid coordinates: (" + coordX + "_" + coordY + ").");
	}

	private void validateGizmoPlacement(Gizmo gizmo, Tile tile) throws GizmoPlacementNotValidException {

		//Get a list of all occupied tiles
		ArrayList<Tile> occupiedTiles = new ArrayList<>();
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				if(tiles[x][y].isOccupied())
					occupiedTiles.add(tiles[x][y]);
			}
		}

		//Get a list of all the tiles this gizmo will occupy
		ArrayList<Tile> tilesGizmoWillAnnex = new ArrayList<>();
		tilesGizmoWillAnnex.add(tile);
		Collections.addAll(tilesGizmoWillAnnex, gizmo.findAnnexedTiles(tile));

		//If an occupied tile is a tile this gizmo will annex, throw exception
		for(Tile t : occupiedTiles){
			if (tilesGizmoWillAnnex.contains(t))
				throw new GizmoPlacementNotValidException("Gizmo cannot be built at " + tile + " since "
						+ t + " is occupied");
		}

	}

	private void validateFrictionValues(double[] arr) throws ModelPropertyException {
		if(arr[0] < 0 || arr[1] < 0)
			throw new ModelPropertyException("Friction values cannot be set to " + Arrays.toString(arr));
	}

	private void validateGravityValue(double val) throws ModelPropertyException {
		//Any gravity is fine...
	}

	private void validateColorString(String str){

		if (str.length() >= 13 && str.length() <= 19) {
			return;
		}

		//Check regex pattern here

		throw new IllegalArgumentException("Given color string is invalid: " + str);
	}


	//==============================
	//	  IModel Implementation
	//==============================


	public void addObserver(Observer o) {
		super.addObserver(o);
	}

	public Tile getTileAt(int tileCoordX, int tileCoordY) throws TileCoordinatesNotValid {
		validateTileCoordiantes((double) tileCoordX, (double)tileCoordY);
		return tiles[tileCoordX][tileCoordY];
	}

	public Tile getTileNear(double coordX, double coordY) throws TileCoordinatesNotValid{
		validateTileCoordiantes(coordX, coordY);
		return getTileAt((int)coordX, (int)coordY);
	}

	public Gizmo placeGizmo(GizmoType gizmoType, Tile tile, String[] propertyValues) throws GizmoPlacementNotValidException {
		//If propertyValues is null, set them to the default values
		if(propertyValues == null){
			propertyValues = Gizmo.getPropertyDefaults(gizmoType, getAllGizmoNames());
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

		if(properties.containsKey(GizmoPropertyType.CURRENT_COLOUR)){
			validateColorString(properties.get(GizmoPropertyType.CURRENT_COLOUR));
		}
		if(properties.containsKey(GizmoPropertyType.DEFAULT_COLOUR)){
			validateColorString(properties.get(GizmoPropertyType.DEFAULT_COLOUR));
		}
		if(properties.containsKey(GizmoPropertyType.ALT_COLOUR)){
			validateColorString(properties.get(GizmoPropertyType.ALT_COLOUR));
		}

		Gizmo gizmo = null;
		switch(gizmoType){
			case FLIPPER:
				gizmo = new Flipper(null, properties);
				validateGizmoPlacement(gizmo, tile);
				tile.placeGizmo(gizmo);
				tickable.add((Flipper) gizmo);
				collidable.add(gizmo);
				break;
			case BALL:
				gizmo = new Ball(this, Color.black, tile.getX(), tile.getY(), properties);
				validateGizmoPlacement(gizmo, tile);
				ball = (Ball) gizmo;
				tickable.add((Ball) gizmo);
				break;
			case ABSORBER:
				gizmo = new Absorber(Color.BLACK, properties);
				validateGizmoPlacement(gizmo, tile);
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

	public void deleteGizmo(String gizmoName) throws GizmoNotFoundException{
		Gizmo gizmo = getGizmoByName(gizmoName);
		deleteGizmo(gizmo);
	}

	public void moveGizmo(String gizmoName, Tile newTile) throws GizmoNotFoundException, GizmoPlacementNotValidException{
		Gizmo gizmo = getGizmoByName(gizmoName);
		validateGizmoPlacement(gizmo, newTile);

		if (gizmo.isTilePlacable()) {

			try {
				Tile oldTile = getTileAt((int) gizmo.getPosition()[0], (int) gizmo.getPosition()[1]);
				oldTile.removeGizmo();
			} catch (TileCoordinatesNotValid e) {
				//This should never happen
				e.printStackTrace();
			}

			newTile.placeGizmo(gizmo);
			gizmo.setAnchorTile(newTile);

		} else {
			((TileIndependentGizmo) gizmo).moveTo(newTile.getX(), newTile.getY());
		}

        this.setChanged();
        this.notifyObservers();
	}

	public void rotateGizmoBy_Deg(String gizmoName, double adjustment) throws GizmoNotFoundException, GizmoPropertyException {
		Gizmo gizmo = getGizmoByName(gizmoName);
		gizmo.rotateBy_Deg(adjustment);
        this.setChanged();
        this.notifyObservers();
	}

	public void rotateGizmoTo_Deg(String gizmoName, double rotationVal) throws GizmoNotFoundException, GizmoPropertyException{
		Gizmo gizmo = getGizmoByName(gizmoName);
		gizmo.rotateTo_Deg(rotationVal);
        this.setChanged();
        this.notifyObservers();
	}

	public String getGizmoProperty(String gizmoName, GizmoPropertyType type) throws GizmoNotFoundException {
		Gizmo gizmo = getGizmoByName(gizmoName);
		return gizmo.getProperty(type);
	}

	public void setGizmoProperty(String gizmoName, GizmoPropertyType prop, String val) throws GizmoNotFoundException, GizmoPropertyException {
		Gizmo gizmo = getGizmoByName(gizmoName);
		gizmo.setProperty(prop, val);
        this.setChanged();
        this.notifyObservers();
	}

    @Override
    public void setAllProperties(String gizmoName, HashMap<GizmoPropertyType, String> properties) throws GizmoNotFoundException {
        Gizmo g = getGizmoByName(gizmoName);

        properties.forEach((p, v) -> {
            try {
                g.setProperty(p,v);
            } catch (GizmoPropertyException e) {
                e.printStackTrace();
            }
        });

        this.setChanged();
        this.notifyObservers();
    }

    public Gizmo loadBall(Double x, Double y, String[] propertyValues) throws GizmoPlacementNotValidException, TileCoordinatesNotValid {

    	Tile tile = getTileAt(x.intValue(), y.intValue());

        //If propertyValues is null, set them to the default values
        if(propertyValues == null){
            propertyValues = Gizmo.getPropertyDefaults(GizmoType.BALL, null);
        }

        //Ensure propertyValues's size matches the number of properties this gizmo has.
        ArrayList<GizmoPropertyType> propertyTypes = GizmoType.BALL.getPropertyTypes();
        assert propertyTypes.size() == propertyValues.length :
                "Length of propertyValues array (" + propertyValues.length + ") " +
                        "does not equal the number of " + GizmoType.BALL + "'s properties (" + propertyTypes.size() + ").";

        //Create a property to value map
        Map<GizmoPropertyType, String> properties = new HashMap<>();
        for(int i = 0; i < propertyTypes.size(); i++){
            properties.put(propertyTypes.get(i), propertyValues[i]);
        }

        Gizmo gizmo = new Ball(this, Color.black, x, y, properties);

        validateGizmoPlacement(gizmo, tile);

        ball = (Ball) gizmo;
        tickable.add((Ball) gizmo);
        gizmos.add(gizmo);
        drawables.add(gizmo);

        // Notify observers ... redraw updated view
        this.setChanged();
        this.notifyObservers();

        return gizmo;
    }

	private Gizmo addBumper(GizmoType gizmoType, Tile tile, Map<GizmoPropertyType, String> properties) throws GizmoPlacementNotValidException {
		Bumper bumper = new Bumper(Color.black, gizmoType, properties);
		validateGizmoPlacement(bumper, tile);
		collidable.add(bumper);
		tile.placeGizmo(bumper);
		return bumper;
	}

    public void setGizmoAction(String gizmoName, GizmoActionType actionType) throws GizmoNotFoundException {
		Gizmo gizmo = getGizmoByName(gizmoName);
		gizmo.setAction(actionType);
	}


	public void connect(String triggerName, String actorName) throws GizmoNotFoundException{
		Triggerable trigger = getGizmoByName(triggerName);
		Triggerable actor = getGizmoByName(actorName);

		trigger.addActor(actor);
	}

	public void connect(int keyCode, TriggerType type, String actorName) throws GizmoNotFoundException {
		KeyEventTriggerable actor = getGizmoByName(actorName);

		if(keyEventTriggerMap.containsKey(keyCode, type)){
			keyEventTriggerMap.get(keyCode, type).add(actor);
		} else {
			Set<KeyEventTriggerable> set = new HashSet<>();
			set.add(actor);
			keyEventTriggerMap.put(keyCode, type, set);
		}
	}

	public void disconnect(String triggerName, String actorName) throws GizmoNotFoundException {
		Triggerable trigger = getGizmoByName(triggerName);
		Triggerable actor = getGizmoByName(actorName);

		trigger.removeActor(actor);
	}

	public void disconnectAll(String triggerName) throws GizmoNotFoundException {
		Triggerable trigger = getGizmoByName(triggerName);

		trigger.removeAllActors();
	}

	public void disconnect(int keyCode, TriggerType type, String actorName) throws GizmoNotFoundException {
		Triggerable actor = getGizmoByName(actorName);

		if(keyEventTriggerMap.containsKey(keyCode, type)){
			keyEventTriggerMap.get(keyCode, type).remove(actor);
		}
	}

	public void disconnectAll(int keyCode, TriggerType type) throws GizmoNotFoundException{
		if(keyEventTriggerMap.containsKey(keyCode, type)){
			keyEventTriggerMap.remove(keyCode, type);
		}
	}

	public double[] getFrictionConstants(){
		return frictionConstants;
	}

	public double getGravityConstant() {
		return gravityConstant;
	}

	public void setFrictionConstants(double[] arr) throws ModelPropertyException {
		validateFrictionValues(arr);
		frictionConstants = arr;
	}

	public void setGravityConstant(double val) throws ModelPropertyException {
		validateGravityValue(val);
		this.gravityConstant = val;
	}

	public ArrayList<Drawable> getDrawables(){
    	return drawables;
	}

	public ArrayList<Drawable> getDebugDrawables() {
		ArrayList<Drawable> drawables = new ArrayList<>();
		for(Collidable col : collidable){
			drawables.add(col.getGameObject());
		}
		if(ball != null){
			drawables.add(ball.getGameObject());
		}
		return drawables;
	}


	//Helpers:

	private void deleteGizmo(Gizmo gizmo){
		try {
			Tile tile = getTileAt((int) gizmo.getPosition()[0], (int) gizmo.getPosition()[1]);
			tile.removeGizmo();
		} catch (TileCoordinatesNotValid e) {
			//This should never happen
			e.printStackTrace();
		}

		tickable.remove(gizmo);
		collidable.remove(gizmo);
		drawables.remove(gizmo);
		gizmos.remove(gizmo);
        this.setChanged();
        this.notifyObservers();
	}

	private List<String> getAllGizmoNames(){
		List<String> names = new ArrayList<>(gizmos.size());
		for(Gizmo g : gizmos){
			names.add(g.getProperty(GizmoPropertyType.NAME));
		}
		return names;
	}
}
