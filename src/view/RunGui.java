package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.MainController;
import model.Model;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class RunGui {

	private Model model;
	private JFrame frame;
	private MainController controller;
	private Board board;

	public RunGui(Model m) {
		model = m;
		controller = new MainController(model);
	}

	public void createAndShowGUI() {

		frame = new JFrame("Murray's MIT Ball and HorizontalLine Collision Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(controller.getKeyListener());

		// Board is passed the Model so it can act as Observer
		board = new Board(500, 500, model);

		Container cp = frame.getContentPane();

		Font gf = new Font("Arial", Font.BOLD, 12);

		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(4, 1));

		JButton button1 = new JButton("Start");
		button1.setFont(gf);
		button1.addActionListener(controller.getActionListener());
		button1.setFocusable(false);
		button1.setMaximumSize(new Dimension(100, 100));
		buttons.add(button1);

		JButton button2 = new JButton("Stop");
		button2.setFont(gf);
		button2.addActionListener(controller.getActionListener());
        button2.setFocusable(false);
		button2.setMaximumSize(new Dimension(100, 100));
		buttons.add(button2);

		JButton button4 = new JButton("Tick");
		button4.setFont(gf);
		button4.addActionListener(controller.getActionListener());
        button4.setFocusable(false);
		button4.setMaximumSize(new Dimension(100, 100));
		buttons.add(button4);

		JButton button5 = new JButton("Flip");
        button5.setFont(gf);
        button5.addActionListener(controller.getActionListener());
        button5.setFocusable(false);
        button5.setMaximumSize(new Dimension(100, 100));
		buttons.add(button5);

		JButton button3 = new JButton("Quit");
		button3.setFont(gf);
		button3.addActionListener(controller.getActionListener());
        button3.setFocusable(false);
		button3.setMaximumSize(new Dimension(100, 100));
		buttons.add(button3);

		cp.add(buttons, BorderLayout.LINE_START);
		cp.add(board, BorderLayout.CENTER);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
