package controller;

import model.*;
import model.gizmo.GizmoPropertyException;
import model.gizmo.TriggerType;
import view.GameFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class MainController implements ActionListener {
    private Model model;
    private GameFrame fr;
    private KeyListener keyListener;
    private Timer timer;
    private String buildModeSave = "";
    private ArrayList<ActionListener> actionListeners = new ArrayList<>();
    ActionListener button;
    ActionListener menu;

    public MainController(){
        keyListener = new MagicKeyListener(this);
        this.model = new Model();
        fr = new GameFrame(this);
        this.timer = new Timer(25, this);
        fr.assignActionListeners();
        button =  new ButtonActionListener(this, fr.getFrame(), model, fr.geActiveBoard(), fr.getView());
        menu =  new MenuActionListener(this, fr.getFrame(), fr.geActiveBoard(), fr.getView());
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

    String getBuildModeSave(){
        return buildModeSave;
    }

    void startTimer() {
        timer.start();
    }

    void stopTimer() {
        timer.stop();
    }

    void switchToRunView(){
        //fr.assignActionListeners();
        this.buildModeSave = model.toString();
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

    public ActionListener getActionListener(String type) {
        if(type.equals("Button")){
            System.out.println("main controller adds: " + button.toString());
            actionListeners.add(button);
            System.out.println("Currently " + actionListeners.size() + " action listeners are added");
            return button;
        }
        else if(type.equals("Menu")){
            System.out.println("main controller adds: " + menu.toString());
            actionListeners.add(menu);
            System.out.println("Currently " + actionListeners.size() + " action listeners are added");
            return menu;
        }

        return (e -> System.out.println("Cannot find type"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            model.tick();
        }
    }

}
