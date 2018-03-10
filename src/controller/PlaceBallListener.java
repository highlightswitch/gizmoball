package controller;

import model.GizmoPlacementNotValidException;
import model.Model;
import model.TileCoordinatesNotValid;
import model.gizmo.GizmoType;

import java.awt.*;

public class PlaceBallListener {
    Model model;
    String pos;
    String vel;
    Color color;
    int x;
    int y;
    int vx;
    int vy;

    public PlaceBallListener(String position, String velocity, Color color, Model m){
        model = m;
        pos = position.replace("(", "");
        pos  = pos.replace(")", "");

        String posX = pos.substring(0, pos.indexOf(","));
        String posY = pos.substring(pos.indexOf(","));
        posY = posY.replace(",", "");
        x = Integer.valueOf(posX);
        y = Integer.valueOf(posY);

        vel = velocity.replace("(", "");
        vel  = vel.replace(")", "");

        String velX = vel.substring(0, vel.indexOf(","));
        String velY = vel.substring(vel.indexOf(","));
        velY = velY.replace(",", "");

        vx = Integer.valueOf(velX);
        vy = Integer.valueOf(velY);

        place();
    }

    public void place(){
        try {
            System.out.println("I am going to place a ball" + " at: (" + x + "," + y + ")");
            model.placeGizmo(GizmoType.BALL,model.getTileAt(x,y), null);
        } catch (GizmoPlacementNotValidException | TileCoordinatesNotValid e1) {
            e1.printStackTrace();
        }

    }
}
