package Statistics;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import Data.CustomIncident;
import Data.Process;

public class HabibStat extends JPanel {
	/**
	 * Default serialVersion ID
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<CustomIncident> incidentsToAnalyse;

	public HabibStat() {
		incidentsToAnalyse = new ArrayList<CustomIncident>();
		incidentsToAnalyse = Process.getCurrentIncidents();

		this.add(draw(Integer.parseInt(Process.getDataStart()), Integer.parseInt(Process.getDataEnd().toString())));

	}

	public JPanel draw(int startYear, int endYear) {
		JFreeChart lineChart = ChartFactory.createLineChart(
				"Trend of sightings between the years " + startYear + " to " + endYear, "Years", "Sightings",
				createDataSet(startYear, endYear - 1), PlotOrientation.VERTICAL, true, true, false);
		ChartPanel chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new Dimension(560, 367));
		return chartPanel;
	}

	private DefaultCategoryDataset createDataSet(int startYear, int endYear) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (int count = startYear; count <= endYear; count++) {
			int yearFrequency = 0;

			for (CustomIncident incid : incidentsToAnalyse) {
				if (incid.getDateAndTime().contains(String.valueOf(count))) {
					yearFrequency++;
				}
			}

				dataset.addValue(yearFrequency, "Sightings", String.valueOf(count));
		}

		return dataset;
	}
}
