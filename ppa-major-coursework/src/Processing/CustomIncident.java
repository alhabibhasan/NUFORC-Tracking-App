package Processing;

import api.ripley.Incident;
/**
 * This is a custom version of the Incidents class from the Ripley API. This class is used within this
 * solution to recreate instances of objects. It is primarily used to cache data as we need to be able to
 * reconstruct incident after they have been stored. Also used to format some attributes of incident e.g.
 * duration.
 * @author k1630580
 *
 */
public class CustomIncident {
	private String city;
	private String dateAndTime;
	private String duration;
	private String incidentID;
	private String posted;
	private String shape;
	private String state;
	private String summary;

	private CustomIncident() {

	}
	/**
	 * 
	 * @param incident the incident to reconstruct
	 * @param duration The new duration to give it
	 */
	public CustomIncident(CustomIncident incident, String duration) {
	    this.city = incident.getCity();
	    this.dateAndTime = incident.getDateAndTime();
	    this.duration = duration;
	    this.incidentID = incident.getIncidentID();
	    this.posted = incident.getPosted();
	    this.shape = incident.getShape();
	    this.summary = incident.getSummary();
	    this.state = incident.getState();
	  }

	/**
	 * 
	 * @param incident The incident to recreate
	 */
	public CustomIncident(Incident incident) {
		this.city = incident.getCity();
		this.dateAndTime = incident.getDateAndTime();
		this.duration = incident.getDuration();
		this.incidentID = incident.getIncidentID();
		this.posted = incident.getPosted();
		this.shape = incident.getShape();
		this.summary = incident.getSummary();
		this.state = incident.getState();
	}

	/**
	 * 
	 * @return State in which the incident occurred
	 */
	public String getState() {
		return state;
	}
	/**
	 * 
	 * @return City in which the incident occurred in
	 */
	public String getCity() {
		return city;
	}
	/**
	 * 
	 * @return Date and time of the incident
	 */
	public String getDateAndTime() {
		return dateAndTime;
	}
	/**
	 * 
	 * @return Duration of the incident
	 */
	public String getDuration() {
		return duration;
	}
	/**
	 * 
	 * @return Incident ID
	 */
	public String getIncidentID() {
		return incidentID;
	}
	/**
	 * 
	 * @return Date posted on the NUFORC database
	 */
	public String getPosted() {
		return posted;
	}
	/**
	 * 
	 * @return Shape of the UFO spotted
	 */
	public String getShape() {
		return shape;
	}
	/**
	 * 
	 * @return Summary of the incident
	 */
	public String getSummary() {
		return summary;
	}
	
	
	@Override
	/**
	 * Returns a string representation of the incident
	 */
	public String toString() {
		return "CustomIncident [city=" + city + ", dateAndTime=" + dateAndTime + ", duration=" + duration
				+ ", incidentID=" + incidentID + ", posted=" + posted + ", shape=" + shape + ", state=" + state
				+ ", summary=" + summary + "]";
	}

}
