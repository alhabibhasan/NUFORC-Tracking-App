package GUI;

import Map.Map;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Data.Process;
import api.ripley.Ripley;

/**
 * This class will be used to construct the GUI for the program.
 * 
 * @author Muhammed Hasan
 *
 */
public class GUI {
	private JFrame frame; // the main frame
	private JPanel north, contentPanel, initCenter, south, mapCenter, statsCenter;

	private Ripley api;
	private JComboBox<Integer> dateFrom, dateTo;
	private JLabel lastUpdate, welcomeText, acknowledgement;
	private JButton buttonLeft, buttonRight;

	private Font font = new Font(null, 0, 15);

	private String currentScreen = "";
	private CardLayout cardLayout = new CardLayout();

	/**
	 * The constructor which will initialise the fields within the class.
	 */
	public GUI() {

		frame = new JFrame();
		north = new JPanel();
		contentPanel = new JPanel();
		initCenter = new JPanel();
		mapCenter = new JPanel();
		south = new JPanel();
		statsCenter = new JPanel();

		dateFrom = new JComboBox<Integer>();
		dateTo = new JComboBox<Integer>();

		acknowledgement = new JLabel();
		lastUpdate = new JLabel();
		welcomeText = new JLabel();

		completeAPILoad();

		buttonLeft = new JButton("<");
		buttonRight = new JButton(">");

		buttonRight.setEnabled(false);
		buttonLeft.setEnabled(false);

		buttonLeft.addActionListener(e -> {
			if (currentScreen.equals("mapScreen")) {
				currentScreen = "firstScreen";
				System.out.println(currentScreen);
				cardLayout.show(contentPanel, "firstScreen");
				mapCenter.removeAll();
			}
			if (currentScreen.equals("firstScreen")) {
				buttonLeft.setEnabled(false);
				buttonRight.setEnabled(true);

				dateFrom.setEnabled(true);
				dateTo.setEnabled(true);

				frame.setResizable(true);
			}

			if (currentScreen.equals("statsScreen")) {
				cardLayout.show(contentPanel, "mapScreen");
				currentScreen = "mapScreen";
				System.out.println(currentScreen);
			}
		});

		buttonRight.addActionListener(e -> {

			if (currentScreen.equals("mapScreen")) {
				cardLayout.show(contentPanel, "statsScreen");
				currentScreen = "statsScreen";
				System.out.println(currentScreen);
			}
			if (currentScreen.equals("firstScreen")) {
				if (getMapData()) {
					currentScreen = "mapScreen";
					System.out.println(currentScreen);
					cardLayout.show(contentPanel, "mapScreen");

					dateFrom.setEnabled(false);
					dateTo.setEnabled(false);

					buttonLeft.setEnabled(true);
					buttonRight.setEnabled(true);

					frame.setResizable(false);

				}
			}

		});
	}

	private boolean validateDateRange() {
		if (((int) dateTo.getSelectedItem() - (int) dateFrom.getSelectedItem() > 10)) {
			JOptionPane.showMessageDialog(new JFrame(),
					"The date range you have selected is too large, please select a smaller range.");
			return false;
		} else if ((int) dateTo.getSelectedItem() < (int) dateFrom.getSelectedItem()) {
			JOptionPane.showMessageDialog(new JFrame(), "The start date must be smaller than the end date.");
			return false;
		}

		return true;

	}

	private void createStatCenter() {
		statsCenter.setLayout(new GridLayout(2, 2));
		Font font = new Font(null, 0, 15);

		JPanel stat1 = new JPanel();
		stat1.setLayout(new BorderLayout());
		JPanel stat2 = new JPanel();
		stat2.setLayout(new BorderLayout());
		JPanel stat3 = new JPanel();
		stat1.setLayout(new BorderLayout());
		JPanel stat4 = new JPanel();
		stat1.setLayout(new BorderLayout());

		JButton stat1Left, stat1Right, stat2Left, stat2right, stat3Left, stat3Right, stat4Left, stat4right;

		stat1Left = new JButton("<");
		stat1Right = new JButton(">");
		stat1Left.setFont(font);
		stat1Right.setFont(font);

		stat1.add(stat1Left, BorderLayout.WEST);
		stat1.add(stat1Right, BorderLayout.EAST);

		statsCenter.add(stat1);
		statsCenter.add(stat2);
		statsCenter.add(stat3);
		statsCenter.add(stat4);

		contentPanel.add(statsCenter, "statsScreen");

	}

	private boolean getMapData() {
		Process data = new Process(api);

		if (validateDateRange()) {
			data.getData(dateFrom.getSelectedItem().toString(), dateTo.getSelectedItem().toString());
		} else {
			return false;
		}

		System.out.println(dateFrom.getSelectedItem().toString());
		System.out.println(dateTo.getSelectedItem().toString());

		HashMap<String, Integer> dataPoints = data.getStateFrequency();

		this.createMapCenter(new Map(dataPoints));

		return true;

	}

	private void completeAPILoad() {
		api = new Ripley("10tLI3CRs9qyVD6ql2OMtA==", "tBgm4pRv9wrVqL46EnH7ew==");

		this.setWelcomeText("<html>Welcome to the Ripley API v" + api.getVersion()
				+ " Please select from the dates above, in order to begin analysing UFO sighting data. </html>");

		this.acknowledgement.setText(api.getAcknowledgementString());

		this.setLastUpdate(api.getLastUpdated());
	}

