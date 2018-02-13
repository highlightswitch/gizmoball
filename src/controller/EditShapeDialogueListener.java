package controller;

import view.EditShapeDialogue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditShapeDialogueListener implements ActionListener {
    JFrame fr;
    public EditShapeDialogueListener(JFrame frame){
        fr = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        EditShapeDialogue shapeDi = new EditShapeDialogue(fr);
    }
}
