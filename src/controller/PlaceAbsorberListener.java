package controller;

import model.GizmoNotFoundException;
import model.GizmoPlacementNotValidException;
import model.Model;
import model.TileCoordinatesNotValid;
import model.gizmo.GizmoPropertyException;
import model.gizmo.GizmoPropertyType;
import model.gizmo.GizmoType;

import java.awt.*;

public class PlaceAbsorberListener {
    Model model;
    int sx;
    int sy;
    int ex;
    int ey;
    int width;
    int height;
    Color c;

    public PlaceAbsorberListener(String start, String end,Color c, Model m){
        model = m;

        String spos = start.replace("(", "");
        spos  = spos.replace(")", "");

        String posXS = spos.substring(0, spos.indexOf(","));
        String posYS = spos.substring(spos.indexOf(","));
        posYS = posYS.replace(",", "");
        sx = Integer.valueOf(posXS);
        sy = Integer.valueOf(posYS);

        String epos = end.replace("(", "");
        epos  = epos.replace(")", "");
        String posXE = epos.substring(0, epos.indexOf(","));
        String posYE = epos.substring(epos.indexOf(","));
        posYE = posYE.replace(",", "");
        ex = Integer.valueOf(posXE);
        ey = Integer.valueOf(posYE);

        width = sx - ex;
        height = sy - ey;

        place();
    }

    private void place() {
        try {
            model.placeGizmo(GizmoType.ABSORBER,model.getTileAt(sx,sy),null);
            model.setGizmoProperty("Absorber", GizmoPropertyType.WIDTH, String.valueOf(width));
            model.setGizmoProperty("Absorber", GizmoPropertyType.HEIGHT, String.valueOf(height));
        } catch (GizmoPlacementNotValidException | TileCoordinatesNotValid e) {
            e.printStackTrace();
        } catch (GizmoPropertyException e) {
            e.printStackTrace();
        } catch (GizmoNotFoundException e) {
            e.printStackTrace();
        }
    }

}
