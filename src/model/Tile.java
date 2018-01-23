package model;

import model.gizmo.Gizmo;

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

    Gizmo getGizmo() {
        return gizmo;
    }

    public int[] getPosition() {
        return new int[]{x, y};
    }

    @Override
    public GameObject getShapeToDraw() {
        if(gizmo != null) {
            GameObject obj = gizmo.getPrototypeGameObject();
            obj.translate(getPosition());
            return obj;
        }

        return null ;
    }

}
