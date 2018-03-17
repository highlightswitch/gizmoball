package controller;

import model.*;
import model.gizmo.GizmoPropertyException;
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

    private String buildModeSave = "";

    public MainController(){
        keyListener = new MagicKeyListener(this);
        this.model = new Model();
        fr = new GameFrame(this);
        this.timer = new Timer(25, this);
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
        //fr.assignActionListeners();
//        this.buildModeSave = model.toString(); //TODO: after saving is finished, enable this and remove the test string below
        this.buildModeSave = "Square s1 5 5\nCircle c1 3 3\nBall b1 3 1 0 0";
        fr.switchToRunView();
    }

    void switchToBuildView(){
       // fr.assignActionListeners();
        if(!buildModeSave.equals("")){
            try {
                GizmoballFileReader fileReader = new GizmoballFileReader(buildModeSave);
                this.setModel(fileReader.getModel());
            } catch (TileCoordinatesNotValid | MalformedGizmoballFileException | GizmoPropertyException | GizmoPlacementNotValidException | GizmoNotFoundException e) {
                e.printStackTrace();
            }
        }
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
            return new ButtonActionListener(this, frame, model, fr.geActiveBoard(), fr.getView());
        }
        else if(type.equals("Menu")){
            return new MenuActionListener(this, frame, fr.geActiveBoard(), fr.getView());
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
