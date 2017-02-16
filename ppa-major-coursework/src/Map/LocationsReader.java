package Map;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class LocationsReader {

	private Scanner reader;
	private HashMap<String, Integer[]> locations;
	private HashMap<String, String> abrevToName;
	
	public LocationsReader() {
		abrevToName = new HashMap<String, String>();
		locations = new HashMap<String, Integer[]>();
	}
	public HashMap<String, Integer[]> getLocations() {
		openFile();
		readFile();
		closeFile();
		
		return locations;
	}
	
	private void openFile() {
		try {
			reader = new Scanner(new File("res//locations.txt"));
			System.out.println("File read successfully.");
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
		
		System.out.println("State coordinates loaded into the system as expected.");
	}
	
	private void closeFile() {
		reader.close();
		System.out.println("File closed.");
	}
	
}