	/**
	 * The createGUI method will create components and define their properties.
	 */
	public void createGUI() {
		currentScreen = "firstScreen";
		if (currentScreen.equals("firstScreen"))
			buttonLeft.setEnabled(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(1100, 770));
		frame.setLayout(new BorderLayout());
		frame.setTitle("Ripley");
		north.setLayout(new BorderLayout());
		contentPanel.setLayout(cardLayout);
		initCenter.setLayout(new BorderLayout());
		mapCenter.setLayout(new BorderLayout());
		south.setLayout(new BorderLayout());

		// add the panels to the jframe's border layout
		frame.getContentPane().add(north, BorderLayout.NORTH);
		frame.getContentPane().add(contentPanel, BorderLayout.CENTER);
		frame.getContentPane().add(south, BorderLayout.SOUTH);

		contentPanel.setLayout(cardLayout);

		createNorth();
		createInitCenter();
		createStatCenter();
		createSouth();

		addComboBoxElements();
		frame.setVisible(true);

	}

	private void createInitCenter() {
		initCenter.add(welcomeText, BorderLayout.CENTER);

		acknowledgement.setHorizontalAlignment(SwingConstants.CENTER);
		acknowledgement.setFont(font);
		initCenter.add(acknowledgement, BorderLayout.SOUTH); // DO NOT REMOVE
																// OTHERWISE WE
																// WILL GET ZERO
																// MARKS

		initCenter.setBorder(new LineBorder(Color.BLACK, 1, false));
		welcomeText.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeText.setFont(font);

		initCenter.add(welcomeText, BorderLayout.CENTER);
		contentPanel.add(initCenter, "firstScreen");
	}

	private void createMapCenter(Map map) {
		map.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println(e.getX() + ", " + e.getY());
			}
		});

		mapCenter.add(map);

		contentPanel.add(mapCenter, "mapScreen");
	}

	private void createSouth() {

		// add a separate panel to the south part of the frame, giving it a
		// border layout
		JPanel southContainer = new JPanel();
		southContainer.setLayout(new BorderLayout());

		south.add(southContainer, BorderLayout.SOUTH);

		lastUpdate.setHorizontalAlignment(SwingConstants.CENTER);
		lastUpdate.setFont(new Font(null, 0, 15));
		southContainer.add(buttonLeft, BorderLayout.WEST);
		southContainer.add(buttonRight, BorderLayout.EAST);
		southContainer.add(lastUpdate, BorderLayout.CENTER);

	}

	private void createNorth() {

		// add another panel for the combo boxes
		JPanel comboBoxContainer = new JPanel();
		comboBoxContainer.setLayout(new FlowLayout());

		// add the container to the east side of the north panel
		north.add(comboBoxContainer, BorderLayout.EAST);

		// give the combo boxes a border
		dateFrom.setBorder(new EmptyBorder(1, 1, 1, 1));
		dateTo.setBorder(new EmptyBorder(1, 1, 1, 1));

		// creating a font to be used

		Font fontToUse = new Font(null, 0, 15);

		// create the labels for the combo boxes

		JLabel from = new JLabel("From: ");
		JLabel to = new JLabel("To: ");

		from.setFont(fontToUse);
		to.setFont(fontToUse);

		// add the combo boxes and the labels to the flow layout container

		comboBoxContainer.add(from);
		comboBoxContainer.add(dateFrom);
		comboBoxContainer.add(to);
		comboBoxContainer.add(dateTo);
	}

	/**
	 * Used to set the text of the lastUpdate label to a given string.
	 * 
	 * @param text
	 *            The string value to assign as the text within the label is
	 *            passed though this parameter.
	 */
	public void setLastUpdate(String text) {
		this.lastUpdate.setText(text);
	}

	/**
	 * Used to set the text property of the welcomeText label.
	 * 
	 * @param text
	 *            The text to set the welcomeText to is passed in here.
	 */
	public void setWelcomeText(String text) {

		this.welcomeText.setText(text);
	}

	/**
	 * Used to retrieve the string stored within the welcomeText label
	 * 
	 * @return The text within the welcomeText label is returned.
	 */
	public String getWelcomeText() {
		return this.welcomeText.getText();
	}

	/**
	 * This method is used to enable or disable the left button in the south of
	 * the JFrame.
	 * 
	 * @param enabled
	 *            The boolean value to assign to the button's enabled field is
	 *            passed in here.
	 */
	public void leftButtonEnabled(boolean enabled) {
		this.buttonLeft.setEnabled(enabled);
	}

	/**
	 * This method is used to enable or disable the right button in the south of
	 * the JFrame.
	 * 
	 * @param enabled
	 *            The boolean value to assign to the button's enabled field is
	 *            passed in here.
	 */
	public void rightButtonEnabled(boolean enabled) {
		this.buttonRight.setEnabled(enabled);
	}

	private void addComboBoxElements() {
		int earliest = api.getStartYear();
		int latest = api.getLatestYear();

		dateFrom.setFont(font);
		dateTo.setFont(font);
		for (int i = earliest; i <= latest; i++) {
			dateFrom.addItem(i);
			dateTo.addItem(i);
		}
	}

}
