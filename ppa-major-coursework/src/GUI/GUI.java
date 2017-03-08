package GUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Data.Process;
import Map.Map;
import Statistics.Stats;
import controllers.ComboBoxListener;
import controllers.LeftButtonListener;
import controllers.RightButtonListener;

/**
 * This class will be used to construct the GUI for the program.
 * 
 * @author Muhammed Hasan, Aflal Asker
 *
 */
public class GUI {
	private JFrame frame; // the main frame
	private JPanel north, contentPanel, initCenter, south, mapCenter, statsCenter;

	private Process data;
	private JComboBox<Integer> dateFrom, dateTo;
	private JLabel lastUpdate, welcomeText, acknowledgement, gettingData, timeTaken;
	private JButton buttonLeft, buttonRight;

	private Font font = new Font(null, 0, 15);

	private String currentScreen = "";
	private CardLayout cardLayout = new CardLayout();

	/**
	 * The constructor which will initialise the GUI components within the
	 * class.
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
		timeTaken = new JLabel();
		gettingData = new JLabel("Grabbing data...");
		
		completeDataLoad();

		buttonLeft = new JButton("<");
		buttonRight = new JButton(">");

		buttonRight.setEnabled(false);
		buttonLeft.setEnabled(false);

		buttonLeft.addActionListener(new LeftButtonListener(this));

		buttonRight.addActionListener(new RightButtonListener(this));
		
		addComboBoxElements();
		ComboBoxListener comboxListener = new ComboBoxListener(dateFrom, dateTo, this);
		dateFrom.addActionListener(comboxListener);
		dateTo.addActionListener(comboxListener);
	}

	public void clearMapCenter() {
		mapCenter.removeAll();
	}

	public void setCardLayout(String changeTo) {
		cardLayout.show(contentPanel, changeTo);
	}

	public void setCurrentScreen(String currentScreen) {
		this.currentScreen = currentScreen;
	}

	public String getCurrentScreen() {
		return this.currentScreen;
	}

	private void createStatCenter() {
		Stats stats = new Stats();
		statsCenter = stats.getPanel();
		contentPanel.add(statsCenter, "statsScreen");

	}

	/**
	 * Loads the map data into the map center within the GUI class.
	 * 
	 * @return If the load was successful, true is returned otherwise false is
	 *         returned.
	 */
	public boolean getData() {
		Process data = new Process();

		Process.getDataFromRange(dateFrom.getSelectedItem().toString(), dateTo.getSelectedItem().toString());

		System.out.println(dateFrom.getSelectedItem().toString());
		System.out.println(dateTo.getSelectedItem().toString());

		HashMap<String, Integer> dataPoints = data.getStateFrequency();

		this.createMapCenter(new Map(dataPoints));

		return true;

	}

	private void completeDataLoad() {

		data = new Process();
		this.setWelcomeText("<html>Welcome to the Ripley API v" + data.getVersion()
				+ " Please select from the dates above, in order to begin analysing UFO sighting data. </html>");

		this.acknowledgement.setText(data.getAcknowledgementString());

		this.setLastUpdate(data.getLastUpdated());
	}

	/**
	 * 
	 * @param resizeable
	 *            True to make frame resizable, false to make it non resizeable.
	 */
	public void setFrameResizeable(boolean resizeable) {
		frame.setResizable(resizeable);
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

	/**
	 * 
	 * @param setTo
	 *            Boolean value to set the combo boxes to.
	 */
	public void setComboBoxes(boolean setTo) {
		dateFrom.setEnabled(setTo);
		dateTo.setEnabled(setTo);
	}

	/**
	 * 
	 * @return Boolean value based on whether the combo boxes are enabled or
	 *         not.
	 */
	public boolean isComboBoxEnabled() {
		return dateFrom.isEnabled() && dateTo.isEnabled();
	}

	private void addComboBoxElements() {
		int earliest = data.getStartYear();
		int latest = data.getLatestYear();

		dateFrom.setFont(font);
		dateTo.setFont(font);
		for (int i = earliest; i <= latest; i++) {
			dateFrom.addItem(i);
			dateTo.addItem(i);
		}
	}

}
