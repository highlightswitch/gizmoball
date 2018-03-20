package controller;

import view.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MenuActionListener implements ActionListener {

    private MainController controller;
    private JFrame frame;

    MenuActionListener(MainController controller, JFrame frame){
        this.controller = controller;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        MouseHandler mouse = controller.getMouseHandler();

        mouse.setType(e.getActionCommand());

        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("GIZMO FILES", "gizmo");
        fileChooser.setFileFilter(filter);

        switch (e.getActionCommand()){
            case "Load":
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        controller.setModel(LoadingHandler.fileToModel(selectedFile));
                        controller.switchToRunView();
                    } catch (Exception ex) {

                        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Loading failed", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
            case "Save":
                int status = fileChooser.showSaveDialog(null);
                if (status == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        String fileName = selectedFile.getCanonicalPath();
                        String game = controller.getBuildModeSave();
                          if (!fileName.endsWith(".gizmo")) {
                              fileName = fileName + ".gizmo";
                          }
                        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                        writer.write(game);
                        writer.close();
                    } catch (IOException ex) {
                    }
                }
                break;
            case "Circle":
            case "Square":
            case "Triangle":
                new EditShapeDialogue(controller, frame, e.getActionCommand(), "Add", null);
                break;
            case "Ball":
                new EditBallDialogue(controller, frame, "Add", null);
                break;
            case "Absorber":
                new EditAbsorberDialogue(controller, frame, "Add", null);
                break;
            case "Flipper":
                new EditFlipperDialogue(controller, frame, "Add", null);
                break;
            case "Rotate":
                controller.getView().setMessage("Click the gizmo you want to rotate");
                mouse.setMode("Edit");
                break;
            case "Delete":
                controller.getView().setMessage("Click the gizmo you want to delete");
                mouse.setMode("Edit");
                break;
            case "Edit":
                controller.getView().setMessage("Click the gizmo you want to edit");
                mouse.setMode("Edit");
                break;
            case "Gravity":
                new GravitySlider(frame, controller.getModel());
                break;
            case "Friction":
                new FrictionSlider(frame, controller.getModel());
                break;
            case "Clear":
                controller.clearModel();
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
