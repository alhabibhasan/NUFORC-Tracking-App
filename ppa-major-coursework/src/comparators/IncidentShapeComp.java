package comparators;

import java.util.Comparator;

import Processing.CustomIncident;
/**
 * Used to sort the incident based on their shape. Incidents will be sorted alphabetically from A-Z
 * @author k1630580
 *
 */
public class IncidentShapeComp implements Comparator<CustomIncident> {

	@Override
	public int compare(CustomIncident i1, CustomIncident i2) {
		String shape1 = i1.getShape();
		String shape2 = i2.getShape();

		return shape1.compareToIgnoreCase(shape2);

	}

}
