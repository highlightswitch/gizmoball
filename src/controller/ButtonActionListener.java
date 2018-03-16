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
        MouseListener edit =  new FindEditorListener(frame,model, panel, e.getActionCommand());
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
            case "Rotate":
            case "Edit":
                frame.removeMouseListener(add);
                //edit =  new FindEditorListener(frame,model, panel, e.getActionCommand());
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
                //add =  new FindAdderListener(frame, model, panel, e.getActionCommand());
                frame.addMouseListener(add);
            case "Key":
                //
                break;
            case "Connect":
                //
                break;
            default:
                System.out.println("dunno");
                break;
        }

	}
}
