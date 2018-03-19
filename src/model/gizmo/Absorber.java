package model.gizmo;

import model.*;
import physics.Circle;
import physics.LineSegment;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class Absorber extends Gizmo implements Collidable {

    private Ball absorbedBall;

    public Absorber(Color colour, Map<GizmoPropertyType, String> properties){
        super(colour, properties);
        this.setAction(GizmoActionType.FIRE_FROM_ABSORBER);
        type = GizmoType.ABSORBER;
    }

    @Override
    public boolean isTilePlacable() {
        return true;
    }

    void setAbsorbedBall (Ball ball) {
        absorbedBall = ball;
    }

    @Override
    public Tile[] findAnnexedTiles(Tile anchorTile) {

        double width = Double.valueOf(this.getProperty(GizmoPropertyType.WIDTH));
        double height = Double.valueOf(this.getProperty(GizmoPropertyType.HEIGHT));
        ArrayList<Tile> tiles = new ArrayList<>();

        for(int xOff = 0; xOff < width; xOff++)
            for (int yOff = 0; yOff < height; yOff++)
                if(!(xOff == 0 && yOff ==0))
                    tiles.add(anchorTile.getNeighbour(xOff, yOff));

        Tile[] arr = new Tile[tiles.size()];
        return tiles.toArray(arr);

    }

    public GizmoType getType(){return type;}

    @Override
    public GameObject getPrototypeGameObject() {

        double width = Double.valueOf(this.getProperty(GizmoPropertyType.WIDTH));
        double height = Double.valueOf(this.getProperty(GizmoPropertyType.HEIGHT));

        LineSegment[] lines = {
                new LineSegment(0, 0, 0, height),
                new LineSegment(0, height, width, height),
                new LineSegment(width, height, width, 0),
                new LineSegment(0, 0, width, 0)
        };

        // These are the circles at either end of the flipper, and also the circles with
        // radius 0 to help with collisions at the ends of LineSegments.
        Circle[] circles = {
                new Circle(0,0, 0),
                new Circle(0, height, 0),
                new Circle(width, 0, 0),
                new Circle(width, height, 0)
        };

        return new StaticGameObject(lines, circles, 1);
    }

    public GameObject getGameObject(){return getPrototypeGameObject().translate(getPosition());}

    @Override
    public Object clone() {
        return super.clone();
    }

    public boolean isAbsorber() {return true;}

    @Override
    public DrawingData getGizmoDrawingData() {

        double width = Double.valueOf(getProperty(GizmoPropertyType.WIDTH));
        double height = Double.valueOf(getProperty(GizmoPropertyType.HEIGHT));

        DrawingData data = new DrawingData();
        ArrayList<Double[]> squarePoly = new ArrayList<>();
        squarePoly.add(new Double[]{0.0, 0.0}); //NE
        squarePoly.add(new Double[]{width, 0.0}); //NW
        squarePoly.add(new Double[]{width, height}); //SW
        squarePoly.add(new Double[]{0.0 , height}); //SE
        data.addPolygon(squarePoly);

        data.setColour(getProperty(GizmoPropertyType.CURRENT_COLOUR));

        return data;
    }

    @Override
    public void setAction(GizmoActionType type) {
        if (type == GizmoActionType.FIRE_FROM_ABSORBER) {
            action = this::action_fireBall;
        } else {
            super.setAction(type);
        }
    }

    private void action_fireBall(){
        if(absorbedBall != null) {
            absorbedBall.fire(this);
            absorbedBall = null;
        }
    }


}
