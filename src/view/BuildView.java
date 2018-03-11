package view;

import controller.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class BuildView implements GameView {
    JFrame frame;
    JPanel panBuild;
    JPopupMenu allShapes;

    public BuildView(JFrame fr, MainController c, Board b) {
        frame = fr;
        panBuild = new JPanel();
        panBuild.setBackground(Color.ORANGE);

        JPanel panControls = new JPanel();
        JToggleButton add = new JToggleButton();
        JToggleButton rotate = new JToggleButton();
        JToggleButton delete = new JToggleButton();
        JToggleButton edit = new JToggleButton();
        JToggleButton pointer = new JToggleButton();

        add.setIcon(new ImageIcon(getClass().getResource("/Images/fillAddSmall.png")));
        rotate.setIcon(new ImageIcon(getClass().getResource("/Images/fillRotateSmall.png")));
        delete.setIcon(new ImageIcon(getClass().getResource("/Images/fillDeleteSmall.png")));
        edit.setIcon(new ImageIcon(getClass().getResource("/Images/fillEditSmall.png")));
        pointer.setIcon(new ImageIcon(getClass().getResource("/Images/fillPointerSmall.png")));

        rotate.setActionCommand("Rotate");
        rotate.addActionListener(c.getActionListener("Button"));

        delete.setActionCommand("Delete");
        delete.addActionListener(c.getActionListener("Button"));

        edit.setActionCommand("Edit");
        edit.addActionListener(c.getActionListener("Button"));

        pointer.setActionCommand("Move");
        pointer.addActionListener(c.getActionListener("Button"));

        add.setBorder(null);
        add.setMargin(new Insets(0, 0, 0, 0));
        add.setContentAreaFilled(false);

        drawPopupMenu();
        add.addMouseListener(new AddPopupListener(allShapes));

        rotate.setBorder(null);
        rotate.setMargin(new Insets(0, 0, 0, 0));
        rotate.setContentAreaFilled(false);

        delete.setBorder(null);
        delete.setMargin(new Insets(0, 0, 0, 0));
        delete.setContentAreaFilled(false);

        edit.setBorder(null);
        edit.setMargin(new Insets(0, 0, 0, 0));
        edit.setContentAreaFilled(false);
        edit.setActionCommand("Edit");
        edit.addActionListener(c.getActionListener("Button"));

        pointer.setBorder(null);
        pointer.setMargin(new Insets(0, 0, 0, 0));
        pointer.setContentAreaFilled(false);

        panControls.add(add);
        panControls.add(rotate);
        panControls.add(delete);
        panControls.add(edit);
        panControls.add(pointer);
        panControls.setOpaque(false);
        panControls.setLayout(new FlowLayout());

        JPanel panGrid = new JPanel(new GridLayout(20, 20));
        panGrid.setOpaque(false);
        panGrid.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        for (int i = 0; i < (20 * 20); i++) {
            final JLabel label = new JLabel();
            label.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            panGrid.add(label);
        }

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
        JButton triangleGizmoButton = new JButton();
        triangleGizmoButton.setIcon(triangleGizmo);
        triangleGizmoButton.setPreferredSize(new Dimension(50,50));
        JButton squareGizmoButton = new JButton();
        squareGizmoButton.setIcon(squareGizmo);
        squareGizmoButton.setPreferredSize(new Dimension(50,50));
        JButton absorberGizmoButton = new JButton();
        absorberGizmoButton.setIcon(absorberGizmo);
        absorberGizmoButton.setPreferredSize(new Dimension(50,50));
        JButton flipperGizmoButton = new JButton();
        flipperGizmoButton.setIcon(flipperGizmo);
        flipperGizmoButton.setPreferredSize(new Dimension(50,50));
        JButton ballGizmoButton = new JButton();
        ballGizmoButton.setIcon(ballGizmo);
        ballGizmoButton.setPreferredSize(new Dimension(50,50));
        /*JMenuItem circleGizmo = new JMenuItem(new ImageIcon(getClass().getResource("/Images/fillCircleSmall.png")));
        JMenuItem triangleGizmo = new JMenuItem(new ImageIcon(getClass().getResource("/Images/fillTriangleSmall.png")));
        JMenuItem squareGizmo = new JMenuItem(new ImageIcon(getClass().getResource("/Images/fillSquareSmall.png")));
        JMenuItem absorberGizmo = new JMenuItem(new ImageIcon(getClass().getResource("/Images/fillRectangleSmall.png")));
        JMenuItem flipperGizmo = new JMenuItem(new ImageIcon(getClass().getResource("/Images/fillFlipperSmall.png")));
        JMenuItem ballGizmo = new JMenuItem(new ImageIcon(getClass().getResource("/Images/borderBallSmall.png")));*/


//        circleGizmo.addActionListener(new AddCircleListener());
//        triangleGizmo.addActionListener(new AddTriangleListener());
//        squareGizmo.addActionListener(new addSquareListener());

        /*circleGizmo.setContentAreaFilled(false);
        triangleGizmo.setContentAreaFilled(false);
        squareGizmo.setContentAreaFilled(false);
        absorberGizmo.setContentAreaFilled(false);
        flipperGizmo.setContentAreaFilled(false);
        ballGizmo.setContentAreaFilled(false);*/


     /* DragDropListener li = new DragDropListener();
      circleGizmo.addMouseListener(li);
       circleGizmo.addMouseListener(li);
       circleGizmo.setTransferHandler(new TransferHandler("icon"));
       triangleGizmo.addMouseListener(li);
       triangleGizmo.setTransferHandler(new TransferHandler("icon"));
       squareGizmo.addMouseListener(li);
       squareGizmo.setTransferHandler(new TransferHandler("icon"));
       absorberGizmo.addMouseListener(li);
       absorberGizmo.setTransferHandler(new TransferHandler("icon"));
       ballGizmo.addMouseListener(li);
       ballGizmo.setTransferHandler(new TransferHandler("icon"));
       flipperGizmo.addMouseListener(li);
       flipperGizmo.setTransferHandler(new TransferHandler("icon"));
*/
        allShapes.add(circleGizmoButton);
        allShapes.add(triangleGizmoButton);
        allShapes.add(squareGizmoButton);
        allShapes.add(absorberGizmoButton);
        allShapes.add(flipperGizmoButton);
        allShapes.add(ballGizmoButton);
        //allShapes.add(triangleGizmo);
       // allShapes.add(squareGizmo);
       // allShapes.add(flipperGizmo);
       // allShapes.add(ballGizmo);
       // allShapes.add(absorberGizmo);
        allShapes.setBackground(Color.WHITE);
        allShapes.setLayout(new FlowLayout());
    }

    @Override
    public JPanel getPanel() {
        return panBuild;
    }

}

