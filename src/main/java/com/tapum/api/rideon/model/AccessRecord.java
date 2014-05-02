package com.tapum.api.rideon.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * 
 * @author Neeraj Jain
 *
 */

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class,
		property = "@id")
@JsonIgnoreProperties({ "routeDirections" })
public class AccessRecord {

	private long id;
	private Date date;
	private String action;

	private StationInfo station;

	private Device device;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccessRecord [id=");
		builder.append(id);
		builder.append(", date=");
		builder.append(date);
		builder.append(", action=");
		builder.append(action);
		builder.append(", station=");
		builder.append(station);
		builder.append(", device=");
		builder.append(device);
		builder.append("]");
		return builder.toString();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public StationInfo getStation() {
		return station;
	}

	public void setStation(StationInfo station) {
		this.station = station;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

}
