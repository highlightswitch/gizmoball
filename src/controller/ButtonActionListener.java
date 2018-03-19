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

        AllMouseListeners mouse = controller.getMouseListener();

        mouse.setType(e.getActionCommand());
        System.out.println("action preformed on " + e.getActionCommand());
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
                mouse.setMode("Edit");
                System.out.println("In " + mouse.getMode() + " mode");
                break;
            case "Circle":
            case "Triangle":
            case "Square":
            case "Absorber":
            case "Flipper":
            case "Ball":
                mouse.setMode("Add");
                System.out.println("In  " + mouse.getMode() + " mode");
                break;
            default:
                break;
        }

	}
}
