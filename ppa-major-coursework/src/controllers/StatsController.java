package controllers;

import java.awt.Dimension;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;

import Statistics.AflalStatistic;
import Statistics.BahaStatistic;
import Statistics.Stats;

public class StatsController {
	private AflalStatistic aflalStat;
	private BahaStatistic bahaStat;
	private Stats statsGUI;

	public StatsController(Stats statsGUI) {
		this.aflalStat = new AflalStatistic();
		this.bahaStat = new BahaStatistic();
		this.statsGUI = statsGUI;
	}

	public JPanel getAflalStat() {
		JFreeChart lineChart = ChartFactory.createLineChart("Time of Sightings ", "(3x hours)", "Sightings",
				aflalStat.getDataSet(), PlotOrientation.VERTICAL, true, true, false);
		ChartPanel chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new Dimension(560, 367));
		return chartPanel;
	}

	public JPanel getBahaStat() {
		JFreeChart chart = ChartFactory.createPieChart("States Frequency", bahaStat.getDataSet(), true, true, true);
		ChartPanel chartPanel = new ChartPanel(chart);
		return chartPanel;
	}

}
