package controller;

import model.GizmoPlacementNotValidException;
import model.Model;
import model.TileCoordinatesNotValid;
import model.gizmo.GizmoType;

import javax.swing.*;
import java.awt.*;

public class PlaceAbsorberListener {
    private Model model;
    private int sx;
    private int sy;
    private String width;
    private String height;
    private String name;
    private int id = 0;
    private String color;

    public PlaceAbsorberListener(String start, String w, String h, Color c, Model m){
        model = m;
        color = c.toString();
        color = color.substring(color.indexOf("["));
        name = "Absorber" + id;
        id++;

        String spos = start.replace("(", "");
        spos  = spos.replace(")", "");

        String posXS = spos.substring(0, spos.indexOf(","));
        String posYS = spos.substring(spos.indexOf(","));
        posYS = posYS.replace(",", "");
        sx = Integer.valueOf(posXS);
        sy = Integer.valueOf(posYS);

        width = w;
        height = h;

        place();
    }

    private void place() {
        try {
            model.placeGizmo(GizmoType.ABSORBER,model.getTileAt(sx,sy),new String[] {name, width, height, color, color, ""});
        } catch (GizmoPlacementNotValidException | TileCoordinatesNotValid e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Gizmo placement is not valid", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
