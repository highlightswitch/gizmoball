package view;

import controller.EditBallListener;
import controller.PlaceBallListener;
import model.Model;
import model.gizmo.Gizmo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditBallDialogue {
    JPanel panDI;
    JDialog edit;
    String intPosition;
    String intVelocity;
    Color color;

    public EditBallDialogue(JFrame f, String mode, Model m, Gizmo g){

        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(getClass().getResource("/Images/borderBallSmall.png")));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel pos = new JLabel("Initial position: ");
        JTextField position = new JTextField("(0,0)");

        JLabel vel = new JLabel("Initial velocity: ");
        JTextField velcoity = new JTextField("(0,0)");

        JColorChooser shapeColour = new JColorChooser();
        shapeColour.setPreviewPanel(new JPanel()); // removes preview pane;
        shapeColour.setOpaque(false);

        JPanel panShape = new JPanel();
        panShape.setLayout(new GridLayout(0,2));

        JPanel panForm = new JPanel();
        panForm.setLayout(new GridLayout(0,1));
        panForm.setOpaque(false);

        panForm.add(label);
        panForm.add(pos);
        panForm.add(position);
        panForm.add(vel);
        panForm.add(velcoity);

        panShape.add(panForm);
        panShape.add(shapeColour);
        panShape.setOpaque(false);

        JPanel panControls = new JPanel();
        JButton ok = new JButton("OK");

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                intPosition = position.getText();
                intVelocity = velcoity.getText();
                color = shapeColour.getColor();
                if(mode.equals("Add")){
                    new PlaceBallListener(intPosition, intVelocity, color, m);
                }else {
                    new EditBallListener(g, intPosition, intVelocity, color, m);
                }
                edit.dispose();
            }
        });

        panControls.add(ok);
        panControls.setOpaque(false);

        panDI = new JPanel();
        panDI.add(panShape);
        panDI.add(panControls);
        panDI.setBackground(Color.ORANGE);
        panDI.setLayout(new BoxLayout(panDI, BoxLayout.Y_AXIS));

        edit =  new JDialog(f, "Ball", true);
        edit.setContentPane(panDI);
        edit.setMinimumSize(new Dimension(900,350));
        edit.setVisible(true);
    }
}
