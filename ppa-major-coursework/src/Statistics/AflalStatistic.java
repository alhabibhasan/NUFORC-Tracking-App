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

import api.ripley.Incident;
import api.ripley.Ripley;

public class AflalStatistic extends JPanel {

	private static ArrayList<Incident> data;
	private static Ripley api;
	private HashMap<String, Integer> timeFrequency;
	private DefaultCategoryDataset dataset;

	public AflalStatistic() {

		timeFrequency = new HashMap<String, Integer>();
		Ripley api = new Ripley("10tLI3CRs9qyVD6ql2OMtA==", "tBgm4pRv9wrVqL46EnH7ew==");
		data = new ArrayList<Incident>();
		data = api.getIncidentsInRange("2016-01-01 00:00:00", "2017-01-01 00:00:00");
		dataset = new DefaultCategoryDataset();
		
		statsLoop();
		
	}
	
	static JPanel graphToShow = (JPanel) new AflalStatistic().draw();
	
	public void statsLoop() {
		
		String to24_03s = "0";
		String to03_06s = "1";
		String to06_09s = "2";
		String to9_12s = "3";
		String to12_15s = "4";
		String to15_18s = "5";
		String to18_21s = "6";
		String to21_23s = "7";

		int to24_03i = 0, to03_06i = 0, to06_9i = 0, to9_12i = 0, to12_15i = 0, to15_18i = 0, to18_21i = 0, to21_23i = 0;

		for (Incident time : data) {

			String storedTime = time.getDateAndTime().substring(11, 13);

			Integer.parseInt(storedTime);

			if (Integer.parseInt(storedTime) <= 24 && Integer.parseInt(storedTime) <= 03) {

				System.out.println("I'm between 00am and 03:00am : " + storedTime);
				to24_03i++;
			}

			if (Integer.parseInt(storedTime) > 03 && Integer.parseInt(storedTime) <= 06) {

				System.out.println("I'm between 03:00am and 06:00am : " + storedTime);
				to03_06i++;
			}

			if (Integer.parseInt(storedTime) > 06 && Integer.parseInt(storedTime) <= 9) {

				System.out.println("I'm between 06:00am and 9:00am : " + storedTime);
				to06_9i++;
			}

			if (Integer.parseInt(storedTime) > 9 && Integer.parseInt(storedTime) <= 12) {

				System.out.println("I'm between 09:00am and 12:00pm : " + storedTime);
				to9_12i++;
			}

			if (Integer.parseInt(storedTime) > 12 && Integer.parseInt(storedTime) <= 15) {

				System.out.println("I'm between 12:00pm and 15:00pm : " + storedTime);
				to12_15i++;
			}

			if (Integer.parseInt(storedTime) > 15 && Integer.parseInt(storedTime) <= 18) {

				System.out.println("I'm between 15:00pm and 18:00pm : " + storedTime);
				to15_18i++;
			}

			if (Integer.parseInt(storedTime) > 18 && Integer.parseInt(storedTime) <= 21) {

				System.out.println("I'm between 18:00pm and 21:00pm : " + storedTime);
				to18_21i++;
			}

			if (Integer.parseInt(storedTime) > 21 && Integer.parseInt(storedTime) <= 23) {

				System.out.println("I'm between 21:00pm and 23:00pm : " + storedTime);
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

		System.out.println("Value:" + timeFrequency.get(to24_03s));
		System.out.println("Value:" + timeFrequency.get(to03_06s));
		System.out.println("Value:" + timeFrequency.get(to06_09s));
		System.out.println("Value:" + timeFrequency.get(to9_12s));
		System.out.println("Value:" + timeFrequency.get(to12_15s));
		System.out.println("Value:" + timeFrequency.get(to15_18s));
		System.out.println("Value:" + timeFrequency.get(to18_21s));
		System.out.println("Value:" + timeFrequency.get(to21_23s));

	}

	public Component draw() {
		JFreeChart lineChart = ChartFactory.createLineChart("Time of Sightings ", "(3x hours)", "Sightings", createDataSet(),
				PlotOrientation.VERTICAL, true, true, false);
		ChartPanel chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new Dimension(560, 367));
		return chartPanel;

	}

	private DefaultCategoryDataset createDataSet() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (String timeRange : timeFrequency.keySet()) {

			dataset.addValue(timeFrequency.get(timeRange), "Sightings", timeRange);

		}

		return dataset;
	}
	
	

}