package controller;

import model.gizmo.TriggerType;
import view.EditShapeDialogue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonActionListener implements ActionListener {

    private MainController controller;
    JFrame frame;

    ButtonActionListener(MainController controller, JFrame f) {
        this.controller = controller;
        frame = f;
	}

    @Override
	public final void actionPerformed(final ActionEvent e) {

        switch (e.getActionCommand()) {
            case "Start":
                controller.startTimer();
                break;
            case "Stop":
                controller.stopTimer();
                break;
            case "Tick":
                controller.getModel().getBall().moveBall(null);
                break;
            case "Delete":
                break;
            case "Rotate":
                break;
            case "Edit":
                EditShapeDialogue shapeDi = new EditShapeDialogue(frame);
                break;
            case "Move":
                //
                break;
        }

	}
}
