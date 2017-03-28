package music;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Music {

	
	
	public void play() {
		
		File sound = new File("res/music.wav");
		
		try{
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(sound));
			clip.loop(100000000);
			clip.start();
			
			Thread.sleep(clip.getLongFramePosition() /1000);
			
		}catch(Exception e){
			
		}
		
		
	}
	
public void playPop() {
		
		File sound = new File("res/pop.wav");
		
		try{
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(sound));
			clip.start();
			
			Thread.sleep(clip.getLongFramePosition() /1000);
			
		}catch(Exception e){
			
		}
		
		
	}
	
	
}
