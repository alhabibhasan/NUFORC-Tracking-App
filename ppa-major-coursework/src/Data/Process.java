package Data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Properties;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import GUI.GUI;
import api.ripley.Incident;
import api.ripley.Ripley;
import edu.emory.mathcs.backport.java.util.Collections;

public class Process extends Observable{

	private static Ripley api = new Ripley("10tLI3CRs9qyVD6ql2OMtA==", "tBgm4pRv9wrVqL46EnH7ew==");
	private Properties props = new Properties();
	private ArrayList<Incident> incidentsFromAPI;
	private static ArrayList<CustomIncident> currentIncidents;
	private List<CustomIncident> incidentsFromFile;
	private static String dataStart;
	private static String dataEnd;
	private static String apiLastUpdate;

	public Process(GUI observer) {
		this.addObserver(observer);
	}

	/**
	 * This method retrieves data from the api (if necessary) after checking that the current data is out of date.
	 * @param dateFrom
	 * @param dateTo
	 * @return The arraylist of all incidents within the current given start and end dates.
	 */
	public ArrayList<CustomIncident> getDataFromRange(String dateFrom, String dateTo) {
		try {
			checkForUpdate();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.out.println("Failed to check for updates.");
		}
		long time1 = System.currentTimeMillis();
		dataStart = dateFrom;
		dataEnd = dateTo;

		System.out.println("Start: " + dataStart);
		System.out.println("End: " + dataEnd);
		ArrayList<CustomIncident> incidentsInRange = new ArrayList<CustomIncident>();
		ArrayList<String> dates = new ArrayList<String>();
		for (CustomIncident incid : incidentsFromFile) {
			int year = Integer.parseInt((incid.getDateAndTime().substring(0, 4)));
			if (year >= Integer.parseInt(dateFrom) && year <= Integer.parseInt(dateTo)) {
				incidentsInRange.add(incid);
				dates.add(incid.getDateAndTime());
			}
		}

		long time2 = System.currentTimeMillis();

		System.out.println("Time taken to get data within range: " + (time2 - time1) + " miliseconds.");
		System.out.println(incidentsInRange.size());
		currentIncidents = incidentsInRange;
		setChanged();
		notifyObservers(getStateFrequency());
		return incidentsInRange;
	}

	/**
	 * 
	 * @return List of incidents within the range given in the method getDataFromRange()
	 */
	public static ArrayList<CustomIncident> getCurrentIncidents() {
		return currentIncidents;
	}
	
	/**
	 * This method retrieves data stored in JSON format from the local machine
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 */
	private void pullLocalData() throws JsonParseException, JsonMappingException {
		long time1 = System.currentTimeMillis();
		ObjectMapper incidentMapper = new ObjectMapper();
		TypeReference<HashMap<String, CustomIncident>> typeRef = new TypeReference<HashMap<String, CustomIncident>>() {
		};
		try {
			Map<String, CustomIncident> incidentMap = incidentMapper.readValue(new File("res//incidents.json"),
					typeRef);

			incidentsFromFile = new ArrayList<>();
			for (Map.Entry<String, CustomIncident> entry : incidentMap.entrySet()) {
				incidentsFromFile.add(entry.getValue());
			}
			long time2 = System.currentTimeMillis();
			System.out.println("Time taken: " + ((time2 - time1) / 1000) + " seconds");
			System.out.println("Size of records: " + incidentsFromFile.size());
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.out.println("JSON file not found.");
		}
	}

