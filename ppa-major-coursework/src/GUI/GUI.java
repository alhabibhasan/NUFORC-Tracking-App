package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * This class will be used to construct the GUI for the program.
 * 
 * @author Muhammed Hasan
 *
 */
public class GUI {
	JFrame frame; // the main frame
	JPanel north, center, south; // different panels for the different sections
									// of the frame.
	JComboBox<Integer> dateFrom, dateTo;
	JLabel lastUpdate, welcomeText;
	JButton buttonLeft, buttonRight;

	/**
	 * The constructor which will initialise the fields within the class.
	 */
	public GUI() {
		frame = new JFrame();
		north = new JPanel();
		center = new JPanel();
		// this centre ^ panel may be renamed and re-purposed as the initial
		// panel to be visible. We will then have separate panels for each
		// view so that when the buttons are pressed to slide through panels,
		// the visibility of each panel is changed.
		south = new JPanel();

		dateFrom = new JComboBox<Integer>();
		dateTo = new JComboBox<Integer>();

		lastUpdate = new JLabel();
		welcomeText = new JLabel();
		
		buttonLeft = new JButton("<");
		buttonRight = new JButton(">");

		buttonLeft.setEnabled(false);
		buttonRight.setEnabled(false);
	}

	/**
	 * The createGUI method will create components and define their properties.
	 */
	public void createGUI() {
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(1000, 800));
		frame.setLayout(new BorderLayout());

		north.setLayout(new BorderLayout());
		center.setLayout(new BorderLayout());
		south.setLayout(new BorderLayout());

		// add the panels to the jframe's border layout
		frame.getContentPane().add(north, BorderLayout.NORTH);
		frame.getContentPane().add(center, BorderLayout.CENTER);
		frame.getContentPane().add(south, BorderLayout.SOUTH);

		// give the centre a border
		center.setBorder(new LineBorder(Color.BLACK, 1, false));

		createNorth();

		createSouth();
		
		frame.setVisible(true);

	}

	private void createCenter() {
		
	}
	
	private void createSouth() {

		// add a separate panel to the south part of the frame, giving it a
		// border layout
		JPanel southContainer = new JPanel();
		southContainer.setLayout(new BorderLayout());

		south.add(southContainer, BorderLayout.SOUTH);

		lastUpdate.setHorizontalAlignment(SwingConstants.CENTER);
		lastUpdate.setFont(new Font(null,0, 15));
		southContainer.add(buttonLeft, BorderLayout.WEST);
		southContainer.add(buttonRight, BorderLayout.EAST);
		southContainer.add(lastUpdate, BorderLayout.CENTER);

	}

	/**
	 * This method is used to compartmentalise the GUI code. This method handles
	 * the code for the north section of the BorderLayout given to the frame
	 * field.
	 */
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
	 * This method contains code required to construct the south section of the
	 * BorderLayout.
	 */
}
