package controller;

import model.GizmoPlacementNotValidException;
import model.Model;
import model.TileCoordinatesNotValid;
import model.gizmo.GizmoType;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddFlipperListener extends MouseAdapter {
    private int x;
    private int y;
    Model model;


    @Override
    public void mouseClicked(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        try {
            model.placeGizmo(GizmoType.FLIPPER, model.getTileAt(x, y), null);
        } catch (GizmoPlacementNotValidException exc){
            exc.printStackTrace();
        } catch (TileCoordinatesNotValid tileCoordinatesNotValid) {
            tileCoordinatesNotValid.printStackTrace();
        }


    }
}
