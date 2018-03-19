package controller;

import model.*;
import model.gizmo.GizmoPropertyException;
import model.gizmo.GizmoPropertyType;
import model.gizmo.GizmoType;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class AllMouseListeners implements MouseListener {
    private Model m;
    private JFrame frame;
    private JPanel panel;
    private String listType;
    private String mode;
    private String name;
    private int id = 0;
    private Tile t = null;
    private GameView view;
    private ArrayList<MouseListener> ms = new ArrayList<>();
    private final MouseListener moveListener;
    private final MouseListener connectListener;

    AllMouseListeners(JFrame f, Model model, JPanel p, GameView v){
        m = model;
        frame = f;
        panel = p;
        listType = "";
        this.mode = "";
        view = v;
        moveListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { }

            @Override
            public void mousePressed(MouseEvent e) { }

            @Override
            public void mouseReleased(MouseEvent e) {
                int offsetx = (frame.getWidth() - panel.getWidth())/2;
                int offsety = (frame.getHeight() - panel.getHeight())/3;
                int x = e.getX() - offsetx;
                int y = e.getY() - offsety;
                int[] xy = getXYNear(x,y);

                try {
                    Tile t2 = m.getTileAt(xy[0], xy[1]);
                    m.moveGizmo(t.getGizmo().getProperty(GizmoPropertyType.NAME), t2);
                    frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                } catch (TileCoordinatesNotValid tileCoordinatesNotValid) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Tile coordinates are not valid", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (GizmoNotFoundException e1) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Gizmo not found", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (GizmoPlacementNotValidException e1) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "You cannot place this Gizmo here", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) { }

            @Override
            public void mouseExited(MouseEvent e) { }
        };

        ms.add(this);
        ms.add(moveListener);

        connectListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int offsetx = (frame.getWidth() - panel.getWidth())/2;
                int offsety = (frame.getHeight() - panel.getHeight())/3;
                int x = e.getX() - offsetx;
                int y = e.getY() - offsety;
                int[] xy = getXYNear(x,y);
                try {
                    Tile t2 = m.getTileAt(xy[0], xy[1]);
                    m.connect(t2.getGizmo().getProperty(GizmoPropertyType.NAME), t.getGizmo().getProperty(GizmoPropertyType.NAME));
                    view.setMessage("Connected to " + t2.getGizmo().getProperty(GizmoPropertyType.NAME));
                } catch (TileCoordinatesNotValid tileCoordinatesNotValid) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Tile coordinates are not valid", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (GizmoNotFoundException e1) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Gizmo not found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) { }

            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) { }

            @Override
            public void mouseExited(MouseEvent e) { }
        };
        ms.add(connectListener);
        System.out.println("Mouse listeners called, currently " + ms.size() + " listeners exist");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("current mouse mode " + getMode() + "!");

        int offsetx = (frame.getWidth() - panel.getWidth())/2;
        int offsety = (frame.getHeight() - panel.getHeight())/3;
        int x = e.getX() - offsetx;
        int y = e.getY() - offsety;
        int[] xy = getXYNear(x,y);

        try {
            t = m.getTileAt(xy[0], xy[1]);
            System.out.println("Getting tile at (" + xy[0] + "," + xy[1] + ")");

        } catch (TileCoordinatesNotValid tileCoordinatesNotValid) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Tile coordinates are not valid", "Error", JOptionPane.ERROR_MESSAGE);
        }

        if(getMode().equals("Add")){
            if(!t.isOccupied()){
                name = getType()+ id;
                id++;
                System.out.println("Adding " + getType());
                switch (getType()){
                    case "Ball":
                        try {

                            m.placeGizmo(GizmoType.BALL, t, null);
                        } catch (GizmoPlacementNotValidException e1) {
                            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Gizmo placement is not valid", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case "Absorber":
                        try {
                            m.placeGizmo(GizmoType.ABSORBER, t, null);
                        } catch (GizmoPlacementNotValidException e1) {
                            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Gizmo placement is not valid", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case "Flipper":
                        try {
                            m.placeGizmo(GizmoType.FLIPPER, t, null);
                        } catch (GizmoPlacementNotValidException e1) {
                            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Gizmo placement is not valid", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case "Circle":
                        try {
                            m.placeGizmo(GizmoType.CIRCLE_BUMPER, t, null);
                        } catch (GizmoPlacementNotValidException e1) {
                            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Gizmo placement is not valid", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case "Triangle":
                        try {
                            m.placeGizmo(GizmoType.TRIANGLE_BUMPER, t, null);
                        } catch (GizmoPlacementNotValidException e1) {
                            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Gizmo placement is not valid", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case "Square":
                        try {
                            m.placeGizmo(GizmoType.SQUARE_BUMPER, t, null);
                        } catch (GizmoPlacementNotValidException e1) {
                            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Gizmo placement is not valid", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                }
                try {
                    t.getGizmo().setProperty(GizmoPropertyType.NAME, name);
                } catch (GizmoPropertyException e1) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Wrong gizmo property", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                System.out.println("Gizmo occupied");
            }
        } else{
            if(t.isOccupied()){
                GizmoType g =  t.getGizmo().getType();
                if(getType().equals("Edit")){
                    switch (g){
                        case BALL:
                            new EditBallDialogue(frame, "Edit", m, t.getGizmo());
                            break;
                        case ABSORBER:
                            new EditAbsorberDialogue(frame, "Edit", m, t.getGizmo());
                            break;
                        case FLIPPER:
                            new EditFlipperDialogue(frame, "Edit", m, t.getGizmo());
                            break;
                        default:
                            String s = g.name().substring(0,g.name().indexOf("_")).toLowerCase();
                            new EditShapeDialogue(frame, s, "Edit", m, t.getGizmo());
                            break;
                    }
                }else if(getType().equals("Delete")){
                    try {
                        m.deleteGizmo(t.getGizmo().getProperty(GizmoPropertyType.NAME));
                    } catch (GizmoNotFoundException e1) {
                        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Cannot find gizmo", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }else if (getType().equals("Rotate")){
                    try {
                        m.rotateGizmoBy_Deg(t.getGizmo().getProperty(GizmoPropertyType.NAME), 90.0);
                    } catch (GizmoNotFoundException e1) {
                        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Cannot find gizmo", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (GizmoPropertyException e1) {
                        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Wrong gizmo property", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }else if(getType().equals("Move")){
                    view.setMessage("Drag and drop to move " + t.getGizmo().getProperty(GizmoPropertyType.NAME));
                    frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    for (MouseListener mousel : frame.getMouseListeners()){
                        frame.removeMouseListener(mousel);
                    }
                    frame.addMouseListener(moveListener);

                } else if(getType().equals("Key")){
                    view.setMessage("To connect this gizmo to a key, press that key now");
                    frame.setFocusable(true);
                    frame.addKeyListener(new TriggerKeyListener(frame, m, t));
                }else {
                    view.setMessage("To connect this gizmo to another gizmo click on that gizmo now");
                    frame.addMouseListener(connectListener);
                }
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public int[] getXYNear(double x, double y){
        Double px = Math.floor(x/25) - 1;
        Double py = Math.floor(y/25) - 1;

        if(px >= 0 && py >= 0){
            return new int[]{px.intValue(),py.intValue()};
        }else {
            return new int[]{0,0};
        }
    }

    public String getType() {
        return listType;
    }

    public void setType(String t){
        listType = t;
    }

    public String getMode(){
        return mode;
    }

    public void setMode(String m){
        mode = m;
    }
}
