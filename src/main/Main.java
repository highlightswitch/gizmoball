package main;

import model.Model;

import javax.swing.*;
import model.GizmoPlacementNotValidException;
import model.gizmo.*;
import view.GameFrame;

import java.util.ArrayList;
import java.util.List;


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
			model.placeGizmo(GizmoType.BALL, model.getTileAt(15, 1), null);
			Flipper leftFlipper = (Flipper) model.placeGizmo(GizmoType.FLIPPER, model.getTileAt(9, 10), null);
			String[] rFProp = Gizmo.getPropertyDefaults(GizmoType.FLIPPER);
			rFProp[2] = "false";
			Flipper rightFlipper = (Flipper) model.placeGizmo(GizmoType.FLIPPER, model.getTileAt(14, 3), rFProp);
			model.placeGizmo(GizmoType.CIRCLE_BUMPER, model.getTileAt(5, 6), null);
			Gizmo square = model.placeGizmo(GizmoType.SQUARE_BUMPER, model.getTileAt(6, 8), null);
			Gizmo triangle = model.placeGizmo(GizmoType.TRIANGLE_BUMPER, model.getTileAt(10, 8), null);
			Gizmo triangle1 = model.placeGizmo(GizmoType.TRIANGLE_BUMPER, model.getTileAt(8, 1), null);

			Gizmo cornerTriangle = model.placeGizmo(GizmoType.TRIANGLE_BUMPER, model.getTileAt(19, 0), null);
			try {
				cornerTriangle.setRotation_Deg(90);
			} catch (GizmoPropertyException e) {
				e.printStackTrace();
			}

			List<Absorber> absorbers = new ArrayList<>();

			absorbers.add((Absorber) model.placeGizmo(GizmoType.ABSORBER, model.getTileAt(3, 15), new String[]{ "1", "6", "1" }));
			absorbers.add((Absorber) model.placeGizmo(GizmoType.ABSORBER, model.getTileAt(2, 17), new String[]{ "2", "15", "1" }));
			absorbers.add((Absorber) model.placeGizmo(GizmoType.ABSORBER, model.getTileAt(0, 19),  new String[]{ "3", "20", "1" }));

			model.connect(square, triangle);
			model.connect(triangle, triangle1);

			for (Absorber a : absorbers) {
				model.connect(32, a); //Key code 32 = space
			}

			model.connect(70, leftFlipper); //Key code 70 = F
			model.connect(71, rightFlipper); //Key code 71 = G

			model.connect(72, leftFlipper); //Key code 72 = H
			model.connect(72, rightFlipper); //Key code 72 = H

			rightFlipper.setAction(GizmoActionType.FLIP_FLIPPER);
			leftFlipper.setAction(GizmoActionType.FLIP_FLIPPER);



		} catch (GizmoPlacementNotValidException e){
			e.printStackTrace();
		}

		GameFrame fr = new GameFrame(model);
	}
}
