package comparators;

import java.util.Comparator;

import Data.CustomIncident;

public class IncidentPostedComp implements Comparator<CustomIncident> {

	@Override
	public int compare(CustomIncident i1, CustomIncident i2) {
		int year1 = Integer.parseInt(i1.getDateAndTime().substring(0, 4));
		int year2 = Integer.parseInt(i2.getDateAndTime().substring(0, 4));

		if (year1 > year2)
			return 1;
		if (year1 == year2)
			return 0;
		if (year1 < year2)
			return -1;

		return 0;
		
	}

}
