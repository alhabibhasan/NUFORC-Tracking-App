package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GUI.GUI;

public class LeftButtonListener implements ActionListener {

	private GUI gui;
	
	public LeftButtonListener(GUI gui) {
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (gui.getCurrentScreen().equals("mapScreen")) {
			gui.setCurrentScreen("firstScreen");
			System.out.println(gui.getCurrentScreen());
			gui.setCardLayout("firstScreen");
			gui.clearMapCenter();
			
			gui.leftButtonEnabled(false);
			gui.rightButtonEnabled(false);
			
			gui.setSelectedDatesVisibility(false);
			gui.setGettingData(false);
			gui.setComboBoxes(true);
			gui.setTimeTakenVisibility(true);
			gui.setTimeTaken("");
			gui.setFrameResizeable(true);
		}
		
		if (gui.getCurrentScreen().equals("statsScreen")) {
			gui.setCardLayout("mapScreen");
			gui.setCurrentScreen("mapScreen");
			System.out.println(gui.getCurrentScreen());
		}

	}

}
