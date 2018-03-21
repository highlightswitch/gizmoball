package view;

import controller.AddPopupListener;
import controller.MainController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BuildView implements GameView {
    private JPanel panBuild;
    private JPanel panBoard;
    private JLabel message;
    private JPopupMenu allShapes;
    private ArrayList<AbstractButton> buttons = new ArrayList<>();
    private MainController controller;

    BuildView(JFrame fr, MainController c, Board b) {
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
        delete.setActionCommand("Delete");
        edit.setActionCommand("Edit");
        pointer.setActionCommand("Move");

        add.setBorder(null);
        add.setMargin(new Insets(0, 0, 0, 0));
        add.setContentAreaFilled(false);

        drawPopupMenu();
        add.addMouseListener(new AddPopupListener(allShapes));

        rotate.setActionCommand("Rotate");
        buttons.add(rotate);

        delete.setActionCommand("Delete");
        buttons.add(delete);

        edit.setActionCommand("Edit");
        buttons.add(edit);

        pointer.setActionCommand("Move");
        buttons.add(pointer);

        keyTrigger.setActionCommand("Key");
        buttons.add(keyTrigger);

        connectTwo.setActionCommand("Connect");
        buttons.add(connectTwo);

        setButtonSettings();

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

        JPanel updates = new JPanel();
        message = new JLabel("<html><span style ='color: white; font-size:12px'>" + "Keep an eye on this space for updates!" +  "</span></html>");
        updates.add(message);
        updates.setOpaque(false);


        panGrid.setOpaque(false);
        panBoard = panGrid;

        panBuild.setLayout(new BorderLayout());
        panBuild.add(panGrid, BorderLayout.PAGE_START);
        panBuild.add(updates, BorderLayout.CENTER);
        panBuild.add(panControls, BorderLayout.SOUTH);
    }


    private void drawPopupMenu() {
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
        circleGizmoButton.setActionCommand("Circle");
        buttons.add(circleGizmoButton);

        JButton triangleGizmoButton = new JButton();
        triangleGizmoButton.setIcon(triangleGizmo);
        triangleGizmoButton.setPreferredSize(new Dimension(50,50));
        triangleGizmoButton.setActionCommand("Triangle");
        buttons.add(triangleGizmoButton);

        JButton squareGizmoButton = new JButton();
        squareGizmoButton.setIcon(squareGizmo);
        squareGizmoButton.setPreferredSize(new Dimension(50,50));
        squareGizmoButton.setActionCommand("Square");
        buttons.add(squareGizmoButton);

        JButton absorberGizmoButton = new JButton();
        absorberGizmoButton.setIcon(absorberGizmo);
        absorberGizmoButton.setPreferredSize(new Dimension(50,50));
        absorberGizmoButton.setActionCommand("Absorber");
        buttons.add(absorberGizmoButton);

        JButton flipperGizmoButton = new JButton();
        flipperGizmoButton.setIcon(flipperGizmo);
        flipperGizmoButton.setPreferredSize(new Dimension(50,50));
        flipperGizmoButton.setActionCommand("Flipper");
        buttons.add(flipperGizmoButton);

        JButton ballGizmoButton = new JButton();
        ballGizmoButton.setIcon(ballGizmo);
        ballGizmoButton.setPreferredSize(new Dimension(50,50));
        ballGizmoButton.setActionCommand("Ball");
        buttons.add(ballGizmoButton);
        setButtonSettings();

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
    public void setAllButtonListeners() {
        for(AbstractButton b: buttons){
            b.addActionListener(controller.getActionListener("Button"));
        }
    }

    private void setButtonSettings(){
        for(AbstractButton b: buttons){
            //b.setFocusable(false);
            b.setBorder(null);
            b.setMargin(new Insets(0, 0, 0, 0));
            b.setContentAreaFilled(false);
        }
    }

    public void setMessage(String m){
        message.setText("<html><span style ='color: white; font-size:12px'>" + m + "</span></html>");
        message.revalidate();
        message.repaint();
    }

}

