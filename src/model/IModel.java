package model;

import model.gizmo.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observer;

public interface IModel {

	void addObserver(Observer o);

	Tile getTileAt(int tileCoordX, int tileCoordY) throws TileCoordinatesNotValid;
	Tile getTileNear(double coordX, double coordY) throws TileCoordinatesNotValid;

	Gizmo placeGizmo(GizmoType gt, Tile t, String[] propertyValues) throws GizmoPlacementNotValidException, TileCoordinatesNotValid;
	void deleteGizmo(String gizmoName) throws GizmoNotFoundException;
	void moveGizmo(String gizmoName, Tile newTile) throws GizmoNotFoundException, GizmoPlacementNotValidException, TileCoordinatesNotValid;
	void rotateGizmoBy_Deg(String gizmoName, double adjustment) throws GizmoNotFoundException, GizmoPropertyException, GizmoNotRotatableException;
	void rotateGizmoTo_Deg(String gizmoName, double rotationVal) throws GizmoNotFoundException, GizmoPropertyException, GizmoNotRotatableException;

	Gizmo placeBall(double cx, double cy, String[] propertyValues) throws GizmoPlacementNotValidException, TileCoordinatesNotValid;

	String getGizmoProperty(String gizmoName, GizmoPropertyType type) throws GizmoNotFoundException;
	void setGizmoProperty(String gizmoName, GizmoPropertyType prop, String val) throws GizmoNotFoundException, GizmoPropertyException;
	void setAllProperties(String gimoName, HashMap<GizmoPropertyType, String> properties) throws GizmoNotFoundException;

	void setGizmoAction(String gizmoName, GizmoActionType actionType) throws GizmoNotFoundException;

	void connect(String triggerName, String actorName) throws GizmoNotFoundException;
	void connect(int keyCode, TriggerType type, String actorName) throws GizmoNotFoundException;
	void disconnect(String triggerName, String actorName) throws GizmoNotFoundException;
	void disconnectAll(String triggerName) throws GizmoNotFoundException;
	void disconnect(int keyCode, TriggerType type, String actorName) throws GizmoNotFoundException;
	void disconnectAll(int keyCode, TriggerType type) throws GizmoNotFoundException;
	void disconnectAllTriggers();
    Gizmo[] getAllTriggers(String triggerName) throws GizmoNotFoundException;

	double[] getFrictionConstants();
	double getGravityConstant();
	void setFrictionConstants(double[] arr) throws ModelPropertyException;
	void setGravityConstant(double val) throws ModelPropertyException;

	ArrayList<Drawable> getDrawables();
	ArrayList<Drawable> getDebugDrawables();
}