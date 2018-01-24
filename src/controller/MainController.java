package controller;

import model.Model;
import model.gizmo.TriggerType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class MainController implements ActionListener {

    private Timer timer;
    private Model model;

    private ActionListener actionListener;
    private KeyListener keyListener;

    public MainController(Model model){
        this.model = model;

        actionListener = new ButtonActionListener(this);
        keyListener = new MagicKeyListener(this);

        this.timer = new Timer(50, this);
    }

    //TODO: This is only here for testing purposes.
    //I'm pretty sure everything that needs the model already has it
    public Model getModel() {
        return model;
    }

    void startTimer() {
        timer.start();
    }

    void stopTimer() {
        timer.stop();
    }

    public KeyListener getKeyListener() {
        return keyListener;
    }

    void keyEventTriggered(int keyCode, TriggerType trigger) {
        model.keyEventTriggered(keyCode, trigger);
    }

    public ActionListener getActionListener() {
        return actionListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            model.tick();
        }
    }

}
