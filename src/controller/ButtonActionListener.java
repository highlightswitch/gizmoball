package controller;

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
                controller.getModel().moveBall();
                break;
            case "Flip":
                controller.getModel().keyEventTriggered(70);
                break;
            case "Quit":
                System.exit(0);
                break;
        }

	}
}
