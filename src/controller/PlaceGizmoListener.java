package controller;

import model.*;
import model.gizmo.GizmoActionType;
import model.gizmo.GizmoType;

import java.awt.*;

public class PlaceGizmoListener{
    private IModel model;
    private GizmoType g;
    private String gname;
    private int id = 0;
    private Color color;
    private GizmoActionType action;
    private int x;
    private int y;

    public PlaceGizmoListener(String gizmo, String position, Color c, String a, String t, Model m){
        model = m;
        String name = gizmo;

        switch (name){
            case "Circle":
                g = GizmoType.CIRCLE_BUMPER;
                gname = name + id;
                id++;
                break;
            case "Triangle":
                g = GizmoType.TRIANGLE_BUMPER;
                gname = name + id;
                id++;
                break;
            case  "Square":
                g = GizmoType.SQUARE_BUMPER;
                gname = name + id;
                id++;
                break;
        }

        String pos = position.replace("(", "");
        pos = pos.replace(")", "");

        String posX = pos.substring(0, pos.indexOf(","));
        String posY = pos.substring(pos.indexOf(","));
        posY = posY.replace(",", "");
        x = Integer.valueOf(posX);
        y = Integer.valueOf(posY);

        color = c;

        switch (a){
            case "Change Colour":
                action = GizmoActionType.CHANGE_COLOUR;
                break;
            case "Rotate":
                action = GizmoActionType.PRINT_TO_CONSOLE;
                break;
            case "Flipper":
                action = GizmoActionType.FLIP_FLIPPER;
                break;
            case "Absorber":
                action = GizmoActionType.FIRE_FROM_ABSORBER;
                break;
        }

        switch (t){
            case "Another Gizmo":
                break;
            case "A Key Press":
                break;
            case "Ball Collision":
                break;
        }
        place();
    }

    private void place() {
        try {
            //System.out.println("I am going to place " + g.toString() + " at: (" + x + "," + y + ")");
            model.placeGizmo(g,model.getTileAt(x,y), new String[]{gname, String.valueOf(0)});
            model.setGizmoAction(gname, action);
            //how to trigger: use connect
        } catch (GizmoPlacementNotValidException | TileCoordinatesNotValid e1) {
            e1.printStackTrace();
        } catch (GizmoNotFoundException e) {
            e.printStackTrace();
        }
    }
}
