package controller;

import model.GizmoNotFoundException;
import model.Model;
import model.Tile;
import model.gizmo.GizmoPropertyType;
import model.gizmo.TriggerType;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TriggerKeyListener implements KeyListener {
    private Model model;
    private Tile tile;
    private JFrame frame;

    TriggerKeyListener(JFrame f, Model m, Tile t){
        System.out.println("triggering");
        tile = t;
        model = m;
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
            model.connect(e.getKeyCode(), TriggerType.valueOf(""),tile.getGizmo().getProperty(GizmoPropertyType.NAME));
        } catch (GizmoNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
