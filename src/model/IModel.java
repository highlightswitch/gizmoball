package model;

import model.gizmo.*;

import java.util.ArrayList;
import java.util.Observer;

public interface IModel {

	void addObserver(Observer o);

	Tile getTileAt(int tileCoordX, int tileCoordY) throws TileCoordinatesNotValid;
	Tile getTileNear(double coordX, double coordY) throws TileCoordinatesNotValid;

	Gizmo placeGizmo(GizmoType gt, Tile t, String[] propertyValues) throws GizmoPlacementNotValidException;
	void deleteGizmo(String gizmoName) throws GizmoNotFoundException;
	void moveGizmo(String gizmoName, Tile newTile) throws GizmoNotFoundException, GizmoPlacementNotValidException;
	void rotateGizmoBy_Deg(String gizmoName, double adjustment) throws GizmoNotFoundException, GizmoPropertyException;
	void rotateGizmoTo_Deg(String gizmoName, double rotationVal) throws GizmoNotFoundException, GizmoPropertyException;

	String getGizmoProperty(String gizmoName, GizmoPropertyType type) throws GizmoNotFoundException;
	void setGizmoProperty(String gizmoName, GizmoPropertyType prop, String val) throws GizmoNotFoundException, GizmoPropertyException;

	void setGizmoAction(String gizmoName, GizmoActionType actionType) throws GizmoNotFoundException;

	void connect(String triggerName, String actorName) throws GizmoNotFoundException;
	void connect(int keyCode, String actorName) throws GizmoNotFoundException;
	void disconnect(String triggerName, String actorName) throws GizmoNotFoundException;
	void disconnectAll(String triggerName) throws GizmoNotFoundException;
	void disconnect(int keyCode, String actorName) throws GizmoNotFoundException;
	void disconnectAll(int keyCode) throws GizmoNotFoundException;

	double[] getFrictionConstant();
	double getGravityConstant();
	void setFrictionConstants(double arr[]) throws ModelPropertyException;
	void setGravityConstant(double val) throws ModelPropertyException;

	ArrayList<Drawable> getDrawables();
	ArrayList<Drawable> getDebugDrawables();
}