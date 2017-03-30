package controllers;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import GUI.GUI;

public class Panel4WindowListener extends WindowAdapter implements WindowListener {

	private GUI gui;
	
	public Panel4WindowListener(GUI gui) {
		this.gui = gui;
	}
	

	@Override
	public void windowClosing(WindowEvent e) {
		gui.setVisibility(true);
		System.out.println("aflallallalalalala");

	}

	
}
