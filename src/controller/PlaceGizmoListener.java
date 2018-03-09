package controller;

import model.GizmoPlacementNotValidException;
import model.Model;
import model.gizmo.GizmoType;

import java.awt.*;

public class PlaceGizmoListener{
    Model model;
    GizmoType g;
    String pos;
    Color color;
    String action;
    String trigger;
    int x;
    int y;

    public PlaceGizmoListener(String gizmo, String position, Color c, String a, String t, Model m){
        model = m;
        switch (gizmo){
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
        pos = position.replace("(", "");
        pos  = pos.replace(")", "");
        pos = pos.replace(",","");
        x = Integer.valueOf(String.valueOf(pos.charAt(0)));
        y = Integer.valueOf(String.valueOf(pos.charAt(1)));
        color = c;
        action = a;
        trigger = t;
        place();
    }

    public void place() {
        try {
            System.out.println("I am going to place " + g.toString() + " at: (" + x + "," + y + ")");
            model.placeGizmo(g,model.getTileAt(x,y), null);
        } catch (GizmoPlacementNotValidException e1) {
            e1.printStackTrace();
        }
    }
}
