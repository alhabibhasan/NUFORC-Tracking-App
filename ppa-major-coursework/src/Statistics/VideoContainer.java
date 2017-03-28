package Statistics;

import java.util.Iterator;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;

public class VideoContainer extends JPanel {
	private Iterator<SearchResult> iteratorSearchResults;
	private JScrollPane scroll;
	public VideoContainer(Iterator<SearchResult> results) {
		super(new GridLayout(25, 1));
		this.iteratorSearchResults = results;
		scroll = new JScrollPane(this);
		addVideos();
	}
	public JScrollPane getScroll(){
		return scroll;
	}
	public void addVideos() {
		while (iteratorSearchResults.hasNext()) {

			SearchResult singleVideo = iteratorSearchResults.next();
			ResourceId rId = singleVideo.getId();
			add(new JLabel(singleVideo.getSnippet().getTitle()));
			setFont(new Font("serif", Font.PLAIN, 30));
			rId.getVideoId();
			
			// Confirm that the result represents a video. Otherwise, the
			// item will not contain a video ID.
			if (rId.getKind().equals("youtube#video")) {
				Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();
				Video vid = new Video(thumbnail.getUrl());
				vid.addMouseListener(new MouseListener(){

					@Override
					public void mouseClicked(MouseEvent e) {
						String videoId = rId.getVideoId();
						videoId = "https://www.youtube.com/watch?v=" + videoId;
						System.out.println(videoId);
						if (Desktop.isDesktopSupported()) {
							try {
								Desktop.getDesktop().browse(new URI(videoId));
							} catch (IOException i) {
								// TODO Auto-generated catch block
								i.printStackTrace();
							} catch (URISyntaxException i) {
								// TODO Auto-generated catch block
								i.printStackTrace();
							}
						}
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
				add(vid);
				//System.out.println(" Title: " + singleVideo.getSnippet().getTitle());
			}
		}
	}
}
