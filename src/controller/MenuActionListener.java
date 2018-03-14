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
                new EditShapeDialogue(frame, e.getActionCommand(), "Add", controller.getModel());
                break;
            case "Ball":
                new EditBallDialogue(frame, "Add", controller.getModel());
                break;
            case "Absorber":
                new EditAbsorberDialogue(frame, "Add", controller.getModel());
                break;
            case "Flipper":
                new EditFlipperDialogue(frame, "Add", controller.getModel());
                break;
            case "Rotate":
                break;
            case "Delete":
                break;
            case "Edit":
                System.out.println("you are in editing mode");
                System.out.println("adding listener to " + panel.hashCode());
                frame.addMouseListener(new FindEditorListener(frame, controller.getModel(), panel));
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
                controller.stopTimer();
                controller.switchToBuildView();
                break;
            case "Run":
                controller.switchToRunView();
                break;
        }
    }
}
