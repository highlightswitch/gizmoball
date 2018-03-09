package view;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrictionSlider {
    JDialog friction;
    int newFriction;
    public FrictionSlider(JFrame f){
        JLabel label = new JLabel("Drag slider to change friction: ");
        JSlider s = new JSlider(JSlider.HORIZONTAL, 0, 50, 25);

        s.setMajorTickSpacing(10);
        s.setMinorTickSpacing(1);
        s.setPaintTicks(true);
        s.setPaintLabels(true);
        s.setOpaque(false);

        s.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                newFriction = (s.getValue()/100);
            }
        });

        JPanel content = new JPanel();
        JPanel panControls = new JPanel();
        JButton ok = new JButton("OK");

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                friction.dispose();
            }
        });

        panControls.add(ok);
        panControls.setOpaque(false);

        content.add(label);
        content.add(s);
        content.add(panControls);
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        content.setBackground(Color.PINK);

        friction = new JDialog(f, "Modify Friction", true);
        friction.setContentPane(content);
        friction.setMinimumSize(new Dimension(400,150));
        friction.setVisible(true);

    }
}
