package controller;

import model.*;
import model.gizmo.GizmoActionType;
import model.gizmo.GizmoType;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class PlaceGizmoListener{

    private MainController controller;

    private GizmoType g;
    private String gname;
    private int id = 0;
    private String color;
    private String alt;
    private GizmoActionType action;
    private boolean colorChange;
    private int x;
    private int y;

    public PlaceGizmoListener(MainController controller, String gizmoName, String position, Color c, Color altc,  String a){
        this.controller = controller;

        switch (a){
            case "Change Colour":
                colorChange =  true;
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

        switch (gizmoName){
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

        gname = gizmoName + id;
        id++;

        if(Pattern.matches("\\p{Punct}\\d{1,2}\\p{Punct}\\d{1,2}\\p{Punct}", position)){
            String pos = position.replace("(", "");
            pos = pos.replace(")", "");

            String posX = pos.substring(0, pos.indexOf(","));
            String posY = pos.substring(pos.indexOf(","));
            posY = posY.replace(",", "");
            x = Integer.valueOf(posX);
            y = Integer.valueOf(posY);
            place();
        } else {
            controller.getView().setMessage("Illegal input format");
        }

    }

    private void place() {
        try {
            IModel model = controller.getIModel();
            if(colorChange){
                model.placeGizmo(g,model.getTileAt(x,y), new String[]{gname, String.valueOf(0), color,color,alt});
            }else{
                model.placeGizmo(g,model.getTileAt(x,y), new String[]{gname, String.valueOf(0), color,color,color});
            }
            model.setGizmoAction(gname, action);
        } catch (GizmoPlacementNotValidException | TileCoordinatesNotValid e1) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Gizmo placement is not valid", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (GizmoNotFoundException e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Cannot find gizmo", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
