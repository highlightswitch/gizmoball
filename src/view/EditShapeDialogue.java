package view;

import controller.EditGizmoListener;
import controller.MainController;
import controller.PlaceGizmoListener;
import model.gizmo.Gizmo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditShapeDialogue {
    private JPanel panDI;
    private String gizmo;
    private String intPosition;
    private Color color;
    private Color altc;
    private String cAction;
    private JDialog edit;

    public EditShapeDialogue(MainController controller, JFrame fr, String shape, String mode, Gizmo g){

        gizmo = shape;

        //change to mapping from action
        JLabel action = new JLabel("When the ball collides with this gizmo the gizmo should: ");
        String[] actions = {"Change Colour", "Do Nothing"};

        JLabel label = new JLabel();

        switch (gizmo.toLowerCase()){
            case "circle":
                label.setIcon(new ImageIcon(getClass().getResource("/Images/fillCircleSmall.png")));
                break;
            case "triangle":
                label.setIcon(new ImageIcon(getClass().getResource("/Images/fillTriangleSmall.png")));
                break;
            case "square":
                label.setIcon(new ImageIcon(getClass().getResource("/Images/fillSquareSmall.png")));
                break;
        }

        label.setHorizontalAlignment(SwingConstants.CENTER);

        JComboBox<String> actionList = new JComboBox<>(actions);

        JLabel pos = new JLabel("Initial position: ");
        JTextField position;

        if(g != null){
            Double x = g.getPosition()[0] ;
            Double y = g.getPosition()[1] ;
            position = new JTextField("(" + x.intValue() + "," + y.intValue() + ")");
        }else {
            position = new JTextField("(0,0)");
        }

        JColorChooser shapeColour = new JColorChooser();
        shapeColour.setPreviewPanel(new JPanel()); // removes preview pane;
        shapeColour.setOpaque(false);

        JPanel panShape = new JPanel();
        panShape.setLayout(new GridLayout(0,2));

        JPanel panForm = new JPanel();
        panForm.setLayout(new GridLayout(0,1));
        panForm.setOpaque(false);

        panForm.add(label);
        panForm.add(action);
        panForm.add(actionList);

        panForm.add(pos);
        panForm.add(position);

        panShape.add(panForm);
        panShape.add(shapeColour);
        panShape.setOpaque(false);

        JPanel panControls = new JPanel();
        JButton ok = new JButton("OK");

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                intPosition = position.getText();
                color = shapeColour.getColor();
                cAction = actions[actionList.getSelectedIndex()];

                if(cAction.equals("Change Colour")){
                    JColorChooser alt = new JColorChooser();
                    alt.setPreviewPanel(new JPanel());
                    JOptionPane.showMessageDialog(fr, alt, "Alternative Color", JOptionPane.QUESTION_MESSAGE);
                    altc = alt.getColor();
                }

                if(mode.equals("Add")){
                    new PlaceGizmoListener(controller, gizmo, intPosition, color, altc, cAction);
                } else {
                    new EditGizmoListener(controller, g, intPosition, color, altc, cAction);
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

        edit =  new JDialog(fr, "Gizmo", true);
        edit.setContentPane(panDI);
        edit.setMinimumSize(new Dimension(900,350));
        edit.setVisible(true);
    }
}
