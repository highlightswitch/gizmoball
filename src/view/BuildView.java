package view;

import controller.AddPopupListener;
import controller.DragDropListener;
import controller.MainController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BuildView implements GameView {
    JFrame frame;
    JPanel panBuild;
    JPanel panBoard;
    JPopupMenu allShapes;
    ArrayList<AbstractButton> buttons = new ArrayList<>();
    MainController controller;

    public BuildView(JFrame fr, MainController c, Board b) {
        frame = fr;
        panBuild = new JPanel();
        panBuild.setBackground(Color.ORANGE);
        controller = c;

        JPanel panControls = new JPanel();
        JToggleButton add = new JToggleButton();
        JToggleButton rotate = new JToggleButton();
        JToggleButton delete = new JToggleButton();
        JToggleButton edit = new JToggleButton();
        JToggleButton pointer = new JToggleButton();
        JToggleButton keyTrigger = new JToggleButton();
        JToggleButton connectTwo = new JToggleButton();

        add.setIcon(new ImageIcon(getClass().getResource("/Images/fillAddSmall.png")));
        rotate.setIcon(new ImageIcon(getClass().getResource("/Images/fillRotateSmall.png")));
        delete.setIcon(new ImageIcon(getClass().getResource("/Images/fillDeleteSmall.png")));
        edit.setIcon(new ImageIcon(getClass().getResource("/Images/fillEditSmall.png")));
        pointer.setIcon(new ImageIcon(getClass().getResource("/Images/fillPointerSmall.png")));
        keyTrigger.setIcon(new ImageIcon(getClass().getResource("/Images/fillKeySmall.png")));
        connectTwo.setIcon(new ImageIcon(getClass().getResource("/Images/borderConnectSmall.png")));

        rotate.setActionCommand("Rotate");
        rotate.addActionListener(c.getActionListener(frame, "Button"));

        delete.setActionCommand("Delete");
        delete.addActionListener(c.getActionListener(frame, "Button"));

        edit.setActionCommand("Edit");
        edit.addActionListener(c.getActionListener(frame, "Button"));

        pointer.setActionCommand("Move");
        pointer.addActionListener(c.getActionListener(frame, "Button"));

        add.setBorder(null);
        add.setMargin(new Insets(0, 0, 0, 0));
        add.setContentAreaFilled(false);

        drawPopupMenu();
        add.addMouseListener(new AddPopupListener(allShapes));

        rotate.setBorder(null);
        rotate.setMargin(new Insets(0, 0, 0, 0));
        rotate.setContentAreaFilled(false);
        rotate.setActionCommand("Rotate");
        buttons.add(rotate);

        delete.setBorder(null);
        delete.setMargin(new Insets(0, 0, 0, 0));
        delete.setContentAreaFilled(false);
        delete.setActionCommand("Delete");
        buttons.add(delete);

        edit.setBorder(null);
        edit.setMargin(new Insets(0, 0, 0, 0));
        edit.setContentAreaFilled(false);
        edit.setActionCommand("Edit");
        buttons.add(edit);

        pointer.setBorder(null);
        pointer.setMargin(new Insets(0, 0, 0, 0));
        pointer.setContentAreaFilled(false);
        pointer.setActionCommand("Move");
        buttons.add(pointer);

        keyTrigger.setBorder(null);
        keyTrigger.setMargin(new Insets(0, 0, 0, 0));
        keyTrigger.setContentAreaFilled(false);
        keyTrigger.setActionCommand("Key");
        keyTrigger.addActionListener(controller.getActionListener(frame, "Button"));

        connectTwo.setBorder(null);
        connectTwo.setMargin(new Insets(0, 0, 0, 0));
        connectTwo.setContentAreaFilled(false);
        connectTwo.setActionCommand("Connect");
        connectTwo.addActionListener(controller.getActionListener(frame, "Button"));

        panControls.add(add);
        panControls.add(rotate);
        panControls.add(delete);
        panControls.add(edit);
        panControls.add(pointer);
        panControls.add(keyTrigger);
        panControls.add(connectTwo);

        panControls.setOpaque(false);
        panControls.setLayout(new FlowLayout());

        JPanel panGrid = new JPanel();

        panGrid.add(b);

        panGrid.setOpaque(false);
        panBoard = panGrid;
        System.out.println("board panel: " + panGrid.hashCode());
        System.out.println("returned board: " + panBoard.hashCode());

        panBuild.setLayout(new BorderLayout());
        panBuild.add(panGrid, BorderLayout.CENTER);
        panBuild.add(panControls, BorderLayout.SOUTH);
    }


    public void drawPopupMenu() {
        allShapes = new JPopupMenu();
        ImageIcon circleGizmo = (new ImageIcon(getClass().getResource("/Images/fillCircleSmall.png")));
        ImageIcon triangleGizmo = (new ImageIcon(getClass().getResource("/Images/fillTriangleSmall.png")));
        ImageIcon squareGizmo = (new ImageIcon(getClass().getResource("/Images/fillSquareSmall.png")));
        ImageIcon absorberGizmo = (new ImageIcon(getClass().getResource("/Images/fillRectangleSmall.png")));
        ImageIcon flipperGizmo = (new ImageIcon(getClass().getResource("/Images/fillFlipperSmall.png")));
        ImageIcon ballGizmo =  (new ImageIcon(getClass().getResource("/Images/borderBallSmall.png")));

        JButton circleGizmoButton = new JButton();
        circleGizmoButton.setIcon(circleGizmo);
        circleGizmoButton.setPreferredSize(new Dimension(50,50));
        circleGizmoButton.setMargin(new Insets(0, 0, 0, 0));
        circleGizmoButton.setContentAreaFilled(false);
        circleGizmoButton.setActionCommand("Circle");
        circleGizmoButton.addActionListener(controller.getActionListener(frame, "Button"));

        JButton triangleGizmoButton = new JButton();
        triangleGizmoButton.setIcon(triangleGizmo);
        triangleGizmoButton.setPreferredSize(new Dimension(50,50));
        triangleGizmoButton.setMargin(new Insets(0, 0, 0, 0));
        triangleGizmoButton.setContentAreaFilled(false);
        triangleGizmoButton.setActionCommand("Triangle");
        triangleGizmoButton.addActionListener(controller.getActionListener(frame, "Button"));

        JButton squareGizmoButton = new JButton();
        squareGizmoButton.setIcon(squareGizmo);
        squareGizmoButton.setPreferredSize(new Dimension(50,50));
        squareGizmoButton.setMargin(new Insets(0, 0, 0, 0));
        squareGizmoButton.setContentAreaFilled(false);
        squareGizmoButton.setActionCommand("Square");
        squareGizmoButton.addActionListener(controller.getActionListener(frame, "Button"));

        JButton absorberGizmoButton = new JButton();
        absorberGizmoButton.setIcon(absorberGizmo);
        absorberGizmoButton.setPreferredSize(new Dimension(50,50));
        absorberGizmoButton.setMargin(new Insets(0, 0, 0, 0));
        absorberGizmoButton.setContentAreaFilled(false);
        absorberGizmoButton.setActionCommand("Absorber");
        absorberGizmoButton.addActionListener(controller.getActionListener(frame, "Button"));

        JButton flipperGizmoButton = new JButton();
        flipperGizmoButton.setIcon(flipperGizmo);
        flipperGizmoButton.setPreferredSize(new Dimension(50,50));
        flipperGizmoButton.setMargin(new Insets(0, 0, 0, 0));
        flipperGizmoButton.setContentAreaFilled(false);
        flipperGizmoButton.setActionCommand("Flipper");
        flipperGizmoButton.addActionListener(controller.getActionListener(frame, "Button"));

        JButton ballGizmoButton = new JButton();
        ballGizmoButton.setIcon(ballGizmo);
        ballGizmoButton.setPreferredSize(new Dimension(50,50));
        ballGizmoButton.setMargin(new Insets(0, 0, 0, 0));
        ballGizmoButton.setContentAreaFilled(false);
        ballGizmoButton.setActionCommand("Ball");
        ballGizmoButton.addActionListener(controller.getActionListener(frame, "Button"));

        allShapes.add(circleGizmoButton);
        allShapes.add(triangleGizmoButton);
        allShapes.add(squareGizmoButton);
        allShapes.add(absorberGizmoButton);
        allShapes.add(flipperGizmoButton);
        allShapes.add(ballGizmoButton);

        allShapes.setBackground(Color.WHITE);
        allShapes.setLayout(new FlowLayout());
    }

    @Override
    public JPanel getPanel() {
        return panBuild;
    }

    @Override
    public JPanel getBoard() {
        return panBoard;
    }

    @Override
    public void setAllButtonListeners() {
        for(AbstractButton b: buttons){
            b.addActionListener(controller.getActionListener(frame, "Button"));
        }
    }

}

