package main;

import model.GizmoPlacementNotValidException;
import model.Model;
import model.gizmo.*;
import view.GameFrame;

import javax.swing.*;

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

		try {
			model.placeGizmo(GizmoType.BALL, model.getTileAt(14, 1), null);
			Flipper leftFlipper = (Flipper) model.placeGizmo(GizmoType.FLIPPER, model.getTileAt(9, 10), null);
			String[] rFProp = Gizmo.getPropertyDefaults(GizmoType.FLIPPER);
			rFProp[2] = "false";
			Flipper rightFlipper = (Flipper) model.placeGizmo(GizmoType.FLIPPER, model.getTileAt(14, 10), rFProp);
			model.placeGizmo(GizmoType.CIRCLE_BUMPER, model.getTileAt(5, 6), null);
			Gizmo square = model.placeGizmo(GizmoType.SQUARE_BUMPER, model.getTileAt(6, 8), null);
			Gizmo triangle = model.placeGizmo(GizmoType.TRIANGLE_BUMPER, model.getTileAt(10, 8), null);

			model.connect(square, triangle);

			Gizmo cornerTriangle = model.placeGizmo(GizmoType.TRIANGLE_BUMPER, model.getTileAt(19, 0), null);
			try {
				cornerTriangle.setRotation_Deg(90);
			} catch (GizmoPropertyException e) {
				e.printStackTrace();
			}

			model.setUpActionMap(leftFlipper, rightFlipper);
			Absorber absorber = (Absorber) model.placeGizmo(GizmoType.ABSORBER, model.getTileAt(0, 19), null);
			model.setUpActionMap(absorber);
		} catch (GizmoPlacementNotValidException e){
			e.printStackTrace();
		}

		GameFrame fr = new GameFrame(model);
	}
}
