package model;

import model.gizmo.*;

import java.util.ArrayList;

public interface IModel {

	Tile getTileAt(int tileCoordX, int tileCoordY);
	/**
	 * This returns the tile under the given coordinates. eg tile (3,5) is under the coords (3.25, 5.89).
	 * This can be used for learning where the mouse was clicked.
	 * @param coordX The x coordinate
	 * @param coordY The y coordinate
	 * @return The tile at coordinates (x,y)
	 */
	Tile getTileNear(double coordX, double coordY);

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

	double getFrictionConstant();
	double getGravityConstant();
	void setFrictionConstant(double val) throws ModelPropertyException;
	void setGravityConstant(double val) throws ModelPropertyException;

	ArrayList<Drawable> getDrawables();
}