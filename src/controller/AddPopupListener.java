package controller;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddPopupListener extends MouseAdapter {
    private JPopupMenu popup;

    public AddPopupListener(JPopupMenu p){
        popup = p;
    }
    public void mousePressed(MouseEvent e) {
        popup.show(e.getComponent(), e.getX() - 160, e.getY() - 95);
    }

    public void mouseReleased(MouseEvent e) {
    }
}
