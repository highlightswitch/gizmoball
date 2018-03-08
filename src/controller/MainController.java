package controller;

import model.Model;
import model.gizmo.TriggerType;
import view.Board;
import view.BuildView;
import view.GameFrame;
import view.RunView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class MainController implements ActionListener {
    private GameFrame fr;
    private Board board;
    private Timer timer;
    private Model model;
    private KeyListener keyListener;

    public MainController(Model model, GameFrame frame, Board b){
        fr = frame;
        board = b;
        this.model = model;
        keyListener = new MagicKeyListener(this);
        this.timer = new Timer(50, this);
    }

    //TODO: This is only here for testing purposes.
    //I'm pretty sure everything that needs the model already has it
    public Model getModel() {
        return model;
    }

    public Board getBoard(){ return board; }

    public void setModel(Model newModel) { model = newModel; }

    public GameFrame getGameFrame() { return fr;}

    void startTimer() {
        timer.start();
    }

    void stopTimer() {
        timer.stop();
    }

    void refreshView(){

    }

    void switchToRunView(){
        fr.drawFrame(new RunView( this, board));
        fr.getFrame().getContentPane().revalidate();
        fr.getFrame().getContentPane().repaint();
        fr.compressMenu();
        fr.getFrame().getJMenuBar().revalidate();
        fr.getFrame().getJMenuBar().repaint();
    }

    void switchToBuildView(){
        fr.drawFrame(new BuildView(fr.getFrame(), this, board));
        fr.extendMenu();
        fr.getFrame().getContentPane().revalidate();
        fr.getFrame().getContentPane().repaint();
        fr.getFrame().getJMenuBar().revalidate();
        fr.getFrame().getJMenuBar().repaint();
    }

    public KeyListener getKeyListener() {
        return keyListener;
    }

    void keyEventTriggered(int keyCode, TriggerType trigger) {
        model.keyEventTriggered(keyCode, trigger);
    }

    public ActionListener getActionListener(String type) {

        if(type.equals("Button")){
            return new ButtonActionListener(this,fr.getFrame());
        }
        else if(type.equals("Menu")){
            return new MenuActionListener(this);
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
