package controllers;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;

import Processing.API;
import Statistics.AflalStatistic;
import Statistics.AnalyseData;
import Statistics.BahaStatistic;
import Statistics.HabibStat;
import Statistics.JamanStat;
import Statistics.Search;
import Statistics.VideoContainer;

/**
 * The controller class between the statistics panel and the model i.e. the
 * stats generators
 * 
 * @author Muhammed Hasan
 *
 */
public class StatsController {
	private AflalStatistic aflalStat;
	private BahaStatistic bahaStat;
	private HabibStat habibStat;
	private JamanStat jamanStat;
	private AnalyseData dA;

	/**
	 * Initializes the objects and orders them to generate their data
	 */
	public StatsController() {
		this.aflalStat = new AflalStatistic();
		this.bahaStat = new BahaStatistic();
		this.habibStat = new HabibStat();
		this.jamanStat = new JamanStat();

		this.dA = new AnalyseData();

		dA.CalculateLikeliestState();
		dA.CalculateNonUSSightings();
		dA.CalculateNumberOfHoaxes();

		aflalStat.createDataSet();
		bahaStat.createDataSet();
		habibStat.createDataSet();
		jamanStat.createDataSet();

	}

	/**
	 * 
	 * @return Returns Aflal's stat in the form of a panel
	 */
	public JPanel getAflalStat() {
		JFreeChart lineChart = ChartFactory.createLineChart("Time of Sightings ", "(3x hours)", "Sightings",
				aflalStat.getDataSet(), PlotOrientation.VERTICAL, true, true, false);
		ChartPanel chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new Dimension(560, 367));
		return chartPanel;
	}

	/**
	 * 
	 * @return Returns Baha's stat in the form of a panel
	 */
	public JPanel getBahaStat() {
		JFreeChart chart = ChartFactory.createPieChart("States Frequency", bahaStat.getDataSet(), true, true, true);
		ChartPanel chartPanel = new ChartPanel(chart);
		return chartPanel;
	}

	/**
	 * 
	 * @return Returns my (Muhammed's) stat in the form of a panel
	 */
	public JPanel getHabibStat() {
		JFreeChart lineChart = ChartFactory.createLineChart(
				"Trend of sightings between the years " + API.getDataStart() + " to " + API.getDataEnd(), "Years",
				"Sightings", habibStat.getDataSet(), PlotOrientation.VERTICAL, true, true, false);
		ChartPanel chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new Dimension(560, 367));
		return chartPanel;
	}

	/**
	 * 
	 * @return Returns Jaman's stat in the form of a panel
	 */
	public JPanel getJamanStat() {
		JFreeChart chart = ChartFactory.createPieChart("All shapes of aliens", jamanStat.getDataSet(), true, true,
				true);
		ChartPanel chartPanel = new ChartPanel(chart);
		return chartPanel;
	}

	/**
	 * 
	 * @return The result of the YouTube API search - all contained within a
	 *         JPanel
	 */
	public JScrollPane getYouTubeVids() {
		Search video = new Search();
		VideoContainer vidCon = new VideoContainer(video.searchYouTube().iterator());
		return vidCon.getScroll();
	}

	/**
	 * 
	 * @return The likeliest state to be visited by an alien
	 */
	public String getLikeliestState() {
		return dA.getLikeliestState();
	}

	/**
	 * 
	 * @return The number of hoaxes from the Analyse Data class
	 */
	public int getNoOfHoaxes() {
		return dA.getNoOfHoaxes();
	}

	/**
	 * 
	 * @return The number of non-us states to be visited by little green men -
	 *         data is retreieved from the Analyse Data class
	 */
	public int getNoOfNonUS() {
		return dA.getNoOfNonUS();
	}

}
