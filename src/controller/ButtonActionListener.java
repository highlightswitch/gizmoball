package controller;

import model.Model;
import view.EditShapeDialogue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;

public class ButtonActionListener implements ActionListener {

    private MainController controller;
    private JFrame frame;
    private Model model;
    private JPanel panel;
    private AllMouseListeners mouse;

    ButtonActionListener(MainController controller, JFrame f, Model m, JPanel p) {
        this.controller = controller;
        frame = f;
        model = m;
        panel = p;
        mouse = new AllMouseListeners(frame, model,panel);
        frame.addMouseListener(mouse);
	}

    @Override
	public final void actionPerformed(final ActionEvent e) {
        mouse.setType(e.getActionCommand());
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
                break;
            case "Circle":
            case "Triangle":
            case "Square":
            case "Absorber":
            case "Flipper":
            case "Ball":
                mouse.setMode("Add");
                break;
            default:
                break;
        }

	}
}
