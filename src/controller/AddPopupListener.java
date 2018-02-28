package controller;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddPopupListener extends MouseAdapter {
    JPopupMenu popup;

    public AddPopupListener(JPopupMenu p){
        popup = p;
    }
    public void mousePressed(MouseEvent e) {
        popup.show(e.getComponent(), e.getX() - 140, e.getY() - 80);
    }

    public void mouseReleased(MouseEvent e) {
        //
    }
}
