
package Statistics;

import java.awt.Component;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.omg.CORBA.DATA_CONVERSION;

import com.google.api.client.util.Data;

import api.ripley.Incident;
import api.ripley.Ripley;
import Data.CustomIncident;
import Data.Process;

/**
 * This class is used to graph the Alien most sighted times.
 * @author Aflal Asker
 *
 */
public class AflalStatistic extends JPanel {

	private static ArrayList<CustomIncident> data;
	private static Ripley api;
	private HashMap<String, Integer> timeFrequency;
	private DefaultCategoryDataset dataset;
	

	public AflalStatistic() {

		
		timeFrequency = new HashMap<String, Integer>();
		data = Process.getCurrentIncidents();
		dataset = new DefaultCategoryDataset();

		statsLoop();

	}

	static JPanel graphToShow = (JPanel) new AflalStatistic().draw();
	/**
	 * This method goes through all the incidents, gets the times and compares it with the provided time.
	 * Every iteration increases the appropriate integer values. Then the values gets added to the HashMap. 
	 */
	public void statsLoop() {

		String to24_03s = "0";
		String to03_06s = "1";
		String to06_09s = "2";
		String to9_12s = "3";
		String to12_15s = "4";
		String to15_18s = "5";
		String to18_21s = "6";
		String to21_23s = "7";

		int to24_03i = 0, to03_06i = 0, to06_9i = 0, to9_12i = 0, to12_15i = 0, to15_18i = 0, to18_21i = 0,
				to21_23i = 0;

		for (CustomIncident time : data) {

			String storedTime = time.getDateAndTime().substring(11, 13);

			Integer.parseInt(storedTime);

			if (Integer.parseInt(storedTime) <= 24 && Integer.parseInt(storedTime) <= 03) {

				to24_03i++;
			}

			if (Integer.parseInt(storedTime) > 03 && Integer.parseInt(storedTime) <= 06) {

				to03_06i++;
			}

			if (Integer.parseInt(storedTime) > 06 && Integer.parseInt(storedTime) <= 9) {

				to06_9i++;
			}

			if (Integer.parseInt(storedTime) > 9 && Integer.parseInt(storedTime) <= 12) {

				to9_12i++;
			}

			if (Integer.parseInt(storedTime) > 12 && Integer.parseInt(storedTime) <= 15) {

				to12_15i++;
			}

			if (Integer.parseInt(storedTime) > 15 && Integer.parseInt(storedTime) <= 18) {

				to15_18i++;
			}

			if (Integer.parseInt(storedTime) > 18 && Integer.parseInt(storedTime) <= 21) {

				to18_21i++;
			}

			if (Integer.parseInt(storedTime) > 21 && Integer.parseInt(storedTime) <= 23) {

				to21_23i++;
			}

		}

		timeFrequency.put(to24_03s, to24_03i);
		timeFrequency.put(to03_06s, to03_06i);
		timeFrequency.put(to06_09s, to06_9i);
		timeFrequency.put(to9_12s, to9_12i);
		timeFrequency.put(to12_15s, to12_15i);
		timeFrequency.put(to15_18s, to15_18i);
		timeFrequency.put(to18_21s, to18_21i);
		timeFrequency.put(to21_23s, to21_23i);

	}
	/**
	 * This method creates the chart to plot the values from the HashMap.  
	 * @return A chart with all the sighting times. Uses the classified data from the HashMap to create a plotted graph. 
	 */
	public Component draw() {
		JFreeChart lineChart = ChartFactory.createLineChart("Time of Sightings ", "(3x hours)", "Sightings",
				createDataSet(), PlotOrientation.VERTICAL, true, true, false);
		ChartPanel chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new Dimension(560, 367));
		return chartPanel;

	}
	
	/**
	 * Gets the keys from the HashMap and adds it to the data set. 
	 * @return The data set vales that has to be plotted in the graph. 
	 */
	private DefaultCategoryDataset createDataSet() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (String timeRange : timeFrequency.keySet()) {

			dataset.addValue(timeFrequency.get(timeRange), "Sightings", timeRange);

		}

		return dataset;
	}

}