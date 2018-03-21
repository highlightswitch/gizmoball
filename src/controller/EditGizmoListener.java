package controller;

import model.*;
import model.gizmo.Gizmo;
import model.gizmo.GizmoActionType;
import model.gizmo.GizmoPropertyType;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class EditGizmoListener {

    private MainController controller;
    private Gizmo gizmo;
    private String color;
    private String alt;
    private boolean colorChange;
    private GizmoActionType action;
    private int x;
    private int y;
    private HashMap<GizmoPropertyType, String> properties = new HashMap<>();

    public EditGizmoListener(MainController controller, Gizmo g, String position, Color c, Color altc, String a){
        this.controller = controller;
        gizmo = g;

        switch (a){
            case "Change Colour":
                colorChange = true;
                action = GizmoActionType.CHANGE_COLOUR;
                break;
            case "Do Nothing":
                action = GizmoActionType.DO_NOTHING;
                break;
        }

        color = c.toString();
        color = color.substring(color.indexOf("["));

        if(colorChange){
            alt = altc.toString();
            alt = alt.substring(alt.indexOf("["));
        }

        String pos = position.replace("(", "");
        pos = pos.replace(")", "");

        String posX = pos.substring(0, pos.indexOf(","));
        String posY = pos.substring(pos.indexOf(","));
        posY = posY.replace(",", "");
        x = Integer.valueOf(posX);
        y = Integer.valueOf(posY);

        edit();
    }

    public void edit(){
        try {
            IModel model = controller.getIModel();
            if (gizmo.getPosition()[0] != x || gizmo.getPosition()[1] != y) {
                model.moveGizmo(gizmo.getProperty(GizmoPropertyType.NAME), model.getTileAt(x,y));
            }
            properties.put(GizmoPropertyType.NAME, gizmo.getProperty(GizmoPropertyType.NAME));
            properties.put(GizmoPropertyType.ROTATION_DEG, String.valueOf(0));
            properties.put(GizmoPropertyType.CURRENT_COLOUR, color);
            properties.put(GizmoPropertyType.DEFAULT_COLOUR, color);
            if(colorChange){
                properties.put(GizmoPropertyType.ALT_COLOUR, alt);
            } else {
                properties.put(GizmoPropertyType.ALT_COLOUR, color);
            }

            model.setAllProperties(gizmo.getProperty(GizmoPropertyType.NAME), properties);
            model.setGizmoAction(gizmo.getProperty(GizmoPropertyType.NAME), action);
        } catch (GizmoNotFoundException e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Cannot find gizmo", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (GizmoPlacementNotValidException e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Gizmo placement is not valid", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (TileCoordinatesNotValid tileCoordinatesNotValid) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Tile coordinates are not valid", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
