package model.gizmo;

import main.Main;
import model.*;

import java.awt.*;

public abstract class Gizmo implements GizmoEventListener, Collidable, Drawable {

    private Color colour;
    protected Tile tile;
    private String name;
    private GizmoType type;

    Gizmo(Color colour){

        this.colour = colour;
    }

    public Color getColour() {
        return colour;
    }

    public abstract GameObject getPrototypeGameObject();

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public abstract GameObject getGameObject();

    public String getName(){ return name;}

    public abstract void rotate();

    public GizmoType getGizmoType(){return type;}

    public double[] getPosition(){
        if(tile != null)
            return new double[] {tile.getPosition()[0], tile.getPosition()[1]};
        else
            return null;
    }

    public DrawingData getDrawingData(){
        return this.getGizmoDrawingData().translate(getPosition());
    }

    protected abstract DrawingData getGizmoDrawingData();
}
