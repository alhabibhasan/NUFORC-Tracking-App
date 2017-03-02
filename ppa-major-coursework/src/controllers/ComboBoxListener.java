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
		if (((int) to.getSelectedItem() - (int) from.getSelectedItem() > 10)) {
			JOptionPane.showMessageDialog(new JFrame(),
					"The date range you have selected is too large, please select a smaller range.");
			from.setSelectedItem(to.getSelectedItem());
			return false;
		} else if ((int) to.getSelectedItem() < (int) from.getSelectedItem()) {
			JOptionPane.showMessageDialog(new JFrame(), "The start date must be smaller than the end date.");
			to.setSelectedItem(from.getSelectedItem());
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
