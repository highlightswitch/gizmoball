package controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import model.*;
import view.*;

public class MenuActionListener implements ActionListener {

    private MainController controller;
    private JFrame frame;
    Model model;

    MenuActionListener(MainController c, JFrame f, Model m){
        controller = c;
        frame = f;
        model = m;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "Load":
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        GizmoballFileReader fileReader = new GizmoballFileReader(selectedFile);
                        controller.setModel(fileReader.getModel());
                        controller.getGameFrame().setModel(fileReader.getModel());
                        controller.getBoard().setModel(fileReader.getModel());
                        controller.switchToRunView();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                break;
            case "Save":
                //
                break;
            case "Circle":
            case "Square":
            case "Triangle":
                EditShapeDialogue shapeDialogue = new EditShapeDialogue(frame, e.getActionCommand(), "Add", model);
                break;
            case "Ball":
                EditBallDialogue ballDialogue = new EditBallDialogue();
                break;
            case "Absorber":
                EditAbsorberDialogue absorberDialogue = new EditAbsorberDialogue();
                break;
            case "Rotate":
                break;
            case "Delete":
                break;
            case "Edit":
                break;
            case "Gravity":
                GravitySlider g = new GravitySlider(frame);
                break;
            case "Friction":
                FrictionSlider f = new FrictionSlider(frame);
                break;
            case "Quit":
                System.exit(0);
                break;
            case "Build":
                controller.switchToBuildView();
                break;
            case "Run":
                controller.switchToRunView();
                break;
        }
    }
}
