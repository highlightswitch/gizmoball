package view;

import controller.EditFlipperListener;
import controller.MainController;
import controller.PlaceFlipperListener;
import model.GizmoNotFoundException;
import model.gizmo.Gizmo;
import model.gizmo.GizmoPropertyType;
import model.gizmo.TriggerType;
import model.util.GizmoUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class EditFlipperDialogue {
    private JPanel panDI;
    private JDialog edit;
    private String intPosition;
    private String orient;
    private Color color;

    public EditFlipperDialogue(MainController controller, JFrame f, String mode, Gizmo g){
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(getClass().getResource("/Images/fillFlipperSmall.png")));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel pos = new JLabel("Initial position: ");
        JTextField position;

        if(g != null){
            Double x = g.getPosition()[0] ;
            Double y = g.getPosition()[1] ;
            position = new JTextField("(" + x.intValue() + "," + y.intValue() + ")");
        }else {
            position = new JTextField("(0,0)");
        }

        JLabel label2 = new JLabel("Select flipper direction: ");
        String[] direction = {"Left", "Right"};
        JComboBox<String> di = new JComboBox<>(direction);
        if(g != null)
            di.setSelectedIndex(Boolean.valueOf(g.getProperty(GizmoPropertyType.IS_LEFT_ORIENTATED)) ? 0 : 1);

        JLabel lbtrig = new JLabel("This gizmo is connected to the following gizmos: ");
        JList<String> triggers = new JList<>();
        DefaultListModel<String> triggerModel = new DefaultListModel<>();

        if(g != null) {
            try {
                for(Gizmo n : controller.getIModel().getAllConnectedGizmos(g.getProperty(GizmoPropertyType.NAME))){
                    triggerModel.addElement(n.getProperty(GizmoPropertyType.NAME));
                }
                for (String k[] : controller.getIModel().getAllConnectedKeys(g.getProperty(GizmoPropertyType.NAME))){
                    triggerModel.addElement(KeyEvent.getKeyText(Integer.valueOf(k[0])) + "  -  " + k[1]);
                }
            } catch (GizmoNotFoundException e) {
                JOptionPane.showMessageDialog(f, "Gizmo not found");
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
                    JOptionPane.showMessageDialog(f, "Gizmo not found!");
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
        panForm.add(pos);
        panForm.add(position);
        panForm.add(label2);
        panForm.add(di);
        panForm.add(lbtrig);
        panForm.add(listScroller);
        panForm.add(rmconnection);

        panShape.add(panForm);
        panShape.add(shapeColour);
        panShape.setOpaque(false);

        JPanel panControls = new JPanel();
        JButton ok = new JButton("OK");


        ok.addActionListener(e -> {
                intPosition = position.getText();
                orient = direction[di.getSelectedIndex()];
                color = shapeColour.getColor();
                if(mode.equals("Add")){
                    new PlaceFlipperListener(controller, intPosition, orient, color);
                }else {
                    new EditFlipperListener(controller, g, intPosition, orient, color);
                }
                edit.dispose();
        });


        panControls.add(ok);
        panControls.setOpaque(false);

        panDI = new JPanel();
        panDI.add(panShape);
        panDI.add(panControls);
        panDI.setBackground(Color.ORANGE);
        panDI.setLayout(new BoxLayout(panDI, BoxLayout.Y_AXIS));

        edit =  new JDialog(f, "Flipper", true);
        edit.setContentPane(panDI);
        edit.setMinimumSize(new Dimension(1000,450));
        edit.setVisible(true);
    }
}
