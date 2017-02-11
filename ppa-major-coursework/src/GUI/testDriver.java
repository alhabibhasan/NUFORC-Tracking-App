package GUI;
import api.ripley.*;
public class testDriver {

	public static void main(String[] args) {
		GUI gui = new GUI();
		Ripley test = new Ripley("10tLI3CRs9qyVD6ql2OMtA==", "tBgm4pRv9wrVqL46EnH7ew==");
		gui.createGUI();
		gui.setWelcomeText("<html>Welcome to the Ripley API v" + test.getVersion() + 
				" Please select from the dates above, in order to begin analysing UFO sighting data. </html>");
				
		gui.setLastUpdate(test.getLastUpdated());
	}
}
