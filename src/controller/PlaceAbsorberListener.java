package controller;

import model.GizmoNotFoundException;
import model.GizmoPlacementNotValidException;
import model.IModel;
import model.TileCoordinatesNotValid;
import model.gizmo.GizmoPropertyType;
import model.gizmo.GizmoType;
import model.gizmo.TriggerType;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class PlaceAbsorberListener {

    private MainController controller;
    private int sx;
    private int sy;
    private String width;
    private String height;
    private String name;
    private int id = 0;
    private String color;

    public PlaceAbsorberListener(MainController controller, String start, String w, String h, Color c){
        this.controller = controller;
        color = c.toString();
        color = color.substring(color.indexOf("["));
        name = "Absorber" + id;
        id++;

        width = w;
        height = h;

        if(Pattern.matches("\\p{Punct}\\d{1,2}\\p{Punct}\\d{1,2}\\p{Punct}", start)){
            String spos = start.replace("(", "");
            spos  = spos.replace(")", "");

            String posXS = spos.substring(0, spos.indexOf(","));
            String posYS = spos.substring(spos.indexOf(","));
            posYS = posYS.replace(",", "");
            sx = Integer.valueOf(posXS);
            sy = Integer.valueOf(posYS);
            place();
        } else{
            System.out.println("Illegal input format");
        }


    }

    private void place() {
        try {
            IModel model = controller.getIModel();
            model.placeGizmo(GizmoType.ABSORBER,model.getTileAt(sx,sy),new String[] {name, width, height, color, color, color});
            model.connect(32, TriggerType.KEY_DOWN, name); //space bar
        } catch (GizmoPlacementNotValidException | TileCoordinatesNotValid e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Gizmo placement is not valid", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (GizmoNotFoundException e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Could not find gizmo", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
