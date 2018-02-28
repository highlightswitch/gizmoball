package main;

import javax.swing.UIManager;

import model.*;
import model.gizmo.*;
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
		model.placeGizmo(GizmoType.BALL, model.getTileAt(14,1), Gizmo.getPropertyDefaults(GizmoType.BALL));
		Flipper leftFlipper =  (Flipper) model.placeGizmo(GizmoType.LEFT_FLIPPER, model.getTileAt(9,10), Gizmo.getPropertyDefaults(GizmoType.LEFT_FLIPPER));
		Flipper rightFlipper =  (Flipper) model.placeGizmo(GizmoType.RIGHT_FLIPPER, model.getTileAt(14,10), Gizmo.getPropertyDefaults(GizmoType.RIGHT_FLIPPER));
		model.placeGizmo(GizmoType.CIRCLE_BUMPER, model.getTileAt(5, 6), Gizmo.getPropertyDefaults(GizmoType.CIRCLE_BUMPER));
		model.placeGizmo(GizmoType.SQUARE_BUMPER, model.getTileAt(6, 10), Gizmo.getPropertyDefaults(GizmoType.SQUARE_BUMPER));

		Gizmo triangle = model.placeGizmo(GizmoType.TRIANGLE_BUMPER, model.getTileAt(19, 0), Gizmo.getPropertyDefaults(GizmoType.TRIANGLE_BUMPER));
		try {
			triangle.rotate();
			triangle.rotate();
		} catch (GizmoPropertyException e) {
			e.printStackTrace();
		}

		model.setUpActionMap(leftFlipper, rightFlipper);
		Absorber absorber = (Absorber) model.placeGizmo(GizmoType.ABSORBER, model.getTileAt(0, 19), Gizmo.getPropertyDefaults(GizmoType.ABSORBER));
		model.setUpActionMap(absorber);

		GameFrame fr = new GameFrame(model);
	}
}
