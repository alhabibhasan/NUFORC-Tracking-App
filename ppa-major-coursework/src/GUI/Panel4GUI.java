package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;

import Aliens.Aliens;
import controllers.sliderListener;
import music.Music;
/**
 * This class creates the view for the animation.
 * @author Jaman
 *
 */

public class Panel4GUI extends JFrame implements Observer{
	
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
		martinBuffered = ImageIO.read(new File("res/astronaut.png"));
		steffenBuffered = ImageIO.read(new File("res/asadImage.png"));
		asadBuffered = ImageIO.read(new File("res/martinUFO.png"));
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
	            ImageIcon space = new ImageIcon("res/space3.png");
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
	            ImageIcon space = new ImageIcon("res/space3.png");
	            space.paintIcon(this, g, 0, 0);
		
	        }	
		};
		music = new Music();
		
	}
	
	/**
	 * This method is where we actually create the gui. In this method we add
	 * all the components to frame and JPanel's
	 */
	public void CreateGUI() {
		aliens.addObserver(this); //This class extends Observer and we want it to observer the aliens class
		setLayout(new BorderLayout());
		setSize((int)width,(int)height);
		add(centerPanel,BorderLayout.CENTER);
		changeSpeed.setForeground(Color.WHITE);
		changeSpeed.setFont (changeSpeed.getFont ().deriveFont (20.0f));
		southPanel.add(startAnimation); southPanel.add(addMartin); southPanel.add(addAsad); 
		southPanel.add(addSteffen); southPanel.add(clear); southPanel.add(speedSlider); 
		southPanel.add(changeSpeed);
		addMartin.setEnabled(false);
		addSteffen.setEnabled(false);
		addAsad.setEnabled(false);
		clear.setEnabled(false);
		add(southPanel, BorderLayout.SOUTH);
		centerPanel.add(asadImage);
		centerPanel.add(steffenImage);
		centerPanel.add(martinImage);
		martinImage.setVisible(false);
		asadImage.setVisible(false);
		steffenImage.setVisible(false);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		speedSlider.addChangeListener(sliderListener);
		
		/*
		 * The Timer class allows the animations in the frame to be smooth and look like 
		 * an actual animation
		 */
		Timer t = new Timer(1000 / aliens.getNbOfImagesPerSecond(), new ActionListener() {
	
            @Override
            public void actionPerformed(ActionEvent e) {
            	//calling the move methods for each image and setting their x and y coordinates appropriately 
               martinImage.setLocation((int) aliens.moveMartinX(), (int) aliens.moveMartinY());
               steffenImage.setLocation((int) aliens.moveSteffenX(), (int) aliens.moveSteffenY());
               asadImage.setLocation((int) aliens.moveAsadX(), (int) aliens.moveAsadY());
               
            }
        });
		
		//When the add martin button is pressed the image of martin will appear on the screen
		addMartin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				martinImage.setVisible(true);
				clear.setEnabled(true);
				
			}
		});
		//When the add steffen button is pressed the image of steffen will appear on the screen
		addSteffen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				steffenImage.setVisible(true);
				clear.setEnabled(true);
			}
		});
		//When the add asad button is pressed the image of asad will appear on the screen
		addAsad.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		asadImage.setVisible(true);
		clear.setEnabled(true);
	}
		});
		
		/*
		 * When the clear button is pressed the screen is paused for a second and
		 * the images will disappear and a pop sound will be made.
		 * The speed of the images and slider value will be reset to 1.
		 */
		clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				music.playPop();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				asadImage.setVisible(false);
				martinImage.setVisible(false);
				steffenImage.setVisible(false);
				speedSlider.setValue(1);
				aliens.setSpeed(1);
				clear.setEnabled(false);
			}
				});
		
		/*
		 * When this button is pressed the animation will start. The background music will start to 
		 * play and the image of martin will appear. Also the add button will be set to enabled
		 * so you can now press them, the start animation button will be setEnabled false as you do not
		 * need to press it again once the button has been pressed once.
		 */
		startAnimation.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				martinImage.setVisible(true);
				addMartin.setEnabled(true);
				addSteffen.setEnabled(true);
				addAsad.setEnabled(true);
				clear.setEnabled(true);
				startAnimation.setEnabled(false);
				music.play();
				t.start();
				
			}
				});
        
		setVisible(true);
		
	}
	//main
	public static void main(String[] args) throws IOException {
		
		Panel4GUI gui = new Panel4GUI();
		gui.CreateGUI();
	}


	/**
	 * This is the update method which checks if the model is updated by the slider listener if 
	 * it is then the view is updated.
	 * @param o
	 * @param arg
	 */
	@Override
	public void update(Observable o, Object arg) {
		aliens.getSpeed();
		
	}
	
	
	

}
