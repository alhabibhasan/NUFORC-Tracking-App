package controllers;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Aliens.Aliens;
/**
 * Controller which makes the changes to the animation models.
 * @author Jaman Salique
 *
 */
public class sliderListener implements ChangeListener {

	
	private Aliens aliens;
	
	 public sliderListener(Aliens aliens) {
		this.aliens = aliens;
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
		if (source.getValueIsAdjusting()) return;
        aliens.setSpeed(source.getValue());

	}

}
