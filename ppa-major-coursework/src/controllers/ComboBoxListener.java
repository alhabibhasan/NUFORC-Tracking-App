package controllers;

import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import GUI.GUI;

public class ComboBoxListener implements ActionListener {
	private GUI gui;
	private JComboBox<Integer> from, to;

	public ComboBoxListener(JComboBox<Integer> from, JComboBox<Integer> to, GUI gui) {
		this.from = from;
		this.to = to;
		this.gui = gui;
	}

	private boolean checkValidRange() {
		if (((int) to.getSelectedItem() > (int) from.getSelectedItem() + 50)) {
			to.setSelectedItem((int) from.getSelectedItem() + 50);
			return false;
		}
		if ((int) to.getSelectedItem() < (int) from.getSelectedItem()) {
			to.setSelectedItem((int) from.getSelectedItem());
			return false;
		}

		return true;
	}

	@Override
	public void actionPerformed(java.awt.event.ActionEvent arg0) {
		// TODO Auto-generated method stub

		checkValidRange();

	}

}
