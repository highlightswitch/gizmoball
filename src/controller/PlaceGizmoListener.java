package controller;

import model.*;
import model.gizmo.Gizmo;
import model.gizmo.GizmoActionType;
import model.gizmo.GizmoPropertyType;
import model.gizmo.GizmoType;

import java.awt.*;

public class PlaceGizmoListener{
    IModel model;
    GizmoType g;
    String name;
    String pos;
    Color color;
    //GizmoPropertyType trigger;
    GizmoActionType action;
    int x;
    int y;

    public PlaceGizmoListener(String gizmo, String position, Color c, String a, String t, Model m){
        model = m;
        name = gizmo;
        switch (name){
            case "Circle":
                g = GizmoType.CIRCLE_BUMPER;
                break;
            case "Triangle":
                g = GizmoType.TRIANGLE_BUMPER;
                break;
            case  "Square":
                g = GizmoType.SQUARE_BUMPER;
                break;
        }

        pos = position.replace("(", "");
        pos  = pos.replace(")", "");

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
            case "Activate Another Gizmo":
                action = GizmoActionType.PRINT_TO_CONSOLE;
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

    public void place() {
        try {
            System.out.println("I am going to place " + g.toString() + " at: (" + x + "," + y + ")");
            model.placeGizmo(g,model.getTileAt(x,y), null);
            // how are objects named initially ?
            model.setGizmoAction(name, action);
            //how to trigger
        } catch (GizmoPlacementNotValidException | TileCoordinatesNotValid e1) {
            e1.printStackTrace();
        } catch (GizmoNotFoundException e) {
            e.printStackTrace();
        }
    }
}
