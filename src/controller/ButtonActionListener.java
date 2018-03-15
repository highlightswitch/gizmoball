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
     //name things !!!!
    ButtonActionListener(MainController controller, JFrame f, Model m, JPanel p) {
        this.controller = controller;
        frame = f;
        model = m;
        panel = p;
	}

    @Override
	public final void actionPerformed(final ActionEvent e) {
        MouseListener edit =  new FindEditorListener(frame,model, panel);
        MouseListener add =  new FindAdderListener(frame, model, panel, e.getActionCommand());
        System.out.println(e.getActionCommand());
        switch (e.getActionCommand()) {
            case "Start":
                controller.startTimer();
                break;
            case "Stop":
                controller.stopTimer();
                break;
            case "Delete":
                //
                break;
            case "Rotate":
                //
                break;
            case "Edit":
                frame.removeMouseListener(add);
                frame.addMouseListener(edit);
                break;
            case "Move":
                //
                break;
            case "Circle":
                frame.removeMouseListener(edit);
                add =  new FindAdderListener(frame, model, panel, e.getActionCommand());
                frame.addMouseListener(add);
            case "Triangle":
                frame.removeMouseListener(edit);
                add =  new FindAdderListener(frame, model, panel, e.getActionCommand());
                frame.addMouseListener(add);
            case "Square":
                frame.removeMouseListener(edit);
                add =  new FindAdderListener(frame, model, panel, e.getActionCommand());
                frame.addMouseListener(add);
            case "Absorber":
                frame.removeMouseListener(edit);
                add =  new FindAdderListener(frame, model, panel, e.getActionCommand());
                frame.addMouseListener(add);
            case "Flipper":
                frame.removeMouseListener(edit);
                add =  new FindAdderListener(frame, model, panel, e.getActionCommand());
                frame.addMouseListener(add);
            case "Ball":
                frame.removeMouseListener(edit);
                add =  new FindAdderListener(frame, model, panel, e.getActionCommand());
                frame.addMouseListener(add);
            default:
                System.out.println("dunno");

        }

	}
}
