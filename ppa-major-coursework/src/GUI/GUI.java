package GUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

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
import Map.MapDrawer;
import Statistics.Stats;
import controllers.GUIMainController;
import controllers.LeftButtonListener;
import controllers.Panel4WindowListener;
import controllers.RightButtonListener;

/**
 * This class will be used to construct the GUI for the program.
 * 
 * @author Muhammed Hasan, Aflal Asker
 *
 */
public class GUI implements Observer {
	private JFrame frame; // the main frame
	private JPanel north, contentPanel, initCenter, south, mapCenter,
			statsCenter;
	private JComboBox<Integer> dateFrom, dateTo;
	private JLabel lastUpdate, welcomeText, acknowledgement, gettingData,
			timeTaken, selectedDates;
	private JButton buttonLeft, buttonRight;

	private Font font = new Font(null, 0, 15);

	private String currentScreen = "";
	private CardLayout cardLayout = new CardLayout();
	private Panel4GUI panel4gui;
	private GUIMainController controller;

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
		selectedDates = new JLabel();
		
		try {
			panel4gui = new Panel4GUI();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		panel4gui.addWindowListener(new Panel4WindowListener(this));

		completeDataLoad();

		buttonLeft = new JButton("<");
		buttonRight = new JButton(">");

		buttonLeft.addActionListener(new LeftButtonListener(this));

		buttonRight.addActionListener(new RightButtonListener(this));
		
		

		addComboBoxElements();
		controller = new GUIMainController(dateFrom,dateTo, this);
		dateFrom.addActionListener(controller);
		dateTo.addActionListener(controller);
	}

	/**
	 * Clears the map panel so we can remove any out-dated maps
	 */
	public void clearMapCenter() {
		mapCenter.removeAll();
	}

	/**
	 * Change the card you are currently viewing
	 * 
	 * @param changeTo
	 *            The card layout to change to.
	 */
	public void setCardLayout(String changeTo) {
		if (changeTo.length() > 0) {
			cardLayout.show(contentPanel, changeTo);
		}
	}

	/**
	 * Set the value of a variable which holds the current screen being viewed
	 * 
	 * @param currentScreen
	 *            The screen being viewed
	 */
	public void setCurrentScreen(String currentScreen) {
		if (currentScreen.length() > 0) {
			this.currentScreen = currentScreen;
		}
	}

	/**
	 * 
	 * @return The string holding the current screen being viewed
	 */
	public String getCurrentScreen() {
		return this.currentScreen;
	}

	private void createStatCenter() {
		Stats stats = new Stats();
		statsCenter = stats.getPanel();
		contentPanel.add(statsCenter, "statsScreen");

	}

	/**
	 * Update the map and stats panel with the latest data.
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Process) {
			HashMap<String, Integer> currentIncidents = (HashMap<String, Integer>) arg;
			this.createMapCenter(new MapDrawer(currentIncidents));
			Stats stats = new Stats();
			statsCenter = stats.getPanel();
			contentPanel.add(statsCenter, "statsScreen");
			System.out.println("Called update in GUI class");
			this.setTimeTaken("Data grabbed from store in: "
					+ ((Process) o).getFetchTime());
			this.rightButtonEnabled(true);
		}
	}

	private void completeDataLoad() {

		
		this.setWelcomeText("<html>Welcome to the Ripley API v"
				+ controller.getVersion()
				+ " Please select from the dates above, in order to begin analysing UFO sighting data. </html>");

		this.acknowledgement.setText(controller.getAcknowledgementString());

		this.setLastUpdate(controller.getLastUpdated());

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
		createSouth();

		frame.setVisible(true);

	}

	private void createInitCenter() {

		JPanel initCenter2 = new JPanel(new GridLayout(5, 1));
		acknowledgement.setHorizontalAlignment(SwingConstants.CENTER);
		acknowledgement.setFont(font);
		initCenter.add(acknowledgement, BorderLayout.SOUTH); // DO NOT REMOVE
																// OTHERWISE WE
																// WILL GET ZERO
																// MARKS
		initCenter.add(initCenter2, BorderLayout.CENTER);
		initCenter2.add(welcomeText);
		initCenter2.add(selectedDates);
		initCenter2.add(gettingData);
		gettingData.setVisible(false);
		initCenter2.add(timeTaken);

		initCenter.setBorder(new LineBorder(Color.BLACK, 1, false));
		welcomeText.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeText.setFont(font);
		gettingData.setHorizontalAlignment(SwingConstants.CENTER);
		gettingData.setFont(font);
		selectedDates.setHorizontalAlignment(SwingConstants.CENTER);
		selectedDates.setFont(font);
		timeTaken.setHorizontalAlignment(SwingConstants.CENTER);
		timeTaken.setFont(font);
		contentPanel.add(initCenter, "firstScreen");
	}

	private void createMapCenter(MapDrawer map) {
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

		buttonLeft.setEnabled(false);
		buttonRight.setEnabled(false);

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
		if (text.length() > 0) {
			this.lastUpdate.setText(text);
		}
	}

	/**
	 * Used to set the text property of the welcomeText label.
	 * 
	 * @param text
	 *            The text to set the welcomeText to is passed in here.
	 */
	public void setWelcomeText(String text) {
		if (text.length() > 0) {
			this.welcomeText.setText(text);
		}
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
		int earliest = controller.getStartYear();
		int latest = controller.getLatestYear();

		dateFrom.setFont(font);
		dateTo.setFont(font);
		for (int i = earliest; i <= latest; i++) {
			dateFrom.addItem(i);
			dateTo.addItem(i);
		}
	}

	/**
	 * 
	 * @return A string containing the date range selected by the user
	 */
	public String getSelectedDates() {
		return selectedDates.getText();
	}

	/**
	 * 
	 * @param text
	 *            the value to assign to the string which displays the date
	 *            range selected by the user
	 */
	public void setSelectedDates(String text) {
		if (text.length() > 0) {
			this.selectedDates.setText(text);
		}
	}

	/**
	 * 
	 * @param b
	 *            set the visibility of the string which show that data is being
	 *            fetched
	 */
	public void setGettingData(boolean b) {
		gettingData.setVisible(b);
	}

	/**
	 * 
	 * @param s
	 *            Set the string which displays the time taken to get data from
	 *            the api
	 */
	public void setTimeTaken(String s) {
		if (s.length() > 0) {
			timeTaken.setText(s);
		}
	}

	/**
	 * 
	 * @param b
	 *            set the visibility of the string which displays the time taken
	 *            to get data from the API
	 */
	public void setTimeTakenVisibility(boolean b) {
		timeTaken.setVisible(b);
	}

	/**
	 * 
	 * @param b
	 *            set the visibility of the string which displays the selected
	 *            date range
	 */
	public void setSelectedDatesVisibility(boolean b) {
		selectedDates.setVisible(b);
	}
	
	public void setVisibility(boolean b){
		frame.setVisible(b);
	}
	
	public void createPanel4(){
		panel4gui.CreateGUI();
	}
	
}
