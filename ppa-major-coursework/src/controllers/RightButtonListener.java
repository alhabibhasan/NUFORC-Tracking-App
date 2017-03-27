package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GUI.GUI;
/**
 * Listener class which is used to change between panel in the GUI
 * @author Muhammed Hasan, Jaman Salique
 *
 */
public class RightButtonListener implements ActionListener {
	private GUI gui;

	/**
	 * 
	 * @param gui The gui to make changes to etc.
	 */
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
				gui.setCurrentScreen("mapScreen"); 
				System.out.println(gui.getCurrentScreen());
				gui.setCardLayout("mapScreen");

				gui.setComboBoxes(false);

				gui.leftButtonEnabled(true);
				gui.rightButtonEnabled(true);

				gui.setFrameResizeable(false);
				gui.setGettingData(false);
				gui.setTimeTakenVisibility(false);
				gui.setSelectedDatesVisibility(false);
		}
		
	}
	
	
}
