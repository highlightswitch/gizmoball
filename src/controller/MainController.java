package controller;

import model.IModel;
import model.Model;
import model.gizmo.TriggerType;
import view.GameFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class MainController implements ActionListener {
    private Model model;
    private GameFrame fr;

    private KeyListener keyListener;
    private Timer timer;

    public MainController(){
        keyListener = new MagicKeyListener(this);
        this.model = new Model();
        fr = new GameFrame(this);
        this.timer = new Timer(50, this);
        fr.assignActionListeners();
    }

    public IModel getIModel() {
        return model;
    }

    Model getModel(){
        return model;
    }

    public void setModel(Model newModel) {
        model = newModel;
        fr.setModel(model);
    }

    void startTimer() {
        timer.start();
    }

    void stopTimer() {
        timer.stop();
    }

    void switchToRunView(){
        setModel(new Model());
        fr.switchToRunView();
    }

    void switchToBuildView(){
        setModel(new Model());
        fr.switchToBuildView();
    }

    public KeyListener getKeyListener() {
        return keyListener;
    }

    void keyEventTriggered(int keyCode, TriggerType trigger) {
        model.keyEventTriggered(keyCode, trigger);
    }

    public ActionListener getActionListener(JFrame frame, String type) {

        if(type.equals("Button")){
            return new ButtonActionListener(this, frame, model, fr.geActiveBoard());
        }
        else if(type.equals("Menu")){
            return new MenuActionListener(this, frame, fr.geActiveBoard());
        }

        return new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               //
           }
        };
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            model.tick();
        }
    }

}
