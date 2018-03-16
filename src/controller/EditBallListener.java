package controller;

import model.*;
import model.gizmo.Gizmo;
import model.gizmo.GizmoPropertyType;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class EditBallListener {
    private IModel model;
    private Gizmo gizmo;
    private String color;
    private String vx;
    private String vy;
    private int x;
    private int y;
    private HashMap<GizmoPropertyType, String> properties = new HashMap<>();
    public EditBallListener(Gizmo g, String position, String velocity, Color c, Model m){
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

        String vel = velocity.replace("(", "");
        vel = vel.replace(")", "");

        vx = vel.substring(0, vel.indexOf(","));
        vy = vel.substring(vel.indexOf(","));
        vy = vy.replace(",", "");

        edit();

    }

    private void edit() {
        try {
            model.moveGizmo(gizmo.getProperty(GizmoPropertyType.NAME), model.getTileAt(x,y));
            properties.put(GizmoPropertyType.NAME, gizmo.getProperty(GizmoPropertyType.NAME));
            properties.put(GizmoPropertyType.VEL_X, vx);
            properties.put(GizmoPropertyType.VEL_Y, vy);
            properties.put(GizmoPropertyType.CURRENT_COLOUR, color);
            properties.put(GizmoPropertyType.DEFAULT_COLOUR, color);
            properties.put(GizmoPropertyType.ALT_COLOUR, "");
            model.setAllProperties(gizmo.getProperty(GizmoPropertyType.NAME), properties);

        } catch (GizmoNotFoundException e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Cannot find gizmo", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (GizmoPlacementNotValidException e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Gizmo placement is not valid", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (TileCoordinatesNotValid tileCoordinatesNotValid) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Tile coordinates are not valid", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
