package model;

import model.gizmo.Gizmo;
import model.gizmo.GizmoPropertyException;
import model.gizmo.GizmoPropertyType;
import model.gizmo.GizmoType;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static java.lang.Integer.parseInt;

public class GizmoballFileReader {

    private File file;
    private Model model;

    public GizmoballFileReader(File file) {
        model = new Model();
        this.file = file;

        readFile();
    }

    private void readFile() {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line);
                ArrayList<String> tokens = new ArrayList<>();
                while(st.hasMoreTokens()){
                    tokens.add(st.nextToken());
                }
              //  System.out.println(tokens);
                if(checkLine(tokens))
                command(tokens);
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkLine(ArrayList<String> command) {
        switch (command.get(0)) {
            case "Square":
            case "Circle":
            case "Triangle":
            case "RightFlipper":
            case "LeftFlipper":
                System.out.println("bumper check");
                if(!model.checkName(command.get(1)) && checkInt(command.get(2)) && checkInt(command.get(3)) && command.size() == 4){
                    return true;
                } else {
                    return false;
                }
            case "Absorber":
                if(!model.checkName(command.get(1)) && checkInt(command.get(2)) && checkInt(command.get(3)) && checkInt(command.get(4)) && checkInt(command.get(5)) && command.size() == 6){
                    return true;
                } else {
                    return false;
                }
            case "Ball":
                if(!model.checkName(command.get(1)) && checkFloat(command.get(2)) && checkFloat(command.get(3)) && checkFloat(command.get(4)) && checkFloat(command.get(5)) && command.size() == 6){
                    return true;
                } else {
                    return false;
                }
            case "Rotate":
                if(model.checkName(command.get(1)) && command.size() == 2){
                    return true;
                } else {
                    return false;
                }
                default: return false;
        }
    }

    private boolean checkInt(String string){
        try {
            Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean checkFloat(String string){
        try {
            Float.parseFloat(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    private void command(ArrayList<String> command) {
        Gizmo gizmo;
        switch (command.get(0)) {
            case "Square":
                model.placeGizmo(GizmoType.SQUARE_BUMPER, model.getTileAt(Integer.parseInt(command.get(2)), Integer.parseInt(command.get(3))), new String[]{command.get(1), "0"});
                break;
            case "Circle":
                model.placeGizmo(GizmoType.CIRCLE_BUMPER, model.getTileAt(Integer.parseInt(command.get(2)), Integer.parseInt(command.get(3))), new String[]{command.get(1), "0"});
                break;
            case "Triangle":
                model.placeGizmo(GizmoType.TRIANGLE_BUMPER, model.getTileAt(Integer.parseInt(command.get(2)), Integer.parseInt(command.get(3))), new String[]{command.get(1), "0"});
                break;
            case "RightFlipper":
                model.placeGizmo(GizmoType.RIGHT_FLIPPER, model.getTileAt(Integer.parseInt(command.get(2)), Integer.parseInt(command.get(3))), new String[]{command.get(1), "0"});
                break;
            case "LeftFlipper":
                model.placeGizmo(GizmoType.LEFT_FLIPPER, model.getTileAt(Integer.parseInt(command.get(2)), Integer.parseInt(command.get(3))), new String[]{command.get(1), "0"});
                break;
            case "Absorber":
                double x1 = Integer.parseInt(command.get(2));
                double y1 = Integer.parseInt(command.get(3));
                double x2 = Integer.parseInt(command.get(4));
                double y2 = Integer.parseInt(command.get(5));
                double w = x2 - x1;
                double h = y2 - y1;

                model.placeGizmo(GizmoType.ABSORBER, model.getTileAt(x1,y1), new String[]{command.get(1), String.valueOf(w), String.valueOf(h)});
                break;
            case "Ball":
                model.placeGizmo(GizmoType.BALL, model.getTileAt( Float.parseFloat(command.get(2)), Float.parseFloat(command.get(3))), new String[]{command.get(1), command.get(4), command.get(5)});
                break;
            case "Rotate":
                try {
                    gizmo = model.getGizmoByName(command.get(1));
                    gizmo.rotate();
                } catch (GizmoNotFoundException e) {
                    e.printStackTrace();
                } catch (GizmoPropertyException e) {
                    e.printStackTrace();
                }
                break;
            case "Delete": //
                break;
            case "Move": //
                break;
            case "Connect": //
                break;
            case "KeyConnect": //
                break;
            case "Gravity": //
                break;
            case "Friction": //
                break;
        }
    }

    public Model getModel() {
        return model;
    }
}
