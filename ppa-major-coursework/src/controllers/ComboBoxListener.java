package controllers;

import java.awt.event.ActionListener;

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

		return true;
	}

	@Override
	public void actionPerformed(java.awt.event.ActionEvent arg0) {
		// TODO Auto-generated method stub

		if (checkValidRange()) {
			apiData.setCustomDataFromRange(String.valueOf(from.getSelectedItem()), String.valueOf(to.getSelectedItem()));
			Thread pullData = new Thread(apiData);
			pullData.start();
			gui.rightButtonEnabled(true);
		}

	}

}
