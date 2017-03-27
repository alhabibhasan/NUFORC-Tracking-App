package Map;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.ImageIcon;

import javax.swing.JPanel;

import GUI.InfoGUI;

public class Map extends JPanel {

  private Graphics g;
  private HashMap<String, Integer[]> locationsFromFile;
  private HashMap<String, Integer> locationsToPlot;
  private HashMap<String, String> abrevToName;

  /**
   * Initialises the map class.
   * 
   * @param locationsToPlot
   *            The values to be draw onto the map are passed in here.
   */
  public Map(HashMap<String, Integer> locationsToPlot) {
    LocationsReader reader = new LocationsReader();
    locationsFromFile = reader.getLocations();
    this.locationsToPlot = locationsToPlot;
    this.abrevToName = reader.getAbrevToState();
  }

  /**
   * This method draws the map with the aliens drawn on to it.
   */
  public void paint(Graphics g) {
    super.paintComponent(g);
    ImageIcon map = new ImageIcon("res/usa.png");
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
      if (locationsFromFile.keySet().contains(point)) {
        String stateAbrev = point;
        String stateName = abrevToName.get(point);
        Integer coord[] = locationsFromFile.get(point);
        Integer x, y;
        x = coord[0];
        y = coord[1];

        ImageIcon alien = new ImageIcon("res/alien.png");
        Image img = alien.getImage();
        scaleFactor = 1;
        currentFrequency = locationsToPlot.get(point);

        if (currentFrequency > Math.ceil(averageFrequency)
            && currentFrequency > 2 * Math.ceil(averageFrequency)) {
          scaleFactor = 40;
        } else if (currentFrequency > Math.ceil(averageFrequency)) {
          scaleFactor = 20;
        } else if (currentFrequency == Math.ceil(averageFrequency)) {
          scaleFactor = 0;
        } else if (currentFrequency < Math.ceil(averageFrequency)) {
          scaleFactor = 0;
        }

        Image newImg = img.getScaledInstance(20 + scaleFactor, 20 + scaleFactor, Image.SCALE_SMOOTH);
        ImageIcon alienDone = new ImageIcon(newImg);
        alienDone.paintIcon(this, g, x, y);

        JPanel alienPanel = new JPanel();
        alienPanel.setSize(30, 30);
        alienPanel.setLocation(x + 5, y + 5);
        alienPanel.setOpaque(false);
        add(alienPanel);
        alienPanel.addMouseListener(new MouseListener() {

          @Override
          public void mouseClicked(MouseEvent arg0) {
            System.out.println("Mouse clicked on: " + point);
            InfoGUI gui = new InfoGUI(stateAbrev, stateName);
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