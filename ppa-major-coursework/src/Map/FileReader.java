package Map;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class FileReader {

	private Scanner reader;
	private HashMap<String, Integer[]> locations;
	
	public FileReader() {
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
		String state = "";
		Integer x = 0;
		Integer y = 0;
		while(reader.hasNext()) {
			state = reader.next();
			x = Integer.parseInt(reader.next());
			y = Integer.parseInt(reader.next());
			
			Integer coords[] = {x, y};
			
			locations.put(state, coords);
		}
		
		System.out.println("State coordinates loaded into the system as expected.");
	}
	
	private void closeFile() {
		reader.close();
		System.out.println("File closed.");
	}
	
}
