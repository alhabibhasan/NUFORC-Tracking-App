package controllers;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;

import Statistics.AflalStatistic;
import Statistics.AnalyseData;
import Statistics.BahaStatistic;
import Statistics.Search;
import Statistics.Stats;
import Statistics.VideoContainer;

public class StatsController {
	private AflalStatistic aflalStat;
	private BahaStatistic bahaStat;
	private AnalyseData dA;

	public StatsController() {
		this.aflalStat = new AflalStatistic();
		this.bahaStat = new BahaStatistic();
		this.dA = new AnalyseData();
		
		dA.CalculateLikeliestState();
		dA.CalculateNonUSSightings();
		dA.CalculateNumberOfHoaxes();
		
		aflalStat.createDataSet();
		bahaStat.createDataSet();
		
		
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

	public JScrollPane getYouTubeVids() {
		Search video = new Search();
		VideoContainer vidCon = new VideoContainer(video.searchYouTube().iterator());
		return vidCon.getScroll();
	}
	
	public String getLikeliestState() {
		return dA.getLikeliestState();
	}
	
	public int getNoOfHoaxes() {
		return dA.getNoOfHoaxes();
	}
	
	public int getNoOfNonUS() {
		return dA.getNoOfNonUS();
	}
	
}
