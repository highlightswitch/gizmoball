package controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import model.*;

public class MenuActionListener implements ActionListener {

    private MainController controller;

    MenuActionListener(MainController c){
        controller = c;
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
                     //   controller.refreshView();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }


                }
                break;
            case "Save":
                //
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
