package music;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Music {

	private Clip clip;
	
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


	public void stopSoundTrack()
	{
		clip.close();
		clip.stop();
	}
}
