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
            case "Rotate":
            case "Edit":
            case "Key":
            case "Connect":
            case "Move":
                mouseHandler.setMode("Edit");
                System.out.println("In " + mouseHandler.getMode() + " mode");
                break;
            case "Circle":
            case "Triangle":
            case "Square":
            case "Absorber":
            case "Flipper":
            case "Ball":
                mouseHandler.setMode("Add");
                System.out.println("In  " + mouseHandler.getMode() + " mode");
                break;
            default:
                break;
        }

	}
}
