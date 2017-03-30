package Statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import org.jfree.data.general.DefaultPieDataset;

import Data.CustomIncident;
import Data.Process;
import Map.LocationsReader;

/**
 * the class is used to create a pie chart for the frequency of US states
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
		listOfIncidents = Process.getCurrentIncidents();
		LocationsReader fileReader = new LocationsReader();
		locations = fileReader.getLocations();
		stateFrequency = Process.getStateFrequency();
	}

	/**
	 * Gets the keys from the HashMap and adds it to the data set. 
	 */
	public void createDataSet() {
		
		for(String key: stateFrequency.keySet()){
			 for (String point : locations.keySet()) {
			      if (locations.keySet().contains(point)) {
			        key = point;
					dataset.setValue(key, stateFrequency.get(key));
				}
			 }
		}
		
		for(String key: stateFrequency.keySet()){
			for(CustomIncident incidents :listOfIncidents){
				if(key.equals("AK") || key.equals("HI")){
					dataset.setValue(key, stateFrequency.get(key));
				}
			 }
		}
	}
	
	/**
	 * @return The data set vales that has to be plotted in the graph.
	 */
	public DefaultPieDataset getDataSet() {
		return dataset;
	}
}