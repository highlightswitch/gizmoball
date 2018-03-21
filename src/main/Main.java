package main;

import controller.MainController;

import javax.swing.*;


public class Main {

	public static boolean debugMode = false;

	public static void main(String[] args) {
		try {
			// Use the platform look and feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Look and Feel error in Main");
		}

		new MainController();

	}
}





