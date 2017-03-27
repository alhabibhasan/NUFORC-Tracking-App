package comparators;

import java.util.Comparator;

import Data.CustomIncident;

public class IncidentShapeComp implements Comparator<CustomIncident> {

	@Override
	public int compare(CustomIncident i1, CustomIncident i2) {
		String shape1 = i1.getShape();
		String shape2 = i2.getShape();

		return shape1.compareToIgnoreCase(shape2);

	}

}
