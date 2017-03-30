package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import Data.CustomIncident;
import controllers.InfoGUIListener;

public class InfoGUI extends JFrame implements Observer {

  private JFrame frame;
  private JComboBox<String> comboBox;
  private JScrollPane pane;
  private JList<String> list;
  private String stateAbrev, stateName;
  private DefaultListModel<String> listModel;
  private InfoGUIListener controller;

  /**
   * Creates a JFrame which display the relevant information
   * 
   * @param stateAbrev
   *            The abbreviation of the state about which info. should be
   *            displayed.
   * @param stateName
   *            The name of the state in question.
   */
  public InfoGUI(String stateAbrev, String stateName) {
    this.stateAbrev = stateAbrev;
    this.stateName = stateName;
    this.listModel = new DefaultListModel<String>();
    this.frame = new JFrame();
    this.comboBox = new JComboBox<String>();
    this.controller = new InfoGUIListener(listModel, comboBox, this);

    this.list = new JList<String>(listModel);
    this.pane = new JScrollPane(list);

    list.addListSelectionListener(e -> {
      if (!list.isSelectionEmpty()) {
      JOptionPane.showMessageDialog(new JFrame(), controller.getSummaryFor(list.getSelectedIndex()),
          "Summary of incident", JOptionPane.INFORMATION_MESSAGE);
      list.clearSelection();
      }
    });

    this.controller.getDefaultData(stateAbrev);

    this.createGUI();
  }

  private void createGUI() {

    frame.setLayout(new BorderLayout());
    frame.setTitle(stateName + " " + "(" + stateAbrev + ")");

    frame.add(comboBox, BorderLayout.NORTH);
    frame.add(pane, BorderLayout.CENTER);

    addComboBoxElements();
    frame.setVisible(true);
    frame.pack();
  }

  private void addComboBoxElements() {

    comboBox.addItem("-");
    comboBox.addItem("Date");
    comboBox.addItem("City");
    comboBox.addItem("Shape");
    comboBox.addItem("Duration");
    comboBox.addItem("Posted");

    comboBox.addActionListener(e -> {
      
      if (((String) comboBox.getSelectedItem()).equals("-")) {
        controller.getDefaultData(stateAbrev);
      }
      if (((String) comboBox.getSelectedItem()).equals("Duration")) {
        controller.getDurationSortedData();
      }
      if (((String) comboBox.getSelectedItem()).equals("Posted")) {
        controller.getPostedSortedData();
      }
      if (((String) comboBox.getSelectedItem()).equals("City")) {
        controller.getCitySortedData();
      }
      if (((String) comboBox.getSelectedItem()).equals("Date")) {
        controller.getDateTimeSortedData();
      }
      if (((String) comboBox.getSelectedItem()).equals("Shape")) {
        controller.getShapeSortedData();
      }
      
      
    });

  }

  /**
   * This method updates the default list model
   */
  @Override
  public void update(Observable o, Object arg) {
    listModel = controller.getListModel();
  }

}