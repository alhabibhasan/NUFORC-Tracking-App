package Data;

import api.ripley.Incident;

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

	public String getState() {
		return state;
	}
	
	public String getCity() {
		return city;
	}

	public String getDateAndTime() {
		return dateAndTime;
	}

	public String getDuration() {
		return duration;
	}

	public String getIncidentID() {
		return incidentID;
	}

	public String getPosted() {
		return posted;
	}

	public String getShape() {
		return shape;
	}

	public String getSummary() {
		return summary;
	}

	@Override
	public String toString() {
		return "CustomIncident [city=" + city + ", dateAndTime=" + dateAndTime + ", duration=" + duration
				+ ", incidentID=" + incidentID + ", posted=" + posted + ", shape=" + shape + ", state=" + state
				+ ", summary=" + summary + "]";
	}

}
