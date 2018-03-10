package controller;

import model.GizmoPlacementNotValidException;
import model.Model;
import model.TileCoordinatesNotValid;
import model.gizmo.GizmoType;

import java.awt.*;

public class PlaceAbsorberListener {
    Model model;
    int sx;
    int sy;
    int ex;
    int ey;
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

        place();
    }

    private void place() {
        try {
            model.placeGizmo(GizmoType.ABSORBER,model.getTileAt(sx,sy),null);
        } catch (GizmoPlacementNotValidException | TileCoordinatesNotValid e) {
            e.printStackTrace();
        }
    }

}
