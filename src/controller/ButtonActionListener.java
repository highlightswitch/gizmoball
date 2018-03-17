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
    private FindAdderListener add;
    private FindEditorListener edit;
    ButtonActionListener(MainController controller, JFrame f, Model m, JPanel p) {
        this.controller = controller;
        frame = f;
        model = m;
        panel = p;
        edit =  new FindEditorListener(frame,model, panel);
        add =  new FindAdderListener(frame, model, panel);
	}

    @Override
	public final void actionPerformed(final ActionEvent e) {
        add.setType(e.getActionCommand());
        edit.setType(e.getActionCommand());
        System.out.println(e.getActionCommand());
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
                frame.removeMouseListener(add);
                frame.addMouseListener(edit);
                break;
            case "Move":
                //
                break;
            case "Circle":
            case "Triangle":
            case "Square":
            case "Absorber":
            case "Flipper":
            case "Ball":
                frame.removeMouseListener(add);
                frame.removeMouseListener(edit);
                frame.addMouseListener(add);
                break;
            default:
                break;
        }

	}
}
