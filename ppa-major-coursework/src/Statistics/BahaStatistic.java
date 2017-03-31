package Statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import org.jfree.data.general.DefaultPieDataset;

import Map.LocationsReader;
import Processing.API;
import Processing.CustomIncident;

/**
 * the class is used to create a pie chart for the frequency of US states
 * 
 * @author baha
 *
 */
public class BahaStatistic {

	private ArrayList<CustomIncident> listOfIncidents;
	private HashMap<String, Integer[]> locations;
	private HashMap<String, Integer> stateFrequency;
	private DefaultPieDataset dataset;

	/**
	 * instantiates the fields and gets data
	 */
	public BahaStatistic() {
		dataset = new DefaultPieDataset();
		listOfIncidents = new ArrayList<CustomIncident>();
		listOfIncidents = API.getCurrentIncidents();
		LocationsReader fileReader = new LocationsReader();
		locations = fileReader.getLocations();
		stateFrequency = API.getStateFrequency();
	}

	/**
	 * Gets the keys from the HashMap and adds it to the data set.
	 */
	public void createDataSet() {

		for (String point : locations.keySet()) {
			dataset.setValue(point, stateFrequency.get(point));
		}
		
		dataset.setValue("AK", stateFrequency.get("AK"));
		dataset.setValue("HI", stateFrequency.get("HI"));
	}

	/**
	 * @return The data set vales that has to be plotted in the graph.
	 */
	public DefaultPieDataset getDataSet() {
		return dataset;
	}
}