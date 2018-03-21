package model;

import model.gizmo.Ball;
import model.gizmo.Gizmo;

public class Tile {

    private Model model;
    private int x, y;
    private Gizmo gizmo;
    private Ball ball;
    private boolean occupied;

    Tile(Model model, int x, int y){
        this.model = model;
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

    public Tile getNeighbour(int xOff, int yOff) throws TileCoordinatesNotValid {
        return model.getTileAt(x+xOff, y+yOff);
    }

    public boolean isOccupied(){
        return occupied;
    }

    public void setOccupiedBy(Gizmo gizmo){
        this.gizmo = gizmo;
        occupied = gizmo != null;
    }

    void setIsOccupiedByBall(Ball ball){
        if(ball != null) {
            this.ball = ball;
            occupied = true;
        } else
            setOccupiedBy(this.gizmo);
    }

    void placeGizmo(Gizmo gizmo) throws TileCoordinatesNotValid {
        this.gizmo = gizmo;
        this.gizmo.setAnchorTile(this);
        occupied = true;
    }

    void removeGizmo(){
        if(gizmo != null) {
            gizmo.removeTile();
            this.gizmo = null;
            occupied = false;
        }
    }

    public Gizmo getGizmo() {
        return gizmo != null ? gizmo : ball;
    }

    @Override
    public String toString(){
        return "<Tile(" + x + "," + y + ")>";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Tile tile = (Tile) obj;
        return ((x == tile.x) && (y == tile.y));
    }

}
