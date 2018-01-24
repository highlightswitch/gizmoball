package controller;

import view.newShapeDialogue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class newShapeDialogueListener implements ActionListener {
    JFrame fr;
    public newShapeDialogueListener(JFrame frame){
        fr = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        newShapeDialogue shapeDi = new newShapeDialogue(fr);
    }
}
