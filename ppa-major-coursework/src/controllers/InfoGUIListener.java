package controllers;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;

import GUI.InfoGUI;
import Processing.Sorts;
/**
 * This class is the controller for the InfoGUI and the Sorts class.
 * @author k1630580
 *
 */
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

	/**
	 * Returns the most current list model which is to be used by the view i.e. InfoGUI
	 * @return
	 */
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

	/**
	 * Tells the Sorts class to sort data based on the date of posting
	 */
	public void getPostedSortedData() {
		listModel.clear();

		for (String ci : sortedDataSource.sortPosted()) {
			listModel.addElement(ci);
		}
	}

	/**
	 * Tells the Sorts class to sort the current data based on the city the incident took place in.
	 */
	public void getCitySortedData() {
		listModel.clear();

		for (String ci : sortedDataSource.sortCity()) {
			listModel.addElement(ci);
		}
	}
	
	/**
	 * Tells the Sorts class to sort the current data based on the date and time the incident occured.
	 */
	public void getDateTimeSortedData() {
		listModel.clear();

		for (String ci : sortedDataSource.sortDateTime()) {
			listModel.addElement(ci);
		}
	}
	/**
	 * Tells the sorts class to sort the current data based on the shape of the UFO(s) spotted in
	 * each incident
	 */
	public void getShapeSortedData() {
		listModel.clear();

		for (String ci : sortedDataSource.sortShape()) {
			listModel.addElement(ci);
		}
	}
	
	/**
	 * Tells the Sorts class to return data as it was received from the API i.e. in random unsorted order
	 * @param stateAbrev To state for which data should be returned
	 */
	public void getDefaultData(String stateAbrev) {
		listModel.clear();
		for (String ci : sortedDataSource.getOutputDataForState(stateAbrev)) {
			listModel.addElement(ci);
		}
	}
	
	/**
	 * Gets the summary of a selected incident
	 * @param selectedIndex The incident selected by the user
	 * @return The String data for the summary of the incident
	 */
	public String getSummaryFor(int selectedIndex) {
		return sortedDataSource.summaryForInci(selectedIndex);
	}

}
