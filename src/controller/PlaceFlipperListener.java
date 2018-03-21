package controller;

import model.GizmoPlacementNotValidException;
import model.IModel;
import model.TileCoordinatesNotValid;
import model.gizmo.GizmoType;
import model.util.GizmoUtils;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class PlaceFlipperListener {

    private MainController controller;
    private int sx;
    private int sy;
    private String di;
    private String name;
    private String color;

    public PlaceFlipperListener(MainController controller, String pos, String direction, Color c){

        this.controller = controller;

        color = c.toString();
        color = color.substring(color.indexOf("["));

        name = GizmoUtils.getUnusedName(controller.getModel().getAllGizmoNames(), GizmoType.FLIPPER);

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
        } catch (GizmoPlacementNotValidException e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Gizmo placement is not valid", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (TileCoordinatesNotValid tileCoordinatesNotValid) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Tile coordinates are not valid", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
