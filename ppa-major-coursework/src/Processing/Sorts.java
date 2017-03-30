package Processing;

import java.util.ArrayList;
import java.util.Observable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import comparators.IncidentCityComp;
import comparators.IncidentPostedComp;
import comparators.IncidentShapeComp;
import comparators.IncidentDurationComp;
import comparators.IncidentDateTimeComp;
import GUI.InfoGUI;
import edu.emory.mathcs.backport.java.util.Collections;
/**
 * This class handles the sorting of incidents which have been selected by the user for further analysis
 * @author Muhammed Hasan
 *
 */
public class Sorts extends Observable {
	private static Pattern minutes = Pattern.compile("\\d+?(?=( )?mins|( )?minutes|( )?minute|( )?min)",
			Pattern.CASE_INSENSITIVE);
	private static Pattern seconds = Pattern.compile("\\d+?(?=( )?sec|( )?second|( )?seconds)",
			Pattern.CASE_INSENSITIVE);
	private static Pattern hours = Pattern.compile("\\d+?(?=( )?hrs|( )?hours|( )?hour)", Pattern.CASE_INSENSITIVE);
	private static Pattern days = Pattern.compile("\\d+?(?=( )?day|( )?days)", Pattern.CASE_INSENSITIVE);
	private ArrayList<CustomIncident> currIncidents = new ArrayList<CustomIncident>();

	public Sorts(InfoGUI gui) {
		addObserver(gui);
	}

	/**
	 * Returns data relevant to a certain given state
	 * 
	 * @param stateAbrev
	 *            The state to return data for
	 * @return Processing relevant to the given state
	 */
	public ArrayList<String> getOutputDataForState(String stateAbrev) {
		ArrayList<CustomIncident> currentIncidents = API.getCurrentIncidents();
		ArrayList<String> outputList = new ArrayList<String>();


		
		for (CustomIncident incid : currentIncidents) {
			if (incid.getState().equals(stateAbrev)) {
				CustomIncident ci = new CustomIncident(incid, formatDuration(incid.getDuration().toString()));
				currIncidents.add(ci);
				outputList.add(getWantedString(ci));
			}
		}
		setChanged();
		notifyObservers();
		return outputList;
	}

	private String getWantedString(CustomIncident incid) {
		return "Time: " + incid.getDateAndTime() + " City: " + incid.getCity() + " Shape: " + incid.getShape()
				+ " Duration: " + incid.getDuration() + " Posted: " + incid.getPosted();
	}

	/**
	 * Gets the summary of the index selected by the user
	 * @param index
	 * @return
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public String summaryForInci(int index) throws ArrayIndexOutOfBoundsException{
		return currIncidents.get(index).getSummary();
	}
	
	/**
	 * Sorts the current list of incidents according to the duration
	 * @return
	 */
	public ArrayList<String> sortDuration() {
		// TODO: Sort data according to duration


		Collections.sort(currIncidents, new IncidentDurationComp());

		ArrayList<String> tempString = new ArrayList<String>();

		for (CustomIncident i : currIncidents) {
			tempString.add(getWantedString(i));
		}

		setChanged();
		notifyObservers();
		return tempString;
	}
	
	/**
	 * Sorts the current list of incidents according to the date posted.
	 * @return
	 */
	public ArrayList<String> sortPosted() {
		Collections.sort(currIncidents, new IncidentPostedComp());

		ArrayList<String> tempString = new ArrayList<String>();

		for (CustomIncident i : currIncidents) {
			tempString.add(getWantedString(i));
		}

		setChanged();
		notifyObservers();
		return tempString;
	}
	/**
	 * Sorts the current list of icidents according to the city the icident took place in
	 * @return
	 */
	public ArrayList<String> sortCity() {
		// TODO: Sort data according to duration


		Collections.sort(currIncidents, new IncidentCityComp());

		ArrayList<String> tempString = new ArrayList<String>();

		for (CustomIncident i : currIncidents) {
			tempString.add(getWantedString(i));
		}

		setChanged();
		notifyObservers();
		return tempString;
	}

	/**
	 * Sorts the current list of incidents according to the date and time it took place
	 * @return
	 */
	public ArrayList<String> sortDateTime() {
		// TODO: Sort data according to duration


		Collections.sort(currIncidents, new IncidentDateTimeComp());

		ArrayList<String> tempString = new ArrayList<String>();

		for (CustomIncident i : currIncidents) {
			tempString.add(getWantedString(i));
		}

		setChanged();
		notifyObservers();
		return tempString;
	}
	/**
	 * Sorts the current list of incidents according to the shape of the ufo
	 * @return
	 */
	public ArrayList<String> sortShape() {
		// TODO: Sort data according to duration


		Collections.sort(currIncidents, new IncidentShapeComp());

		ArrayList<String> tempString = new ArrayList<String>();

		for (CustomIncident i : currIncidents) {
			tempString.add(getWantedString(i));
		}

		setChanged();
		notifyObservers();
		return tempString;
	}
	
	private static String formatDuration(String duration) {
		Matcher minuteMatch = minutes.matcher(duration);
		Matcher hourMatch = hours.matcher(duration);
		Matcher secondMatch = seconds.matcher(duration);
		Matcher dayMatch = days.matcher(duration);

		if (minuteMatch.find()) {
			return minuteMatch.group() + " minute(s)";
		} else if (hourMatch.find()) {
			return hourMatch.group() + " hour(s)";
		} else if (secondMatch.find()) {
			return secondMatch.group() + " second(s)";
		} else if (dayMatch.find()) {
			return dayMatch.group() + " day(s)";
		} else {
			return "ambiguous";
		}

	}

}
