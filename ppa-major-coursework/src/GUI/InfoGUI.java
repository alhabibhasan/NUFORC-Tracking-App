package GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Data.Process;
import api.ripley.Incident;
import api.ripley.Ripley;

public class InfoGUI extends JFrame {

	private JFrame frame;
	private JPanel north, center;
	private JComboBox<String> comboBox;
	private JScrollPane pane;
	private JList list;
	private static String[] stringArr;
	private static Incident[] incidentArr;
	private String stateAbrev, stateName;

	public InfoGUI(String stateAbrev, String stateName) {
		this.stateAbrev = stateAbrev;
		this.stateName = stateName;

		frame = new JFrame();
		north = new JPanel();
		center = new JPanel();
		comboBox = new JComboBox<String>();

	}

	public void createGUI() {

		frame.setLayout(new BorderLayout());
		frame.setTitle(stateName + " " + "(" + stateAbrev + ")");
		frame.getContentPane().add(center, BorderLayout.CENTER);
		frame.getContentPane().add(north, BorderLayout.NORTH);
		north.add(comboBox);

		addComboBoxElements();

		frame.setVisible(true);

		setupList();
		frame.pack();
	}

	private void addComboBoxElements() {
		String Date = "Date";
		String City = "City";
		String Shape = "Shape";
		String Duration = "Duration";
		String Posted = "Posted";

		comboBox.addItem(Date);
		comboBox.addItem(City);
		comboBox.addItem(Shape);
		comboBox.addItem(Duration);
		comboBox.addItem(Posted);

	}

	public void setupList() {

		ArrayList<Incident> allIncids = Process.sortListForState(Process.getAllIncidents(), stateAbrev);
		incidentArr = new Incident[allIncids.size()];
		stringArr = new String[allIncids.size()];
		allIncids.toArray(incidentArr);
		for (int i = 0; i <= incidentArr.length - 1; i++) {
			stringArr[i] = incidentArr[i].getDateAndTime() + " " + incidentArr[i].getCity() + " "
					+ incidentArr[i].getDuration() + " " + incidentArr[i].getPosted();
		}
		list = new JList(stringArr);
		list.setVisibleRowCount(20);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				JOptionPane.showMessageDialog(new JFrame(), incidentArr[list.getSelectedIndex()].getSummary());
			}
		}

		);
		center.add(new JScrollPane(list));
	}

}
