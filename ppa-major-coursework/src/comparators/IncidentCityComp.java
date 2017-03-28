package comparators;

import java.util.Comparator;

import Data.CustomIncident;
/**
 * The class implements comparator. it is used to sort incidents based on the city of the incident.
 * Cities are sorted alphabetically from A-Z
 * @author Jaman Salique
 *
 */
public class IncidentCityComp implements Comparator<CustomIncident> {

	@Override
	public int compare(CustomIncident i1, CustomIncident i2) {
		String city1 = i1.getCity();
		String city2 = i2.getCity();

		return city1.compareToIgnoreCase(city2);

	}

}
