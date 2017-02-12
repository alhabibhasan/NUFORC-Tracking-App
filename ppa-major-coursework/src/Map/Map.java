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
	private HashMap<String, Integer[]> locations;
	private HashMap<String, Integer> points;
	
	public Map(HashMap<String, Integer> points) {
		FileReader reader = new FileReader();
		locations = reader.getLocations();
		this.points = points;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ImageIcon map = new ImageIcon("res\\usa.png");
		map.paintIcon(this, g, 0, 0);

		this.g = g;
		setAliens();
	}

	private void setAliens() {
		double averageFrequency = 0;
		for (String point : points.keySet()) {
			averageFrequency += points.get(point);
		}
		
		averageFrequency = averageFrequency/points.size();
		
		System.out.println("Average frequency is: " + averageFrequency);
		
		int currentFrequency;
		int scaleFactor;
		for (String point : points.keySet()) {
			if (locations.keySet().contains(point)) {
				Integer coord[] = locations.get(point);
				Integer x, y;
				x = coord[0];
				y = coord[1];
	
				ImageIcon alien = new ImageIcon("res\\alien.png");
				Image img = alien.getImage();
				scaleFactor = 1;
				currentFrequency = points.get(point);
				System.out.println(point + " " + points.get(point));
				if (currentFrequency > Math.floor(averageFrequency) && currentFrequency > 2 * Math.floor(averageFrequency)) scaleFactor = 4;
				if (currentFrequency > Math.floor(averageFrequency) ) scaleFactor = 3;
				if (currentFrequency == Math.floor(averageFrequency)) scaleFactor = 2;
				if (currentFrequency < Math.floor(averageFrequency)) scaleFactor = 1;
				
				Image newImg = img.getScaledInstance(15 * scaleFactor, 15 * scaleFactor, Image.SCALE_SMOOTH);
				ImageIcon alienDone = new ImageIcon(newImg);
				alienDone.paintIcon(this, g, x, y);
				
				JPanel alienPanel = new JPanel();
				alienPanel.setSize(15 * scaleFactor, 15 * scaleFactor);
				alienPanel.setLocation(x, y);
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
