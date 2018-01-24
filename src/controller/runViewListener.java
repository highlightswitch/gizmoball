package controller;

import view.Board;
import view.gizmoFrame;
import view.runView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class runViewListener implements ActionListener {
    gizmoFrame fr;
    Board board;
    MainController cont;

    public runViewListener(gizmoFrame frame, Board b, MainController c){
        fr = frame;
        board = b;
        cont = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fr.drawFrame(new runView( cont, board));
        fr.getFrame().getContentPane().revalidate();
        fr.getFrame().getContentPane().repaint();
        fr.compressMenu();
        fr.getFrame().getJMenuBar().revalidate();
        fr.getFrame().getJMenuBar().repaint();
    }
}
