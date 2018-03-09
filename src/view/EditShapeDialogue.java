package view;

import controller.PlaceGizmoListener;
import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditShapeDialogue {
    JPanel panDI;
    String gizmo;
    String intPosition;
    Color color;
    String cAction;
    String cTrigger;
    JDialog edit;
    Board board;

    public EditShapeDialogue(JFrame fr, String shape, String mode, Model model){
        gizmo = shape;

        JLabel action = new JLabel("When the ball collides with this gizmo the gizmo should: ");
        String[] actions = {"Change Colour", "Rotate", "Activate Another Gizmo"};
        JLabel trigger = new JLabel("This gizmo is triggered by: ");
        String[] triggers = {"A Key Press", "Another Gizmo", "Ball Collision"};

        JLabel label = new JLabel();

        switch (gizmo){
            case "Circle":
                label.setIcon(new ImageIcon(getClass().getResource("/Images/fillCircleSmall.png")));
                break;
            case "Triangle":
                label.setIcon(new ImageIcon(getClass().getResource("/Images/fillTriangleSmall.png")));
                break;
            case "Square":
                label.setIcon(new ImageIcon(getClass().getResource("/Images/fillSquareSmall.png")));
                break;
        }

        JComboBox actionList = new JComboBox(actions);
        JComboBox triggerList = new JComboBox(triggers);

        JLabel pos = new JLabel("Initial position: ");
        JTextField position = new JTextField("(0,0)");

        JColorChooser shapeColour = new JColorChooser();
        shapeColour.setPreviewPanel(new JPanel()); // removes preview pane;

        JPanel panShape = new JPanel();
        panShape.setLayout(new GridLayout(0,2));

        JPanel panForm = new JPanel();
        panForm.setLayout(new BoxLayout(panForm, BoxLayout.Y_AXIS));
        panForm.setOpaque(false);

        panForm.add(label);
        panForm.add(action);
        panForm.add(actionList);
        panForm.add(pos);
        panForm.add(position);
        panForm.add(trigger);
        panForm.add(triggerList);

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
                    color = shapeColour.getColor();
                    cAction = actions[actionList.getSelectedIndex()];
                    cTrigger = triggers[triggerList.getSelectedIndex()];
                    new PlaceGizmoListener(gizmo, intPosition, color, cAction, cTrigger, model);
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

        edit =  new JDialog(fr, "Gizmo", true);
        edit.setContentPane(panDI);
        edit.setMinimumSize(new Dimension(400,200));
        edit.setVisible(true);
    }
}
