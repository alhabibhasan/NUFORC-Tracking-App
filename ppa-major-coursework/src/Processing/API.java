package Processing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Observable;

import GUI.GUI;
import api.ripley.Ripley;
import edu.emory.mathcs.backport.java.util.Collections;

/**
 * This class handles the logistics of the program such as calculating the
 * frequency of UFO sightings in every city containied within the results from
 * the API. It also carries queries the API for details such as the last update,
 * acknowledgements, and dates of ealiest and latest inserts.
 * 
 * The class outsources data retrieval to more specialised class i.e. caching
 * which handles data retreival and storage.
 * 
 * @author Muhammed Hasan
 *
 */
public class API extends Observable implements Runnable {
	// the api keys below must not be changed.
	private static Ripley api = new Ripley("10tLI3CRs9qyVD6ql2OMtA==", "tBgm4pRv9wrVqL46EnH7ew==");
	private static ArrayList<CustomIncident> currentIncidents;
	private ArrayList<CustomIncident> incidentsFromFile;
	private static String dataStart = String.valueOf(api.getStartYear());
	private static String dataEnd = String.valueOf(api.getLatestYear());
	private static HashMap<String, Integer> mapData;
	private Caching cache;

	public API(GUI observer) {
		this.addObserver(observer);
		cache = new Caching();
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
		try {
			cache.checkForUpdate();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.out.println("Failed to check for updates.");
		}
		incidentsFromFile = cache.getCurrentIncidents();
		System.out.println("Start: " + dataStart);
		System.out.println("End: " + dataEnd);
		ArrayList<CustomIncident> incidentsInRange = new ArrayList<CustomIncident>();
		for (CustomIncident incid : incidentsFromFile) {
			try {
				int year = Integer.parseInt((incid.getDateAndTime().substring(0, 4)));
				if (year >= Integer.parseInt(dataStart) && year <= Integer.parseInt(dataEnd)) {
					// check the date of each incident
					incidentsInRange.add(incid);
				}
			} catch (NullPointerException e) {
				System.out.println("Incident without date or time");
			}

		}

		System.out.println(incidentsInRange.size());
		currentIncidents = incidentsInRange;

		calculateStateFrequency(); // caluclate the frequency of incidents in
									// each state

		setChanged();
		notifyObservers(getStateFrequency());
	}

	/**
	 * This method is used to set the range from within which we should extract
	 * data
	 * 
	 * @param dateFrom
	 * @param dateTo
	 */
	public void setCustomDataFromRange(String dateFrom, String dateTo) {
		API.dataStart = dateFrom;
		API.dataEnd = dateTo;
		// if the user specifies a custom range, we will use it, otherwise we
		// will get the earliest and latest dates
		// from the api and use them
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
		return API.currentIncidents;
	}

	/**
	 * @return mapData All cities where incidents occured and the frequency of
	 *         incidents in this city are contained within this hashmap
	 */
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
				// add to the list only if it took place in the state we are
				// looking at
				sortedList.add(incid);
			}
		}
		return sortedList;

	}

	/**
	 * @return The acknowledgement string from the Ripley API
	 */
	public String getAcknowledgementString() {
		return api.getAcknowledgementString();
	}

	/**
	 * @return time the NUFORC database was last updated
	 */
	public String getLastUpdated() {
		return api.getLastUpdated();
	}

	/**
	 * @return current version of the API
	 */
	public double getVersion() {
		return api.getVersion();
	}

	/**
	 * @return year of the earliest ufo sighting stored in the database
	 */
	public int getStartYear() {
		return api.getStartYear();
	}

	/**
	 * @return year of the latest ufo sighting stored in the database
	 */
	public int getLatestYear() {
		return api.getLatestYear();
	}

}
