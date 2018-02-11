package model;

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
                command(tokens);
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void command(ArrayList<String> command) {
        switch (command.get(0)) {
            case "Square": //
                break;
            case "Circle": //
                break;
            case "Triangle": //
                break;
            case "RightFlipper": //
                break;
            case "LeftFlipper": //
                break;
            case "Absorber": //
                break;
            case "Ball":
                model.addBall(command.get(1), Float.parseFloat(command.get(2)), Float.parseFloat(command.get(3)), Float.parseFloat(command.get(4)), Float.parseFloat(command.get(5)));
                break;
            case "Rotate": //
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
