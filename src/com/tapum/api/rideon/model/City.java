package com.tapum.api.rideon.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * 
 * @author Neeraj Jain
 * 
 */

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class,
		property = "@id")
public class City {
	private long id;

	private String name;

	private State state;

	private String polygon;

	private String timezone;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getPolygon() {
		return polygon;
	}

	public void setPolygon(String polygon) {
		this.polygon = polygon;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("City [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", state=");
		builder.append(state);
		builder.append(", polygon=");
		builder.append(polygon);
		builder.append(", timezone=");
		builder.append(timezone);
		builder.append("]");
		return builder.toString();
	}

}
