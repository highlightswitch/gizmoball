package main;

import controller.MainController;
import model.GizmoNotFoundException;
import model.GizmoPlacementNotValidException;
import model.IModel;
import model.TileCoordinatesNotValid;
import model.gizmo.*;
import model.util.GizmoUtils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
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

		//testKeys(model);

	}

	@SuppressWarnings("Duplicates")
	private static IModel test(IModel model) throws GizmoNotRotatableException {

		try {
			Gizmo ball = model.placeGizmo(GizmoType.BALL, model.getTileAt(15, 1), null);
			Flipper leftFlipper = (Flipper) model.placeGizmo(GizmoType.FLIPPER, model.getTileAt(2, 10), null);
			String[] rFProp = GizmoUtils.getPropertyDefaults(GizmoType.FLIPPER, null);
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
			}

			List<Absorber> absorbers = new ArrayList<>();

			absorbers.add((Absorber) model.placeGizmo(GizmoType.ABSORBER, model.getTileAt(3, 15), new String[]{"1", "6", "1", "[r=255,g=255,b=255]", "[r=255,g=255,b=255]", "[r=16,g=219,b=139]"}));
			absorbers.add((Absorber) model.placeGizmo(GizmoType.ABSORBER, model.getTileAt(2, 17), new String[]{"2", "15", "1", "[r=255,g=255,b=255]", "[r=255,g=255,b=255]", "[r=16,g=219,b=139]"}));
			absorbers.add((Absorber) model.placeGizmo(GizmoType.ABSORBER, model.getTileAt(0, 19), new String[]{"3", "20", "1", "[r=239,g=193,b=35]", "[r=255,g=255,b=255]", "[r=16,g=219,b=139]"}));

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
		}

		return null;
	}

	@SuppressWarnings("Duplicates")
	private static IModel magicalDisappearingBallTrick(IModel model) {

		try {
			Gizmo ball = model.placeGizmo(GizmoType.BALL, model.getTileAt(15, 1), null);
			Flipper leftFlipper = (Flipper) model.placeGizmo(GizmoType.FLIPPER, model.getTileAt(2, 10), null);
			String[] rFProp = GizmoUtils.getPropertyDefaults(GizmoType.FLIPPER, null);
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
			} catch (GizmoNotRotatableException e) {
			}

			List<Absorber> absorbers = new ArrayList<>();
			absorbers.add((Absorber) model.placeGizmo(GizmoType.ABSORBER, model.getTileAt(3, 15), new String[]{"1", "6", "1", "[r=255,g=255,b=255]", "[r=255,g=255,b=255]", "[r=16,g=219,b=139]"}));
			absorbers.add((Absorber) model.placeGizmo(GizmoType.ABSORBER, model.getTileAt(2, 17), new String[]{"2", "15", "1", "[r=255,g=255,b=255]", "[r=255,g=255,b=255]", "[r=16,g=219,b=139]"}));
			absorbers.add((Absorber) model.placeGizmo(GizmoType.ABSORBER, model.getTileAt(0, 19), new String[]{"3", "20", "1", "[r=239,g=193,b=35]", "[r=255,g=255,b=255]", "[r=16,g=219,b=139]"}));

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
		} catch (GizmoNotFoundException e) {
		}

		return null;
	}

	private static void testFlipperRotation(IModel model){

		try {

			String[] rFProp = GizmoUtils.getPropertyDefaults(GizmoType.FLIPPER, null);

			Flipper l1 = (Flipper) model.placeGizmo(GizmoType.FLIPPER, model.getTileAt(2, 5), null);
			Flipper l2 = (Flipper) model.placeGizmo(GizmoType.FLIPPER, model.getTileAt(4, 5), null);
			Flipper l3 = (Flipper) model.placeGizmo(GizmoType.FLIPPER, model.getTileAt(6, 5), null);
			Flipper l4 = (Flipper) model.placeGizmo(GizmoType.FLIPPER, model.getTileAt(8, 5), null);

			Flipper r1 = (Flipper) model.placeGizmo(GizmoType.FLIPPER, model.getTileAt(2, 15), null);
			Flipper r2 = (Flipper) model.placeGizmo(GizmoType.FLIPPER, model.getTileAt(4, 15), null);
			Flipper r3 = (Flipper) model.placeGizmo(GizmoType.FLIPPER, model.getTileAt(6, 15), null);
			Flipper r4 = (Flipper) model.placeGizmo(GizmoType.FLIPPER, model.getTileAt(8, 15), null);

			model.setGizmoProperty(r1.getProperty(GizmoPropertyType.NAME), GizmoPropertyType.IS_LEFT_ORIENTATED, "false");
			model.setGizmoProperty(r2.getProperty(GizmoPropertyType.NAME), GizmoPropertyType.IS_LEFT_ORIENTATED, "false");
			model.setGizmoProperty(r3.getProperty(GizmoPropertyType.NAME), GizmoPropertyType.IS_LEFT_ORIENTATED, "false");
			model.setGizmoProperty(r4.getProperty(GizmoPropertyType.NAME), GizmoPropertyType.IS_LEFT_ORIENTATED, "false");

			model.connect(49, TriggerType.KEY_DOWN, l1.getProperty(GizmoPropertyType.NAME));
			model.connect(50, TriggerType.KEY_DOWN, l2.getProperty(GizmoPropertyType.NAME));
			model.connect(51, TriggerType.KEY_DOWN, l3.getProperty(GizmoPropertyType.NAME));
			model.connect(52, TriggerType.KEY_DOWN, l4.getProperty(GizmoPropertyType.NAME));

			model.connect(53, TriggerType.KEY_DOWN, r1.getProperty(GizmoPropertyType.NAME));
			model.connect(54, TriggerType.KEY_DOWN, r2.getProperty(GizmoPropertyType.NAME));
			model.connect(55, TriggerType.KEY_DOWN, r3.getProperty(GizmoPropertyType.NAME));
			model.connect(56, TriggerType.KEY_DOWN, r4.getProperty(GizmoPropertyType.NAME));

			model.connect(49, TriggerType.KEY_UP, l1.getProperty(GizmoPropertyType.NAME));
			model.connect(50, TriggerType.KEY_UP, l2.getProperty(GizmoPropertyType.NAME));
			model.connect(51, TriggerType.KEY_UP, l3.getProperty(GizmoPropertyType.NAME));
			model.connect(52, TriggerType.KEY_UP, l4.getProperty(GizmoPropertyType.NAME));

			model.connect(53, TriggerType.KEY_UP, r1.getProperty(GizmoPropertyType.NAME));
			model.connect(54, TriggerType.KEY_UP, r2.getProperty(GizmoPropertyType.NAME));
			model.connect(55, TriggerType.KEY_UP, r3.getProperty(GizmoPropertyType.NAME));
			model.connect(56, TriggerType.KEY_UP, r4.getProperty(GizmoPropertyType.NAME));

			model.setGizmoAction(l1.getProperty(GizmoPropertyType.NAME), GizmoActionType.FLIP_FLIPPER);
			model.setGizmoAction(l2.getProperty(GizmoPropertyType.NAME), GizmoActionType.FLIP_FLIPPER);
			model.setGizmoAction(l3.getProperty(GizmoPropertyType.NAME), GizmoActionType.FLIP_FLIPPER);
			model.setGizmoAction(l4.getProperty(GizmoPropertyType.NAME), GizmoActionType.FLIP_FLIPPER);

			model.setGizmoAction(r1.getProperty(GizmoPropertyType.NAME), GizmoActionType.FLIP_FLIPPER);
			model.setGizmoAction(r2.getProperty(GizmoPropertyType.NAME), GizmoActionType.FLIP_FLIPPER);
			model.setGizmoAction(r3.getProperty(GizmoPropertyType.NAME), GizmoActionType.FLIP_FLIPPER);
			model.setGizmoAction(r4.getProperty(GizmoPropertyType.NAME), GizmoActionType.FLIP_FLIPPER);

			model.rotateGizmoTo_Deg(l2.getProperty(GizmoPropertyType.NAME), 90);
			model.rotateGizmoTo_Deg(l3.getProperty(GizmoPropertyType.NAME), 180);
			model.rotateGizmoTo_Deg(l4.getProperty(GizmoPropertyType.NAME), 270);

			model.rotateGizmoTo_Deg(r2.getProperty(GizmoPropertyType.NAME), 90);
			model.rotateGizmoTo_Deg(r3.getProperty(GizmoPropertyType.NAME), 180);
			model.rotateGizmoTo_Deg(r4.getProperty(GizmoPropertyType.NAME), 270);


		} catch (GizmoPlacementNotValidException e) {
		} catch (TileCoordinatesNotValid tileCoordinatesNotValid) {
		} catch (GizmoNotFoundException e) {
		} catch (GizmoPropertyException e) {
		} catch (GizmoNotRotatableException e) {
		}


	}

	private static void testKeys(IModel model){
		try {
			Gizmo l = model.placeGizmo(GizmoType.FLIPPER, model.getTileAt(5,5), null);
			Gizmo l2 = model.placeGizmo(GizmoType.FLIPPER, model.getTileAt(7,5), null);
			model.connect(32, TriggerType.KEY_DOWN, l.getProperty(GizmoPropertyType.NAME));
			model.connect(32, TriggerType.KEY_UP, l.getProperty(GizmoPropertyType.NAME));
			model.connect(70, TriggerType.KEY_DOWN, l.getProperty(GizmoPropertyType.NAME));
			model.connect(71, TriggerType.KEY_UP, l.getProperty(GizmoPropertyType.NAME));

			model.connect(32, TriggerType.KEY_DOWN, l2.getProperty(GizmoPropertyType.NAME));
			model.connect(32, TriggerType.KEY_UP, l2.getProperty(GizmoPropertyType.NAME));


			System.out.println(Arrays.deepToString(model.getAllConnectedKeys(l.getProperty(GizmoPropertyType.NAME))));
		} catch (GizmoPlacementNotValidException e) {
			e.printStackTrace();
		} catch (TileCoordinatesNotValid tileCoordinatesNotValid) {
			tileCoordinatesNotValid.printStackTrace();
		} catch (GizmoNotFoundException e) {
			e.printStackTrace();
		}
	}
}





