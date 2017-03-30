package Statistics;

import java.util.ArrayList;

import org.jfree.data.category.DefaultCategoryDataset;

import Data.CustomIncident;
import Data.Process;

public class HabibStat {
	private ArrayList<CustomIncident> incidentsToAnalyse;
	private DefaultCategoryDataset dataset;
	public HabibStat() {
		incidentsToAnalyse = Process.getCurrentIncidents();
	}
	
	public void createDataSet() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (int count = Integer.parseInt(Process.getDataStart()); count <= Integer.parseInt(Process.getDataEnd()); count++) {
			int yearFrequency = 0;

			for (CustomIncident incid : incidentsToAnalyse) {
				if (incid.getDateAndTime().contains(String.valueOf(count))) {
					yearFrequency++;
				}
			}

				dataset.addValue(yearFrequency, "Sightings", String.valueOf(count));
		}

		this.dataset = dataset;
	}
	
	public DefaultCategoryDataset getDataSet() {
		return this.dataset;
	}
}


