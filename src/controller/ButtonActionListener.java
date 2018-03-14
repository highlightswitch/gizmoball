package controller;

import model.Model;
import view.EditShapeDialogue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonActionListener implements ActionListener {

    private MainController controller;
    private JFrame frame;
    private Model model;

    ButtonActionListener(MainController controller, JFrame f, Model m) {
        this.controller = controller;
        frame = f;
        model = m;
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
            case "Delete":
                break;
            case "Rotate":
                break;
            case "Edit":
                new EditShapeDialogue(frame, e.getActionCommand(), "Edit", model);
                break;
            case "Move":
                //
                break;
        }

	}
}
