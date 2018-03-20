package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonActionListener implements ActionListener {

    private MainController controller;

    ButtonActionListener(MainController controller) {
        this.controller = controller;
	}

    @Override
	public final void actionPerformed(final ActionEvent e) {

        MouseHandler mouseHandler = controller.getMouseHandler();

        mouseHandler.setType(e.getActionCommand());
        switch (e.getActionCommand()) {
            case "Start":
                controller.startTimer();
                break;
            case "Stop":
                controller.stopTimer();
                break;
            case "Delete":
                controller.getView().setMessage("Click the gizmo you want to delete");
                mouseHandler.setMode("Edit");
                break;
            case "Rotate":
                controller.getView().setMessage("Click the gizmo you want to rotate");
                mouseHandler.setMode("Edit");
                break;
            case "Edit":
                controller.getView().setMessage("Click the gizmo you want to edit");
                mouseHandler.setMode("Edit");
                break;
            case "Key":
                controller.getView().setMessage("Click the gizmo you want to connect");
                mouseHandler.setMode("Edit");
                break;
            case "Connect":
                controller.getView().setMessage("Click and drag to connect two gizmos");
                mouseHandler.setMode("Edit");
                break;
            case "Move":
                controller.getView().setMessage("Click and drag gizmo to new tile");
                mouseHandler.setMode("Edit");
                System.out.println("In " + mouseHandler.getMode() + " mode");
                break;
            case "Circle":
                controller.getView().setMessage("Click on tile to add Circle");
                mouseHandler.setMode("Add");
                break;
            case "Triangle":
                controller.getView().setMessage("Click on tile to add Triangle");
                mouseHandler.setMode("Add");
                break;
            case "Square":
                controller.getView().setMessage("Click on tile to add Square");
                mouseHandler.setMode("Add");
                break;
            case "Absorber":
                controller.getView().setMessage("Click on tile to add 5x1 Absorber");
                mouseHandler.setMode("Add");
                break;
            case "Flipper":
                controller.getView().setMessage("Click on tile to add Left Flipper");
                mouseHandler.setMode("Add");
                break;
            case "Ball":
                controller.getView().setMessage("Click on tile to add Ball");
                mouseHandler.setMode("Add");
                System.out.println("In  " + mouseHandler.getMode() + " mode");
                break;
            default:
                break;
        }

	}
}
