package controller;

import jdk.nashorn.internal.scripts.JO;
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

public class PlaceFlipperListener {

    private MainController controller;
    private int sx;
    private int sy;
    private String di;
    private String name;
    private int id = 0;
    private String color;

    public PlaceFlipperListener(MainController controller, String pos, String direction, Color c){

        this.controller = controller;

        color = c.toString();
        color = color.substring(color.indexOf("["));

        name = "Flipper" + id;
        id++;

        if(direction.equals("Left")){
            di = "true";
        } else {
            di = "false";
        }

        if(Pattern.matches("\\p{Punct}\\d{1,2}\\p{Punct}\\d{1,2}\\p{Punct}", pos)){
            String spos = pos.replace("(", "");
            spos  = spos.replace(")", "");

            String posXS = spos.substring(0, spos.indexOf(","));
            String posYS = spos.substring(spos.indexOf(","));
            posYS = posYS.replace(",", "");
            sx = Integer.valueOf(posXS);
            sy = Integer.valueOf(posYS);
            place();
        } else{
            controller.getView().setMessage("Illegal input format");
        }

    }

    private void place() {
        try {
            IModel model = controller.getIModel();
            model.placeGizmo(GizmoType.FLIPPER, model.getTileAt(sx,sy), new String[]{name, String.valueOf(0), di,color,color, color});
            model.connect(71, TriggerType.KEY_DOWN, name); //Key code 71 = G
            model.connect(70, TriggerType.KEY_UP, name); //Key code 70 = F
        } catch (GizmoPlacementNotValidException e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Gizmo placement is not valid", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (TileCoordinatesNotValid tileCoordinatesNotValid) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Tile coordinates are not valid", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (GizmoNotFoundException e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Gizmo not found");
        }
    }
}
