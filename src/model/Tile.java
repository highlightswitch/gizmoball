package model;

import model.gizmo.Gizmo;
import model.gizmo.GizmoType;

public class Tile {

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

    public int[] getPosition() {
        return new int[]{x, y};
    }

    void placeGizmo(Gizmo gizmo){
        this.gizmo = gizmo;
        this.gizmo.setTile(this);
    }

    public void removeGizmo(){
        this.gizmo = null;
    }

    public Gizmo getGizmo() {
        return gizmo;
    }

}
