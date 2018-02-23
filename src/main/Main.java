package main;

import javax.swing.UIManager;

import model.*;
import model.gizmo.Absorber;
import model.gizmo.Flipper;
import model.gizmo.GizmoType;
import model.gizmo.Square;
import view.GameFrame;

public class Main {

	public static boolean debugMode = true;

	public static void main(String[] args) {
		try {
			// Use the platform look and feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Look and Feel error in Main");
		}

		Model model = new Model();
		model.placeGizmo(GizmoType.BALL, "ball", model.getTileAt(14,1));
		Flipper leftFlipper =  (Flipper) model.placeGizmo(GizmoType.LEFT_FLIPPER, "leftFlipper", model.getTileAt(9,10));
		Flipper rightFlipper =  (Flipper) model.placeGizmo(GizmoType.RIGHT_FLIPPER, "rightFlipper", model.getTileAt(14,10));
		model.placeGizmo(GizmoType.CIRCLE_BUMPER, "circle",model.getTileAt(5, 6));
		model.placeGizmo(GizmoType.SQUARE_BUMPER, "square", model.getTileAt(6, 10));
		model.placeGizmo(GizmoType.TRIANGLE_BUMPER,"triangle", model.getTileAt(8, 12));

		model.setUpActionMap(leftFlipper, rightFlipper);
		Absorber absorber = (Absorber) model.placeGizmo(GizmoType.ABSORBER, "absorber", model.getTileAt(0, 19));
		model.setUpActionMap(absorber);

		GameFrame fr = new GameFrame(model);
	}
}
