package model.gizmo;

import model.GameObject;

import java.awt.*;

public abstract class Gizmo implements GizmoEventListener  {

    private Color colour;

    Gizmo(Color colour){

        this.colour = colour;
    }

    public Color getColour() {
        return colour;
    }

    public abstract GameObject getPrototypeGameObject();

}
