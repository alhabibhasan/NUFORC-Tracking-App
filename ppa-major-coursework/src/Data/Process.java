package Data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

/**
 * This class is used to: - connect to the api + get any data api related data -
 * cache data from the api - retrieved locally stored data - update local data
 * 
 * @author Muhammed Hasan
 *
 */
public class Process extends Observable implements Runnable {

	private static Ripley api = new Ripley("10tLI3CRs9qyVD6ql2OMtA==", "tBgm4pRv9wrVqL46EnH7ew==");
	private Properties props = new Properties();
	private ArrayList<Incident> incidentsFromAPI;
	private static ArrayList<CustomIncident> currentIncidents;
	private List<CustomIncident> incidentsFromFile;
	private static String dataStart = String.valueOf(api.getStartYear());
	private static String dataEnd = String.valueOf(api.getLatestYear());
	private static String apiLastUpdate;
	private long totalTime;
	private static HashMap<String, Integer> mapData;
	private String fetchTime;

	public Process(GUI observer) {
		this.addObserver(observer);
	}

	/**
	 * This method retrieves data from the api (if necessary) after checking
	 * that the current data is out of date.
	 * 
	 * @param dateFrom
	 * @param dateTo
	 * @return The arraylist of all incidents within the current given start and
	 *         end dates.
	 */
	@Override
	public void run() {
		long time1 = System.currentTimeMillis();
		try {
			checkForUpdate();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.out.println("Failed to check for updates.");
		}

		System.out.println("Start: " + dataStart);
		System.out.println("End: " + dataEnd);
		ArrayList<CustomIncident> incidentsInRange = new ArrayList<CustomIncident>();
		ArrayList<String> dates = new ArrayList<String>();
		for (CustomIncident incid : incidentsFromFile) {
			try {
			int year = Integer.parseInt((incid.getDateAndTime().substring(0, 4)));
			if (year >= Integer.parseInt(dataStart) && year <= Integer.parseInt(dataEnd)) {
				incidentsInRange.add(incid);
				dates.add(incid.getDateAndTime());
			}
			} catch (NullPointerException e) {
				System.out.println("Incident without date or time");
			}
			
		}

		System.out.println(incidentsInRange.size());
		currentIncidents = incidentsInRange;

		long time2 = System.currentTimeMillis();
		totalTime = time2 - time1;

		fetchTime = convertMilisToMinutes(totalTime);

		calculateStateFrequency();
		
		setChanged();
		notifyObservers(getStateFrequency());
	}

	/**
	 * 
	 * @return The last fetch time for data from the API
	 */
	public String getFetchTime() {
		System.out.println("Total time to get data ## ### : " + fetchTime);
		return this.fetchTime;
	}

	private String convertMilisToMinutes(long time) {
		String seconds, minutes;
		long x;
		x = time / 1000;
		seconds = String.valueOf(x % 60);

		x = x / 60;
		minutes = String.valueOf(x % 60);

		return minutes + " minute(s), " + seconds + " seconds.";
	}

	/**
	 * This method is used to set the range from within which we should extract
	 * data
	 * 
	 * @param dateFrom
	 * @param dateTo
	 */
	public void setCustomDataFromRange(String dateFrom, String dateTo) {
		this.dataStart = dateFrom;
		this.dataEnd = dateTo;
	}

	/**
	 * 
	 * @return List of incidents within the range given in the method
	 *         getDataFromRange()
	 */
	public static ArrayList<CustomIncident> getCurrentIncidents() {
		return currentIncidents;
	}

	/**
	 * This method retrieves data stored in JSON format from the local machine
	 * 
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
	 * In the case that the current data is out of date, this method is called
	 * to retrieve data from the api and then save the data in JSON format.
	 * CustomIncident objects are required in order to save the incidents.
	 */
	private void pullLatestDataFromAPI() {
		System.out.println("need to get data from API current data out of date");
		

		incidentsFromAPI = this.getAPIData(String.valueOf(api.getStartYear()), String.valueOf(api.getLatestYear()));

		System.out.println("Sorting...");
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
		try {
			System.out.println("Pulling local data");
			pullLocalData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to check that local data is up to date, if data is
	 * not up to date, then data is retrieved and local data is updated.
	 * 
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
				pullLocalData();
			}
		} else {
			System.out.println("need to get data no data available at all at the moment");
			pullLatestDataFromAPI();

		}

