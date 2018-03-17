package controller;

import model.GizmoNotFoundException;
import model.Model;
import model.Tile;
import model.TileCoordinatesNotValid;
import model.gizmo.GizmoPropertyException;
import model.gizmo.GizmoPropertyType;
import model.gizmo.GizmoType;
import view.*;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;

public class FindEditorListener implements MouseListener {
    private Model m;
    private JFrame frame;
    private JPanel panel;
    String type;

    FindEditorListener(JFrame f, Model model, JPanel p){
        System.out.println("hi");
        m = model;
        frame = f;
        panel = p;
        type = "";

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Clicked: " + e.getX() + ", " + e.getY());
        try {
            int offsetx = (frame.getWidth() - panel.getWidth())/2;
            int offsety = (frame.getHeight() - panel.getHeight())/3;
            int x = e.getX() - offsetx;
            int y = e.getY() - offsety;
            int[] xy = getXYNear(x,y);

            System.out.println("Getting tile at: " + xy[0] + ", " +  xy[1]);
            Tile t = m.getTileAt(xy[0], xy[1]);

            if(t.isOccupied()){
               GizmoType g =  t.getGizmo().getType();
               if(getType().equals("Edit")){
                   switch (g){
                       case BALL:
                           new EditBallDialogue(frame, "Edit", m, t.getGizmo());
                           break;
                       case ABSORBER:
                           new EditAbsorberDialogue(frame, "Edit", m, t.getGizmo());
                       case FLIPPER:
                           new EditFlipperDialogue(frame, "Edit", m, t.getGizmo());
                       default:
                           String s = g.name().substring(0,g.name().indexOf("_")).toLowerCase();
                           new EditShapeDialogue(frame, s, "Edit", m, t.getGizmo());
                   }
               }else if(getType().equals("Delete")){
                   try {
                       m.deleteGizmo(t.getGizmo().getProperty(GizmoPropertyType.NAME));
                   } catch (GizmoNotFoundException e1) {
                       JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Cannot find gizmo", "Error", JOptionPane.ERROR_MESSAGE);
                   }
               }else if (getType().equals("Rotate")){
                   try {
                       m.rotateGizmoBy_Deg(t.getGizmo().getProperty(GizmoPropertyType.NAME), 90.0);
                   } catch (GizmoNotFoundException e1) {
                       JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Cannot find gizmo", "Error", JOptionPane.ERROR_MESSAGE);
                   } catch (GizmoPropertyException e1) {
                       JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Wrong gizmo property", "Error", JOptionPane.ERROR_MESSAGE);
                   }
               } else if(getType().equals("Key")){
                   JOptionPane.showMessageDialog(frame, "To connect this gizmo to a key, press that key now", "Key Trigger", JOptionPane.INFORMATION_MESSAGE);
                   new TriggerKeyListener(frame, m, t);
               }else {
                   // connect two
               }
            }
        } catch (TileCoordinatesNotValid tileCoordinatesNotValid) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Tile coordinates are not valid", "Error", JOptionPane.ERROR_MESSAGE);
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

    public String getType() {
        return type;
    }

    public void setType(String t){
        type = t;
    }
}
