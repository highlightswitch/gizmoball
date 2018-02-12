package main;

import javax.swing.UIManager;

import model.*;
import model.gizmo.Flipper;
import model.gizmo.GizmoType;
import model.gizmo.Square;
import view.GameFrame;

public class Main {

	public static void main(String[] args) {
		try {
			// Use the platform look and feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Look and Feel error in Main");
		}

		Model model = new Model();
		model.addBall("sphere", 12, 12, 0, 0);
		model.addSquare("square", 9,10);
		GameFrame fr = new GameFrame(model);
	}
}
