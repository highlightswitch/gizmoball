package view;

import controller.PlaceAbsorberListener;
import controller.PlaceBallListener;
import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditAbsorberDialogue {
    JPanel panDI;
    JDialog edit;
    String startPosition;
    String endPosition;
    Color color;

    public EditAbsorberDialogue(JFrame f, String mode, Model m){
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(getClass().getResource("/Images/fillRectangleSmall.png")));

        JLabel spos = new JLabel("Start position (top left): ");
        JTextField sposition = new JTextField("(0,0)");

        JLabel epos = new JLabel("End position (bottom right): ");
        JTextField eposition = new JTextField("(0,0)");

        JColorChooser shapeColour = new JColorChooser();
        shapeColour.setPreviewPanel(new JPanel()); // removes preview pane;
        shapeColour.setOpaque(false);

        JPanel panShape = new JPanel();
        panShape.setLayout(new GridLayout(0,2));

        JPanel panForm = new JPanel();
        panForm.setLayout(new GridLayout(0,1));
        panForm.setOpaque(false);

        panForm.add(label);
        panForm.add(spos);
        panForm.add(sposition);
        panForm.add(epos);
        panForm.add(eposition);

        panShape.add(panForm);
        panShape.add(shapeColour);
        panShape.setOpaque(false);

        JPanel panControls = new JPanel();
        JButton ok = new JButton("OK");

        if(mode.equals("Add")){
            ok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    startPosition = sposition.getText();
                    endPosition = eposition.getText();
                    color = shapeColour.getColor();
                    new PlaceAbsorberListener(startPosition, endPosition , color, m);
                    edit.dispose();
                }
            });
        } else {
            //edit shape listener;
        }

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
