package Statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import org.jfree.data.general.DefaultPieDataset;

import Data.CustomIncident;
import Data.Process;
import Map.LocationsReader;

public class BahaStatistic {

	private ArrayList<CustomIncident> listOfIncidents;
	private HashMap<String, Integer[]> locations;
	private HashMap<String, Integer> stateFrequency;
	private DefaultPieDataset dataset; 
	
	public BahaStatistic() {
		dataset = new DefaultPieDataset();
		listOfIncidents = new ArrayList<CustomIncident>();
		listOfIncidents = Process.getCurrentIncidents();
		LocationsReader fileReader = new LocationsReader();
		locations = fileReader.getLocations();
		stateFrequency = Process.getStateFrequency();
		
		
	}

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
	
	public DefaultPieDataset getDataSet() {
		return dataset;
	}
}