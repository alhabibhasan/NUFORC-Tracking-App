package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GUI.GUI;
/**
 * Listener class which is used to change between panel in the GUI
 * @author Muhammed Hasan, Jaman Salique
 *
 */
public class LeftButtonListener implements ActionListener {

	private GUI gui;
	/**
	 * 
	 * @param gui The GUI the listener should manipulate
	 */
	public LeftButtonListener(GUI gui) {
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (gui.getCurrentScreen().equals("mapScreen")) {
			
			// sets the gui components to enabled or disabled after the action is carried out
			gui.setCurrentScreen("firstScreen");
			System.out.println(gui.getCurrentScreen());
			gui.setCardLayout("firstScreen");
			gui.clearMapCenter();
			
			gui.leftButtonEnabled(false);
			gui.rightButtonEnabled(false);
			gui.setFrameResizeable(true);
			gui.setSelectedDatesVisibility(false);
			gui.setGettingData(false);
			gui.setComboBoxes(true);
			gui.setTimeTakenVisibility(false);
			gui.setTimeTaken("");
			gui.setFrameResizeable(true);
		}
		
		if (gui.getCurrentScreen().equals("statsScreen")) {
			gui.setCardLayout("mapScreen");
			gui.setCurrentScreen("mapScreen");
			gui.setFrameResizeable(false);
			System.out.println(gui.getCurrentScreen());
		}

	}

}
