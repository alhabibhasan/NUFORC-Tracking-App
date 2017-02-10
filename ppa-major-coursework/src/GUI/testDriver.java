package GUI;
import api.ripley.*;
public class testDriver {

	public static void main(String[] args) {
		GUI gui = new GUI();
		Ripley test = new Ripley("10tLI3CRs9qyVD6ql2OMtA==", "tBgm4pRv9wrVqL46EnH7ew==");
		gui.createGUI();
		
		gui.setLastUpdate(test.getLastUpdated());
	}
}
