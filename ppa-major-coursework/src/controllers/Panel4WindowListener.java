package controllers;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import GUI.GUI;
import GUI.Panel4GUI;
/**
 * Opens us the suprise 'panel'
 * @author Alfal Asker, Jaman Salique
 *
 */
public class Panel4WindowListener extends WindowAdapter implements WindowListener {

	private GUI gui;
	private Panel4GUI panel4GUI;
	
	public Panel4WindowListener(GUI gui, Panel4GUI panel4GUI) {
		this.gui = gui;
		this.panel4GUI = panel4GUI;
	}
	

	@Override
	public void windowClosing(WindowEvent e) {
		gui.setVisibility(true);
		panel4GUI.setEnabledStarAnimation(true);

	}

	
}
