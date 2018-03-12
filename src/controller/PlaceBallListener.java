package controller;

import model.GizmoPlacementNotValidException;
import model.IModel;
import model.Model;
import model.TileCoordinatesNotValid;
import model.gizmo.GizmoType;

import java.awt.*;

public class PlaceBallListener {
    private IModel model;
    Color color;
    private String name;
    private int id;
    private int x;
    private int y;
    private String vx;
    private String vy;

    public PlaceBallListener(String position, String velocity, Color color, Model m){
        model = m;
        name = "Ball" + id;
        id++;

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

        //vx = Integer.valueOf(velX);
        //vy = Integer.valueOf(velY);

        place();
    }

    private void place(){
        try {
            //System.out.println("I am going to place a ball" + " at: (" + x + "," + y + ")");
            model.placeGizmo(GizmoType.BALL,model.getTileAt(x,y), new String[]{name, vx, vy});
        } catch (GizmoPlacementNotValidException | TileCoordinatesNotValid e1) {
            e1.printStackTrace();
        }
    }
}
