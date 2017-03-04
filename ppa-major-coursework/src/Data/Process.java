package Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import api.ripley.Incident;
import api.ripley.Ripley;
import edu.emory.mathcs.backport.java.util.Collections;

public class Process {

	private static Ripley api;
	private static ArrayList<Incident> allIncidents;
	private int dataStart, dataEnd;
	public Process() {
		this.api =  new Ripley("10tLI3CRs9qyVD6ql2OMtA==", "tBgm4pRv9wrVqL46EnH7ew==");
	}
	/**
	 * This method uses the Ripley API to download data from the database between the given years.
	 * @param startYear The start year.
	 * @param endYear The end year.
	 * @return The list of incidents which occured between the start and the end year.
	 */
	public ArrayList<Incident> getData(String startYear, String endYear) {
		this.dataStart =Integer.parseInt(startYear);
		this.dataEnd = Integer.parseInt(endYear);
		long startTime = System.currentTimeMillis();
		Process.allIncidents = api.getIncidentsInRange( startYear + "-01-01 00:00:00", endYear + "-12-31 00:00:00");
		long endTime = System.currentTimeMillis();
		
		long totalTimeSeconds = (endTime - startTime) / 1000;
		
		System.out.println("Seconds required: " + totalTimeSeconds);
		
		return allIncidents;
	}
	/**
	 * returns one of data to be used for processing. The data retreieved is not stored in the system. Rather, the data is used and then disposed of.
	 * @param startYear
	 * @param endYear
	 * @return The data from the given range.
	 */
	public ArrayList<Incident> getOneOfData(String startYear, String endYear) {
		return api.getIncidentsInRange( startYear + "-01-01 00:00:00", endYear + "-12-31 00:00:00");
	}
	
	/**
	 * Returns the earliest year from the current set of incidents not for all incidents.
	 * @return
	 */
	public int getDataStart() {
		return dataStart;
	}
	/**
	 * Returns the latest year from the current set of incidents.
	 * @return
	 */
	public int getDataEnd() {
		return dataEnd;
	}
	/**
	 * Returns the array list of all incidents within the years stored in getDataStart() and getDataEnd().
	 * @return
	 */
	public static ArrayList<Incident> getAllIncidents() {
		return Process.allIncidents;
	}
	
	/**
	 * This method uses the list of all incidents stored within the process class and returns the states within which aliens were sighted as well as the frequency.
	 * This data is returned in the form of a hash map.
	 * @return The hash map with each state mapping to the frequency of sightings for that state.
	 */
	public HashMap<String, Integer> getStateFrequency() {
		
		ArrayList<String> incidentStates = new ArrayList<String>();

		//below we are adding all states within the list of incidents to an array list, this will allow us to check the frequency of visits to a given state.
		for (Incident incident : allIncidents) {
			incidentStates.add(incident.getState());
		}

		// the states are derived from each incident object within the list of incidents. we are now using a hash set as we do not want to have repeated values for the states
		HashSet<String> states = new HashSet<String>();
		for (Incident i : allIncidents) {
			states.add(i.getState().toString());
		}
		
		System.out.println("Total size: " + allIncidents.size());
		System.out.println("Total States: " + states.size());
		
		HashMap<String, Integer> stateFrequency = new HashMap<String, Integer>();
		
		// we are now placing the states along with the frequency at which they appear in the original list of states
		for (String state : states) {
			stateFrequency.put(state, Collections.frequency(incidentStates, state));
		}
		
		outputHashMap(stateFrequency); // use when needed.
		return stateFrequency;
	}

	/**
	 * prints details of all incident stored in the current set of incidents to the console
	 * @param list
	 */
	public void outputAllIncidentsList(ArrayList<Incident> list) {
		for (Incident element : list) {
			System.out.println(element.toString());
		}
	}
	
	/**
	 * Prints the hashmap of states with the frequnecy of alien sightings. To be used for testing purposes only.
	 * @param map
	 */
	public void outputHashMap(HashMap<String, Integer> map) {
		for (String state : map.keySet()) {
			String key = state.toString();
			String value = map.get(state).toString();

			System.out.println(key + " " + value);
		}
	}

	/**
	 * This method is used to sort an array list of incidents. Incidents are sorted in terms of the date earliest to latest.
	 * @param list The list to be sorted.
	 */
	public static ArrayList<Incident> sortIncidentList(ArrayList<Incident> list) {
		Collections.sort(list, new Comparator<Incident>() {

			@Override
			public int compare(Incident i1, Incident i2) {
				Parser parse = new Parser();

				List<DateGroup> a = parse.parse(i1.getDateAndTime());
				DateGroup dateParsed = a.get(0);
				String date = dateParsed.getDates().toString();
				String year = date.substring(25, 29);

				int yearA = Integer.parseInt(year);

				List<DateGroup> b = parse.parse(i2.getDateAndTime());
				dateParsed = b.get(0);

				date = dateParsed.getDates().toString();
				year = date.substring(25, 29);

				int yearB = Integer.parseInt(year);

				if (yearA > yearB)
					return 1;
				if (yearA == yearB)
					return 0;
				if (yearA < yearB)
					return -1;

				return 0;
			}

		});
		
		return list;
	}
	
	/**
	 * Cycles through a list of all incidents. Then returns the incidents which took place in the given state. 
	 * @param state The state to sort by.
	 * @return All incidents which took place in the given state.
	 */
	public static ArrayList<Incident> sortListForState(String state) {
		
		ArrayList<Incident> sortedList = new ArrayList<Incident>();
		for(Incident incid: allIncidents){
			if(incid.getState().equals(state)){
				sortedList.add(incid);
			}
		}
		return sortedList;
		
	}
	
	/**
	 * 
	 * @return Acknowledgement String from the Ripley API.
	 */
	public String getAcknowledgementString() {
		return api.getAcknowledgementString();
	}
	/**
	 * 
	 * @return time and date of last update of the UFO database
	 */
	public String getLastUpdated() {
		return api.getLastUpdated();
	}
	
	/**
	 * 
	 * @return Current api version being used.
	 */
	public double getVersion() {
		return api.getVersion();
	}
	
	/**
	 * 
	 * @return The earliest year of records.
	 */
	public int getStartYear() {
		return api.getStartYear();
	}
	
	/**
	 * 
	 * @return The latest year of records.
	 */
	public int getLatestYear() {
		return api.getLatestYear();
	}
}
