package controller;

import model.*;
import model.gizmo.GizmoNotRotatableException;
import model.gizmo.GizmoPropertyException;
import model.gizmo.GizmoPropertyType;
import model.gizmo.TriggerType;
import view.Board;
import view.GameFrame;
import view.GameView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

public class MainController implements ActionListener {

    private Model model;
    private String mode;
    private GameFrame fr;
    private KeyListener keyListener;
    private Timer timer;
    private String buildModeSave = "";

    private ButtonActionListener button;
    private MenuActionListener menu;

    private MouseHandler mouseHandler;

    public MainController(){

        keyListener = new MagicKeyListener(this);
        this.model = new Model();
        fr = new GameFrame(this);
        this.timer = new Timer(25, this);

        this.mouseHandler = new MouseHandler(this, fr.getFrame());
        menu = new MenuActionListener(this, fr.getFrame());
        button = new ButtonActionListener(this);
        fr.assignActionListeners();
        this.updateMouseListener();

        mode = "Build";

        switchToBuildView();

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

    public void clearModel(){
        this.setModel(new Model());
    }

    GameView getView(){
        return fr.getView();
    }

    String getBuildModeSave(){
        return buildModeSave;
    }

    MouseHandler getMouseHandler() {
        return mouseHandler;
    }

    void updateMouseListener(){
        Board board = fr.getActualBoard();
        for(MouseListener m : board.getMouseListeners()){
            board.removeMouseListener(m);
        }
        board.addMouseListener(mouseHandler.getCurrentListener());
    }

    void startTimer() {
        timer.start();
    }

    void stopTimer() {
        timer.stop();
    }

    void switchToRunView(){
        this.buildModeSave = model.toString();
        fr.switchToRunView();
        mode = "Run";
    }

    void switchToBuildView(){
        if(!buildModeSave.equals("")){
            try {
                this.setModel(LoadingHandler.stringToModel(buildModeSave));
            } catch (TileCoordinatesNotValid | MalformedGizmoballFileException | GizmoPropertyException | GizmoPlacementNotValidException | GizmoNotFoundException e) {
                e.printStackTrace();
            } catch (GizmoNotRotatableException e) {
                e.printStackTrace();
            }
        }
        fr.switchToBuildView();
        mode = "Build";
        timer.stop();
    }

    public KeyListener getKeyListener() {
        return keyListener;
    }

    void keyEventTriggered(int keyCode, TriggerType trigger) {
        if(mode.equals("Build")){
            System.out.println("you pressed a key");
            JOptionPane.showMessageDialog(fr.getFrame(), "The key you are selecting is: " + keyCode, "Key Code", JOptionPane.INFORMATION_MESSAGE);
            mouseHandler.connectToKeyCode(keyCode);
        } else {
            model.keyEventTriggered(keyCode, trigger);
        }
    }

    public ActionListener getActionListener(String type) {
        if(type.equals("Button")){
            return button;
        }
        else if(type.equals("Menu")){
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
