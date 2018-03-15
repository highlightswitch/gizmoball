package model.gizmo;

import model.*;
import physics.Vect;

import java.awt.*;
import java.util.List;
import java.util.Map;

public abstract class Gizmo extends Triggerable implements Collidable, Drawable {

    private Color colour;
    private Tile anchorTile;
    private Tile[] annexedTiles;

    protected GizmoType type;

    private Map<GizmoPropertyType, String> properties;

    Gizmo(Color colour, Map<GizmoPropertyType, String> props){
        properties = props;
        this.colour = colour;
    }

    public Color getColour() {
        return colour;
    }

    public GizmoType getType(){return type;}

    public String getProperty(GizmoPropertyType prop){
        return properties.getOrDefault(prop, null);
    }

    public void setProperty(GizmoPropertyType prop, String val) throws GizmoPropertyException {
        if(properties.containsKey(prop)){
            properties.put(prop, val);
        } else {
            throw new GizmoPropertyException("Attempted to set property that has not previously been set at compile time" +
                    "\n Property: " + prop +
                    "\n Value: " + val);
        }
    }

    public String toString(){
        switch (type){
            case FLIPPER:
                if(Boolean.valueOf(getProperty(GizmoPropertyType.IS_LEFT_ORIENTATED))){
                    return "LeftFlipper" + " " + getProperty(GizmoPropertyType.NAME) + " " + anchorTile.getX() + " " + anchorTile.getY();
                } else {
                    return "RightFlipper" + " " + getProperty(GizmoPropertyType.NAME) + " " + anchorTile.getX() + " " + anchorTile.getY();
                }
            case BALL:
                //Name, Vel_X, Vel_Y
                return "Ball" + " " + getProperty(GizmoPropertyType.NAME) + " " + anchorTile.getX() + " " + anchorTile.getY() + " " + getProperty(GizmoPropertyType.VEL_X) + " " + getProperty(GizmoPropertyType.VEL_Y);
            case CIRCLE_BUMPER:
                return "Circle" + " " + getProperty(GizmoPropertyType.NAME) + " " + anchorTile.getX() + " " + anchorTile.getY();
            case SQUARE_BUMPER:
                return "Square" + " " + getProperty(GizmoPropertyType.NAME) + " " + anchorTile.getX() + " " + anchorTile.getY();
            case TRIANGLE_BUMPER:
                return "Triangle" + " " + getProperty(GizmoPropertyType.NAME) + " " + anchorTile.getX() + " " + anchorTile.getY();
            case ABSORBER:
                double x2 = anchorTile.getX() + Double.parseDouble(getProperty(GizmoPropertyType.WIDTH));
                double y2 = anchorTile.getY() + Double.parseDouble(getProperty(GizmoPropertyType.HEIGHT));
                return "Absorber" + " " + getProperty(GizmoPropertyType.NAME) + " " + anchorTile.getX() + " " + anchorTile.getY() + " " + x2 + " " + y2;
                default: return "Something's gone horribly wrong.";
        }
    }

    public void setAnchorTile(Tile anchorTile) {
        this.anchorTile = anchorTile;
        setAnnexedTiles(findAnnexedTiles(anchorTile));
    }

    private void setAnnexedTiles(Tile[] tiles){
        annexedTiles = tiles;
        for(Tile t : annexedTiles){
            t.setOccupied(true);
        }
    }

    private void removeAnnexedTiles(){
        for(Tile t : annexedTiles){
            t.setOccupied(false);
        }
        annexedTiles = null;
    }

    public void removeTile(){
        removeAnnexedTiles();
        this.anchorTile = null;
    }

    public double[] getPosition(){
        if(anchorTile != null)
            return new double[] {anchorTile.getPosition()[0], anchorTile.getPosition()[1]};
        else
            return null;
    }

    public abstract boolean isTilePlacable();

    public void rotateBy_Deg(double val) throws GizmoPropertyException {
        double rotation = Double.valueOf(getProperty(GizmoPropertyType.ROTATION_DEG));
        rotation = (rotation + val) % 360;
        rotateTo_Deg(rotation);
    }

    public void rotateTo_Deg(double val) throws GizmoPropertyException {
        setProperty(GizmoPropertyType.ROTATION_DEG, String.valueOf(val));
    }

    public DrawingData getDrawingData(){
        return this.getGizmoDrawingData().translate(getPosition());
    }

