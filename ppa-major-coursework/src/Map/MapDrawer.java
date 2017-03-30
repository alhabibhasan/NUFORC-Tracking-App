package Map;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import GUI.InfoGUI;
/**
 * This class creates a map with the aliens plotted in the appropriate locations. It is of type JPanel
 * so it 'returns' the map in the form of a panel.
 * @author Muhammed Hasan, Jaman Salique
 *
 */
public class MapDrawer extends JPanel {

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
  public MapDrawer(HashMap<String, Integer> locationsToPlot) {
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
    map.paintIcon(this, g, 0, 0); // first add the map of the US

    this.g = g;
    setAliens();
  }

  private void setAliens() {
    double averageFrequency = 0;
    for (String point : locationsToPlot.keySet()) {
      averageFrequency += locationsToPlot.get(point);
    }

    averageFrequency = averageFrequency / locationsToPlot.size(); // finding the average


    int currentFrequency;
    int scaleFactor;
    for (String point : locationsToPlot.keySet()) {
      if (locationsFromFile.keySet().contains(point)) { // only adding US states
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

        // determining the size of the head of the alien
        if (currentFrequency > Math.ceil(averageFrequency)
            && currentFrequency > 2 * Math.ceil(averageFrequency)) { // if the frequency if twice the average
          scaleFactor = 40;
        } else if (currentFrequency > Math.ceil(averageFrequency)) { // greater than average
          scaleFactor = 20;
        } else if (currentFrequency == Math.ceil(averageFrequency)) { // equal to average
          scaleFactor = 0;
        } else if (currentFrequency < Math.ceil(averageFrequency)) { // less than average
          scaleFactor = 0;
        }

        // scaling the image by adding our scale factor
        Image newImg = img.getScaledInstance(20 + scaleFactor, 20 + scaleFactor, Image.SCALE_SMOOTH); 
        ImageIcon alienDone = new ImageIcon(newImg);
        alienDone.paintIcon(this, g, x, y);

        // below we are adding a panel over each alien image allowing us to register a click event on each alien
        // if a click is registered on the alien, then we will load up the infoGUI
        JPanel alienPanel = new JPanel(); 
        alienPanel.setSize(30, 30);
        alienPanel.setLocation(x + 5, y + 5); 
        alienPanel.setOpaque(false); // set the panel to be invisible
        add(alienPanel); // adding the panel to the overall panel
        alienPanel.addMouseListener(new MouseListener() {

          @Override
          public void mouseClicked(MouseEvent arg0) {
            System.out.println("Mouse clicked on: " + point);
            InfoGUI gui = new InfoGUI(stateAbrev, stateName); //loading up the infoGUI
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