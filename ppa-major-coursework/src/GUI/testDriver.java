package GUI;

public class testDriver {

	/**
	 * Certain method etc. need to be moved to a separate class and package,
	 * they are placed here only for testing.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		long time1 = System.currentTimeMillis();
		GUI gui = new GUI();
		gui.createGUI();

		long time2 = System.currentTimeMillis();

		System.out.println("Time taken to create GUI: " + (time2 - time1) / 1000 + " seconds.");

	}

}
