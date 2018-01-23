package controller;

import view.newShape;

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
        newShape shapeDi = new newShape(fr);
    }
}
