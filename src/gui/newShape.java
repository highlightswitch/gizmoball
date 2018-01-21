package gui;

import javax.swing.*;
import java.awt.*;

public class newShape {
    JPanel panShape;

    public newShape(JFrame fr){
        String[] actions = {"change colour", "rotate", "move flipper", "idk"};
        String[] triggers = {"key press", "other shape collision", "self collision", "idk"};
        JLabel lb = new JLabel("shape icon");
        JComboBox actionList = new JComboBox(actions);
        JComboBox triggerList = new JComboBox(triggers);
        JTextField position = new JTextField();
        JColorChooser shapeColour = new JColorChooser();

        panShape = new JPanel();
        panShape.setLayout(new GridLayout(0,2));

        panShape.add(lb);
        panShape.add(actionList);
        panShape.add(position);
        panShape.add(triggerList);
        panShape.add(shapeColour);
        panShape.setBackground(Color.ORANGE);

        JDialog shape =  new JDialog(fr, "Add a Gizmo", true);
        shape.setContentPane(panShape);
        shape.setVisible(true);
    }
}
