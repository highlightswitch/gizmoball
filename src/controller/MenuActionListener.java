package controller;

import model.GizmoballFileReader;
import model.Model;
import view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileDescriptor;

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
                EditShapeDialogue shapeDialogue = new EditShapeDialogue(frame, e.getActionCommand(), "Add", controller.getModel());
                break;
            case "Ball":
                EditBallDialogue ballDialogue = new EditBallDialogue(frame, "Add", controller.getModel());
                break;
            case "Absorber":
                EditAbsorberDialogue absorberDialogue = new EditAbsorberDialogue(frame, "Add", controller.getModel());
                break;
            case "Rotate":
                break;
            case "Delete":
                break;
            case "Edit":
                frame.addMouseListener(new FindEditorListener());
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
