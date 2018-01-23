package controller;

import view.gizmoFrame;
import view.runView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class runViewListener implements ActionListener {
    gizmoFrame fr;

    public runViewListener(gizmoFrame frame){
        fr = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fr.drawFrame(new runView());
        fr.getFrame().getContentPane().revalidate();
        fr.getFrame().getContentPane().repaint();
        fr.compressMenu();
        fr.getFrame().getJMenuBar().revalidate();
        fr.getFrame().getJMenuBar().repaint();
    }
}
