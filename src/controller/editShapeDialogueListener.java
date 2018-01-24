package controller;

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
        newShapeDialogue shapeDi = new newShapeDialogue(fr);
    }
}
