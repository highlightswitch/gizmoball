package model;

import model.gizmo.Gizmo;
import model.gizmo.GizmoType;

import java.util.ArrayList;

public class Tile implements Drawable {

    private int x, y;
    private Gizmo gizmo;

    Tile(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    void placeGizmo(Gizmo gizmo){
        this.gizmo = gizmo;
        this.gizmo.setTile(this);
    }

    public Gizmo getGizmo() {
        return gizmo;
    }

    public int[] getPosition() {
        return new int[]{x, y};
    }

    public DrawableData getDrawableData(){
        if(gizmo != null) {
            return gizmo.getDrawableData();
        }
        return null;
    }

    public GizmoType getTypeOfGizmo() {
        if(gizmo != null) {
            GizmoType type = gizmo.getGizmoType();
            return type;
        }

        return null ;
    }
}
