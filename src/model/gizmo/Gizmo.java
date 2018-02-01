package model.gizmo;

import model.Collidable;
import model.GameObject;
import model.Tile;

import java.awt.*;

public abstract class Gizmo implements GizmoEventListener, Collidable {

    private Color colour;
    protected Tile tile;

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
}
