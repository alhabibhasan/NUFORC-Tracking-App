package Statistics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Video extends JPanel {
	private String thumbnail;

	public Video(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		URL url;
		try {
			url = new URL(thumbnail);
			URLConnection conn = url.openConnection();
			InputStream in = conn.getInputStream();
			BufferedImage image = ImageIO.read(url); 
			ImageIcon backImage = new ImageIcon(image.getScaledInstance(100, 100, image.SCALE_SMOOTH));
			backImage.paintIcon(this, g, 0, 0);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}