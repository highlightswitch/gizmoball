package controller;

import view.Board;
import view.buildView;
import view.gizmoFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class buildViewListener implements ActionListener {
    gizmoFrame fr;
    Board board;
    MainController cont;

    public buildViewListener(gizmoFrame frame, Board b, MainController c){
        fr = frame;
        board = b;
        cont = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fr.drawFrame(new buildView(fr.getFrame(), cont, board));
        fr.extendMenu();
        fr.getFrame().getContentPane().revalidate();
        fr.getFrame().getContentPane().repaint();
        fr.getFrame().getJMenuBar().revalidate();
        fr.getFrame().getJMenuBar().repaint();
    }
}
