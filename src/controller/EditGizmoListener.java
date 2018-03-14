package controller;

import model.*;
import model.gizmo.Gizmo;
import model.gizmo.GizmoActionType;
import model.gizmo.GizmoPropertyType;
import model.gizmo.GizmoType;

import java.awt.*;
import java.util.HashMap;

public class EditGizmoListener {
    private IModel model;
    private Gizmo gizmo;
    private String color;
    private GizmoActionType action;
    private int x;
    private int y;
    private HashMap<GizmoPropertyType, String> properties = new HashMap<>();

    public EditGizmoListener(Gizmo g, String position, Color c, String a, String t, Model m){
        model = m;
        gizmo = g;

        color = c.toString();
        color = color.substring(color.indexOf("["));

        String pos = position.replace("(", "");
        pos = pos.replace(")", "");

        String posX = pos.substring(0, pos.indexOf(","));
        String posY = pos.substring(pos.indexOf(","));
        posY = posY.replace(",", "");
        x = Integer.valueOf(posX);
        y = Integer.valueOf(posY);


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

        edit();
    }

    public void edit(){
        try {
            model.moveGizmo(gizmo.getProperty(GizmoPropertyType.NAME), model.getTileAt(x,y));
            properties.put(GizmoPropertyType.NAME, gizmo.getProperty(GizmoPropertyType.NAME));
            properties.put(GizmoPropertyType.ROTATION_DEG, String.valueOf(0));
            properties.put(GizmoPropertyType.CURRENT_COLOUR, color);
            properties.put(GizmoPropertyType.DEFAULT_COLOUR, color);
            properties.put(GizmoPropertyType.ALT_COLOUR, "");
            model.setAllProperties(gizmo.getProperty(GizmoPropertyType.NAME), properties);
        } catch (GizmoNotFoundException e) {
            e.printStackTrace();
        } catch (GizmoPlacementNotValidException e) {
            e.printStackTrace();
        } catch (TileCoordinatesNotValid tileCoordinatesNotValid) {
            tileCoordinatesNotValid.printStackTrace();
        }
    }
}
