package controller;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

    public class DragDropListener extends MouseAdapter {
        public DragDropListener() {
        }

        public void mousePressed(MouseEvent e) {
            JComponent jc = (JComponent) e.getSource();
            TransferHandler th = jc.getTransferHandler();
            th.exportAsDrag(jc, e, TransferHandler.COPY);
        }
    }


