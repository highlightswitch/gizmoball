package view;

import controller.MainController;
import model.IModel;

import javax.swing.*;
import java.awt.*;

public class GameFrame {
     private JFrame frMain;
     private JMenuBar top;
     private JMenu mTools;
     private JMenu add;
     private JMenuItem rotate;
     private JMenuItem edit;
     private JMenu settings;
     private JMenuItem delete;
     private MainController controller;
     private Board board;

    public GameFrame(MainController cont){
        controller = cont;
        board = new Board(500, 500, cont.getIModel());
        frMain = new JFrame("Gizmoball");
        mTools = new JMenu("Tools");
        top = new JMenuBar();

        JMenu mFile = new JMenu("File");
        JMenu mView = new JMenu("View");

        JMenuItem load = new JMenuItem("Load");
        load.setActionCommand("Load");
        load.addActionListener(controller.getActionListener(this.frMain, "Menu"));

        JMenuItem save = new JMenuItem("Save");
        save.setActionCommand("Save");
        save.addActionListener(controller.getActionListener(this.frMain, "Menu"));

        JMenuItem quit = new JMenuItem("Quit");
        quit.setActionCommand("Quit");
        quit.addActionListener(controller.getActionListener(this.frMain, "Menu"));

        JMenuItem run = new JMenuItem("Run View");
        run.setActionCommand("Run");
        run.addActionListener(controller.getActionListener(this.frMain, "Menu"));

        JMenuItem build = new JMenuItem("Build View");
        build.setActionCommand("Build");
        build.addActionListener(controller.getActionListener(this.frMain, "Menu"));

        add = new JMenu("Add a Gizmo");
        JMenu shape = new JMenu("Add a Shape");

        JMenuItem circle = new JMenuItem("Circle");
        circle.setActionCommand("Circle");
        circle.addActionListener(controller.getActionListener(this.frMain, "Menu"));

        JMenuItem square = new JMenuItem("Square");
        square.setActionCommand("Square");
        square.addActionListener(controller.getActionListener(this.frMain, "Menu"));

        JMenuItem triangle = new JMenuItem("Triangle");
        triangle.setActionCommand("Triangle");
        triangle.addActionListener(controller.getActionListener(this.frMain, "Menu"));

        shape.add(circle);
        shape.add(square);
        shape.add(triangle);

        JMenuItem ball = new JMenuItem("Add a Ball");
        ball.setActionCommand("Ball");
        ball.addActionListener(controller.getActionListener(this.frMain, "Menu"));

        JMenuItem absorber = new JMenuItem("Add an Absorber");
        absorber.setActionCommand("Absorber");
        absorber.addActionListener(controller.getActionListener(this.frMain, "Menu"));

        add.add(shape);
        add.add(ball);
        add.add(absorber);

        rotate = new JMenuItem("Rotate");
        rotate.setActionCommand("Rotate");
        rotate.addActionListener(controller.getActionListener(this.frMain, "Menu"));

        edit = new JMenuItem("Edit");
        edit.setActionCommand("Edit");
        edit.addActionListener(controller.getActionListener(this.frMain, "Menu"));

        settings = new JMenu("Settings");
        JMenuItem gravity = new JMenuItem("Change Gravity");
        gravity.setActionCommand("Gravity");
        gravity.addActionListener(controller.getActionListener(this.frMain, "Menu"));

        JMenuItem friction = new JMenuItem("Change Friction");
        friction.setActionCommand("Friction");
        friction.addActionListener(controller.getActionListener(this.frMain, "Menu"));

        settings.add(gravity);
        settings.add(friction);

        delete = new JMenuItem("Delete");
        delete.setActionCommand("Delete");
        delete.addActionListener(controller.getActionListener(this.frMain, "Menu"));

        mFile.add(load);
        mFile.add(save);
        mFile.add(quit);

        mView.add(run);
        mView.add(build);

        top.add(mFile);
        top.add(mView);

        GameView view = new RunView(frMain, controller, board);

        drawFrame(view);
    }

    public void setModel(IModel model){
        this.board.setModel(model);
    }

    public void switchToRunView(){
        this.drawFrame(new RunView(frMain, controller, board));
        frMain.getContentPane().revalidate();
        frMain.getContentPane().repaint();
        this.compressMenu();
        frMain.getJMenuBar().revalidate();
        frMain.getJMenuBar().repaint();
    }

    public void switchToBuildView(){
        this.drawFrame(new BuildView(frMain, controller, board));
        this.extendMenu();
        frMain.getContentPane().revalidate();
        frMain.getContentPane().repaint();
        frMain.getJMenuBar().revalidate();
        frMain.getJMenuBar().repaint();
    }

    public void drawFrame(GameView g){
        //open running view by default then user can change to build view
        frMain.setContentPane(g.getPanel());
        frMain.setJMenuBar(top);
        frMain.setVisible(true);
        frMain.addKeyListener(controller.getKeyListener());
        frMain.setMinimumSize(new Dimension(550,650));
        frMain.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void extendMenu(){
        mTools.add(add);
        mTools.add(rotate);
        mTools.add(edit);
        mTools.add(delete);
        mTools.addSeparator();
        mTools.add(settings);
        top.add(mTools);
    }

    public void compressMenu(){
        top.remove(mTools);
    }

    public JFrame getFrame() {
        return frMain;
    }

}