		System.out.println("Done");
	}

	/**
	 * This method is used to update the last update time of the local data -
	 * which is stored on the local machine. This is called every time the local
	 * data store is updated.
	 * 
	 * @param lastUpdate
	 *            The time of the latest update.
	 * @throws IOException
	 */
	private void updateLastFetch(String lastUpdate) throws IOException {
		FileOutputStream out = new FileOutputStream("res//api.properties");
		props.setProperty("api.lastUpdate", lastUpdate);
		props.store(out, null);
		out.close();
	}

	/**
	 * This is the only method within the solution. It is used to download all
	 * data from the API.
	 * 
	 * @param startYear
	 * @param endYear
	 * @return
	 */
	private ArrayList<Incident> getAPIData(String startYear, String endYear) {
		if (endYear.equals(api.getLatestYear())) {
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			System.out.println(dtf.format(now)); //2016-11-16 12:08:43
			
			return api.getIncidentsInRange(startYear + "-01-01 00:00:00", dtf.format(now).toString());
		}
		return api.getIncidentsInRange(startYear + "-01-01 00:00:00", endYear + "-12-31 00:00:00");
	}

	/**
	 * 
	 * @return The start date for the current data set.
	 */
	public static String getDataStart() {
		return dataStart;
	}

	/**
	 * 
	 * @return The end date for the current data set.
	 */
	public static String getDataEnd() {
		return dataEnd;
	}

	/**
	 * 
	 * @return List of incidents within the range given in the method
	 *         getDataFromRange()
	 */
	public static ArrayList<CustomIncident> getAllIncidents() {
		return Process.currentIncidents;
	}

	public static HashMap<String, Integer> getStateFrequency() {
		return mapData;
	}
	
	/**
	 * This method uses the list of all incidents stored within the process
	 * class and returns the states within which aliens were sighted as well as
	 * the frequency. This data is returned in the form of a hash map. The data
	 * is at the moment used only to draw the map.
	 * 
	 * @return The hash map with each state mapping to the frequency of
	 *         sightings for that state.
	 */
	public static void calculateStateFrequency() {

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
		mapData = stateFrequency;
	}

	/**
	 * 
	 * @param list
	 *            Outputs all incidents retrieved from the API
	 */
	public void outputAllIncidentsList(ArrayList<Incident> list) {
		for (Incident element : list) {
			System.out.println(element.toString());
		}
	}

	/**
	 * 
	 * @param map
	 *            Outputs the frequency of sightings for each US state as
	 *            received from the API
	 */
	public void outputHashMap(HashMap<String, Integer> map) {
		for (String state : map.keySet()) {
			String key = state.toString();
			String value = map.get(state).toString();

			System.out.println(key + " " + value);
		}
	}

	/**
	 * Returns incidents which took place in a given state
	 * 
	 * @param state
	 *            The state to get incidents for
	 * @return The list of incidents from the given state
	 */
	public static ArrayList<CustomIncident> sortListForState(String state) {

		ArrayList<CustomIncident> sortedList = new ArrayList<CustomIncident>();

		for (CustomIncident incid : currentIncidents) {
			if (incid.getState().equals(state)) {
				sortedList.add(incid);
			}
		}
		return sortedList;

	}

	/**
	 * 
	 * @return The acknowledgement string from the Ripley API
	 */
	public String getAcknowledgementString() {
		return api.getAcknowledgementString();
	}

	/**
	 * 
	 * @return time the NUFORC database was last updated
	 */
	public String getLastUpdated() {
		return api.getLastUpdated();
	}

	/**
	 * 
	 * @return current version of the API
	 */
	public double getVersion() {
		return api.getVersion();
	}

	/**
	 * 
	 * @return year of the earliest ufo sighting stored in the database
	 */
	public int getStartYear() {
		return api.getStartYear();
	}

	/**
	 * 
	 * @return year of the latest ufo sighting stored in the database
	 */
	public int getLatestYear() {
		return api.getLatestYear();
	}

}
