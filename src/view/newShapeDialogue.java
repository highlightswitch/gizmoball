package view;

import javax.swing.*;
import java.awt.*;

public class newShapeDialogue {
    JPanel panDI;

    public newShapeDialogue(JFrame fr){
        String[] actions = {"change colour", "rotate", "connect to other gizmo", "idk"};
        String[] triggers = {"key press", "connect to other gizmo", "connect to self", "idk"};
        JLabel label = new JLabel("shape icon");
        JComboBox actionList = new JComboBox(actions);
        JComboBox triggerList = new JComboBox(triggers);
        JTextField position = new JTextField();

        JColorChooser shapeColour = new JColorChooser();
        shapeColour.setPreviewPanel(new JPanel()); // removes preview pane;

        JPanel panShape = new JPanel();
        panShape.setLayout(new GridLayout(0,2));

        JPanel panForm = new JPanel();
        panForm.setLayout(new BoxLayout(panForm, BoxLayout.Y_AXIS));
        panForm.setOpaque(false);

        panForm.add(label);
        panForm.add(actionList);
        panForm.add(position);
        panForm.add(triggerList);

        panShape.add(panForm);
        panShape.add(shapeColour);
        panShape.setOpaque(false);

        JPanel panControls = new JPanel();
        JButton ok = new JButton("Ok");
        panControls.add(ok);
        panControls.setOpaque(false);

        panDI = new JPanel();
        panDI.add(panShape);
        panDI.add(panControls);
        panDI.setBackground(Color.ORANGE);
        panDI.setLayout(new BoxLayout(panDI, BoxLayout.Y_AXIS));

        JDialog shape =  new JDialog(fr, "Add a Gizmo", true);
        shape.setContentPane(panDI);
        shape.setMinimumSize(new Dimension(400,200));
        shape.setVisible(true);
    }
}
