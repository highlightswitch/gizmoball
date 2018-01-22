package controller;

import model.gizmo.TriggerType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

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
                controller.getModel().getBall().moveBall();
                break;
            case "Flip Down":
                controller.getModel().keyEventTriggered(70, TriggerType.KEY_DOWN);
                break;
            case "Flip Up":
                controller.getModel().keyEventTriggered(70, TriggerType.KEY_UP);
                break;
            case "Quit":
                System.exit(0);
                break;
        }

	}
}
