package model.gizmo;

import main.Main;
import model.*;

import java.awt.*;

public abstract class Gizmo implements GizmoEventListener, Collidable, Drawable {

    private Color colour;
    private Tile tile;
    private String name;

    protected GizmoType type;

    Gizmo(Color colour){
        this.colour = colour;
    }

    public Color getColour() {
        return colour;
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

    public String getName(){ return name;}

    public DrawingData getDrawingData(){
        return this.getGizmoDrawingData().translate(getPosition());
    }

    public abstract void rotate();

    public abstract GameObject getPrototypeGameObject();
    public abstract GameObject getGameObject();

    protected abstract DrawingData getGizmoDrawingData();
}
