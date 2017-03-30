package Map;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class LocationsReader {

	private Scanner reader;
	private HashMap<String, Integer[]> locations;
	private HashMap<String, String> abrevToName;
	/**
	 * Initialises the locations reader which reads the locations from file.
	 */
	public LocationsReader() {
		abrevToName = new HashMap<String, String>();
		locations = new HashMap<String, Integer[]>();
	}
	/**
	 * 
	 * @return returns the complete set of locations read from file.
	 */
	public HashMap<String, Integer[]> getLocations() {
		openFile();
		readFile();
		closeFile();
		
		return locations;
	}
	
	private void openFile() {
		try {
			reader = new Scanner(new File("res//locations.txt"));
		} catch (IOException ioe) {
			System.out.println("Could not open file, error: " + ioe.getMessage() );
		}
	}
	
	private void readFile() {
		String stateName = "";
		String stateAbrev = "";
		Integer x = 0;
		Integer y = 0;
		while(reader.hasNext()) {
			stateName = reader.next();
			stateAbrev = reader.next();
			x = Integer.parseInt(reader.next());
			y = Integer.parseInt(reader.next());
			
			Integer coords[] = {x, y};
			abrevToName.put(stateAbrev, stateName);
			locations.put(stateAbrev, coords);
		}
	}
	
	private void closeFile() {
		reader.close();
	}
	/**
	 * Returns a hashmap containing the abbreviation of US states each of which maps to the fullname 
	 * of the state
	 * @return
	 */
	public HashMap<String, String> getAbrevToState() {
		return this.abrevToName;
	}
}
