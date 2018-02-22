package controller;

import model.gizmo.TriggerType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonActionListener implements ActionListener {

    private MainController controller;

    ButtonActionListener(MainController controller) {
        this.controller = controller;
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
                //
                break;
            case "Rotate":
                //
                break;
            case "Edit":
                //
                break;
            case "Move":
                //
                break;
        }

	}
}
