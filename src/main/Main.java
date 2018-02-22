package main;

import javax.swing.UIManager;

import model.*;
import model.gizmo.Absorber;
import model.gizmo.Flipper;
import model.gizmo.GizmoType;
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

		model.placeGizmo(GizmoType.BALL, model.getTileAt(5,5));
		Absorber absorber = (Absorber) model.placeGizmo(GizmoType.ABSORBER, model.getTileAt(0, 19));
		Flipper flipper = (Flipper) model.placeGizmo(GizmoType.FLIPPER, model.getTileAt(19, 10));
		model.setUpActionMap(flipper, absorber);
		GameFrame fr = new GameFrame(model);
	}
}
