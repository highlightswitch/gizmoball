package controller;

import model.GizmoPlacementNotValidException;
import model.IModel;
import model.TileCoordinatesNotValid;
import model.gizmo.GizmoType;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class PlaceBallListener {

    private MainController controller;
    private String color;
    private String name;
    private int id;
    private int x;
    private int y;
    private String vx;
    private String vy;

    public PlaceBallListener(MainController controller, String position, String velocity, Color c){
        this.controller = controller;
        name = "Ball" + id;
        id++;
        color = c.toString();
        color = color.substring(color.indexOf("["));

        if(Pattern.matches("\\p{Punct}\\d{1,2}\\p{Punct}\\d{1,2}\\p{Punct}", position)){
            String pos = position.replace("(", "");
            pos = pos.replace(")", "");

            String posX = pos.substring(0, pos.indexOf(","));
            String posY = pos.substring(pos.indexOf(","));
            posY = posY.replace(",", "");
            x = Integer.valueOf(posX);
            y = Integer.valueOf(posY);
        }else {
            System.out.println("Illegal input format");
        }

        if(Pattern.matches("\\p{Punct}\\d{1,2}\\p{Punct}\\d{1,2}\\p{Punct}", velocity)){
            String vel = velocity.replace("(", "");
            vel = vel.replace(")", "");

            vx = vel.substring(0, vel.indexOf(","));
            vy = vel.substring(vel.indexOf(","));
            vy = vy.replace(",", "");
            place();
        }else {
            System.out.println("Illegal input format");
        }
    }

    private void place(){
        try {
            IModel model = controller.getIModel();
            model.placeGizmo(GizmoType.BALL,model.getTileAt(x,y), new String[]{name, vx, vy, color, color,color});
        } catch (GizmoPlacementNotValidException | TileCoordinatesNotValid e1) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Gizmo placement is not valid", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
