package controllers;

import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import Data.Process;

import GUI.GUI;
/**
 * A listener for the combo box used in the main gui to select date range. Acts as a controller in the
 * MVC model
 * @author k1630580
 *
 */
public class ComboBoxListener implements ActionListener {
	private GUI gui;
	private JComboBox<Integer> from, to;
	private Process apiData;
	/**
	 * Initializes the fields within the class
	 * @param from
	 * @param to
	 * @param gui
	 * @param api
	 */
	public ComboBoxListener(JComboBox<Integer> from, JComboBox<Integer> to, GUI gui, Process api) {
		this.from = from;
		this.to = to;
		this.gui = gui;
		this.apiData = api;
	}

	private boolean checkValidRange() {
		if (((int) to.getSelectedItem() > (int) from.getSelectedItem() + 2000)) {
			to.setSelectedItem((int) from.getSelectedItem() + 2000);
		}
		if ((int) to.getSelectedItem() < (int) from.getSelectedItem()) {
			to.setSelectedItem((int) from.getSelectedItem());
		}
		gui.setGettingData(true);
		return true;
	}

	/**
	 * This method is executed each time the date range is changed.
	 */
	@Override
	public void actionPerformed(java.awt.event.ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		if (checkValidRange()) {
			gui.setSelectedDates("Data range selected: " + from.getSelectedItem().toString() + "-" + to.getSelectedItem().toString());
			gui.setSelectedDatesVisibility(true);
			apiData.setCustomDataFromRange(String.valueOf(from.getSelectedItem()), String.valueOf(to.getSelectedItem()));
			Thread pullData = new Thread(apiData);
			pullData.start();
			
		}
		
		
	}
	
	
}
