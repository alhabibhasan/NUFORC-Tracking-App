package Statistics;

import java.awt.Component;
import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import Data.Process;

public class BahaStatistics extends JPanel{

	private HashMap<String, Integer> stateFrequency;
	
	public BahaStatistics(){
		stateFrequency = Process.getStateFrequency();
		this.add(draw());
	}
	
	public Component draw() {
		JFreeChart chart = ChartFactory.createPieChart("frequency", createDataSet(), true, true, true);
		ChartPanel chartPanel = new ChartPanel(chart);
		return chartPanel;
	}
	
	private DefaultPieDataset createDataSet() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for(String key: stateFrequency.keySet()){
			dataset.setValue(key, stateFrequency.get(key));
		}
		return dataset;
	}
}