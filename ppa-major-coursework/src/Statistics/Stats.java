package Statistics;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class creates the structure of the statistics panel. It doesnt generate the statistics.
 * @author Aflal Asker, Muhammed Hasan
 *
 */
public class Stats {
	private JPanel stats;
	private CardLayout card1, card2, card3, card4;
	private JPanel stat1, stat2, stat3, stat4;
	private JPanel stat1Center, stat2Center, stat3Center, stat4Center;
	private JPanel stat1Cont1, stat1Cont2, stat2Cont1, stat2Cont2, stat3Cont1, stat3Cont2, stat4Cont1, stat4Cont2;
	private JButton stat1Left, stat1Right, stat2Left, stat2Right, stat3Left, stat3Right, stat4Left, stat4Right;
	private Preferences panelPref = Preferences.userNodeForPackage(Stats.class);
	private final String PANEL1_PREF = "panel1_pref";
	private final String PANEL2_PREF = "panel2_pref";
	private final String PANEL3_PREF = "panel3_pref";
	private final String PANEL4_PREF = "panel4_pref";
	public Stats() {
		stats = new JPanel(new GridLayout(2, 2)); // give the frame a 2x2 layout

		card1 = new CardLayout();
		card2 = new CardLayout();
		card3 = new CardLayout();
		card4 = new CardLayout();

		stat1 = new JPanel(new BorderLayout());
		stat2 = new JPanel(new BorderLayout());
		stat3 = new JPanel(new BorderLayout());
		stat4 = new JPanel(new BorderLayout());

		stats.add(stat1);
		stats.add(stat2);
		stats.add(stat3);
		stats.add(stat4);

		setUpButton();

		setUpCenters();

		setUpContentPanels();

		showPreferedPanels();
		
		
	}

	private void showPreferedPanels() {
		if (panelPref.get(PANEL1_PREF, "emptyPan1").equals("panel1")) {
			card1.show(stat1Center, "stat1Pan1");
		} else if (panelPref.get(PANEL1_PREF, "emptyPan1").equals("panel2")) {
			card1.show(stat1Center, "stat1Pan2");
		} else {
			card1.show(stat1Center, "stat1Pan1");
		}
		
		if (panelPref.get(PANEL2_PREF, "emptyPan1").equals("panel1")) {
			card2.show(stat2Center, "stat2Pan1");
		} else if (panelPref.get(PANEL2_PREF, "emptyPan1").equals("panel2")) {
			card2.show(stat2Center, "stat2Pan2");
		} else {
			card2.show(stat2Center, "stat2Pan1");
		}
		
		if (panelPref.get(PANEL3_PREF, "emptyPan1").equals("panel1")) {
			card3.show(stat3Center, "stat3Pan1");
		} else if (panelPref.get(PANEL3_PREF, "emptyPan1").equals("panel2")) {
			card3.show(stat3Center, "stat3Pan2");
		} else {
			card3.show(stat3Center, "stat3Pan1");
		}

		if (panelPref.get(PANEL4_PREF, "emptyPan1").equals("panel1")) {
			card4.show(stat4Center, "stat4Pan1");
		} else if (panelPref.get(PANEL3_PREF, "emptyPan1").equals("panel2")) {
			card4.show(stat4Center, "stat4Pan2");
		} else {
			card4.show(stat4Center, "stat4Pan1");
		}
	}

	/**
	 * Returns a complete stats panel which contains the required statistics.
	 * 
	 * @return The complete statistic panel.
	 */
	public JPanel getPanel() {
		return stats;
	}

