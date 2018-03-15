package main;

import controller.MainController;
import model.GizmoNotFoundException;
import model.GizmoPlacementNotValidException;
import model.IModel;
import model.TileCoordinatesNotValid;
import model.gizmo.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class Main {

	public static boolean debugMode = false;

	public static void main(String[] args) {
		try {
			// Use the platform look and feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Look and Feel error in Main");
		}

		MainController cont = new MainController();

		IModel model = cont.getIModel();

		test(model);
	}

	@SuppressWarnings("Duplicates")
	private static IModel test(IModel model) {

		try {
			Gizmo ball = model.placeGizmo(GizmoType.BALL, model.getTileAt(15, 1), null);
			Flipper leftFlipper = (Flipper) model.placeGizmo(GizmoType.FLIPPER, model.getTileAt(2, 10), null);
			String[] rFProp = Gizmo.getPropertyDefaults(GizmoType.FLIPPER, null);
			rFProp[0] = "rf_0";
			rFProp[2] = "false";
			Flipper rightFlipper = (Flipper) model.placeGizmo(GizmoType.FLIPPER, model.getTileAt(15, 3), rFProp);
			model.placeGizmo(GizmoType.CIRCLE_BUMPER, model.getTileAt(5, 6), null);
			Gizmo square = model.placeGizmo(GizmoType.SQUARE_BUMPER, model.getTileAt(6, 8), null);

			Gizmo triangle = model.placeGizmo(GizmoType.TRIANGLE_BUMPER, model.getTileAt(10, 8), null);
			model.placeGizmo(GizmoType.TRIANGLE_BUMPER, model.getTileAt(8, 0), null);
			Gizmo deleteMe = model.placeGizmo(GizmoType.CIRCLE_BUMPER, model.getTileAt(2, 6), null);
			Gizmo cornerTriangle = model.placeGizmo(GizmoType.TRIANGLE_BUMPER, model.getTileAt(19, 0), null);
			try {
				cornerTriangle.rotateTo_Deg(90);
			} catch (GizmoPropertyException e) {
				e.printStackTrace();
			}

			List<Absorber> absorbers = new ArrayList<>();

			absorbers.add((Absorber) model.placeGizmo(GizmoType.ABSORBER, model.getTileAt(3, 15), new String[]{"1", "6", "1", "red", "red", "red"}));
			absorbers.add((Absorber) model.placeGizmo(GizmoType.ABSORBER, model.getTileAt(2, 17), new String[]{"2", "15", "1", "red", "red", "red"}));
			absorbers.add((Absorber) model.placeGizmo(GizmoType.ABSORBER, model.getTileAt(0, 19), new String[]{"3", "20", "1", "red", "red", "red"}));

			model.moveGizmo(ball.getProperty(GizmoPropertyType.NAME), model.getTileAt(0, 12));

			model.deleteGizmo(deleteMe.getProperty(GizmoPropertyType.NAME));

			model.connect(square.getProperty(GizmoPropertyType.NAME), triangle.getProperty(GizmoPropertyType.NAME));

			for (Absorber ab : absorbers) {
				model.setGizmoAction(ab.getProperty(GizmoPropertyType.NAME), GizmoActionType.FIRE_FROM_ABSORBER);
				model.connect(32, TriggerType.KEY_DOWN, ab.getProperty(GizmoPropertyType.NAME)); //Key code 32 = space
				model.connect(ab.getProperty(GizmoPropertyType.NAME), ab.getProperty(GizmoPropertyType.NAME));

			}

			model.connect(70, TriggerType.KEY_DOWN, leftFlipper.getProperty(GizmoPropertyType.NAME)); //Key code 70 = F
			model.connect(71, TriggerType.KEY_DOWN, rightFlipper.getProperty(GizmoPropertyType.NAME)); //Key code 71 = G

			model.connect(70, TriggerType.KEY_UP, leftFlipper.getProperty(GizmoPropertyType.NAME)); //Key code 70 = F
			model.connect(71, TriggerType.KEY_UP, rightFlipper.getProperty(GizmoPropertyType.NAME)); //Key code 71 = G

			model.connect(72, TriggerType.KEY_DOWN, leftFlipper.getProperty(GizmoPropertyType.NAME)); //Key code 72 = H
			model.connect(72, TriggerType.KEY_DOWN, rightFlipper.getProperty(GizmoPropertyType.NAME)); //Key code 72 = H

			model.connect(leftFlipper.getProperty(GizmoPropertyType.NAME), leftFlipper.getProperty(GizmoPropertyType.NAME));

			model.setGizmoAction(leftFlipper.getProperty(GizmoPropertyType.NAME), GizmoActionType.FLIP_FLIPPER);
			model.setGizmoAction(rightFlipper.getProperty(GizmoPropertyType.NAME), GizmoActionType.FLIP_FLIPPER);

		} catch (GizmoPlacementNotValidException | TileCoordinatesNotValid e){
			return model;

		} catch (GizmoNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	@SuppressWarnings("Duplicates")
	private static IModel magicalDisappearingBallTrick(IModel model) {

		try {
			Gizmo ball = model.placeGizmo(GizmoType.BALL, model.getTileAt(15, 1), null);
			Flipper leftFlipper = (Flipper) model.placeGizmo(GizmoType.FLIPPER, model.getTileAt(2, 10), null);
			String[] rFProp = Gizmo.getPropertyDefaults(GizmoType.FLIPPER, null);
			rFProp[0] = "rf_0";
			rFProp[2] = "false";
			Flipper rightFlipper = (Flipper) model.placeGizmo(GizmoType.FLIPPER, model.getTileAt(15, 3), rFProp);
			model.placeGizmo(GizmoType.CIRCLE_BUMPER, model.getTileAt(5, 6), null);
			Gizmo square = model.placeGizmo(GizmoType.SQUARE_BUMPER, model.getTileAt(6, 8), null);
			Gizmo triangle = model.placeGizmo(GizmoType.TRIANGLE_BUMPER, model.getTileAt(10, 8), null);
			model.placeGizmo(GizmoType.TRIANGLE_BUMPER, model.getTileAt(8, 0), null);
			Gizmo t = model.placeGizmo(GizmoType.TRIANGLE_BUMPER, model.getTileAt(10, 1), null);
			Gizmo deleteMe = model.placeGizmo(GizmoType.CIRCLE_BUMPER, model.getTileAt(2, 6), null);

			Gizmo cornerTriangle = model.placeGizmo(GizmoType.TRIANGLE_BUMPER, model.getTileAt(19, 0), null);
			try {
				cornerTriangle.rotateTo_Deg(90);
				t.rotateBy_Deg(180);
			} catch (GizmoPropertyException e) {
				e.printStackTrace();
			}

			List<Absorber> absorbers = new ArrayList<>();
			absorbers.add((Absorber) model.placeGizmo(GizmoType.ABSORBER, model.getTileAt(3, 15), new String[]{"1", "6", "1", "red", "red", "red"}));
			absorbers.add((Absorber) model.placeGizmo(GizmoType.ABSORBER, model.getTileAt(2, 17), new String[]{"2", "15", "1", "red", "red", "red"}));
			absorbers.add((Absorber) model.placeGizmo(GizmoType.ABSORBER, model.getTileAt(0, 19), new String[]{"3", "20", "1", "red", "red", "red"}));

			model.moveGizmo(ball.getProperty(GizmoPropertyType.NAME), model.getTileAt(0, 12));
			model.deleteGizmo(deleteMe.getProperty(GizmoPropertyType.NAME));

			model.connect(square.getProperty(GizmoPropertyType.NAME), triangle.getProperty(GizmoPropertyType.NAME));

			for (Absorber ab : absorbers) {
				model.setGizmoAction(ab.getProperty(GizmoPropertyType.NAME), GizmoActionType.FIRE_FROM_ABSORBER);
				model.connect(32, TriggerType.KEY_DOWN, ab.getProperty(GizmoPropertyType.NAME)); //Key code 32 = space
			}

			model.connect(70, TriggerType.KEY_DOWN, leftFlipper.getProperty(GizmoPropertyType.NAME)); //Key code 70 = F
			model.connect(71, TriggerType.KEY_DOWN, rightFlipper.getProperty(GizmoPropertyType.NAME)); //Key code 71 = G

			model.connect(70, TriggerType.KEY_UP, leftFlipper.getProperty(GizmoPropertyType.NAME)); //Key code 70 = F
			model.connect(71, TriggerType.KEY_UP, rightFlipper.getProperty(GizmoPropertyType.NAME)); //Key code 71 = G

			model.connect(72, TriggerType.KEY_DOWN, leftFlipper.getProperty(GizmoPropertyType.NAME)); //Key code 72 = H
			model.connect(72, TriggerType.KEY_DOWN, rightFlipper.getProperty(GizmoPropertyType.NAME)); //Key code 72 = H

			model.connect(leftFlipper.getProperty(GizmoPropertyType.NAME), leftFlipper.getProperty(GizmoPropertyType.NAME));

			model.setGizmoAction(leftFlipper.getProperty(GizmoPropertyType.NAME), GizmoActionType.FLIP_FLIPPER);
			model.setGizmoAction(rightFlipper.getProperty(GizmoPropertyType.NAME), GizmoActionType.FLIP_FLIPPER);

			return model;

		} catch (GizmoPlacementNotValidException | TileCoordinatesNotValid e) {
			e.printStackTrace();
		} catch (GizmoNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}
}





