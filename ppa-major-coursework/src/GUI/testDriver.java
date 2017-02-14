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
		gui.createGUI();
		
		

		long time2 = System.currentTimeMillis();

		System.out.println("Time taken: " + (time2 - time1) / 1000 + " seconds.");
		gui.rightButtonEnabled(true);
	}
	
	
}
