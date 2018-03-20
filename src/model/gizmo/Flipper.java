package model.gizmo;

import model.*;
import model.util.GizmoMaths;
import physics.Angle;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class Flipper extends Gizmo implements Tickable, Collidable {

    private double flipSpeed;

    private final double length = 2;
    private final double width = 0.5;

    private double currentMovement;
    private double flipPos;

   // protected GizmoType type;

    public Flipper(Color colour, Map<GizmoPropertyType, String> properties){
        super(colour, properties);
        this.setAction(GizmoActionType.FLIP_FLIPPER);

        flipSpeed = 0.1;

        flipPos = 0;
        currentMovement = 0;
        type = GizmoType.FLIPPER;
    }

    public GizmoType getType(){return type;}

    @Override
    public boolean isTilePlacable() {
        return true;
    }

    private boolean isLeftFlipper(){
        return Boolean.valueOf(getProperty(GizmoPropertyType.IS_LEFT_ORIENTATED));
    }

    private void moveFlipper(){
        flipPos = GizmoMaths.clamp(flipPos + currentMovement, 0, 1);
    }

    private boolean isMovingUp(){
        return currentMovement > 0;
    }

    private boolean isMovingDown(){
        return currentMovement < 0;
    }

    private void setMovingUp(){
        currentMovement = flipSpeed;
    }

    private void setMovingDown(){
        currentMovement = -1* flipSpeed;
    }

    @Override
    public Tile[] findAnnexedTiles(Tile anchorTile) {

        //The below means that the flipper occupies 2 tiles
        // in the x and y directions (ie a 2x2 square).
        double width = 2;
        double height = 2;
        ArrayList<Tile> tiles = new ArrayList<>();

        for(int xOff = 0; xOff < width; xOff++)
            for (int yOff = 0; yOff < height; yOff++)
                if(!(xOff == 0 && yOff ==0))
                    tiles.add(anchorTile.getNeighbour(xOff, yOff));

        Tile[] arr = new Tile[tiles.size()];
        return tiles.toArray(arr);

    }

    @Override
    public GameObject getPrototypeGameObject() {

        LineSegment[] lines;
        Circle[] circles;
        GameObject gameObject;

        if(isLeftFlipper()) {

            lines = new LineSegment[] {
                    new LineSegment(0, width / 2, 0, length - width / 2),
                    new LineSegment(width, width / 2, width, length - width / 2)
            };

            // These are the circles at either end of the flipper, and also the circles with
            // radius 0 to help with collisions at the ends of LineSegments.
            circles = new Circle[] {
                    new Circle(width / 2, width / 2, width / 2),
                    new Circle(width / 2, length - width / 2, width / 2),
                    new Circle(0, width/2, 0),
                    new Circle(0, length - width/2, 0),
                    new Circle(width, width/2, 0),
                    new Circle(width, length - width/2, 0)
            };

            double angularVelocity = 0.0;
            if(flipPos > 0.0 && flipPos < 1.0)
                angularVelocity = currentMovement < 0 ? Math.toRadians(180) : -1 * Math.toRadians(180);

            gameObject = new RotatingGameObject(
                    lines,
                    circles,
                    0.95,
                    new Vect(width/2, width/2),
                    angularVelocity
            );

            gameObject.rotateAroundPointByAngle(
                    new Vect(width/2, width/2),
                    new Angle(Math.toRadians(-90 * flipPos))
            );

        } else{

            lines = new LineSegment[] {
                    new LineSegment(2 - width, width / 2, 2 - width, length - width / 2),
                    new LineSegment(2, width / 2, 2, length - width / 2)
            };

            // These are the circles at either end of the flipper, and also the circles with
            // radius 0 to help with collisions at the ends of LineSegments.
            circles = new Circle[] {
                    new Circle(2 - width / 2, width / 2, width / 2),
                    new Circle( 2 - width / 2, length - width / 2, width / 2),
                    new Circle(2 - width, width/2, 0),
                    new Circle(2 - width, length - width/2, 0),
                    new Circle(2, width/2, 0),
                    new Circle(2, length - width/2, 0)
            };

            double angularVelocity = 0.0;
            if(flipPos > 0.0 && flipPos < 1.0)
                angularVelocity = currentMovement < 0 ? Math.toRadians(180) : -1 * Math.toRadians(180);
            gameObject = new RotatingGameObject(
                    lines,
                    circles,
                    0.95,
                    new Vect(width/2, width/2),
                    angularVelocity
            );

            gameObject.rotateAroundPointByAngle(
                    new Vect(2 - width/2, width/2),
                    new Angle( Math.toRadians(90 * flipPos))
            );

        }

//        GameObject gameObject = new StaticGameObject(lines, circles, 0.95);

        return gameObject;
    }

    @Override
    public GameObject getGameObject() {
        return getPrototypeGameObject().rotateAroundPointByAngle( new Vect(1,1),  new Angle(getCurrentRotationInRadians())).translate(getPosition());
    }

    @Override
    public Object clone() {
        return super.clone();
    }

    @Override
    public boolean isAbsorber() {
        return false;
    }

    @Override
    public DrawingData getGizmoDrawingData() {
        DrawingData data = new DrawingData();

        if(isLeftFlipper()) {
            ArrayList<Double[]> poly = new ArrayList<>();
            poly.add(new Double[]{0.0, width / 2}); // NW
            poly.add(new Double[]{0.0, length - width / 2}); // SW
            poly.add(new Double[]{width, length - width / 2}); // SE
            poly.add(new Double[]{width, width / 2}); // NE
            data.addPolygon(poly);

            data.addCircle(new Double[]{width / 2, width / 2, width / 2});
            data.addCircle(new Double[]{width / 2, length - width / 2, width / 2});

            data.rotateAroundPivotByRadians(new double[]{width/2, width/2}, Math.toRadians(-90 * flipPos));
        } else {
            ArrayList<Double[]> poly = new ArrayList<>();
            poly.add(new Double[]{2.0, width / 2}); // NW
            poly.add(new Double[]{2.0, length - width / 2}); // SW
            poly.add(new Double[]{2 - width, length - width / 2}); // SE
            poly.add(new Double[]{2 - width, width / 2}); // NE
            data.addPolygon(poly);

            data.addCircle(new Double[]{2 - width / 2, width / 2, width / 2});
            data.addCircle(new Double[]{2 - width / 2, length - width / 2, width / 2});

            data.rotateAroundPivotByRadians(new double[]{2 - width/2, width/2}, Math.toRadians(90 * flipPos));
        }

        data.rotateAroundPivotByRadians(new int[]{1,1}, getCurrentRotationInRadians());

        data.setColour(getProperty(GizmoPropertyType.CURRENT_COLOUR));

        return data;
    }

    @Override
    public void tick() {
        moveFlipper();
    }


    @Override
    public void setAction(GizmoActionType type) {
        this.actionType = type;
        if (type == GizmoActionType.FLIP_FLIPPER) {
            action = this::action_moveFlipper;
        } else {
            super.setAction(type);
        }
    }

    private void action_moveFlipper(){
        if(isMovingDown() && flipPos != 0){
            setMovingUp();
        } else if(isMovingUp() && flipPos != 1) {
            setMovingDown();
        } else if(flipPos == 0){
            setMovingUp();
        } else if(flipPos == 1){
            setMovingDown();
        } else {
            System.err.println("action_MoveFlipper: This should never happen");
        }
    }

}
