package Map;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.ImageIcon;

import javax.swing.JPanel;

public class Map extends JPanel {

	private Graphics g;
	private HashMap<String, Integer[]> locationsFromFile;
	private HashMap<String, Integer> locationsToPlot;

	public Map(HashMap<String, Integer> locationsToPlot) {
		LocationsReader reader = new LocationsReader();
		locationsFromFile = reader.getLocations();
		this.locationsToPlot = locationsToPlot;
	}

	/**
	 * This method draws the map with the aliens drawn on to it.
	 */
	public void paint(Graphics g) {
		super.paintComponent(g);
		ImageIcon map = new ImageIcon("res\\usa.png");
		map.paintIcon(this, g, 0, 0);

		this.g = g;
		setAliens();
	}

	private void setAliens() {
		double averageFrequency = 0;
		for (String point : locationsToPlot.keySet()) {
			averageFrequency += locationsToPlot.get(point);
		}

		averageFrequency = averageFrequency / locationsToPlot.size();

		System.out.println("Average frequency is: " + Math.ceil(averageFrequency));

		int currentFrequency;
		int scaleFactor;
		for (String point : locationsToPlot.keySet()) {
			System.out.println(point + " " + locationsToPlot.get(point));
			if (locationsFromFile.keySet().contains(point)) {
				Integer coord[] = locationsFromFile.get(point);
				Integer x, y;
				x = coord[0];
				y = coord[1];

				ImageIcon alien = new ImageIcon("res\\alien.png");
				Image img = alien.getImage();
				scaleFactor = 1;
				currentFrequency = locationsToPlot.get(point);

				if (currentFrequency > Math.ceil(averageFrequency)
						&& currentFrequency > 2 * Math.ceil(averageFrequency))
					scaleFactor = 60;
				if (currentFrequency > Math.ceil(averageFrequency))
					scaleFactor = 20;
				if (currentFrequency == Math.ceil(averageFrequency))
					scaleFactor = 10;
				if (currentFrequency < Math.ceil(averageFrequency))
					scaleFactor = 1;

				Image newImg = img.getScaledInstance(25 + scaleFactor, 25 + scaleFactor, Image.SCALE_SMOOTH);
				ImageIcon alienDone = new ImageIcon(newImg);
				alienDone.paintIcon(this, g, x, y);

				JPanel alienPanel = new JPanel();
				alienPanel.setSize(25, 25);
				alienPanel.setLocation(x + 5, y + 5);
				alienPanel.setOpaque(false);
				add(alienPanel);
				alienPanel.addMouseListener(new MouseListener() {

					@Override
					public void mouseClicked(MouseEvent arg0) {
						System.out.println("Mouse clicked on: " + point);

					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseExited(MouseEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mousePressed(MouseEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseReleased(MouseEvent arg0) {
						// TODO Auto-generated method stub

					}

				});

			}
		}
	}

}
