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
//
//		model.placeGizmo(GizmoType.BALL, "", model.getTileAt(5,5));
//
//		model.placeGizmo(GizmoType.CIRCLE_BUMPER, "", model.getTileAt(5, 18));
//		model.placeGizmo(GizmoType.SQUARE_BUMPER, "", model.getTileAt(5, 2));
//		model.placeGizmo(GizmoType.TRIANGLE_BUMPER, "", model.getTileAt(8, 12));
//		model.placeGizmo(GizmoType.TRIANGLE_BUMPER, "", model.getTileAt(12, 18));
//
//		Flipper leftFlipper =  (Flipper) model.placeGizmo(GizmoType.LEFT_FLIPPER,"", model.getTileAt(9,10));
//		Flipper rightFlipper =  (Flipper) model.placeGizmo(GizmoType.RIGHT_FLIPPER, "", model.getTileAt(14,10));
//		model.setUpActionMap(leftFlipper, rightFlipper);

		GameFrame fr = new GameFrame(model);
	}
}
