package controller;

import view.buildView;
import view.gizmoFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class buildViewListener implements ActionListener {
    gizmoFrame fr;

    public buildViewListener(gizmoFrame frame){
        fr = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fr.drawFrame(new buildView(fr.getFrame()));
        fr.extendMenu();
        fr.getFrame().getContentPane().revalidate();
        fr.getFrame().getContentPane().repaint();
        fr.getFrame().getJMenuBar().revalidate();
        fr.getFrame().getJMenuBar().repaint();
    }
}
