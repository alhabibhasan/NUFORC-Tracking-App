package GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import controllers.sliderListener;
import Aliens.Aliens;
import Music.Music;

public class Panel4GUI extends JFrame {
	
	//JPanel fields
		private JPanel centerPanel, southPanel, northPanel;
		//Jlabel fields
		private JLabel martinImage, steffenImage, asadImage, changeSpeed;
		//BufferedImage fields
		private BufferedImage martinBuffered, steffenBuffered, asadBuffered;
		//JButton fields
		private JButton clear, addMartin, addSteffen, addAsad,startAnimation;
		//JSlider field
		private JSlider speedSlider;
		///Alien field
		private Aliens aliens;
		//sliderListener field
		private sliderListener sliderListener;
		//Random field
		private Random random;
		//Dimension field
		private Dimension screenSize;
		//double fields
		private double width,height;
		//Music field
		private Music music;
		
		/**
		 * The constructor for the class. This is where all the fields are initialised
		 * @throws IOException
		 */
		public Panel4GUI() throws IOException {
			screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			width = screenSize.getWidth(); //assigning the width of the computer screen being used to width field
			height = screenSize.getHeight(); //assigning the height of the computer screen being used to height field
			aliens = new Aliens(width, height);
			southPanel = new JPanel();
			martinBuffered = ImageIO.read(new File("images/astronaut.png"));
			steffenBuffered = ImageIO.read(new File("images/asadImage.png"));
			asadBuffered = ImageIO.read(new File("images/martinUFO.png"));
			martinImage = new JLabel(new ImageIcon(martinBuffered));
			steffenImage = new JLabel(new ImageIcon(steffenBuffered.getScaledInstance(150,300, steffenBuffered.SCALE_FAST)));
			asadImage = new JLabel(new ImageIcon(asadBuffered.getScaledInstance(300,150, asadBuffered.SCALE_FAST)));
			changeSpeed = new JLabel("< Change Speed!");
			clear = new JButton("Clear Screen!");
			addMartin = new JButton("Add Martin!");
			addSteffen = new JButton("Add Steffen!");
			addAsad = new JButton("Add Asad!");
			startAnimation = new JButton("Start Animation!");
			speedSlider = new JSlider(1, 50, 2);
			sliderListener = new sliderListener(aliens);
			random = new Random();
			northPanel = new JPanel();
			
			/*
			 * This paintComponent method is being used to set a background image
			 * to the center JPanel
			 */
			centerPanel = new JPanel(){
		        public void paintComponent(Graphics g) {
		            super.paintComponent(g);
		            ImageIcon space = new ImageIcon("images/space3.png");
		            space.paintIcon(this, g, 0, 0);
		        }
		        
			};
			
			/*
			 * This paintComponent method is being used to set a background image
			 * to the south JPanel
			 */
			southPanel = new JPanel(){
		        public void paintComponent(Graphics g) {
		            super.paintComponent(g);
		            ImageIcon space = new ImageIcon("images/space3.png");
		            space.paintIcon(this, g, 0, 0);
			
		        }	
			};
			music = new Music();
			
		}

}
