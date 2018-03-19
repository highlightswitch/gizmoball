package view;

import controller.SetFriction;
import model.IModel;
import model.ModelPropertyException;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrictionSlider {

    private JDialog friction;
    private double newXFriction;
    private double newYFriction;

    public FrictionSlider(JFrame f, IModel m){
        JLabel label = new JLabel("Drag slider to change friction on the x axis: ");
        JSlider sx = new JSlider(JSlider.HORIZONTAL, 0, 50, 25);

        sx.setMajorTickSpacing(10);
        sx.setMinorTickSpacing(1);
        sx.setPaintTicks(true);
        sx.setPaintLabels(true);
        sx.setOpaque(false);

        sx.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                newXFriction = (sx.getValue()/100);
            }
        });

        JLabel label2 = new JLabel("Drag slider to change friction on the y axis: ");
        JSlider sy = new JSlider(JSlider.HORIZONTAL, 0, 50, 25);

        sy.setMajorTickSpacing(10);
        sy.setMinorTickSpacing(1);
        sy.setPaintTicks(true);
        sy.setPaintLabels(true);
        sy.setOpaque(false);

        sy.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                newYFriction = (sy.getValue()/100);
            }
        });

        JPanel content = new JPanel();
        JPanel panControls = new JPanel();
        JButton ok = new JButton("OK");

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new SetFriction(m, newXFriction, newYFriction);
                    friction.dispose();
                } catch (ModelPropertyException e1) {
                    e1.printStackTrace();
                }
            }
        });

        panControls.add(ok);
        panControls.setOpaque(false);

        content.add(label);
        content.add(sx);
        content.add(label2);
        content.add(sy);
        content.add(panControls);
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        content.setBackground(Color.ORANGE);

        friction = new JDialog(f, "Modify Friction", true);
        friction.setContentPane(content);
        friction.setMinimumSize(new Dimension(400,200));
        friction.setVisible(true);

    }
}
