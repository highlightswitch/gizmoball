package controller;

import model.GizmoPlacementNotValidException;
import model.Model;
import model.TileCoordinatesNotValid;
import model.gizmo.GizmoType;

import java.awt.*;

public class PlaceAbsorberListener {
    private Model model;
    private int sx;
    private int sy;
    private int ex;
    private int ey;
    private int width;
    private int height;
    private String name;
    private int id = 0;
    Color c;

    public PlaceAbsorberListener(String start, String end, Color c, Model m){
        model = m;

        name = "Absorber" + id;
        id++;

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
            model.placeGizmo(GizmoType.ABSORBER,model.getTileAt(sx,sy),new String[] {name, String.valueOf(width), String.valueOf(height) });
        } catch (GizmoPlacementNotValidException | TileCoordinatesNotValid e) {
            e.printStackTrace();
        }
    }

}