	private void setUpContentPanels() {
		AnalyseData data = new AnalyseData();
		
		stat1Cont1 = new JPanel();
		stat1Cont2 = new JPanel(new BorderLayout()); // Might cause merge
														// conflict

		stat1Cont1.add(new JLabel("<html><span style='font-size:20px'>"
						+"Likeliest State:" + "<br><br><br><br><br>"
						+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+data.likeliestState()+ "</span></html>"));
		stat1Cont2.add(new JLabel("Panel 2"));


		stat1Center.add(stat1Cont1, "stat1Pan1");
		stat1Center.add(stat1Cont2, "stat1Pan2");
		
		stat2Cont1 = new JPanel();
		stat2Cont2 = new JPanel();
		
		stat2Cont1.add(new JLabel("<html><span style='font-size:20px'>" 
				+ "Number of Hoax:" +"<br><br><br><br><br>"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ String.valueOf(data.numberOfHoax()) + "</span></html>"));

		stat2Center.add(stat2Cont1, "stat2Pan1");
		stat2Center.add(stat2Cont2, "stat2Pan2");

		stat3Cont1 = new JPanel();
		stat3Cont2 = new JPanel();

		stat3Cont1.add(new JLabel("<html><span style='font-size:20px'>" + "Number of non US sightings" +"<br><br><br><br><br>"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ String.valueOf(data.nonUSSightings())+ "</span></html>"));

		stat3Cont2.add(new BahaStatistics(), BorderLayout.CENTER);
		
		stat3Center.add(stat3Cont1, "stat3Pan1");
		stat3Center.add(stat3Cont2, "stat3Pan2");

		stat4Cont1 = new JPanel();
		stat4Cont2 = new JPanel();
		
		Search video = new Search();
		VideoContainer vidCon = new VideoContainer(video.searchYouTube().iterator());
		
		stat4Cont1.setLayout((new BorderLayout()));
		stat4Cont1.add(vidCon.getScroll());
		
		stat4Center.add(stat4Cont1, "stat4Pan1");
		stat4Center.add(stat4Cont2, "stat4Pan2");
	}

	private void setUpCenters() {
		stat1Center = new JPanel();
		stat1Center.setLayout(card1);
		stat1.add(stat1Center, BorderLayout.CENTER);

		stat2Center = new JPanel();
		stat2Center.setLayout(card2);
		stat2.add(stat2Center, BorderLayout.CENTER);

		stat3Center = new JPanel();
		stat3Center.setLayout(card3);
		stat3.add(stat3Center, BorderLayout.CENTER);

		stat4Center = new JPanel();
		stat4Center.setLayout(card4);
		stat4.add(stat4Center, BorderLayout.CENTER);
	}

	private void setUpButton() {
		// buttons for panel 1
		stat1Left = new JButton("<");
		stat1Right = new JButton(">");

		stat1Left.addActionListener(e -> {
			card1.show(stat1Center, "stat1Pan1");
			panelPref.put(PANEL1_PREF, "panel1");
		});

		stat1Right.addActionListener(e -> {
			card1.show(stat1Center, "stat1Pan2");
			panelPref.put(PANEL1_PREF, "panel2");
		});

		stat1.add(stat1Left, BorderLayout.WEST);
		stat1.add(stat1Right, BorderLayout.EAST);

		stat2Left = new JButton("<");
		stat2Right = new JButton(">");

		stat2.add(stat2Left, BorderLayout.WEST);
		stat2.add(stat2Right, BorderLayout.EAST);

		stat2Left.addActionListener(e -> {
			card2.show(stat2Center, "stat2Pan1");
			panelPref.put(PANEL2_PREF, "panel1");
		});
		stat2Right.addActionListener(e -> {
			card2.show(stat2Center, "stat2Pan2");
			panelPref.put(PANEL2_PREF, "panel2");
		});

		stat3Left = new JButton("<");
		stat3Right = new JButton(">");

		stat3.add(stat3Left, BorderLayout.WEST);
		stat3.add(stat3Right, BorderLayout.EAST);

		stat3Left.addActionListener(e -> {
			card3.show(stat3Center, "stat3Pan1");
			panelPref.put(PANEL3_PREF, "panel1");
		});
		stat3Right.addActionListener(e -> {
			card3.show(stat3Center, "stat3Pan2");
			panelPref.put(PANEL3_PREF, "panel2");
		});

		stat4Left = new JButton("<");
		stat4Right = new JButton(">");

		stat4.add(stat4Left, BorderLayout.WEST);
		stat4.add(stat4Right, BorderLayout.EAST);

		stat4Left.addActionListener(e -> {
			card4.show(stat4Center, "stat4Pan1");
			panelPref.put(PANEL4_PREF, "panel1");
		});
		stat4Right.addActionListener(e -> {
			card4.show(stat4Center, "stat4Pan2");
			panelPref.put(PANEL4_PREF, "panel2");
		});

	}
}
