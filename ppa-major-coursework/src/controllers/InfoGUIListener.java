package controllers;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;

import Data.Sorts;
import GUI.InfoGUI;

public class InfoGUIListener {
	private JComboBox<String> comboBox;
	private DefaultListModel<String> listModel;
	private Sorts sortedDataSource;
	private InfoGUI gui;

	/**
	 * The constructor is passed a list model, which it will update with the
	 * requested data.
	 * 
	 * @param listModel
	 *            The list model to update
	 */
	public InfoGUIListener(DefaultListModel<String> listModel, JComboBox<String> comboBox, InfoGUI gui) {
		this.listModel = listModel;
		this.comboBox = comboBox;
		this.sortedDataSource = new Sorts(gui);
	}

	public DefaultListModel<String> getListModel() {
		return this.listModel;
	}

	/**
	 * Gets sets the default list model to show data in a given sorted order for
	 * a given state
	 * 
	 * @param selectedItem
	 *            Which criterion to use to sort the data
	 * @param stateAbrev
	 *            The state for which data should be retreieved.
	 */
	public void getDurationSortedData() {
		listModel.clear();

		for (String ci : sortedDataSource.sortDuration()) {
			listModel.addElement(ci);
		}

	}

	public void getPostedSortedData() {
		listModel.clear();

		for (String ci : sortedDataSource.sortPosted()) {
			listModel.addElement(ci);
		}
	}

	public void getCitySortedData() {
		listModel.clear();

		for (String ci : sortedDataSource.sortCity()) {
			listModel.addElement(ci);
		}
	}
	
	public void getDateTimeSortedData() {
		listModel.clear();

		for (String ci : sortedDataSource.sortDateTime()) {
			listModel.addElement(ci);
		}
	}

	public void getShapeSortedData() {
		listModel.clear();

		for (String ci : sortedDataSource.sortShape()) {
			listModel.addElement(ci);
		}
	}
	
	public void getDefaultData(String stateAbrev) {
		listModel.clear();
		for (String ci : sortedDataSource.getOutputDataForState(stateAbrev)) {
			listModel.addElement(ci);
		}
	}

	public String getSummaryFor(int selectedIndex) {
		return sortedDataSource.summaryForInci(selectedIndex);
	}

}
