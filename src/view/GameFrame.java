package view;

import controller.MainController;
import model.IModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameFrame {

     private JFrame frMain;
     private JMenuBar top;
     private JMenu mTools;
     private JMenu add;
     private JMenuItem rotate;
     private JMenuItem edit;
     private JMenu settings;
     private JMenuItem delete;
     private JMenuItem clearBoard;
     private MainController controller;
     private Board board;
     private ArrayList<JMenuItem> menuItems;
     private GameView view;

    private JMenuItem runViewMenuItem;
    private JMenuItem buildViewMenuItem;

    public GameFrame(MainController cont){
        controller = cont;
        frMain = new JFrame("Gizmoball");
        mTools = new JMenu("Tools");
        top = new JMenuBar();
        menuItems = new ArrayList<>();
        board = new Board(500, 500, cont.getIModel());

        JMenu mFile = new JMenu("File");
        JMenu mView = new JMenu("View");

        JMenuItem load = new JMenuItem("Load");
        load.setActionCommand("Load");
        menuItems.add(load);

        JMenuItem save = new JMenuItem("Save");
        save.setActionCommand("Save");
        menuItems.add(save);

        JMenuItem quit = new JMenuItem("Quit");
        quit.setActionCommand("Quit");
        menuItems.add(quit);

        runViewMenuItem = new JMenuItem("Run View");
        runViewMenuItem.setActionCommand("Run");
        menuItems.add(runViewMenuItem);

        buildViewMenuItem = new JMenuItem("Build View");
        buildViewMenuItem.setActionCommand("Build");
        menuItems.add(buildViewMenuItem);

        add = new JMenu("Add a Gizmo");
        JMenu shape = new JMenu("Add a Shape");

        JMenuItem circle = new JMenuItem("Circle");
        circle.setActionCommand("Circle");
        menuItems.add(circle);

        JMenuItem square = new JMenuItem("Square");
        square.setActionCommand("Square");
        menuItems.add(square);

        JMenuItem triangle = new JMenuItem("Triangle");
        triangle.setActionCommand("Triangle");
        menuItems.add(triangle);

        shape.add(circle);
        shape.add(square);
        shape.add(triangle);

        JMenuItem ball = new JMenuItem("Add a Ball");
        ball.setActionCommand("Ball");
        menuItems.add(ball);

        JMenuItem absorber = new JMenuItem("Add an Absorber");
        absorber.setActionCommand("Absorber");
        menuItems.add(absorber);

        JMenuItem flipper  = new JMenuItem("Add a Flipper");
        flipper.setActionCommand("Flipper");
        menuItems.add(flipper);

        add.add(shape);
        add.add(ball);
        add.add(absorber);
        add.add(flipper);

        rotate = new JMenuItem("Rotate");
        rotate.setActionCommand("Rotate");
        menuItems.add(rotate);

        edit = new JMenuItem("Edit");
        edit.setActionCommand("Edit");
        menuItems.add(edit);

        settings = new JMenu("Settings");
        JMenuItem gravity = new JMenuItem("Change Gravity");
        gravity.setActionCommand("Gravity");
        menuItems.add(gravity);

        JMenuItem friction = new JMenuItem("Change Friction");
        friction.setActionCommand("Friction");
        menuItems.add(friction);

        JMenuItem rmtriggers = new JMenuItem("Remove All Triggers");
        rmtriggers.setActionCommand("Remove");
        menuItems.add(rmtriggers);

        settings.add(gravity);
        settings.add(friction);
        settings.add(rmtriggers);

        delete = new JMenuItem("Delete");
        delete.setActionCommand("Delete");
        menuItems.add(delete);

        clearBoard = new JMenuItem("Clear Board");
        clearBoard.setActionCommand("Clear");
        menuItems.add(clearBoard);

        mFile.add(load);
        mFile.add(save);
        mFile.add(quit);

        mView.add(runViewMenuItem);
        mView.add(buildViewMenuItem);

        top.add(mFile);
        top.add(mView);

        //This is hacky but if we only run build view,
        //the key listener doesn't work
        switchToRunView();
        switchToBuildView();
    }

    public void setModel(IModel model){
        this.board.setModel(model);
    }

    public void switchToRunView(){
        board.updateMode("Run");
        this.drawFrame(new RunView(controller, board));
        this.assignActionListeners();
        frMain.getContentPane().revalidate();
        frMain.getContentPane().repaint();

        this.compressMenu();

        runViewMenuItem.setVisible(false);
        buildViewMenuItem.setVisible(true);

        //Do this so that keyListener works
        frMain.requestFocus();
        frMain.getJMenuBar().revalidate();
        frMain.getJMenuBar().repaint();
        frMain.validate();
        frMain.repaint();
    }

    public void switchToBuildView(){
        board.updateMode("Build");
        this.drawFrame(new BuildView(frMain, controller, board));

        this.extendMenu();
        runViewMenuItem.setVisible(true);
        buildViewMenuItem.setVisible(false);
        this.assignActionListeners();

        frMain.getContentPane().revalidate();
        frMain.getContentPane().repaint();
        frMain.getJMenuBar().revalidate();
        frMain.getJMenuBar().repaint();
        frMain.validate();
        frMain.repaint();
    }

    private void drawFrame(GameView g){
        //open running view by default then user can change to build view

        this.view = g;

        JPanel activePanel = g.getPanel();

        frMain.setContentPane(activePanel);
        frMain.setJMenuBar(top);
        frMain.setVisible(true);

        frMain.addKeyListener(controller.getKeyListener());

        frMain.setMinimumSize(new Dimension(550,650));
        frMain.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void extendMenu(){
        mTools.add(add);
        mTools.add(rotate);
        mTools.add(edit);
        mTools.add(delete);
        mTools.add(settings);
        mTools.add(clearBoard);
        top.add(mTools);
    }

    private void compressMenu(){
        top.remove(mTools);
    }

    public JFrame getFrame() {
        return frMain;
    }

    //I'm unsure why geActiveBoard exists. Left it in for now
    public Board getActualBoard(){
        return board;
    }

    public GameView getView(){
        return view;
    }

    public void assignActionListeners(){
        for(JMenuItem m: menuItems){
            if(m.getActionListeners().length == 0)
                m.addActionListener(controller.getActionListener("Menu"));
        }
        view.setAllButtonListeners();
    }
}
