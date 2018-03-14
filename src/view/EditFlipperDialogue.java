package view;

import controller.PlaceBallListener;
import controller.PlaceFlipperListener;
import model.Model;
import model.gizmo.Gizmo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditFlipperDialogue {
    JPanel panDI;
    JDialog edit;
    String intPosition;
    String orient;
    Color color;

    public EditFlipperDialogue(JFrame f, String mode, Model m, Gizmo g){
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(getClass().getResource("/Images/fillFlipperSmall.png")));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel pos = new JLabel("Initial position: ");
        JTextField position = new JTextField("(0,0)");

        JLabel label2 = new JLabel("Select flipper direction: ");
        String[] direction = {"Left", "Right"};
        JComboBox di = new JComboBox(direction);

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
        panForm.add(label2);
        panForm.add(di);

        panShape.add(panForm);
        panShape.add(shapeColour);
        panShape.setOpaque(false);

        JPanel panControls = new JPanel();
        JButton ok = new JButton("OK");

        if(mode.equals("Add")){
            ok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    intPosition = position.getText();
                    orient = direction[di.getSelectedIndex()];
                    color = shapeColour.getColor();
                    new PlaceFlipperListener(intPosition,orient,color,m);
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

        edit =  new JDialog(f, "Flipper", true);
        edit.setContentPane(panDI);
        edit.setMinimumSize(new Dimension(900,350));
        edit.setVisible(true);
    }
}
