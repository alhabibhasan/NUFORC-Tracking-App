package GUI;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Random;

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
		

}
