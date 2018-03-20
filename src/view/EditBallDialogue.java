package view;

import controller.EditBallListener;
import controller.MainController;
import controller.PlaceBallListener;
import model.Model;
import model.gizmo.Gizmo;
import model.gizmo.GizmoPropertyType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditBallDialogue {
    private JPanel panDI;
    private JDialog edit;
    private String intPosition;
    private String intVelocity;
    private Color color;

    public EditBallDialogue(MainController controller, JFrame f, String mode, Gizmo g){

        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(getClass().getResource("/Images/borderBallSmall.png")));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel pos = new JLabel("Initial position: ");
        JTextField position = new JTextField("(" + (int)g.getPosition()[0] + "," + (int)g.getPosition()[1] + ")");

        JLabel vel = new JLabel("Initial velocity: ");
        JTextField velocity = new JTextField("(" +  g.getProperty(GizmoPropertyType.VEL_X) + "," +  g.getProperty(GizmoPropertyType.VEL_Y) + ")");


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
        panForm.add(velocity);

        panShape.add(panForm);
        panShape.add(shapeColour);
        panShape.setOpaque(false);

        JPanel panControls = new JPanel();
        JButton ok = new JButton("OK");

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                intPosition = position.getText();
                intVelocity = velocity.getText();
                color = shapeColour.getColor();
                if(mode.equals("Add")){
                    new PlaceBallListener(controller, intPosition, intVelocity, color);
                }else {
                    new EditBallListener(controller, g, intPosition, intVelocity, color);
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
