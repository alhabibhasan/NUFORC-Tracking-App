package comparators;

import java.util.Comparator;

import Data.CustomIncident;

public class IncidentCityComp implements Comparator<CustomIncident> {

	@Override
	public int compare(CustomIncident i1, CustomIncident i2) {
		String city1 = i1.getCity();
		String city2 = i2.getCity();

		return city1.compareToIgnoreCase(city2);

	}

}
