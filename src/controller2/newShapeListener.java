package controller2;

import gui.newShape;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class newShapeListener implements ActionListener {
    JFrame frame;
    public newShapeListener(JFrame fr){
        frame = fr;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        newShape ns = new newShape(frame);
        System.out.println("hi");
        // get all info from ns to return to model
    }
}
