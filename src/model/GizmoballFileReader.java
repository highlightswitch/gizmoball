package model;

import model.gizmo.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class GizmoballFileReader {

    private File file;
    private Model model;

    private final String defaultColour = "[r=255,g=255,b=255]";
    private final String altColour = "[r=255,g=0,b=0]";

    public GizmoballFileReader(File file) throws MalformedGizmoballFileException {
        model = new Model();
        this.file = file;

        readFile();
    }

    private void readFile() throws MalformedGizmoballFileException {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.length() > 0) {
                    StringTokenizer st = new StringTokenizer(line);
                    ArrayList<String> tokens = new ArrayList<>();
                    while (st.hasMoreTokens()) {
                        tokens.add(st.nextToken());
                    }
                    if (checkLine(tokens)) {
                        try {
                            command(tokens);
                        } catch (GizmoPlacementNotValidException | TileCoordinatesNotValid e) {
                            e.printStackTrace();
                        } catch (GizmoNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkLine(ArrayList<String> command) {
        System.out.println(Arrays.deepToString(command.toArray()));
        switch (command.get(0)) {
            case "Square":
            case "Circle":
            case "Triangle":
            case "RightFlipper":
            case "LeftFlipper":
                if(!model.checkName(command.get(1)) && checkInt(command.get(2)) && checkInt(command.get(3)) && command.size() == 4){
                    return true;
                } else {
                    return false;
                }
            case "Absorber":
                if(!model.checkName(command.get(1)) && checkInt(command.get(2)) && checkInt(command.get(3)) && checkInt(command.get(4)) && checkInt(command.get(5)) && Integer.parseInt(command.get(2)) < Integer.parseInt(command.get(4)) && Integer.parseInt(command.get(3)) < Integer.parseInt(command.get(5)) && command.size() == 6){
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
            case "Delete":
                if(model.checkName(command.get(1)) && command.size() == 2){
                    return true;
                } else {
                    return false;
                }
            case "Move":
                if(model.checkName(command.get(1)) && checkInt(command.get(2)) && checkInt(command.get(3)) && command.size() == 4){
                    return true;
                } else {
                    return false;
                }
            case "Connect":
                if(model.checkName(command.get(1)) && model.checkName(command.get(2)) && command.size() == 3){
                    return true;
                } else {
                    return false;
                }
            case "KeyConnect":
                if(command.get(1).equals("key") && checkInt(command.get(2)) && model.checkName(command.get(4)) && command.size() == 5){
                    return true;
                } else {
                    return false;
                }
            case "Gravity":
//                if(model.checkName(command.get(1)) && checkFloat(command.get(2)) && command.size() == 2){
//                    return true;
//                } else {
//                    return false;
//                }
                return true;
            case "Friction":
                //check friction format
//                if(model.checkName(command.get(1)) && checkFloat(command.get(2)) && command.size() == 2){
//                    return true;
//                } else {
//                    return false;
//                }
                return true;
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


    private void command(ArrayList<String> command) throws GizmoPlacementNotValidException, TileCoordinatesNotValid, GizmoNotFoundException, MalformedGizmoballFileException {
        Gizmo gizmo;
        GizmoType type;
        Tile tile;
        String[] propertyValues;
        switch (command.get(0)) {
            case "Square":
                type = GizmoType.SQUARE_BUMPER;
                tile = model.getTileAt(Integer.parseInt(command.get(2)), Integer.parseInt(command.get(3)));
                propertyValues = new String[]{command.get(1), "0", defaultColour, defaultColour, altColour};

                model.placeGizmo(type, tile, propertyValues);
                break;
            case "Circle":
                type = GizmoType.CIRCLE_BUMPER;
                tile = model.getTileAt(Integer.parseInt(command.get(2)), Integer.parseInt(command.get(3)));
                propertyValues = new String[]{command.get(1), "0", defaultColour, defaultColour, altColour};

                model.placeGizmo(type, tile, propertyValues);
                break;
            case "Triangle":
                type = GizmoType.TRIANGLE_BUMPER;
                tile = model.getTileAt(Integer.parseInt(command.get(2)), Integer.parseInt(command.get(3)));
                propertyValues = new String[]{command.get(1), "0", defaultColour, defaultColour, altColour};

                model.placeGizmo(type, tile, propertyValues);
                break;
            case "RightFlipper":
                type = GizmoType.FLIPPER;
                tile = model.getTileAt(Integer.parseInt(command.get(2)), Integer.parseInt(command.get(3)));
                propertyValues = new String[]{command.get(1), "0", "false", defaultColour, defaultColour, altColour};

                model.placeGizmo(type, tile, propertyValues);
                break;
            case "LeftFlipper":
                type = GizmoType.FLIPPER;
                tile = model.getTileAt(Integer.parseInt(command.get(2)), Integer.parseInt(command.get(3)));
                propertyValues = new String[]{command.get(1), "0", "true", defaultColour, defaultColour, altColour};

                model.placeGizmo(type, tile, propertyValues);
                break;
            case "Absorber":
                int x1 = Integer.parseInt(command.get(2));
                int y1 = Integer.parseInt(command.get(3));
                int x2 = Integer.parseInt(command.get(4));
                int y2 = Integer.parseInt(command.get(5));
                int w = x2 - x1;
                int h = y2 - y1;

                type = GizmoType.ABSORBER;
                tile = model.getTileAt(x1, y1);
                propertyValues = new String[]{command.get(1), String.valueOf(w), String.valueOf(h), defaultColour, defaultColour, altColour};

                model.placeGizmo(type, tile, propertyValues);
                break;
            case "Ball":
                type = GizmoType.BALL;
                tile = model.getTileNear ( Double.parseDouble(command.get(2)), Double.parseDouble(command.get(3)));
                propertyValues = new String[]{command.get(1), command.get(4), command.get(5), defaultColour, defaultColour, altColour };

                model.placeBall(Double.parseDouble(command.get(2)), Double.parseDouble(command.get(3)), propertyValues);

              //  model.placeGizmo(type, tile, propertyValues);
                break;
            case "Rotate":
                try {
                    model.rotateGizmoBy_Deg(command.get(1), 90);
                } catch (GizmoNotFoundException e) {
                    e.printStackTrace();
                } catch (GizmoPropertyException e) {
                    e.printStackTrace();
                }
                break;
            case "Delete": try {
                model.deleteGizmo(command.get(1));
            } catch (GizmoNotFoundException e) {
                e.printStackTrace();
            }
                break;
            case "Move":  try {
                model.moveGizmo(command.get(1), model.getTileAt(Integer.parseInt(command.get(2)), Integer.parseInt(command.get(3))));
            } catch (GizmoNotFoundException e) {
                e.printStackTrace();
            }
                break;
            case "Connect":
                model.connect(command.get(1), command.get(2));
                break;
            case "KeyConnect":
                TriggerType triggerType;
                switch (command.get(3)) {
                    case "up":
                        triggerType = TriggerType.KEY_UP;
                        break;
                    case "down":
                        triggerType = TriggerType.KEY_DOWN;
                        break;
                    default:
                        throw new MalformedGizmoballFileException("KeyConnection line is malformed");
                }
                model.connect(Integer.parseInt(command.get(2)), triggerType, command.get(4));
                break;
            case "Gravity": try {
                model.setGravityConstant(Float.parseFloat(command.get(1)));
            } catch (ModelPropertyException e){
                e.printStackTrace();
            }
                break;
            case "Friction": try {
                model.setFrictionConstants(
                        new double[]{
                                Float.parseFloat(command.get(1)),
                                Float.parseFloat(command.get(2))
                        }
                );
            } catch (ModelPropertyException e){
                e.printStackTrace();
            }
                break;
        }
    }

    public Model getModel() {
        return model;
    }
}
