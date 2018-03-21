package view;

import controller.EditAbsorberListener;
import controller.MainController;
import controller.PlaceAbsorberListener;
import model.GizmoNotFoundException;
import model.gizmo.Gizmo;
import model.gizmo.GizmoPropertyType;
import model.gizmo.Triggerable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditAbsorberDialogue {

    private JPanel panDI;
    private JDialog edit;
    private String startPosition;
    private String widthS;
    private String heightS;
    private Color color;

    public EditAbsorberDialogue(MainController controller, JFrame f, String mode, Gizmo g){
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(getClass().getResource("/Images/fillRectangleSmall.png")));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel spos = new JLabel("Start position (top left): ");

        JTextField sposition;

        if(g != null){
            Double x = g.getPosition()[0] ;
            Double y = g.getPosition()[1] ;
            sposition = new JTextField("(" + x.intValue() + "," + y.intValue() + ")");
        }else {
            sposition = new JTextField("(0,0)");
        }

        JLabel lbtrig = new JLabel("This gizmo is connected to the following gizmos: ");
        JList<model.gizmo.Triggerable> triggers = new JList<>();
        DefaultListModel<model.gizmo.Triggerable> triggerModel = new DefaultListModel<>();
        if(g != null) {
            try {
                for(Triggerable n : controller.getIModel().getAllTriggers(g.getProperty(GizmoPropertyType.NAME))){
                    triggerModel.addElement(n);
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
                //controller.getIModel().disconnect(g.getProperty((GizmoPropertyType.NAME)), triggerModel.get(i));
                triggerModel.remove(i);
            }
        });

        JLabel w = new JLabel("Width: ");

        JTextField width;

        JLabel h = new JLabel("Height: ");
        JTextField height;

        if(g != null){
            width = new JTextField(g.getProperty(GizmoPropertyType.WIDTH));
            height = new JTextField(g.getProperty(GizmoPropertyType.HEIGHT));
        }else {
            width = new JTextField();
            height = new JTextField();
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
        panForm.add(spos);
        panForm.add(sposition);
        panForm.add(w);
        panForm.add(width);
        panForm.add(h);
        panForm.add(height);
        panForm.add(lbtrig);
        panForm.add(listScroller);
        panForm.add(rmconnection);

        panShape.add(panForm);
        panShape.add(shapeColour);
        panShape.setOpaque(false);

        JPanel panControls = new JPanel();
        JButton ok = new JButton("OK");

        ok.addActionListener( e -> {
                startPosition = sposition.getText();
                widthS  = width.getText();
                heightS = height.getText();
                color = shapeColour.getColor();
                if(mode.equals("Add")){
                    new PlaceAbsorberListener(controller, startPosition, widthS, heightS, color);
                } else {
                    new EditAbsorberListener(controller, g, startPosition, widthS, heightS, color);
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
        edit =  new JDialog(f, "Ball", true);
        edit.setContentPane(panDI);
        edit.setMinimumSize(new Dimension(1000,350));
        edit.setVisible(true);
    }
}
