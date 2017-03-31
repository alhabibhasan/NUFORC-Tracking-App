package Processing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import api.ripley.Incident;
import api.ripley.Ripley;
/**
 * This class handles all data extraction processes from the Ripley API, it also caches data is gets from the API
 * @author Muhammed Hasan
 *
 */
public class Caching {
	private static Ripley api = new Ripley("10tLI3CRs9qyVD6ql2OMtA==", "tBgm4pRv9wrVqL46EnH7ew==");
	private ArrayList<Incident> incidentsFromAPI;
	private ArrayList<CustomIncident> incidentsFromFile;
	private String apiLastUpdate;
	private Properties props = new Properties();

	

	/**
	 * This method is used to check that local data is up to date, if data is
	 * not up to date, then data is retrieved and local data is updated.
	 * 
	 * @throws IOException
	 */
	public void checkForUpdate() throws IOException {
		FileInputStream in = new FileInputStream("res//api.properties");
		props.load(in);
		in.close();

		Parser parse = new Parser();
		List<DateGroup> a = parse.parse(api.getLastUpdated());
		DateGroup dateFromAPI = a.get(0);
		apiLastUpdate = dateFromAPI.getDates().toString();

		System.out.println("API last updated: " + apiLastUpdate);

		if (!props.getProperty("api.lastUpdate").equals("null")) {
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
					typeRef); // read the incidents from the file into a map

			incidentsFromFile = new ArrayList<>();
			for (Map.Entry<String, CustomIncident> entry : incidentMap.entrySet()) {
				incidentsFromFile.add(entry.getValue()); // copy the data into an array list
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

		ObjectMapper mapper = new ObjectMapper();

		Map<String, CustomIncident> incidentMap = new HashMap<>();

		TypeReference ref = new TypeReference<Map<String, Incident>>() {
		};

		for (Incident incident : incidentsFromAPI) {
			incidentMap.put("inident" + incidentsFromAPI.indexOf(incident), new CustomIncident(incident));
			// placing each incident into a map which will be written to a json file
		}

		try {
			mapper.writeValue(new File("res//incidents.json"), incidentMap); //writting the data to file
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
	 * This is the only method within the solution. It is used to download all
	 * data from the API.
	 * 
	 * @param startYear
	 * @param endYear
	 * @return
	 */
	private ArrayList<Incident> getAPIData(String startYear, String endYear) {
		if (endYear.equals(api.getLatestYear())) {
			// this ensures that we cannot query the API with a date in the future e.g. december 2017
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			System.out.println(dtf.format(now)); 

			return api.getIncidentsInRange(startYear + "-01-01 00:00:00", dtf.format(now).toString());
		}
		return api.getIncidentsInRange(startYear + "-01-01 00:00:00", endYear + "-12-31 00:00:00");
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
	 * Returns all incidents which are stored in the cache which is all data from the API
	 * @return
	 */
	public ArrayList<CustomIncident> getCurrentIncidents() {
		return this.incidentsFromFile;
	}
}
