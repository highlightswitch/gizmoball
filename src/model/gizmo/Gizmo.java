package model.gizmo;

import main.Main;
import model.*;

import java.awt.*;
import java.util.HashMap;

public abstract class Gizmo implements GizmoEventListener, Collidable, Drawable {

    private Color colour;
    private Tile tile;

    protected GizmoType type;

    protected HashMap<GizmoPropertyType, String> properties;

    Gizmo(String name, Color colour){
        properties = new HashMap<>();
        properties.put(GizmoPropertyType.NAME, name);
        properties.put(GizmoPropertyType.ROTATION_DEG, "0");
        this.colour = colour;
    }

    public Color getColour() {
        return colour;
    }

    public String getProperty(GizmoPropertyType prop){
        return properties.getOrDefault(prop, null);
    }

    public void setProperty(GizmoPropertyType prop, String val) throws GizmoPropertyException {
        if(properties.containsKey(prop)){
            properties.put(prop, val);
        } else {
            throw new GizmoPropertyException("Attempted to set property that has not previously been set at compile time" +
                    "\n Property: " + prop +
                    "\n Value: " + val);
        }
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public double[] getPosition(){
        if(tile != null)
            return new double[] {tile.getPosition()[0], tile.getPosition()[1]};
        else
            return null;
    }

    public void rotate() throws GizmoPropertyException {
        double rotation = Double.valueOf(getProperty(GizmoPropertyType.ROTATION_DEG));
        rotation = (rotation + 90) % 360;
        rotateByDeg(rotation);
    }

    private void rotateByDeg(double val) throws GizmoPropertyException {
        setProperty(GizmoPropertyType.ROTATION_DEG, String.valueOf(val));
    }

    public DrawingData getDrawingData(){
        return this.getGizmoDrawingData().translate(getPosition());
    }


    public abstract GameObject getPrototypeGameObject();
    public abstract GameObject getGameObject();

    protected abstract DrawingData getGizmoDrawingData();
}
