package controller;

import model.*;
import model.gizmo.Gizmo;
import model.gizmo.GizmoPropertyType;

import java.awt.*;
import java.util.HashMap;

public class EditAbsorberListener {
    private IModel model;
    private Gizmo gizmo;
    private String color;
    private String width;
    private String height;
    private int x;
    private int y;
    private HashMap<GizmoPropertyType, String> properties = new HashMap<>();

    public EditAbsorberListener(Gizmo g, String start, String w, String h, Color c, Model m){
        model = m;
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
            model.moveGizmo(gizmo.getProperty(GizmoPropertyType.NAME), model.getTileAt(x,y));
            properties.put(GizmoPropertyType.NAME, gizmo.getProperty(GizmoPropertyType.NAME));
            properties.put(GizmoPropertyType.WIDTH, width);
            properties.put(GizmoPropertyType.HEIGHT, height);
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
