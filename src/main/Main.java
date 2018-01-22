package main;

import javax.swing.UIManager;

import model.*;
import model.gizmo.Flipper;
import model.gizmo.GizmoType;
import view.RunGui;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class Main {

	public static void main(String[] args) {
		try {
			// Use the platform look and feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Look and Feel error in Main");
		}

		Model model = new Model();

		Flipper flipper =  (Flipper) model.placeGizmo(GizmoType.FLIPPER, model.getTileAt(10,10));
		model.setUpActionMap(flipper);

		RunGui gui = new RunGui(model);
		gui.createAndShowGUI();
	}
}
