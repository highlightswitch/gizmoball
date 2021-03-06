package view;

import controller.SetGravity;
import model.IModel;
import model.ModelPropertyException;

import javax.swing.*;
import java.awt.*;

public class GravitySlider {
    private JDialog gravity;
    private double newGravity;

    public GravitySlider(JFrame f, IModel m){
        JLabel label = new JLabel("Drag slider to change gravity: ");
        JSlider s = new JSlider(JSlider.HORIZONTAL, -35, 35, 25);

        s.setMajorTickSpacing(10);
        s.setMinorTickSpacing(5);
        s.setPaintTicks(true);
        s.setPaintLabels(true);
        s.setOpaque(false);

        s.addChangeListener(e -> newGravity = s.getValue());

        JPanel content = new JPanel();
        JPanel panControls = new JPanel();
        JButton ok = new JButton("OK");

        ok.addActionListener(e -> {
			try {
				new SetGravity(m, newGravity);
			} catch (ModelPropertyException e1) {
				JOptionPane.showMessageDialog(f, "You cannot modify this property");
			}
			gravity.dispose();
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
