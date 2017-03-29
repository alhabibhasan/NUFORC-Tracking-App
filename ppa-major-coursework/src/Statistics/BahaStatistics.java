package Statistics;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
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

import Data.CustomIncident;
import Data.Process;
import Map.LocationsReader;

public class BahaStatistics extends JPanel{

	private ArrayList<CustomIncident> listOfIncidents;
	private HashMap<String, Integer[]> locations;
	private HashMap<String, Integer> stateFrequency;
	
	public BahaStatistics(){

		listOfIncidents = new ArrayList<CustomIncident>();
		listOfIncidents = Process.getCurrentIncidents();
		LocationsReader fileReader = new LocationsReader();
		locations = fileReader.getLocations();
		stateFrequency = Process.getStateFrequency();
		this.add(draw());
	}
	
	public Component draw() {
		JFreeChart chart = ChartFactory.createPieChart("States Frequency", createDataSet(), true, true, true);
		ChartPanel chartPanel = new ChartPanel(chart);
		return chartPanel;
	}
	
	public DefaultPieDataset createDataSet() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for(String key: stateFrequency.keySet()){
			 for (String point : locations.keySet()) {
			      if (locations.keySet().contains(point)) {
			        key = point;
					dataset.setValue(key, stateFrequency.get(key));
				}
			 }
		}
		
		for(String key: stateFrequency.keySet()){
			for(CustomIncident incidents :listOfIncidents){
				if(key.equals("AK") || key.equals("HI")){
					dataset.setValue(key, stateFrequency.get(key));
				}
			 }
		}
		System.out.println("items count: " +dataset.getItemCount());
		return dataset;	
	}
}