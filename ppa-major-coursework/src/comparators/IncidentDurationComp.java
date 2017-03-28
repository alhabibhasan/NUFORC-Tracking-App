package comparators;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Data.CustomIncident;
/**
 * Used to sort incidents according to the duration of each incident. sorted from quickest to longest
 * duration
 * @author k1630580
 *
 */
public class IncidentDurationComp implements Comparator<CustomIncident> {

	private static Pattern minutes = Pattern.compile("\\d+?(?=( )?mins|( )?minutes|( )?minute|( )?min)",
			Pattern.CASE_INSENSITIVE);
	private static Pattern seconds = Pattern.compile("\\d+?(?=( )?sec|( )?second|( )?seconds)",
			Pattern.CASE_INSENSITIVE);
	private static Pattern hours = Pattern.compile("\\d+?(?=( )?hrs|( )?hours|( )?hour)", Pattern.CASE_INSENSITIVE);
	private static Pattern days = Pattern.compile("\\d+?(?=( )?day|( )?days)", Pattern.CASE_INSENSITIVE);
	/**
	 * Comparator which sorts incidents according to their duration.
	 */
	@Override
	public int compare(CustomIncident i1, CustomIncident i2) {
		String[] i1Duration = formatDuration(i1.getDuration());
		String[] i2Duration = formatDuration(i2.getDuration());

		

		if (i1Duration[1].equals(i2Duration[1])) {
			return Integer.compare(Integer.parseInt(i1Duration[0]), Integer.parseInt(i2Duration[0]));
		} else {
		
			if (i1Duration[0].equals("-1")) {
				return 1;
			}
			
			if (i2Duration[0].equals("-1")) {
				return -1;
			}
			
			// second is smaller than everything else
			if (i1Duration[1].equals("second")) {
				return -1;
			}

			if (i1Duration[1].equals("minute") && (i2Duration[1].equals("second"))) {
				return 1;
			}
			
			if (i1Duration[1].equals("minute") && (i2Duration[1].equals("hour"))) {
				return -1;
			}
			
			if (i1Duration[1].equals("minute") && (i2Duration[1].equals("day"))) {
				return -1;
			}
			
			
			if (i1Duration[1].equals("hour") && (i2Duration[1].equals("second"))) {
				return 1;
			}
			
			if (i1Duration[1].equals("hour") && (i2Duration[1].equals("minute"))) {
				return 1;
			}
			
			if (i1Duration[1].equals("hour") && (i2Duration[1].equals("day"))) {
				return -1;
			}

			// day is bigger than eveything else
			if (i1Duration[1].equals("day")) {
				return 1;
			}
			
		}

		return 0;

	}
	/**
	 * This function is used to derive the duration of each incident from a given incident
	 * @param duration
	 * @return A string array containing the number and length of duration e.g. n (minutes|seconds|days) etc.
	 */
	private String[] formatDuration(String duration) {
		String[] toReturn = { "empty", "empty" };
		Matcher minuteMatch = minutes.matcher(duration);
		Matcher hourMatch = hours.matcher(duration);
		Matcher secondMatch = seconds.matcher(duration);
		Matcher dayMatch = days.matcher(duration);

		if (minuteMatch.find()) {
			toReturn[0] = minuteMatch.group();
			toReturn[1] = "minute";
			return toReturn;
		} else if (hourMatch.find()) {
			toReturn[0] = hourMatch.group();
			toReturn[1] = "hour";
			return toReturn;
		} else if (secondMatch.find()) {
			toReturn[0] = secondMatch.group();
			toReturn[1] = "second";
			return toReturn;
		} else if (dayMatch.find()) {
			toReturn[0] = dayMatch.group();
			toReturn[1] = "day";
			return toReturn;
		} else {
			toReturn[0] = "-1";
			toReturn[1] = "unmatchable";
			return toReturn;
		}

	}

}
