package controller;

import model.*;
import model.gizmo.*;
import model.util.GizmoUtils;
import view.EditAbsorberDialogue;
import view.EditBallDialogue;
import view.EditFlipperDialogue;
import view.EditShapeDialogue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler {

    private String listType;
    private String mode;
    private Tile t = null;

    private MouseListener currentListener;
    private final MouseListener defaultListener;
    private final MouseListener moveListener;
    private final MouseListener connectListener;
    private MainController controller;

    MouseHandler(MainController controller, JFrame frame){
        this.controller = controller;
        listType = "";
        this.mode = "";

        defaultListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // Get the tile eq. to the pixel point the user clicks
                int x = e.getX();
                int y = e.getY();
                int[] xy = getXYNear(x, y);

                try {
                    IModel m = controller.getIModel();
                    t = m.getTileAt(xy[0], xy[1]);

                } catch (TileCoordinatesNotValid tileCoordinatesNotValid) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Tile coordinates are not valid", "Error", JOptionPane.ERROR_MESSAGE);
                }

                // if the user is looking to add something new, check that the tile is not already occupied, then add appropriate gizmo
                if (getMode().equals("Add")) {
                    if (!t.isOccupied()) {
                        switch (getType()) {
                            case "Ball":
                                try {
                                    IModel m = controller.getIModel();
                                    m.placeGizmo(GizmoType.BALL, t, null);
                                } catch (GizmoPlacementNotValidException|TileCoordinatesNotValid e1) {
                                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Gizmo placement is not valid", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                                break;
                            case "Absorber":
                                try {
                                    IModel m = controller.getIModel();
                                    m.placeGizmo(GizmoType.ABSORBER, t,null);
                                } catch (GizmoPlacementNotValidException|TileCoordinatesNotValid e1) {
                                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Gizmo placement is not valid", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                                break;
                            case "Left Flipper":
                                try {
                                    IModel m = controller.getIModel();
                                    m.placeGizmo(GizmoType.FLIPPER, t, null);
                                } catch (GizmoPlacementNotValidException|TileCoordinatesNotValid e1) {
                                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Gizmo placement is not valid", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                                break;
                            case "Right Flipper":
                                try {
                                    IModel m = controller.getIModel();
                                    //Hacky but theres 3 hours till the deadline so..
                                    String[] props = GizmoUtils.getPropertyDefaults(GizmoType.FLIPPER, controller.getModel().getAllGizmoNames());
                                    props[2] = "false";
                                    m.placeGizmo(GizmoType.FLIPPER, t, props);
                                } catch (GizmoPlacementNotValidException|TileCoordinatesNotValid e1) {
                                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Gizmo placement is not valid", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                                break;
                            case "Circle":
                                try {
                                    IModel m = controller.getIModel();
                                    m.placeGizmo(GizmoType.CIRCLE_BUMPER, t, null);
                                } catch (GizmoPlacementNotValidException|TileCoordinatesNotValid e1) {
                                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Gizmo placement is not valid", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                                break;
                            case "Triangle":
                                try {
                                    IModel m = controller.getIModel();
                                    m.placeGizmo(GizmoType.TRIANGLE_BUMPER, t, null);
                                } catch (GizmoPlacementNotValidException|TileCoordinatesNotValid e1) {
                                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Gizmo placement is not valid", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                                break;
                            case "Square":
                                try {
                                    IModel m = controller.getIModel();
                                    m.placeGizmo(GizmoType.SQUARE_BUMPER, t, null);
                                } catch (GizmoPlacementNotValidException|TileCoordinatesNotValid e1) {
                                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Gizmo placement is not valid", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                                break;
                        }
                    }
                } else {
                    // if the user is looking to edit an existing gizmo check that the tile is occupied then call the appropriate action
                    if (t.isOccupied()) {
                        GizmoType g = t.getGizmo().getType();
                        switch (getType()) {
                            case "Edit":
                                switch (g) {
                                    case BALL:
                                        new EditBallDialogue(controller, frame, "Edit", t.getGizmo());
                                        break;
                                    case ABSORBER:
                                        new EditAbsorberDialogue(controller, frame, "Edit", t.getGizmo());
                                        break;
                                    case FLIPPER:
                                        new EditFlipperDialogue(controller, frame, "Edit", t.getGizmo());
                                        break;
                                    default:
                                        String s = g.name().substring(0, g.name().indexOf("_")).toLowerCase();
                                        new EditShapeDialogue(controller, frame, s, "Edit", t.getGizmo());
                                        break;
                                }
                                break;
                            case "Delete":
                                try {
                                    IModel m = controller.getIModel();
                                    m.deleteGizmo(t.getGizmo().getProperty(GizmoPropertyType.NAME));
                                } catch (GizmoNotFoundException e1) {
                                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Cannot find gizmo", "Error", JOptionPane.ERROR_MESSAGE);
                                } catch (TileCoordinatesNotValid tileCoordinatesNotValid) {
                                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Coordinates not valid", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                                break;
                            case "Rotate":
                                try {
                                    IModel m = controller.getIModel();
                                    m.rotateGizmoBy_Deg(t.getGizmo().getProperty(GizmoPropertyType.NAME), 90.0);
                                } catch (GizmoNotFoundException e1) {
                                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Cannot find gizmo", "Error", JOptionPane.ERROR_MESSAGE);
                                } catch (GizmoPropertyException e1) {
                                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Wrong gizmo property", "Error", JOptionPane.ERROR_MESSAGE);
                                } catch (GizmoNotRotatableException e1){
                                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Cannot rotate this gizmo", "Error", JOptionPane.ERROR_MESSAGE);

                                }
                                break;
                            case "Move":
                                // to track two mouse events on the frame, once the user clicks on a gizmo to move switch out
                                // current listener for move listener, run move listener events and return control
                                controller.getView().setMessage("Drag and drop to move " + t.getGizmo().getProperty(GizmoPropertyType.NAME));
                                frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
                                currentListener = moveListener;
                                controller.updateMouseListener();
                                break;
                            case "Key":
                                if(t.getGizmo().getType() != GizmoType.BALL){
                                    controller.getView().setMessage("Press key to connect that key to " + t.getGizmo().getProperty(GizmoPropertyType.NAME));
                                    frame.requestFocus();
                                } else {
                                    controller.getView().setMessage("You cannot add actions to the Ball!");
                                }
                                break;
                            case "Connect":
                                if(t.getGizmo().getType() != GizmoType.BALL){
                                    controller.getView().setMessage("Connect " + t.getGizmo().getProperty(GizmoPropertyType.NAME) + " to...");
                                    currentListener = connectListener;
                                    controller.updateMouseListener();
                                } else {
                                    controller.getView().setMessage("You cannot add actions to the Ball!");
                                }
                                break;
                        }
                    }
                }
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
        };

        moveListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { }

            @Override
            public void mousePressed(MouseEvent e) { }

            @Override
            public void mouseReleased(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                int[] xy = getXYNear(x,y);

                try {
                    IModel m = controller.getIModel();
                    Tile t2 = m.getTileAt(xy[0], xy[1]);
                    if (!t.equals(t2)) {
                        m.moveGizmo(t.getGizmo().getProperty(GizmoPropertyType.NAME), t2);
                    }
                    frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    currentListener = defaultListener;
                    controller.updateMouseListener();
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

        connectListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { }

            @Override
            public void mousePressed(MouseEvent e) { }

            @Override
            public void mouseReleased(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                int[] xy = getXYNear(x,y);

                try {
                    IModel m = controller.getIModel();
                    Tile t2 = m.getTileAt(xy[0], xy[1]);
                    if(t2.getGizmo() != null) {
                        if (t2.getGizmo().getType() != GizmoType.BALL) {
                            m.connect(t.getGizmo().getProperty(GizmoPropertyType.NAME), t2.getGizmo().getProperty(GizmoPropertyType.NAME));
                            controller.getView().setMessage("Connected to " + t2.getGizmo().getProperty(GizmoPropertyType.NAME));
                        } else {
                            controller.getView().setMessage("You cannot add actions to the Ball!");
                        }
                    } else {
                        controller.getView().setMessage("No gizmo here! Try again.");
                    }
                    currentListener = defaultListener;
                    controller.updateMouseListener();
                } catch (TileCoordinatesNotValid tileCoordinatesNotValid) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Tile coordinates are not valid", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (GizmoNotFoundException e1) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Gizmo not found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) { }

            @Override
            public void mouseExited(MouseEvent e) { }
        };

        currentListener = defaultListener;
    }

    MouseListener getCurrentListener() {
        return currentListener;
    }

    private int[] getXYNear(double x, double y){
        Double px = Math.floor(x/25);
        Double py = Math.floor(y/25);

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

    private String getMode(){
        return mode;
    }

    void setMode(String m){
        mode = m;
    }

    void connectToKeyCode(int keyCode, TriggerType type){
        try {
            IModel model = controller.getIModel();
            String gizmoName = t.getGizmo().getProperty(GizmoPropertyType.NAME);
            model.connect(keyCode, type, gizmoName);
            controller.getView().setMessage("The " + KeyEvent.getKeyText(keyCode) + " key now activates " + gizmoName);
        } catch (GizmoNotFoundException e1) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Cannot find Gizmo");
        }
    }
}
