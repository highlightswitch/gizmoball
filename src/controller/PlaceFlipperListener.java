package controller;

import model.GizmoPlacementNotValidException;
import model.Model;
import model.TileCoordinatesNotValid;
import model.gizmo.GizmoType;

import java.awt.*;

public class PlaceFlipperListener {
    private Model model;
    private int sx;
    private int sy;
    private String di;
    private String name;
    private int id = 0;
    private String color;

    public PlaceFlipperListener(String pos, String direction, Color c, Model m){
        model = m;

        color = c.toString();
        color = color.substring(color.indexOf("["));

        name = "Flipper" + id;
        id++;

        String spos = pos.replace("(", "");
        spos  = spos.replace(")", "");

        String posXS = spos.substring(0, spos.indexOf(","));
        String posYS = spos.substring(spos.indexOf(","));
        posYS = posYS.replace(",", "");
        sx = Integer.valueOf(posXS);
        sy = Integer.valueOf(posYS);

        if(direction.equals("Left")){
            di = "true";
        } else {
            di = "false";
        }



        place();

    }

    private void place() {
        try {
            model.placeGizmo(GizmoType.FLIPPER, model.getTileAt(sx,sy), new String[]{name, String.valueOf(0), di,color,color, ""});
        } catch (GizmoPlacementNotValidException e) {
            e.printStackTrace();
        } catch (TileCoordinatesNotValid tileCoordinatesNotValid) {
            tileCoordinatesNotValid.printStackTrace();
        }
    }
}
