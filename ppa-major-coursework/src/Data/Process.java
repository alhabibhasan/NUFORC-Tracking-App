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
	public void getData(String startYear, String endYear) {
		this.dataStart =Integer.parseInt(startYear);
		this.dataEnd = Integer.parseInt(endYear);
		long startTime = System.currentTimeMillis();
		Process.allIncidents = api.getIncidentsInRange( startYear + "-01-01 00:00:00", endYear + "-12-31 00:00:00");
		long endTime = System.currentTimeMillis();
		
		long totalTimeSeconds = (endTime - startTime) / 1000;
		
		System.out.println("Seconds required: " + totalTimeSeconds);
	}
	
	public int getDataStart() {
		return dataStart;
	}
	public int getDataEnd() {
		return dataEnd;
	}
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

	public void outputAllIncidentsList(ArrayList<Incident> list) {
		for (Incident element : list) {
			System.out.println(element.toString());
		}
	}

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
				DateGroup dateFor = a.get(0);
				String date = dateFor.getDates().toString();
				String year = date.substring(25, 29);

				int yearA = Integer.parseInt(year);

				List<DateGroup> b = parse.parse(i2.getDateAndTime());
				dateFor = b.get(0);

				date = dateFor.getDates().toString();
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
	
	public static ArrayList<Incident> sortListForState(ArrayList<Incident> list, String state) {
		
		ArrayList<Incident> sortedList = new ArrayList<Incident>();
		
		for(Incident incid: list){
			if(incid.getState().equals(state)){
				sortedList.add(incid);
			}
		}
		return sortedList;
		
	}
	
	public String getAcknowledgementString() {
		return api.getAcknowledgementString();
	}
	
	public String getLastUpdated() {
		return api.getLastUpdated();
	}
	
	public double getVersion() {
		return api.getVersion();
	}
	
	public int getStartYear() {
		return api.getStartYear();
	}
	
	public int getLatestYear() {
		return api.getLatestYear();
	}
}
