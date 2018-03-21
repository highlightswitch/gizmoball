package model;

import model.gizmo.*;
import model.util.GizmoUtils;
import model.util.ManyToManyMap;

import java.util.*;

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

    private ManyToManyMap<KeyTriggerPair, Gizmo> keyEventTriggerMap;

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

        keyEventTriggerMap = new ManyToManyMap<>();

		tickable = new ArrayList<>();
		drawables = new ArrayList<>();
        gizmos = new ArrayList<>();

		collidable = new ArrayList<>();
		collidable.add(walls);

	}

	Tile[][] getTiles(){
    	return tiles;
	}

	public String toString(){

        StringBuilder game = new StringBuilder();

        for (Gizmo gizmo: gizmos) {

        	String gizmoName = gizmo.getProperty(GizmoPropertyType.NAME);

        	//Gizmo creation
            game.append(gizmo.toString()).append("\n");

            //Gizmo rotation
            if( gizmo.getType() != GizmoType.BALL && gizmo.getType() != GizmoType.ABSORBER) {
            	double rotation = Double.parseDouble(gizmo.getProperty(GizmoPropertyType.ROTATION_DEG));
				rotation = rotation / 90;
				for (int i = 0; i < rotation; i++) {
					game.append("Rotate").append(" ")
							.append(gizmoName).append("\n");
				}
            }

            //Gizmo action
			game.append("Action").append(" ")
					.append(gizmoName).append(" ")
					.append(gizmo.getActionType()).append("\n");

			//Gizmo connections
			for(Gizmo g : gizmo.getConnections()){
				game.append("Connect").append(" ")
						.append(gizmo.getProperty(GizmoPropertyType.NAME)).append(" ")
						.append(g.getProperty(GizmoPropertyType.NAME)).append("\n");
			}

			//Gizmo colour
			int[] c = GizmoUtils.colourStringParser(gizmo.getProperty(GizmoPropertyType.CURRENT_COLOUR));
			int[] d = GizmoUtils.colourStringParser(gizmo.getProperty(GizmoPropertyType.DEFAULT_COLOUR));
			int[] a = GizmoUtils.colourStringParser(gizmo.getProperty(GizmoPropertyType.ALT_COLOUR));
			game.append("Colour").append(" ")
					.append(gizmo.getProperty(GizmoPropertyType.NAME)).append(" ")
					.append(c[0]).append(" ").append(c[1]).append(" ").append(c[2]).append(" ")
					.append(d[0]).append(" ").append(d[1]).append(" ").append(d[2]).append(" ")
					.append(a[0]).append(" ").append(a[1]).append(" ").append(a[2]).append("\n");

        }

        //Key connections
		//TODO: make less hacky :)
		Set<Gizmo> values;
        KeyTriggerPair pair;
        for(int i = 0; i < 223; i++){
        	pair = new KeyTriggerPair(i, TriggerType.KEY_DOWN);
        	if(keyEventTriggerMap.containsK(pair)) {
				values = keyEventTriggerMap.getV(pair);
				for(Gizmo g : values){
        			game.append("KeyConnect").append(" ").append("key").append(" ").append(i).append(" ")
							.append("down").append(" ").append(g.getProperty(GizmoPropertyType.NAME)).append("\n");
				}
        	}
        	pair = new KeyTriggerPair(i, TriggerType.KEY_UP);
			if(keyEventTriggerMap.containsK(pair)) {
				values = keyEventTriggerMap.getV(pair);
				for(Gizmo g : values){
					game.append("KeyConnect").append(" ").append("key").append(" ").append(i).append(" ")
							.append("up").append(" ").append(g.getProperty(GizmoPropertyType.NAME)).append("\n");
				}
			}
		}

        //Gravity and friction
        game.append("Gravity ").append(gravityConstant).append("\n");
        game.append("Friction ").append(frictionConstants[0]).append(" ").append(frictionConstants[1]).append("\n");
        return game.toString();
        
    }

	public ArrayList<Collidable> getCollidable() {
		return collidable;
	}

	public Ball getBall() {
		return ball;
	}

	Gizmo getGizmoByName(String name) throws GizmoNotFoundException {
		for (Gizmo gizmo : gizmos) {
			if (gizmo.getProperty(GizmoPropertyType.NAME).equals(name)) {
				return gizmo;
			}
		}
		throw new GizmoNotFoundException("Cannot find gizmo with name: " + name);
    }

	public boolean checkName(String name){
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

		KeyTriggerPair pair = new KeyTriggerPair(keyCode, trigger);
		Set<Gizmo> connectedGizmos = keyEventTriggerMap.getV(pair);

		if(connectedGizmos != null) {
			for (Gizmo gizmo : connectedGizmos) {
				switch (trigger) {
					case KEY_DOWN:
						gizmo.keyDown();
						break;
					case KEY_UP:
						gizmo.keyUp();
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

	private void validateGizmoPlacement(Gizmo gizmo, Tile tile) throws GizmoPlacementNotValidException, TileCoordinatesNotValid {

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

	public Gizmo placeGizmo(GizmoType gizmoType, Tile tile, String[] propertyValues) throws GizmoPlacementNotValidException, TileCoordinatesNotValid {

		Map<GizmoPropertyType, String> properties = getCorrectPropertiesMap(gizmoType, propertyValues);

		Gizmo gizmo = null;
		switch(gizmoType){
			case FLIPPER:
				gizmo = new Flipper(properties);
				validateGizmoPlacement(gizmo, tile);
				tile.placeGizmo(gizmo);
				tickable.add((Flipper) gizmo);
				collidable.add(gizmo);
				gizmos.add(gizmo);
				drawables.add(gizmo);
				this.connect(Boolean.valueOf(properties.get(GizmoPropertyType.IS_LEFT_ORIENTATED)) ? 70 : 71, TriggerType.KEY_DOWN, gizmo); //Default key trigger for left F, right G
				this.connect(Boolean.valueOf(properties.get(GizmoPropertyType.IS_LEFT_ORIENTATED)) ? 70 : 71, TriggerType.KEY_UP, gizmo); //Default key trigger for left F, right G
				break;
			case BALL:
				if(ball == null)
					gizmo = placeBall(tile.getX() + 0.5, tile.getY() + 0.5, propertyValues);
				else
					throw new GizmoPlacementNotValidException("Cannot place multiple balls");
				break;
			case ABSORBER:
				gizmo = new Absorber(properties);
				validateGizmoPlacement(gizmo, tile);
				collidable.add(gizmo);
				tile.placeGizmo(gizmo);
				gizmos.add(gizmo);
				drawables.add(gizmo);
				this.connect(32, TriggerType.KEY_DOWN, gizmo); //Default key trigger is space bar
				break;
			case CIRCLE_BUMPER:
				gizmo = addBumper(GizmoType.CIRCLE_BUMPER, tile, properties);
				gizmos.add(gizmo);
				drawables.add(gizmo);
				break;
			case SQUARE_BUMPER:
				gizmo = addBumper(GizmoType.SQUARE_BUMPER, tile, properties);
				gizmos.add(gizmo);
				drawables.add(gizmo);
				break;
			case TRIANGLE_BUMPER:
				gizmo = addBumper(GizmoType.TRIANGLE_BUMPER, tile, properties);
				gizmos.add(gizmo);
				drawables.add(gizmo);
				break;
		}

		// Notify observers ... redraw updated view
		this.setChanged();
		this.notifyObservers();

		return gizmo;

	}

	public void deleteGizmo(String gizmoName) throws GizmoNotFoundException, TileCoordinatesNotValid {
		Gizmo gizmo = getGizmoByName(gizmoName);
		deleteGizmo(gizmo);
	}

	public void moveGizmo(String gizmoName, Tile newTile) throws GizmoNotFoundException, GizmoPlacementNotValidException, TileCoordinatesNotValid {
		Gizmo gizmo = getGizmoByName(gizmoName);
		validateGizmoPlacement(gizmo, newTile);

		if(gizmo.getType() == GizmoType.BALL){
			moveBall(gizmoName, newTile.getX() + 0.5f, newTile.getY()+ 0.5f);
		} else if (gizmo.isTilePlacable()) {

		    Tile oldTile = getTileAt((int) gizmo.getPosition()[0], (int) gizmo.getPosition()[1]);
		    oldTile.removeGizmo();

			newTile.placeGizmo(gizmo);
			gizmo.setAnchorTile(newTile);

		} else {
			((TileIndependentGizmo) gizmo).moveTo(newTile.getX(), newTile.getY());
		}

        this.setChanged();
        this.notifyObservers();
	}

    public void moveBall(String name, float x, float y)  throws GizmoNotFoundException, GizmoPlacementNotValidException, TileCoordinatesNotValid {
        Ball ball = (Ball) getGizmoByName(name);

        Set<Tile> oldTiles = getBallTiles(ball);
        for(Tile tile : oldTiles){
        	tile.setIsOccupiedByBall(null);
		}

		Tile newTile = getTileNear(x, y);
		validateGizmoPlacement(ball, newTile);
		ball.setCx(x);
		ball.setCy(y);

		Set<Tile> newTiles = getBallTiles(ball);
		for(Tile tile : newTiles){
			tile.setIsOccupiedByBall(ball);
		}
    }

	public void rotateGizmoBy_Deg(String gizmoName, double adjustment) throws GizmoNotFoundException, GizmoPropertyException, GizmoNotRotatableException {
		Gizmo gizmo = getGizmoByName(gizmoName);
		gizmo.rotateBy_Deg(adjustment);
        this.setChanged();
        this.notifyObservers();
	}

	public void rotateGizmoTo_Deg(String gizmoName, double rotationVal) throws GizmoNotFoundException, GizmoPropertyException, GizmoNotRotatableException {
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

	public Gizmo placeBall(double cx, double cy, String[] propertyValues) throws GizmoPlacementNotValidException, TileCoordinatesNotValid {

		Map<GizmoPropertyType, String> properties = getCorrectPropertiesMap(GizmoType.BALL, propertyValues);
        Ball ball= new Ball(this, cx, cy, properties);
		Set<Tile> ballTiles;
        try{
			ballTiles = getBallTiles(ball);
		} catch (TileCoordinatesNotValid tileCoordinatesNotValid) {
			throw new GizmoPlacementNotValidException("Ball is placed in position that is partially or fully off the board");
		}

        for(Tile t : ballTiles) {
			validateGizmoPlacement(ball, t);
		}

        this.ball = ball;
        tickable.add(ball);
        gizmos.add(ball);

        //Set tiles using this ball is placed in to be occupied
		for(Tile t : ballTiles){
			t.setIsOccupiedByBall(ball);
		}

        // Notify observers ... redraw updated view
        this.setChanged();
        this.notifyObservers();

        return ball;
    }

	private Gizmo addBumper(GizmoType gizmoType, Tile tile, Map<GizmoPropertyType, String> properties) throws GizmoPlacementNotValidException, TileCoordinatesNotValid {
		Bumper bumper = new Bumper(gizmoType, properties);
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
		Gizmo trigger = getGizmoByName(triggerName);
		Gizmo actor = getGizmoByName(actorName);

		trigger.addActor(actor);
	}

	public void connect(int keyCode, TriggerType type, String actorName) throws GizmoNotFoundException {
		Gizmo actor = getGizmoByName(actorName);
		keyEventTriggerMap.put(new KeyTriggerPair(keyCode, type), actor);
	}

	public void disconnect(String triggerName, String actorName) throws GizmoNotFoundException {
		Gizmo trigger = getGizmoByName(triggerName);
		Gizmo actor = getGizmoByName(actorName);

		trigger.removeActor(actor);
	}

	public void disconnectAll(String triggerName) throws GizmoNotFoundException {
		Gizmo trigger = getGizmoByName(triggerName);

		trigger.removeAllActors();
	}

	public Gizmo[] getAllConnectedGizmos(String actorName) throws GizmoNotFoundException{
        Gizmo trigger = getGizmoByName(actorName);
//        //Triggerable[] t =
//        //keyEventTriggerMap.get(triggerName)
//        for(Gizmo g : trigger.getAllActors()){
//          //  t.getActionType()  want all connections names
//        }
	    return trigger.getAllActors();
    }

    public String[][] getAllConnectedKeys(String actorName) throws GizmoNotFoundException {
		Gizmo actor = getGizmoByName(actorName);
		Set<KeyTriggerPair> set = keyEventTriggerMap.getK(actor);
		ArrayList<String[]> list = new ArrayList<>();
        if(set != null && set.size() > 0){
            for(KeyTriggerPair pair : set){
                String type = pair.triggerType == TriggerType.KEY_UP ? "Up" : "Down";
                list.add(new String[]{String.valueOf(pair.keyCode), type});
            }
            String[][] arr = new String[list.size()][2];
            for(int i = 0; i < list.size(); i++){
                arr[i][0] = list.get(i)[0];
                arr[i][1] = list.get(i)[1];
            }
            return arr;
        } else {
            return new String[][]{};
        }

	}

	//Stop key being connected to this gizmo and gizmo being connected to this key
	public void disconnect(int keyCode, TriggerType type, String actorName) throws GizmoNotFoundException {
		Gizmo actor = getGizmoByName(actorName);
		KeyTriggerPair pair = new KeyTriggerPair(keyCode, type);

		if(keyEventTriggerMap.containsK(pair)){
			keyEventTriggerMap.remove(pair, actor);
		}
	}

	//Stop key being connected to any gizmo
	public void disconnectAll(int keyCode, TriggerType type){
		KeyTriggerPair pair = new KeyTriggerPair(keyCode, type);

		if(keyEventTriggerMap.containsK(pair)){
			keyEventTriggerMap.removeAllV(pair);
		}
	}

	public void disconnectAllTriggers(){
		keyEventTriggerMap.clear();
		for(Gizmo gizmo : gizmos){
			gizmo.removeAllActors();
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

	public void setGravityConstant(double val) {
		this.gravityConstant = val;
	}

	public ArrayList<Drawable> getDrawables(){
		//This is done so that the ball is always drawn last
		ArrayList<Drawable> newList = new ArrayList<>(drawables);
    	if(ball != null)
    		newList.add(ball);
		return newList;
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

	private void deleteGizmo(Gizmo gizmo) throws TileCoordinatesNotValid {
        Tile tile = getTileAt((int) gizmo.getPosition()[0], (int) gizmo.getPosition()[1]);

        if(!(gizmo.getType() == GizmoType.BALL)){
            tile.removeGizmo();
        } else {
			for(Tile t : getBallTiles(ball)){
				t.setIsOccupiedByBall(null);
			}
            ball = null;
        }

        for(Gizmo g : gizmos){
			try {
				this.disconnect(g.getProperty(GizmoPropertyType.NAME), gizmo.getProperty(GizmoPropertyType.NAME));
			} catch (GizmoNotFoundException e) {
				//This should never happen
			}
		}

		tickable.remove(gizmo);
		collidable.remove(gizmo);
		drawables.remove(gizmo);
		gizmos.remove(gizmo);
        this.setChanged();
        this.notifyObservers();
	}

	public List<String> getAllGizmoNames(){
		List<String> names = new ArrayList<>(gizmos.size());
		for(Gizmo g : gizmos){
			names.add(g.getProperty(GizmoPropertyType.NAME));
		}
		return names;
	}

	private Set<Tile> getBallTiles(Ball ball) throws TileCoordinatesNotValid {
		Set<Tile> ballTiles = new HashSet<>();
		double[] ballCentre = ball.getCentre();
		double ballRadius = ball.getRadius();

		//The below gets compass points from the centre on the circumference and gets the tile that point is in.

			ballTiles.add(getTileNear(ballCentre[0] - ballRadius, ballCentre[1])); //E
			ballTiles.add(getTileNear(ballCentre[0] + ballRadius, ballCentre[1])); //W
			ballTiles.add(getTileNear(ballCentre[0], ballCentre[1] + ballRadius)); //S
			ballTiles.add(getTileNear(ballCentre[0], ballCentre[1] - ballRadius)); //N

			ballTiles.add(getTileNear(ballCentre[0] - ballRadius * 1/Math.sqrt(2),
					ballCentre[1] - ballRadius * 1/Math.sqrt(2))); //NE
			ballTiles.add(getTileNear(ballCentre[0] + ballRadius * 1/Math.sqrt(2),
					ballCentre[1] - ballRadius * 1/Math.sqrt(2))); //NW
			ballTiles.add(getTileNear(ballCentre[0] + ballRadius * 1/Math.sqrt(2),
					ballCentre[1] + ballRadius * 1/Math.sqrt(2))); //SW
			ballTiles.add(getTileNear(ballCentre[0] - ballRadius * 1/Math.sqrt(2),
					ballCentre[1] + ballRadius * 1/Math.sqrt(2))); //SE

			return ballTiles;
	}

	private Map<GizmoPropertyType, String> getCorrectPropertiesMap(GizmoType gizmoType, String[] propertyValues){

		//If propertyValues is null, set them to the default values
		if(propertyValues == null){
			propertyValues = GizmoUtils.getPropertyDefaults(gizmoType, getAllGizmoNames());
		}

		//TODO: ensure this works
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

		return properties;
	}

	private void connect(int keyCode, TriggerType type, Gizmo gizmo){
		try {
			this.connect(keyCode, type, gizmo.getProperty(GizmoPropertyType.NAME));
		} catch (GizmoNotFoundException ignored) {

		}
	}
}
