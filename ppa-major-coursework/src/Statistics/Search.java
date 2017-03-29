package Statistics;

import java.awt.GridLayout;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import javax.swing.JFrame;

/*
 * Copyright (c) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

/*
 * The following piece if code is based upon the sample provided by Google on how to use the YouTube API to retrieve
 * search results.
 */

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;

/**
 * Retrieve search results using the YouTube API.
 *
 * @author Jeremy Walker
 */
public class Search {

	/**
	 * Global variable which will hold the name of the file which contains the
	 * developers key.
	 */
	private static final String PROPERTIES_FILENAME = "res//youtube.properties";

	/**
	 * Define a global instance of a Youtube object, which will be used to make
	 * YouTube Data API requests.
	 */
	private static YouTube youtube;

	/**
	 * Initialize a YouTube object to search for videos on YouTube. Then display
	 * the name and thumbnail image and publishing data of each video in the
	 * result set.
	 *
	 * @param args
	 *            command line args.
	 */
	public List<SearchResult> searchYouTube() {
		// Read the developer key from the properties file.
		Properties properties = new Properties();
		List<SearchResult> searchResultList = null;
		try {
			FileInputStream in = new FileInputStream("res//api.properties");
			properties.load(in);
			in.close();

		} catch (IOException e) {
			System.err.println(
					"There was an error reading " + PROPERTIES_FILENAME + ": " + e.getCause() + " : " + e.getMessage());
			System.exit(1);
		}

		try {
			// This object is used to make YouTube Data API requests. The last
			// argument is required, but since we don't need anything
			// initialized when the HttpRequest is initialized, we override
			// the interface and provide a no-op function.
			youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
				public void initialize(HttpRequest request) throws IOException {
				}
			}).setApplicationName("Youtube-API Search").build();

			// Prompt the user to enter a query term or enter here directly the
			// term to use for the search.
			// String queryTerm = getInputQuery();

			// Define the API request for retrieving search results.
			YouTube.Search.List search = youtube.search().list("id,snippet");

			// The api key is extracted from the properties file here.
			 String apiKey = properties.getProperty("youtube.apikey");
			search.setKey(apiKey);
			search.setQ("ufo");
			search.setMaxResults((long)50);
			// search.setLocation("51.5076, 0.1278");
			// search.setLocationRadius("5km");

			// Restrict the search results to only include videos.
			search.setType("video");

			// Retrieving the fields we want from the JSON structure for each
			// search result
			search.setFields(
					"items(id/kind,id/videoId,snippet/title,snippet/publishedAt,snippet/thumbnails/default/url)");
			

			// Call the API to execute the search and print results.
			SearchListResponse searchResponse = search.execute();
			searchResultList = searchResponse.getItems();
			if (searchResultList != null && searchResultList.size() > 1) {
				//prettyPrint(searchResultList.iterator());
			} else {
				System.out.println("The search returned no results.");
			}
		} catch (GoogleJsonResponseException e) {
			System.err.println(
					"There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());
		} catch (IOException e) {
			System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return searchResultList;
	}

	/*
	 * Prints out all results in the Iterator. For each result, print the title,
	 * video ID, and thumbnail.
	 *
	 * @param iteratorSearchResults Iterator of SearchResults to print
	 *
	 * @param query Search query (String)
	 */
	private static void prettyPrint(Iterator<SearchResult> iteratorSearchResults) {

		System.out.println("\n=============================================================");
		System.out.println("   All videos found on YouTube about UFOs                    \n");
		System.out.println("=============================================================\n");
		while (iteratorSearchResults.hasNext()) {

			SearchResult singleVideo = iteratorSearchResults.next();
			ResourceId rId = singleVideo.getId();

			// Confirm that the result represents a video. Otherwise, the
			// item will not contain a video ID.
			if (rId.getKind().equals("youtube#video")) {
				Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();
				DateTime publishedTime = singleVideo.getSnippet().getPublishedAt();
				System.out.println(" Video Id " + rId.getVideoId());
				System.out.println(" Title: " + singleVideo.getSnippet().getTitle());
				System.out.println(" Thumbnail: " + thumbnail.getUrl());
				System.out.println(" Date Published: " + publishedTime);
				System.out.println("\n-------------------------------------------------------------\n");
			}
		}
	}
}