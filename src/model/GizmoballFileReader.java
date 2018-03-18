package model;

import model.gizmo.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class GizmoballFileReader {

    private Model model;

    private final String defaultColour = "[r=255,g=255,b=255]";
    private final String altColour = "[r=255,g=0,b=0]";

    public GizmoballFileReader(String str) throws TileCoordinatesNotValid, MalformedGizmoballFileException, GizmoPropertyException, GizmoNotFoundException, GizmoPlacementNotValidException {
        this.model = new Model();
        readFile(str);
    }

    public GizmoballFileReader(File file) throws MalformedGizmoballFileException, TileCoordinatesNotValid, GizmoPropertyException, GizmoNotFoundException, GizmoPlacementNotValidException {
        this.model = new Model();

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String text = "";
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                text += "\n" + line;
            }
            readFile(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFile(String str) throws MalformedGizmoballFileException, GizmoPropertyException, GizmoPlacementNotValidException, TileCoordinatesNotValid, GizmoNotFoundException {
        StringTokenizer fileTokenizer = new StringTokenizer(str, "\n");
        String line;
        while (fileTokenizer.hasMoreTokens()) {
            line = fileTokenizer.nextToken();
			if (line.length() > 0) {
				StringTokenizer lineTokenizer = new StringTokenizer(line);
				ArrayList<String> tokens = new ArrayList<>();
				while (lineTokenizer.hasMoreTokens()) {
					tokens.add(lineTokenizer.nextToken());
				}
				if (checkLine(tokens)) {
					command(tokens);
				}
			}
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
                } else if(model.checkName(command.get(1)) && checkFloat(command.get(2)) && checkFloat(command.get(3)) && command.size() == 4) {
                    return true;
                }else{
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
            case "Action":
                if(model.checkName(command.get(1)) && ( command.get(2).equals("CHANGE_COLOUR") || command.get(2).equals("FLIP_FLIPPER") || command.get(2).equals("FIRE_FROM_ABSORBER")) && command.size() == 3){
                    return true;
                } else {
                    return false;
                }
            case "Colour":
                if(model.checkName(command.get(1)) && command.size() == 11){
                    for(int i = 2; i < command.size(); i++)
                        if(!checkInt(command.get(i)))
                            return false;
                    return true;
                } else {
                    return false;
                }
            case "Gravity":
                if(checkFloat(command.get(1)) && command.size() == 2){
                    return true;
                } else {
                    return false;
                }
            case "Friction":
                if(checkFloat(command.get(1)) && checkFloat(command.get(2)) && command.size() == 3){
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


    private void command(ArrayList<String> command) throws GizmoPlacementNotValidException, TileCoordinatesNotValid, GizmoNotFoundException, MalformedGizmoballFileException, GizmoPropertyException {
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
                propertyValues = new String[]{command.get(1), command.get(4), command.get(5), defaultColour, defaultColour, altColour };
                model.placeBall(Double.parseDouble(command.get(2)), Double.parseDouble(command.get(3)), propertyValues);
                break;
            case "Rotate":
                model.rotateGizmoBy_Deg(command.get(1), 90);
                break;
            case "Delete":
                model.deleteGizmo(command.get(1));
                break;
            case "Move":
                if(checkInt(command.get(2))) {
                    model.moveGizmo(command.get(1), model.getTileAt(Integer.parseInt(command.get(2)), Integer.parseInt(command.get(3))));
                } else {
                    model.moveGizmo(command.get(1), Float.parseFloat(command.get(2)), Float.parseFloat(command.get(3)));
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
            case "Action":
                GizmoActionType actionType;
                switch (command.get(2)){
                    case "CHANGE_COLOUR":
                        actionType = GizmoActionType.CHANGE_COLOUR;
                        break;
                    case "FLIP_FLIPPER":
                        actionType = GizmoActionType.FLIP_FLIPPER;
                        break;
                    case "FIRE_FROM_ABSORBER":
                        actionType = GizmoActionType.FIRE_FROM_ABSORBER;
                        break;
                    default:
                        throw new MalformedGizmoballFileException("Action type is malformed");
                }
                model.setGizmoAction(command.get(1), actionType);
                break;
            case "Colour":
                String cr = command.get(3);
                String cg = command.get(4);
                String cb = command.get(5);
                String currentColour = "[r="+cr+",g="+cg+",b="+cb+"]";

                String dr = command.get(5);
                String dg = command.get(6);
                String db = command.get(7);
                String defaultColour = "[r="+dr+",g="+dg+",b="+db+"]";

                String ar = command.get(3);
                String ag = command.get(4);
                String ab = command.get(5);
                String altColour = "[r="+ar+",g="+ag+",b="+ab+"]";

                System.out.println("before " + model.getGizmoProperty(command.get(1), GizmoPropertyType.CURRENT_COLOUR));

                model.setGizmoProperty(command.get(1), GizmoPropertyType.CURRENT_COLOUR, currentColour);
                model.setGizmoProperty(command.get(1), GizmoPropertyType.DEFAULT_COLOUR, defaultColour);
                model.setGizmoProperty(command.get(1), GizmoPropertyType.ALT_COLOUR, altColour);
                System.out.println(model.getGizmoProperty(command.get(1), GizmoPropertyType.CURRENT_COLOUR));
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
