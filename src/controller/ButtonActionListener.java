package controller;

import model.Model;
import view.EditShapeDialogue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonActionListener implements ActionListener {

    private MainController controller;
    JFrame frame;

    ButtonActionListener(MainController controller, JFrame f, Model m) {
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
            case "Delete":
                break;
            case "Rotate":
                break;
            case "Edit":
                //EditShapeDialogue shapeDi = new EditShapeDialogue(frame);
                break;
            case "Move":
                //
                break;
            case "circle":
                frame.addMouseListener(new AddCircleListener());
            case "triangle":
                frame.addMouseListener(new AddTriangleListener());
            case "square":
                frame.addMouseListener(new AddSquareListener());
            case "absorber":
                frame.addMouseListener(new AddAbsorberListener());
            case "flipper":
                frame.addMouseListener(new AddFlipperListener());
            case "ball":
                frame.addMouseListener(new AddBallListener());



        }

	}
}
