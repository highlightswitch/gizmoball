package controller;

import model.IModel;
import model.Model;
import model.Tile;
import model.TileCoordinatesNotValid;
import model.gizmo.Gizmo;
import model.gizmo.GizmoType;
import view.EditAbsorberDialogue;
import view.EditBallDialogue;
import view.EditShapeDialogue;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FindEditorListener implements MouseListener {
    Model m;
    JFrame frame;

    public FindEditorListener(JFrame f, Model model){
        m = model;
        frame = f;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Clicked: " + e.getX() + ", " + e.getY());
        try {
            Tile t = m.getTileNear(e.getX(), e.getY());
            if(t.isOccupied()){
               GizmoType g =  t.getGizmo().getType();
               switch (g){
                   case BALL:
                       new EditBallDialogue(frame, "Edit", m);
                       break;
                   case ABSORBER:
                       new EditAbsorberDialogue(frame, "Edit", m);
                   default:
                       new EditShapeDialogue(frame, g.toString(), "Edit", m);
               }
            }
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
}
