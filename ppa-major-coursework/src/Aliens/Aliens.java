package Aliens;

	
	import java.util.Observable;
	import java.util.Random;

	/**
	 * This class represents the aliens/characters of the animation. This
	 * class holds the functionality of each character/alien.
	 * @author Jaman123 Aflal Asker
	 *
	 */
	public class Aliens extends Observable{
		
		 private static final int NB_OF_IMAGES_PER_SECOND = 50;
		    private static final int WIDTH = 800;
		    private static final int HEIGHT = 600;
		    private Random random;

		    //these 2 fields will control the direction of the martin character
		    private double dxMartin;
		    private double dyMartin;

		    //these 2 fields will hold the x and y coordinate of the martin character
		    private double xMartin = WIDTH / 2;
		    private double yMartin = HEIGHT / 2;
		
		    //these 2 fields will control the direction of the asad character
		    private double dxAsad;
		    private double dyAsad;
		    
		    //these 2 fields will hold the x and y coordinate of the asad character
		    private double xAsad = WIDTH / 2;
		    private double yAsad = HEIGHT / 2;
		    
		    //these 2 fields will control the direction of the steffen character
		    private double dxSteffen;
		    private double dySteffen;

		    //these 2 fields will hold the x and y coordinate of the steffen character
		    private double xSteffen = WIDTH / 2;
		    private double ySteffen = HEIGHT / 2;

		    private double speed,width,height;
		    
		    /**
		     * This is the constructor for this class that takes 2 double parameters.
		     * This is where the dx and dy fields are initialised. Also the width, height and random is intiailised.
		     * @param width
		     * @param height
		     */
		    
		    public Aliens(double width, double height) {
		    	this.width = width;
		    	this.height = height;
		    	random = new Random();
		    	speed = 2 * Math.PI * (0.5 + random.nextDouble()); //sets the speed to a random number
		    	
		    	//The lines underneath makes sure initially the characters are moving in different directions
		    	dxMartin = speed * (random.nextBoolean() ? 1 : -1);
		         dyMartin = speed * (random.nextBoolean() ? 1 : -1);
		         dxAsad = speed * (random.nextBoolean() ? 1 : -1);
		         dyAsad = speed * (random.nextBoolean() ? 1 : -1);
		         dxSteffen = speed * (random.nextBoolean() ? 1 : -1);
		         dySteffen = speed * (random.nextBoolean() ? 1 : -1);
		    	
		    }
		    
		    /**
		     * This method controls the movement in the x axis for the martin character.
		     * It checks the where the right and left side of the screen is if it reaches
		     * one of the ends it bounces off the edge.
		     * @return
		     */
		    public double moveMartinX() {
		    	if(dxMartin < 0){
	            	dxMartin = -speed;
	            }else if(dxMartin > 0){
	            	dxMartin = speed;
	            }
		    	xMartin += dxMartin;
	             yMartin += dyMartin;
	             if (xMartin + 500 > width) {
	                 xMartin = width - 500;
	                 dxMartin = -speed;
	             } else if (xMartin < -400) {
	                 xMartin = -400;
	                 dxMartin = speed;
	             }
	             return xMartin;
	           
		    }
		    /**
		     * This method controls the movement in the y axis for the martin character.
		     * It checks the where the top and bottom of the screen is if it reaches
		     * one of the ends it bounces off the edge.
		     * @return
		     */
		    public double moveMartinY() {
		    	if(dyMartin < 0){
	            	dyMartin = -speed;
	            }else if(dyMartin > 0){
	            	dyMartin = speed;
	            }
		    	if (yMartin + 500 > height) {
	                yMartin = height - 500;
	                dyMartin = -speed;
	            } else if (yMartin < -200) {
	                yMartin = -200;
	                dyMartin = speed;
	            }
		    	
		    	return yMartin;
		    }
		    
		    /**
		     * This method controls the movement in the x axis for the steffen character.
		     * It checks the where the right and left side of the screen is if it reaches
		     * one of the ends it bounces off the edge.
		     * @return
		     */
		    public double moveSteffenX() {
		    	
		    	if(dxSteffen < 0){
	            	dxSteffen  = -speed;
	            }else if(dxSteffen  > 0){
	            	dxSteffen  = speed;
	            }
		    	
		    	xSteffen += dxSteffen;
	            ySteffen += dySteffen;
	            if (xSteffen + 150 > width) {
	                xSteffen = width - 150;
	                dxSteffen = -speed;
	            } else if (xSteffen < 0) {
	                xSteffen = 0;
	                dxSteffen = speed;
	            }
	            return xSteffen;
	          
		    }
		    
		    /**
		     * This method controls the movement in the y axis for the steffen character.
		     * It checks the where the top and bottom of the screen is if it reaches
		     * one of the ends it bounces off the edge.
		     * @return
		     */
		    public double moveSteffenY() {
		    	
		    	if(dySteffen < 0){
	            	dySteffen = -speed;
	            }else if(dySteffen > 0){
	            	dySteffen = speed;
	            }
		    	
		    	if (ySteffen + 300 > height) {
	               ySteffen = height - 300;
	               dySteffen = -speed;
	           } else if (ySteffen < 0) {
	               ySteffen = 0;
	               dySteffen = speed;
	           }
		    	return ySteffen;
		    }
		    
		    /**
		     * This method controls the movement in the x axis for the martin character.
		     * It checks the where the right and left side of the screen is if it reaches
		     * one of the ends it bounces off the edge.
		     * @return
		     */
		    public double moveAsadX() {
		    	
		    	if(dxAsad < 0){
	            	dxAsad  = -speed;
	            }else if(dxAsad  > 0){
	            	dxAsad  = speed;
	            }
		    	
		    	xAsad += dxAsad;
	           yAsad += dyAsad;
	           if (xAsad + 300 > width) {
	               xAsad = width - 300;
	               dxAsad = -speed;
	           } else if (xAsad < 0) {
	        	   xAsad = 0;
	               dxAsad = speed;
	           }
	           return xAsad;
	         
		    }
		    
		    /**
		     * This method controls the movement in the y axis for the asad character.
		     * It checks the where the top and bottom of the screen is if it reaches
		     * one of the ends it bounces off the edge.
		     * @return
		     */
		    public double moveAsadY() {
		    	
		    	if(dyAsad < 0){
	            	dyAsad = -speed;
	            }else if(dyAsad > 0){
	            	dyAsad = speed;
	            }
		    	
		    	if (yAsad + 150 > height) {
	              yAsad = height - 150;
	              dyAsad = -speed;
	          } else if (yAsad < 0) {
	              yAsad = 0;
	              dyAsad = speed;
	          }
		    	return yAsad;
		    }
		    
		    /**
			 * @return speed
			 */
			public double getSpeed() {
				return speed;
			}

			/**
			 * @param speed the speed to set
			 */
			public void setSpeed(double speed) {
				this.speed = speed;
				setChanged();
				notifyObservers();
			}


			/**
			 * 
			 * @return NB_OF_IMAGES_PER_SECOND
			 */
			public static int getNbOfImagesPerSecond() {
				return NB_OF_IMAGES_PER_SECOND;
			}



}
