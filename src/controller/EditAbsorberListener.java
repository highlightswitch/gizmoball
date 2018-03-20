package controller;

import model.*;
import model.gizmo.Gizmo;
import model.gizmo.GizmoPropertyType;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class EditAbsorberListener {

    private MainController controller;
    private Gizmo gizmo;
    private String color;
    private String width;
    private String height;
    private int x;
    private int y;
    private HashMap<GizmoPropertyType, String> properties = new HashMap<>();

    public EditAbsorberListener(MainController controller, Gizmo g, String start, String w, String h, Color c){
        this.controller = controller;
        gizmo = g;

        color = c.toString();
        color = color.substring(color.indexOf("["));

        String pos = start.replace("(", "");
        pos = pos.replace(")", "");

        String posX = pos.substring(0, pos.indexOf(","));
        String posY = pos.substring(pos.indexOf(","));
        posY = posY.replace(",", "");
        x = Integer.valueOf(posX);
        y = Integer.valueOf(posY);

        width = w;
        height = h;

        edit();
    }

    private void edit() {
        try {
            IModel model = controller.getIModel();
            if(gizmo.getPosition()[0] == x && gizmo.getPosition()[1] == y){
                //
            } else {
                model.moveGizmo(gizmo.getProperty(GizmoPropertyType.NAME), model.getTileAt(x,y));
            }
            properties.put(GizmoPropertyType.NAME, gizmo.getProperty(GizmoPropertyType.NAME));
            properties.put(GizmoPropertyType.WIDTH, width);
            properties.put(GizmoPropertyType.HEIGHT, height);
            properties.put(GizmoPropertyType.CURRENT_COLOUR, color);
            properties.put(GizmoPropertyType.DEFAULT_COLOUR, color);
            properties.put(GizmoPropertyType.ALT_COLOUR, color);
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
