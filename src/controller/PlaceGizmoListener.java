package controller;

import model.GizmoPlacementNotValidException;
import model.Model;
import model.gizmo.GizmoType;
import view.Board;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlaceGizmoListener implements ActionListener{
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
        x = pos.charAt(0);
        y = pos.charAt(1);
        color = c;
        action = a;
        trigger = t;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            model.placeGizmo(g,model.getTileAt(x,y), null);

        } catch (GizmoPlacementNotValidException e1) {
            e1.printStackTrace();
        }
    }
}
