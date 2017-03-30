package music;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Music {
	
	private Clip clip;
	
	/**
	 * This method takes a wav sound file and plays the sound from the file when 
	 * the method is called.
	 */
	public void play() {
		
		File sound = new File("res/music.wav");
		
		try{
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(sound));
			clip.loop(0);
			clip.start();
			
			Thread.sleep(clip.getLongFramePosition() /1000);
			
		}catch(Exception e){
			System.out.println("failed to open soundtrack");
		}
		
		
	}
	
	/**
	 * This method takes a wav sound file and plays the sound from the file when 
	 * the method is called.
	 */
public void playPop() {
		
		File sound = new File("res/pop.wav");
		
		try{
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(sound));
			clip.start();

			
			Thread.sleep(clip.getLongFramePosition() /1000);
			
		}catch(Exception e){
			System.out.println("Failed to play sound track.");
		}
		
		
	}

	/**
	 * when this method is called it stops the sound track
	 */
	public void stopSoundTrack()
	{
		clip.close();
		clip.stop();
	}
}