	/**
	 * In the case that the current data is out of date, this method is called to retrieve data from the api
	 * and then save the data in JSON format. CustomIncident objects are required in order to save the incidents.
	 */
	private void pullLatestDataFromAPI() {
		System.out.println("need to get data from API current data out of date");
		long time1 = System.currentTimeMillis();

		incidentsFromAPI = this.getAPIData(String.valueOf(api.getStartYear()), String.valueOf(api.getLatestYear()));
		long time1sort = System.currentTimeMillis();
		System.out.println("Sorting...");

		
		long time1sortend = System.currentTimeMillis();
		System.out.println("Sorting finished " + ((time1sortend - time1sort) / 1000) + " seconds needed.");


		System.out.println("Size of records: " + incidentsFromAPI.size());
		ObjectMapper mapper = new ObjectMapper();

		Map<String, CustomIncident> incidentMap = new HashMap<>();

		TypeReference ref = new TypeReference<Map<String, Incident>>() {
		};

		for (Incident incident : incidentsFromAPI) {
			incidentMap.put("inident" + incidentsFromAPI.indexOf(incident), new CustomIncident(incident));
		}

		try {
			mapper.writeValue(new File("res//incidents.json"), incidentMap);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			updateLastFetch(apiLastUpdate);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long time2 = System.currentTimeMillis();
		long totalTime = (((time2 - time1)));
		System.out.println("Data fetch and save completed in " + (totalTime / 1000) + " seconds.");
		try {
			System.out.println("Pulling local data");
			pullLocalData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to check that local data is up to date, if data is not up to date, then data is retrieved 
	 * and local data is updated.
	 * @throws IOException
	 */
	private void checkForUpdate() throws IOException {
		FileInputStream in = new FileInputStream("res//api.properties");
		props.load(in);
		in.close();

		Parser parse = new Parser();
		List<DateGroup> a = parse.parse(api.getLastUpdated());
		DateGroup dateFromAPI = a.get(0);
		apiLastUpdate = dateFromAPI.getDates().toString();

		System.out.println("API last updated: " + apiLastUpdate);

		if (!props.getProperty("api.lastUpdate").equals("null")) {
			System.out.println("File not null");
			String localDataLastUpdate = props.getProperty("api.lastUpdate");

			String localDate = parse.parse(localDataLastUpdate).get(0).getDates().toString();

			if (!localDate.equals(apiLastUpdate)) {
				pullLatestDataFromAPI(); // pull and save to local storage
			} else {
				System.out.println("Local data is up to date");
				System.out.println("Pulling local data.");
				System.out.println("API last update: " + apiLastUpdate);
				System.out.println("Local data last update: " + localDate);
				pullLocalData();
			}
		} else {
			System.out.println("need to get data no data available at all at the moment");
			pullLatestDataFromAPI();

		}

		System.out.println("Done");
	}

	/**
	 * This method is used to update the last update time of the local data - which is stored on the local machine.
	 * This is called every time the local data store is updated.
	 * @param lastUpdate The time of the latest update.
	 * @throws IOException
	 */
	private void updateLastFetch(String lastUpdate) throws IOException {
		FileOutputStream out = new FileOutputStream("res//api.properties");
		props.setProperty("api.lastUpdate", lastUpdate);
		props.store(out, null);
		out.close();
	}
	/**
	 * This is the only method within the solution. It is used to download all data from the API.
	 * @param startYear
	 * @param endYear
	 * @return
	 */
	private ArrayList<Incident> getAPIData(String startYear, String endYear) {
		return api.getIncidentsInRange(startYear + "-01-01 00:00:00", endYear + "-12-31 00:00:00");
	}

	/**
	 * 
	 * @return The start date for the current data set.
	 */
	public String getDataStart() {
		return dataStart;
	}
	/**
	 * 
	 * @return The end date for the current data set.
	 */
	public String getDataEnd() {
		return dataEnd;
	}

	public static ArrayList<CustomIncident> getAllIncidents() {
		return Process.currentIncidents;
	}

	/**
	 * This method uses the list of all incidents stored within the process
	 * class and returns the states within which aliens were sighted as well as
	 * the frequency. This data is returned in the form of a hash map.
	 * 
	 * @return The hash map with each state mapping to the frequency of
	 *         sightings for that state.
	 */
	public HashMap<String, Integer> getStateFrequency() {

		ArrayList<String> incidentStates = new ArrayList<String>();

		// below we are adding all states within the list of incidents to an
		// array list, this will allow us to check the frequency of visits to a
		// given state.
		for (CustomIncident incident : currentIncidents) {
			incidentStates.add(incident.getState());
		}

		// the states are derived from each incident object within the list of
		// incidents. we are now using a hash set as we do not want to have
		// repeated values for the states
		HashSet<String> states = new HashSet<String>();
		for (CustomIncident i : currentIncidents) {
			states.add(i.getState().toString());
		}

		System.out.println("Total size: " + currentIncidents.size());
		System.out.println("Total States: " + states.size());

		HashMap<String, Integer> stateFrequency = new HashMap<String, Integer>();

		// we are now placing the states along with the frequency at which they
		// appear in the original list of states
		for (String state : states) {
			stateFrequency.put(state, Collections.frequency(incidentStates, state));
		}

		// outputHashMap(stateFrequency); // use when needed.
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
	 * This method is used to sort an array list of incidents. Incidents are
	 * sorted in terms of the date earliest to latest.
	 * 
	 * @param list
	 *            The list to be sorted.
	 */
	public static ArrayList<CustomIncident> sortIncidentList(ArrayList<CustomIncident> list) {
		Collections.sort(list, new Comparator<CustomIncident>() {

			@Override
			public int compare(CustomIncident i1, CustomIncident i2) {

				int year1 = Integer.parseInt(i1.getDateAndTime().substring(0, 4));
				int year2 = Integer.parseInt(i2.getDateAndTime().substring(0, 4));

				if (year1 > year2)
					return 1;
				if (year1 == year2)
					return 0;
				if (year1 < year2)
					return -1;

				return 0;
			}

		});

		return list;
	}

	public static ArrayList<CustomIncident> sortListForState(String state) {

		ArrayList<CustomIncident> sortedList = new ArrayList<CustomIncident>();

		for (CustomIncident incid : currentIncidents) {
			if (incid.getState().equals(state)) {
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
