package controller;

import model.*;
import model.gizmo.GizmoPropertyException;
import model.gizmo.GizmoPropertyType;
import model.gizmo.GizmoType;

import java.awt.*;

public class PlaceBallListener {
    IModel model;
    String pos;
    String vel;
    Color color;
    String name;
    int id;
    int x;
    int y;
    String vx;
    String vy;

    public PlaceBallListener(String position, String velocity, Color color, Model m){
        model = m;
        name = "Ball" + id;
        id++;

        pos = position.replace("(", "");
        pos  = pos.replace(")", "");

        String posX = pos.substring(0, pos.indexOf(","));
        String posY = pos.substring(pos.indexOf(","));
        posY = posY.replace(",", "");
        x = Integer.valueOf(posX);
        y = Integer.valueOf(posY);

        vel = velocity.replace("(", "");
        vel  = vel.replace(")", "");

        vx = vel.substring(0, vel.indexOf(","));
        vy = vel.substring(vel.indexOf(","));
        vy = vy.replace(",", "");

        //vx = Integer.valueOf(velX);
        //vy = Integer.valueOf(velY);

        place();
    }

    public void place(){
        try {
            //System.out.println("I am going to place a ball" + " at: (" + x + "," + y + ")");
            model.placeGizmo(GizmoType.BALL,model.getTileAt(x,y), new String[]{name, vx, vy});
        } catch (GizmoPlacementNotValidException | TileCoordinatesNotValid e1) {
            e1.printStackTrace();
        }
    }
}
