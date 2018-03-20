package controller;

import model.GizmoNotFoundException;
import model.IModel;
import model.Tile;
import model.gizmo.GizmoPropertyType;
import model.gizmo.TriggerType;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TriggerKeyListener implements KeyListener {

    private MainController controller;
    private Tile tile;
    private JFrame frame;

    TriggerKeyListener(MainController controller, JFrame f, Tile t){
        System.out.println("triggering");
        this.controller = controller;
        tile = t;
        frame = f;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("you pressed a key");
        JOptionPane.showMessageDialog(frame, "The key you are selecting is: " + e.getKeyCode(), "Key Code", JOptionPane.INFORMATION_MESSAGE);
        try {
            IModel model = controller.getIModel();
            model.connect(e.getKeyCode(), TriggerType.valueOf(""),tile.getGizmo().getProperty(GizmoPropertyType.NAME));
        } catch (GizmoNotFoundException e1) {
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
