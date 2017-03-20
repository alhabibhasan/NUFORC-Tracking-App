package controllers;

import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import Data.Process;

import GUI.GUI;

public class ComboBoxListener implements ActionListener {
	private GUI gui;
	private JComboBox<Integer> from, to;
	private Process apiData;
	
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

	@Override
	public void actionPerformed(java.awt.event.ActionEvent arg0) {
		// TODO Auto-generated method stub
		long time1 = 0;
		long time2 = 0;
		long finalTime = 0;
		if (checkValidRange()) {
			time1 = System.currentTimeMillis();
			gui.setSelectedDates("Data range selected, " + from.getSelectedItem().toString() +  "-" +  to.getSelectedItem().toString());
			this.apiData.getDataFromRange(from.getSelectedItem().toString(), to.getSelectedItem().toString());
			time2 = System.currentTimeMillis();
		}
		finalTime = time2-time1;
		gui.setTimeTaken("Data grabbed in: " + convertLong(finalTime));
	}
	
	private String convertLong(long longs){
	
		long minutes = TimeUnit.MILLISECONDS.toMinutes(longs);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(longs)%60;
		return minutes + " " + "minutes,"+ " " + seconds + " " + "seconds";
		
	}

}
