package controller;

import model.GizmoPlacementNotValidException;
import model.Model;
import model.Tile;
import model.TileCoordinatesNotValid;
import model.gizmo.GizmoType;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FindAdderListener implements MouseListener {
    private Model m;
    private JFrame frame;
    private JPanel panel;
    private String type;

    FindAdderListener(JFrame f, Model model, JPanel p, String t){
        System.out.println("hello");
        m = model;
        frame = f;
        panel = p;
        type = t;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("add Clicked: " + e.getX() + ", " + e.getY());
        try {
            int offsetx = (frame.getWidth() - panel.getWidth())/2;
            int offsety = (frame.getHeight() - panel.getHeight())/3;

            int x = e.getX() - offsetx;
            int y = e.getY() - offsety;

            int[] xy = getXYNear(x,y);

            System.out.println("Getting tile at: " + xy[0] + ", " +  xy[1]);
            Tile t = m.getTileAt(xy[0], xy[1]);

            if(!t.isOccupied()){
                switch (type){
                    case "Ball":
                        try {
                            m.placeGizmo(GizmoType.BALL, t, null);
                        } catch (GizmoPlacementNotValidException e1) {
                            e1.printStackTrace();
                        }
                        break;
                    case "Absorber":
                        try {
                            m.placeGizmo(GizmoType.ABSORBER, t, null);
                        } catch (GizmoPlacementNotValidException e1) {
                            e1.printStackTrace();
                        }
                    case "Flipper":
                        try {
                            m.placeGizmo(GizmoType.FLIPPER, t, null);
                        } catch (GizmoPlacementNotValidException e1) {
                            e1.printStackTrace();
                        }
                    case "Circle":
                        try {
                            m.placeGizmo(GizmoType.CIRCLE_BUMPER, t, null);
                        } catch (GizmoPlacementNotValidException e1) {
                            e1.printStackTrace();
                        }
                        break;
                    case "Triangle":
                        try {
                            m.placeGizmo(GizmoType.TRIANGLE_BUMPER, t, null);
                        } catch (GizmoPlacementNotValidException e1) {
                            e1.printStackTrace();
                        }
                        break;
                    case "Square":
                        try {
                            m.placeGizmo(GizmoType.SQUARE_BUMPER, t, null);
                        } catch (GizmoPlacementNotValidException e1) {
                            e1.printStackTrace();
                        }
                        break;
                }
                }
            System.out.println("tile occupied");
        } catch (TileCoordinatesNotValid tileCoordinatesNotValid) {
            tileCoordinatesNotValid.printStackTrace();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public int[] getXYNear(double x, double y){
        Double px = Math.floor(x/25) - 1;
        Double py = Math.floor(y/25) - 1;

        if(px >= 0 && py >= 0){
            return new int[]{px.intValue(),py.intValue()};
        }else {
            return new int[]{0,0};
        }
    }
}
