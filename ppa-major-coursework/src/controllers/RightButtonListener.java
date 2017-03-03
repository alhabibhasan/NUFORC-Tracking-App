package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GUI.GUI;

public class RightButtonListener implements ActionListener {
	private GUI gui;

	public RightButtonListener(GUI gui) {
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (gui.getCurrentScreen().equals("mapScreen")) {
			gui.setCardLayout("statsScreen");
			gui.setCurrentScreen("statsScreen"); 
			System.out.println(gui.getCurrentScreen());
		}
		if (gui.getCurrentScreen().equals("firstScreen")) {
			 if (gui.getMapData()) {
				gui.setCurrentScreen("mapScreen"); 
				System.out.println(gui.getCurrentScreen());
				gui.setCardLayout("mapScreen");

				gui.setComboBoxes(false);

				gui.leftButtonEnabled(true);
				gui.rightButtonEnabled(true);

				gui.setFrameResizeable(false);

			 }
		}
		
	}
}
