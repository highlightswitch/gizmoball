package view;

import controller.EditGizmoListener;
import controller.MainController;
import controller.PlaceGizmoListener;
import model.GizmoNotFoundException;
import model.gizmo.Gizmo;
import model.gizmo.GizmoActionType;
import model.gizmo.GizmoPropertyType;
import model.gizmo.TriggerType;
import model.util.GizmoUtils;

import javax.swing.*;
import java.awt.*;

public class EditShapeDialogue {
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
        actionList.setSelectedIndex( g != null && g.getActionType() == GizmoActionType.CHANGE_COLOUR ? 0 : 1);

        JLabel pos = new JLabel("Initial position: ");
        JTextField position;

        if(g != null){
            Double x = g.getPosition()[0] ;
            Double y = g.getPosition()[1] ;
            position = new JTextField("(" + x.intValue() + "," + y.intValue() + ")");
        }else {
            position = new JTextField("(0,0)");
        }

        JLabel lbtrig = new JLabel("This gizmo is connected to the following gizmos: ");

        //TODO: Should this not be a list of strings? -EB
        JList<String> triggers = new JList<>();
        DefaultListModel<String> triggerModel = new DefaultListModel<>();

        if(g != null) {
            try {
                for(Gizmo n : controller.getIModel().getAllConnectedGizmos(g.getProperty(GizmoPropertyType.NAME))){
                    triggerModel.addElement(n.getProperty(GizmoPropertyType.NAME));
                }
                for (String k[] : controller.getIModel().getAllConnectedKeys(g.getProperty(GizmoPropertyType.NAME))){
                    triggerModel.addElement(k[0] + "  -  " + k[1]);
                }
            } catch (GizmoNotFoundException e) {
                JOptionPane.showMessageDialog(fr, "Gizmo not found");
            }
        }
        triggers.setModel(triggerModel);
        triggers.setLayoutOrientation(JList.VERTICAL);
        triggers.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        triggers.setVisibleRowCount(3);

        JScrollPane listScroller = new JScrollPane(triggers);
        JButton rmconnection = new JButton("Delete Connection");
        rmconnection.addActionListener(e-> {
            for(int i : triggers.getSelectedIndices()){
                try {
                    if(triggerModel.get(i).matches("\\d")){
                       controller.getIModel().disconnect(Integer.valueOf(triggerModel.get(i)), TriggerType.KEY_DOWN, g.getProperty((GizmoPropertyType.NAME)));
                    } else{
                        controller.getIModel().disconnect(g.getProperty((GizmoPropertyType.NAME)), triggerModel.get(i));
                    }
                } catch (GizmoNotFoundException e1) {
                    JOptionPane.showMessageDialog(fr, "Gizmo not found!");
                }
                triggerModel.remove(i);
            }
        });

        JColorChooser shapeColour = new JColorChooser();
        shapeColour.setPreviewPanel(new JPanel()); // removes preview pane;
        shapeColour.setOpaque(false);
        if(g != null) {
            int[] currentRGB = GizmoUtils.colourStringParser(g.getProperty(GizmoPropertyType.DEFAULT_COLOUR));
            shapeColour.setColor(currentRGB[0], currentRGB[1], currentRGB[2]);
        }

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

        panForm.add(lbtrig);
        panForm.add(listScroller);
        panForm.add(rmconnection);

        panShape.add(panForm);
        panShape.add(shapeColour);
        panShape.setOpaque(false);

        JPanel panControls = new JPanel();
        JButton ok = new JButton("OK");

        ok.addActionListener( e -> {
                intPosition = position.getText();
                color = shapeColour.getColor();
                cAction = actions[actionList.getSelectedIndex()];

                if(cAction.equals("Change Colour")){
                    JColorChooser alt = new JColorChooser();
                    if(g != null) {
                        int[] currentRGB = GizmoUtils.colourStringParser(g.getProperty(GizmoPropertyType.ALT_COLOUR));
                        alt.setColor(currentRGB[0], currentRGB[1], currentRGB[2]);
                    }
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

        });

        panControls.add(ok);
        panControls.setOpaque(false);

        JPanel panDI = new JPanel();
        panDI.add(panShape);
        panDI.add(panControls);
        panDI.setBackground(Color.ORANGE);
        panDI.setLayout(new BoxLayout(panDI, BoxLayout.Y_AXIS));

        edit =  new JDialog(fr, "Gizmo", true);
        edit.setContentPane(panDI);
        edit.setMinimumSize(new Dimension(1000,450));
        edit.setVisible(true);
    }
}
