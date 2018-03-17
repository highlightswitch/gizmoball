package controller;

import model.GizmoPlacementNotValidException;
import model.IModel;
import model.Model;
import model.TileCoordinatesNotValid;
import model.gizmo.GizmoType;

import javax.swing.*;
import java.awt.*;

public class PlaceBallListener {
    private IModel model;
    String color;
    private String name;
    private int id;
    private int x;
    private int y;
    private String vx;
    private String vy;

    public PlaceBallListener(String position, String velocity, Color c, Model m){
        model = m;
        name = "Ball" + id;
        id++;
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

        place();
    }

    private void place(){
        try {
            System.out.println(color); // [r=51,g=0,b=153]
            model.placeGizmo(GizmoType.BALL,model.getTileAt(x,y), new String[]{name, vx, vy, color, color,color});
            // connect one trigger to one action
        } catch (GizmoPlacementNotValidException | TileCoordinatesNotValid e1) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Gizmo placement is not valid", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
