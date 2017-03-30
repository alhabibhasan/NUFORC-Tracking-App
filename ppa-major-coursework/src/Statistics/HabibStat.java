package Statistics;

import java.util.ArrayList;

import org.jfree.data.category.DefaultCategoryDataset;

import Processing.API;
import Processing.CustomIncident;

public class HabibStat {
	private ArrayList<CustomIncident> incidentsToAnalyse;
	private DefaultCategoryDataset dataset;
	public HabibStat() {
		incidentsToAnalyse = API.getCurrentIncidents();
	}
	/**
	 * This method creates the dataset required to make the pie chart with shape information
	 */
	public void createDataSet() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (int count = Integer.parseInt(API.getDataStart()); count <= Integer.parseInt(API.getDataEnd()); count++) {
			int yearFrequency = 0;

			for (CustomIncident incid : incidentsToAnalyse) {
				if (incid.getDateAndTime().contains(String.valueOf(count))) {
					yearFrequency++; // adding onto the frequency for the current year
				}
			}

				dataset.addValue(yearFrequency, "Sightings", String.valueOf(count));
		}

		this.dataset = dataset;
	}
	
	/**
	 * 
	 * @return The dataset to use to draw the diagram
	 */
	public DefaultCategoryDataset getDataSet() {
		return this.dataset;
	}
}


