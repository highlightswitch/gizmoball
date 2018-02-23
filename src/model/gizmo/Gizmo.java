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

    public DrawingData getDrawingData(){
        return this.getGizmoDrawingData().translate(getPosition());
    }

    public abstract void rotate();

    public abstract GameObject getPrototypeGameObject();
    public abstract GameObject getGameObject();

    protected abstract DrawingData getGizmoDrawingData();
}
