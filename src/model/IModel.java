package model;

import model.gizmo.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observer;

public interface IModel {

	void addObserver(Observer o);

	// returns tile object with coord x and y
	Tile getTileAt(int tileCoordX, int tileCoordY) throws TileCoordinatesNotValid;

	// returns tile object with coords withing range of x and y - NOT TO BE USED TO TRANSLATE PIXELS TO TILES
	Tile getTileNear(double coordX, double coordY) throws TileCoordinatesNotValid;

	//Adds Gizmo to current model
	Gizmo placeGizmo(GizmoType gt, Tile t, String[] propertyValues) throws GizmoPlacementNotValidException, TileCoordinatesNotValid;

	//Removes Gizmo from current model
	void deleteGizmo(String gizmoName) throws GizmoNotFoundException, TileCoordinatesNotValid;

	//Changes Gizmo position, updates relevant tiles' occupied flag
	void moveGizmo(String gizmoName, Tile newTile) throws GizmoNotFoundException, GizmoPlacementNotValidException, TileCoordinatesNotValid;

	//Rotates Gizmo by a degrees
	void rotateGizmoBy_Deg(String gizmoName, double adjustment) throws GizmoNotFoundException, GizmoPropertyException, GizmoNotRotatableException;

	//Rotates Gizmo to a degress
	void rotateGizmoTo_Deg(String gizmoName, double rotationVal) throws GizmoNotFoundException, GizmoPropertyException, GizmoNotRotatableException;

	// Adds a Ball to the model
	Gizmo placeBall(double cx, double cy, String[] propertyValues) throws GizmoPlacementNotValidException, TileCoordinatesNotValid;

	// Returns value of property t
	String getGizmoProperty(String gizmoName, GizmoPropertyType type) throws GizmoNotFoundException;

	//Sets value of property t
	void setGizmoProperty(String gizmoName, GizmoPropertyType prop, String val) throws GizmoNotFoundException, GizmoPropertyException;

	//Replaces existing propety type, value mapping with HashMap h
	void setAllProperties(String gimoName, HashMap<GizmoPropertyType, String> properties) throws GizmoNotFoundException;

	//Specifies what Gizmo does when triggered
	void setGizmoAction(String gizmoName, GizmoActionType actionType) throws GizmoNotFoundException;

	//Connects the actions of two Gizmos
	void connect(String triggerName, String actorName) throws GizmoNotFoundException;

	//Binds a Gizmo to a Key
	void connect(int keyCode, TriggerType type, String actorName) throws GizmoNotFoundException;

	// Removes connection between two Gizmos
	void disconnect(String triggerName, String actorName) throws GizmoNotFoundException;

	// Removes all actions and connections associated with this Gizmo
	void disconnectAll(String triggerName) throws GizmoNotFoundException;

	//Removes Gizmo, Key binding
	void disconnect(int keyCode, TriggerType type, String actorName) throws GizmoNotFoundException;

	//Removes all binding between Gizmos and this key
	void disconnectAll(int keyCode, TriggerType type) throws GizmoNotFoundException;

	//Disconnects all actions and key bindings in the model
	void disconnectAllTriggers();

	//Returns all gizmos connected to current gizmo
    Gizmo[] getAllConnectedGizmos(String actorName) throws GizmoNotFoundException;

    //Returns all keys bound to current gizmo
    String[][] getAllConnectedKeys(String actorName) throws GizmoNotFoundException;

    //Returns friction values
	double[] getFrictionConstants();

	//Returns gravity value
	double getGravityConstant();

	//Updates model friction
	void setFrictionConstants(double[] arr) throws ModelPropertyException;

	//Updates gravity value
	void setGravityConstant(double val) throws ModelPropertyException;

	//Returns all objects which extend drawable for Board to render
	ArrayList<Drawable> getDrawables();
	ArrayList<Drawable> getDebugDrawables();
}