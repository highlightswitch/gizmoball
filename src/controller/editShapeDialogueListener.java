package controller;

import view.editShapeDialogue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class editShapeDialogueListener implements ActionListener {
    JFrame fr;
    public editShapeDialogueListener(JFrame frame){
        fr = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        editShapeDialogue shapeDi = new editShapeDialogue(fr);
    }
}
