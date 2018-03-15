package controller;

import model.GizmoballFileReader;
import view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MenuActionListener implements ActionListener {

    private MainController controller;
    private JFrame frame;
    private JPanel panel;

    MenuActionListener(MainController c, JFrame f, JPanel p){
        controller = c;
        frame = f;
        panel = p;
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
                new EditShapeDialogue(frame, e.getActionCommand(), "Add", controller.getModel(), null);
                break;
            case "Ball":
                new EditBallDialogue(frame, "Add", controller.getModel(), null);
                break;
            case "Absorber":
                new EditAbsorberDialogue(frame, "Add", controller.getModel(), null);
                break;
            case "Flipper":
                new EditFlipperDialogue(frame, "Add", controller.getModel(), null);
                break;
            case "Rotate":
                frame.addMouseListener(new FindEditorListener(frame, controller.getModel(), panel, "Rotate"));
                break;
            case "Delete":
                frame.addMouseListener(new FindEditorListener(frame, controller.getModel(), panel, "Delete"));
                break;
            case "Edit":
                frame.addMouseListener(new FindEditorListener(frame, controller.getModel(), panel, "Edit"));
                break;
            case "Gravity":
                new GravitySlider(frame, controller.getModel());
                break;
            case "Friction":
                new FrictionSlider(frame, controller.getModel());
                break;
            case "Quit":
                System.exit(0);
                break;
            case "Build":
                controller.switchToBuildView();
                controller.stopTimer();
                break;
            case "Run":
                controller.switchToRunView();
                break;
        }
    }
}
