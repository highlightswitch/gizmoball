package controller;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class addPopupListener extends MouseAdapter {
    JPopupMenu popup;

    public addPopupListener(JPopupMenu p){
        popup = p;
    }
    public void mousePressed(MouseEvent e) {
        popup.show(e.getComponent(), e.getX() - 90, e.getY() - 65);
    }

    public void mouseReleased(MouseEvent e) {
        //
    }
}
