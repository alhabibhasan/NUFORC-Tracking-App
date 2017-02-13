package GUI;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
import Data.Process;
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

		Process data = new Process(test);
		
		data.getData("2016", "2017");
		
		HashMap<String, Integer> dataPoints = data.getStateFrequency();

		Map d = new Map(dataPoints);
		gui.setMap(d);

		long time2 = System.currentTimeMillis();

		System.out.println("Time taken: " + (time2 - time1) / 1000 + " seconds.");
		gui.rightButtonEnabled(true);
	}
	
	
}
