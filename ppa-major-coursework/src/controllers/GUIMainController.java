package controllers;

import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JComboBox;

import GUI.GUI;
import Processing.API;
/**
 * A listener for the combo box used in the main gui to select date range. Acts as a controller in the
 * MVC model
 * @author k1630580
 *
 */
public class GUIMainController implements ActionListener {
	private GUI gui;
	private JComboBox<Integer> from, to;
	private API apiData;
	private long click;
	
	public long getClickTime() {
		return this.click;
	}
	/**
	 * Initialises the fields within the class
	 * @param from
	 * @param to
	 * @param gui
	 * @param api
	 */
	public GUIMainController(JComboBox<Integer> from, JComboBox<Integer> to, GUI gui) {
		this.from = from;
		this.to = to;
		this.gui = gui;
		this.apiData = new API(gui);
	}

	private boolean checkValidRange() {
		// ensures that the dates selected are always in a valid format
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
		click = System.currentTimeMillis();
		// TODO Auto-generated method stub
		
		if (checkValidRange()) {
			// cals the api class to get data etc.
			gui.setSelectedDates("Processing range selected: " + from.getSelectedItem().toString() + "-" + to.getSelectedItem().toString());
			gui.setSelectedDatesVisibility(true);
			apiData.setCustomDataFromRange(String.valueOf(from.getSelectedItem()), String.valueOf(to.getSelectedItem()));
			Thread pullData = new Thread(apiData);
			pullData.start();
		}
		
		
	}

	public double getVersion() {
		return apiData.getVersion();
	}

	public String getAcknowledgementString() {
		// TODO Auto-generated method stub
		return apiData.getAcknowledgementString();
	}

	public String getLastUpdated() {
		return apiData.getLastUpdated();
	}

	public int getStartYear() {
		// TODO Auto-generated method stub
		return apiData.getStartYear();
	}
	
	public int getLatestYear() {
		// TODO Auto-generated method stub
		return apiData.getLatestYear();
	}
	
	public String convertMilisToMinutes(long time) {
		String seconds, minutes;
		long x;
		x = time / 1000;
		seconds = String.valueOf(x % 60);

		x = x / 60;
		minutes = String.valueOf(x % 60);

		return minutes + " minute(s), " + seconds + " seconds."; // return the time taken in the desired format
	}

	
	
}
