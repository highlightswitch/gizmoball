package gui;

import javax.swing.*;
import java.awt.*;

public class buildView {
    JPanel panBuild;

     public buildView() {
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

         JPanel panGrid = new JPanel();
         panGrid.setOpaque(false);

         panBuild.setLayout(new BorderLayout());
         panBuild.add(panGrid, BorderLayout.CENTER);
         panBuild.add(panControls, BorderLayout.SOUTH);
    }

    public JPanel getPanel() {
        return panBuild;
    }
}
