package controller;

import model.GizmoPlacementNotValidException;
import model.Model;
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
        pos = pos.replace(",","");
        x = Integer.valueOf(String.valueOf(pos.charAt(0)));
        y = Integer.valueOf(String.valueOf(pos.charAt(1)));

        vel = velocity.replace("(", "");
        vel  = vel.replace(")", "");
        vel = vel.replace(",","");
        vx = Integer.valueOf(String.valueOf(vel.charAt(0)));
        vy = Integer.valueOf(String.valueOf(vel.charAt(1)));

        place();
    }

    public void place(){
        try {
            System.out.println("I am going to place a ball" + " at: (" + x + "," + y + ")");
            model.placeGizmo(GizmoType.BALL,model.getTileAt(x,y), null);
        } catch (GizmoPlacementNotValidException e1) {
            e1.printStackTrace();
        }

    }
}
