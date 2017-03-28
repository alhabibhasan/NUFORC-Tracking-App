package Statistics;

import java.util.ArrayList;
import java.util.HashMap;

import Data.Process;
import Map.LocationsReader;
import Data.CustomIncident;

public class AnalyseData {
	private ArrayList<CustomIncident> listOfIncidents;
	private HashMap<String, Integer[]> locations;
	private HashMap<String, Integer> stateFrequency;
	
	public AnalyseData() {
		
		listOfIncidents = new ArrayList<CustomIncident>();
		
		listOfIncidents = Process.getCurrentIncidents();

		LocationsReader fileReader = new LocationsReader();

		locations = fileReader.getLocations();

		stateFrequency = Process.getStateFrequency();
		
		System.out.println("Hoaxes: " + numberOfHoax());
		System.out.println("Non US: " + nonUSSightings());
		System.out.println("likeliest: " + likeliestState());
	}

	public int numberOfHoax(){
		String Hoax = "HOAX";
		int noOfHoaxes = 0;
		for (CustomIncident incidents : listOfIncidents) {
			if(incidents.getSummary().contains(Hoax)){
				noOfHoaxes++;
			}
			
		}
		return noOfHoaxes;
	}
	
	public int nonUSSightings(){
		int nonUSCity = 0;
		for(CustomIncident incidents :listOfIncidents){
			if(!locations.keySet().contains(incidents.getState()) && !incidents.getState().equals("AK") && !incidents.getState().equals("HI")){
				
				nonUSCity++;
			}	
		}
		return nonUSCity;	
	}
	
	public String likeliestState(){
		
		String highestState = "";
		int currentHighest = 0;
		
		for(String key: stateFrequency.keySet()){
			if(stateFrequency.get(key) > currentHighest){
				currentHighest = stateFrequency.get(key);
				highestState = key;
			}
		}
		return highestState;
	}
}
