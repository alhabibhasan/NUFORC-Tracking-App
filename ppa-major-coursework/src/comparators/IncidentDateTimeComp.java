package comparators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import Data.CustomIncident;

public class IncidentDateTimeComp implements Comparator<CustomIncident> {

	@Override
	public int compare(CustomIncident i1, CustomIncident i2) {
		SimpleDateFormat wantedFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		Date d1; 
		Date d2;
		try {
			d1 = wantedFormat.parse(i1.getDateAndTime());
			d2 = wantedFormat.parse(i2.getDateAndTime());
			return d1.compareTo(d2);
		} catch (ParseException e) {
			System.out.println("Could not parse time");
			e.printStackTrace();
			return 0;
		}
		
		
	}

}