    public abstract Tile[] findAnnexedTiles(Tile anchorTile);

    public abstract GameObject getPrototypeGameObject();
    public abstract GameObject getGameObject();

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isAbsorber() {
        return false;
    }

    protected abstract DrawingData getGizmoDrawingData();

    @Override
    public CollisionDetails timeUntilCollisionWithBall(GameObject ballGO, Vect ballVelocity) {
        CollisionDetails cd = getGameObject().timeUntilCollisionWithBall(ballGO, ballVelocity);
        cd.setCollidingWith(this);
        return cd;
    }

    @Override
    public void collide(){
        super.trigger();
    }


    @Override
    public void setAction(GizmoActionType type){
        if(type == GizmoActionType.CHANGE_COLOUR){
            action = this::action_changeColour;
        } else if(type == GizmoActionType.PRINT_TO_CONSOLE){
            action = this::action_printToConsole;
        } else {
            action = this::action_printToConsole;
            throw new IllegalArgumentException();
        }
    }

    private void action_changeColour(){
//        System.out.println("colour changed");
        try {
            String currentColour = getProperty(GizmoPropertyType.CURRENT_COLOUR);
            String defaultColour = getProperty(GizmoPropertyType.DEFAULT_COLOUR);
            String altColour = getProperty(GizmoPropertyType.ALT_COLOUR);
            if(currentColour.equals(defaultColour))
                this.setProperty(GizmoPropertyType.CURRENT_COLOUR, getProperty(GizmoPropertyType.ALT_COLOUR));
            else if(currentColour.equals(altColour))
                this.setProperty(GizmoPropertyType.CURRENT_COLOUR, getProperty(GizmoPropertyType.DEFAULT_COLOUR));
            else
                throw new IllegalStateException("Current colour of " + this + "is not default or alt colour");
        } catch (GizmoPropertyException e) {
            //This should never happen
        }
    }

    private void action_printToConsole(){
        System.out.println( this.getProperty(GizmoPropertyType.NAME) + " activated");
    }

    @Override
    public void keyDown(){
        super.keyDown();
        //Override me to do something
    }

    @Override
    public void keyUp(){
        super.keyUp();
        //Override me to do something
    }


    public static String[] getPropertyDefaults(GizmoType type, List<String> usedNames){
        String[] propVals = null;
        switch (type){
            case FLIPPER:
                //Name, Rotation_Deg
                propVals = new String[]{ "leftFlipper_0", "0", "true", "[r=255,g=255, b=255]", "[r=255,g=255, b=255]", "[r=255,g=0, b=0]" };
                break;
            case BALL:
                //Name, Vel_X, Vel_Y
                propVals = new String[]{ "ball_0", "0", "0", "[r=255,g=255, b=255]", "[r=255,g=255, b=255]", "[r=255,g=0, b=0]" };
                break;
            case CIRCLE_BUMPER:
                //Name, Rotation_Deg
                propVals = new String[]{ "circleBumper_0", "0", "[r=255,g=255, b=255]", "[r=255,g=255, b=255]", "[r=255,g=0, b=0]" };
                break;
            case SQUARE_BUMPER:
                //Name, Rotation_Deg
                propVals = new String[]{ "squareBumper_0", "0", "[r=255,g=255, b=255]", "[r=255,g=255, b=255]", "[r=255,g=0, b=0]" };
                break;
            case TRIANGLE_BUMPER:
                //Name, Rotation_Deg
                propVals = new String[]{ "triangleBumper_0", "0", "[r=255,g=255, b=255]", "[r=255,g=255, b=255]", "[r=255,g=0, b=0]" };
                break;
            case ABSORBER:
                //Name, width, height
                propVals = new String[]{ "absorber_0", "20", "1", "[r=255,g=255, b=255]", "[r=255,g=255, b=255]", "[r=255,g=0, b=0]" };
                break;
        }

        if(usedNames != null){
            String newName = propVals[0];
            while(usedNames.contains(newName)){
                String[] arr = newName.split("_");
                arr[arr.length -1] = String.valueOf(Integer.parseInt(arr[arr.length -1]) + 1);
                StringBuilder sb = new StringBuilder();
                for(String s : arr)
                    sb.append(s);
                newName = sb.toString();
                System.out.println(newName);
            }
            propVals[0] = newName;
        }

        return propVals;

    }

}
