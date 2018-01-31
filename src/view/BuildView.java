package view;

import controller.*;

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
         JButton add = new JButton();
         JButton rotate = new JButton();
         JButton delete = new JButton();
         JButton edit = new JButton();
         JButton pointer = new JButton();

         add.setIcon(new ImageIcon("img/fillAddSmall.png"));
         rotate.setIcon(new ImageIcon("img/fillRotateSmall.png"));
         delete.setIcon(new ImageIcon("img/fillDeleteSmall.png"));
         edit.setIcon(new ImageIcon("img/fillEditSmall.png"));
         pointer.setIcon(new ImageIcon("img/fillPointerSmall.png"));

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

         JPanel panGrid = new JPanel(new GridLayout(20,20));
         panGrid.setOpaque(false);
         panGrid.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));

         for (int i =0; i<(20*20); i++){
             final JLabel label = new JLabel();
             label.setBorder(BorderFactory.createLineBorder(Color.WHITE));
             panGrid.add(label);
         }

         //panGrid.add(b);

         panBuild.setLayout(new BorderLayout());
         panBuild.add(panGrid, BorderLayout.CENTER);
         panBuild.add(panControls, BorderLayout.SOUTH);
    }


    public void drawPopupMenu(){
        allShapes = new JPopupMenu();

        JMenuItem circleGizmo = new JMenuItem(new ImageIcon("img/fillCircleSmall.png"));
        JMenuItem triangleGizmo = new JMenuItem(new ImageIcon("img/fillTriangleSmall.png"));
        JMenuItem squareGizmo = new JMenuItem(new ImageIcon("img/fillSquareSmall.png"));
        JMenuItem absorberGizmo = new JMenuItem(new ImageIcon("img/fillRectangleSmall.png"));
        JMenuItem flipperGizmo = new JMenuItem(new ImageIcon("img/fillFlipperSmall.png"));
        JMenuItem ballGizmo = new JMenuItem(new ImageIcon("img/borderBallSmall.png"));

        circleGizmo.setContentAreaFilled(false);
        triangleGizmo.setContentAreaFilled(false);
        squareGizmo.setContentAreaFilled(false);
        absorberGizmo.setContentAreaFilled(false);
        flipperGizmo.setContentAreaFilled(false);
        ballGizmo.setContentAreaFilled(false);

        allShapes.add(circleGizmo);
        allShapes.add(triangleGizmo);
        allShapes.add(squareGizmo);
        allShapes.add(flipperGizmo);
        allShapes.add(ballGizmo);
        allShapes.add(absorberGizmo);
        allShapes.setBackground(Color.WHITE);
        allShapes.setLayout(new FlowLayout());
    }

    @Override
    public JPanel getPanel() {
        return panBuild;
    }
}
