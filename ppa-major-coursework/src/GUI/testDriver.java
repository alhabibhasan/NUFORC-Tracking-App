package GUI;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import Map.Map;
import api.ripley.Incident;
import api.ripley.Ripley;
import edu.emory.mathcs.backport.java.util.Collections;

public class testDriver {

	/**
	 * Certain method etc. need to be moved to a separate class and package, they are placed here only for testing.
	 * @param args
	 */
	public static void main(String[] args) {
		long time1 = System.currentTimeMillis();

		GUI gui = new GUI();
		Ripley test = new Ripley("10tLI3CRs9qyVD6ql2OMtA==", "tBgm4pRv9wrVqL46EnH7ew==");
		gui.createGUI();
		gui.setWelcomeText("<html>Welcome to the Ripley API v" + test.getVersion()
				+ " Please select from the dates above, in order to begin analysing UFO sighting data. </html>");
		gui.setAcknowledgement(test.getAcknowledgementString());

		gui.setLastUpdate(test.getLastUpdated());

		HashMap<String, Integer> dataPoints = getData(test, "", "");

		Map d = new Map(dataPoints);
		gui.setMap(d);

		long time2 = System.currentTimeMillis();

		System.out.println("Time taken: " + (time2 - time1) / 1000 + " seconds.");
		gui.rightButtonEnabled(true);
	}
	
	private static HashMap<String, Integer> getData(Ripley api, String startYear, String endYear) {
		ArrayList<Incident> allIncidents = api.getIncidentsInRange("2016-01-01 00:00:00", "2016-01-02 00:00:00");

		sortList(allIncidents);

		System.out.println("Duration: " + allIncidents.get(9).getDuration());
		
		ArrayList<String> incidentStates = new ArrayList<String>();

		for (Incident incident : allIncidents) {
			incidentStates.add(incident.getState());
		}

		HashSet<String> states = new HashSet<String>();
		for (Incident i : allIncidents) {
			states.add(i.getState().toString());
		}
		
		System.out.println("Total size: " + allIncidents.size());
		System.out.println("Total States: " + states.size());
		
		HashMap<String, Integer> stateFrequency = new HashMap<String, Integer>();
		
		for (String state : states) {
			stateFrequency.put(state, Collections.frequency(incidentStates, state));
		}
		
		outputHashMap(stateFrequency);
		return stateFrequency;
	}

	public static void outputList(ArrayList<Incident> list) {
		for (Incident element : list) {
			System.out.println(element.toString());
		}
	}

	public static void outputHashMap(HashMap<String, Integer> map) {
		for (String state : map.keySet()) {
			String key = state.toString();
			String value = map.get(state).toString();

			System.out.println(key + " " + value);
		}
	}

	public static void sortList(ArrayList<Incident> list) {
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
	}
}
