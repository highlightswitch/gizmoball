package controller;

import model.*;
import model.gizmo.GizmoActionType;
import model.gizmo.GizmoType;

import javax.swing.*;
import java.awt.*;

public class PlaceGizmoListener{
    private IModel model;
    private GizmoType g;
    private String gname;
    private int id = 0;
    private String color;
    private GizmoActionType action;
    private int x;
    private int y;

    public PlaceGizmoListener(String gizmo, String position, Color c, String a, Model m){
        model = m;
        String name = gizmo;
        color = c.toString();
        color = color.substring(color.indexOf("["));

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

        gname = name + id;
        id++;

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
            case "Do Nothing":
                action = GizmoActionType.PRINT_TO_CONSOLE;
                break;
            case "Flipper":
                action = GizmoActionType.FLIP_FLIPPER;
                break;
            case "Absorber":
                action = GizmoActionType.FIRE_FROM_ABSORBER;
                break;
        }
        place();
    }

    private void place() {
        try {
            model.placeGizmo(g,model.getTileAt(x,y), new String[]{gname, String.valueOf(0), color,color,color});
            model.setGizmoAction(gname, action);
        } catch (GizmoPlacementNotValidException | TileCoordinatesNotValid e1) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Gizmo placement is not valid", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (GizmoNotFoundException e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Cannot find gizmo", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
