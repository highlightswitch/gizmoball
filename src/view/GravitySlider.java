package view;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GravitySlider {
    JDialog gravity;
    int newGravity;

    public GravitySlider(JFrame f){
        JLabel label = new JLabel("Drag slider to change gravity: ");
        JSlider s = new JSlider(JSlider.HORIZONTAL, -35, 35, 25);

        s.setMajorTickSpacing(10);
        s.setMinorTickSpacing(5);
        s.setPaintTicks(true);
        s.setPaintLabels(true);
        s.setOpaque(false);

        s.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                newGravity = s.getValue();
            }
        });

        JPanel content = new JPanel();
        JPanel panControls = new JPanel();
        JButton ok = new JButton("OK");

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gravity.dispose();
            }
        });

        panControls.add(ok);
        panControls.setOpaque(false);

        content.add(label);
        content.add(s);
        content.add(panControls);
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        content.setBackground(Color.ORANGE);

        gravity = new JDialog(f, "Modify Gravity", true);
        gravity.setContentPane(content);
        gravity.setMinimumSize(new Dimension(400,150));
        gravity.setVisible(true);
    }
}
