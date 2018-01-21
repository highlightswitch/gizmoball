package gui;

import controller2.newShapeListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class buildView {
    JPanel panBuild;

     public buildView(JFrame frame) {
         panBuild = new JPanel();
         panBuild.setBackground(Color.ORANGE);

         JPanel panControls = new JPanel();
         JButton add = new JButton();
         JButton rotate = new JButton();
         JButton delete = new JButton();
         JButton edit = new JButton();

         add.setIcon(new ImageIcon("img/fillAddSmall.png"));
         rotate.setIcon(new ImageIcon("img/fillRotateSmall.png"));
         delete.setIcon(new ImageIcon("img/fillDeleteSmall.png"));
         edit.setIcon(new ImageIcon("img/fillEditSmall.png"));

         add.setBorder(null);
         add.setMargin(new Insets(0, 0, 0, 0));
         add.setContentAreaFilled(false);

         add.addActionListener(new newShapeListener(frame));

         rotate.setBorder(null);
         rotate.setMargin(new Insets(0, 0, 0, 0));
         rotate.setContentAreaFilled(false);

         delete.setBorder(null);
         delete.setMargin(new Insets(0, 0, 0, 0));
         delete.setContentAreaFilled(false);

         edit.setBorder(null);
         edit.setMargin(new Insets(0, 0, 0, 0));
         edit.setContentAreaFilled(false);

         panControls.add(add);
         panControls.add(rotate);
         panControls.add(delete);
         panControls.add(edit);
         panControls.setOpaque(false);
         panControls.setLayout(new FlowLayout());

         JPanel panGrid = new JPanel(new GridLayout(20,20));
         panGrid.setOpaque(false);
         panGrid.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

         for (int i =0; i<(20*20); i++){
             final JLabel label = new JLabel();
             label.setBorder(BorderFactory.createLineBorder(Color.WHITE));
             panGrid.add(label);
         }

         panBuild.setLayout(new BorderLayout());
         panBuild.add(panGrid, BorderLayout.CENTER);
         panBuild.add(panControls, BorderLayout.SOUTH);
    }

    public JPanel getPanel() {
        return panBuild;
    }
}
