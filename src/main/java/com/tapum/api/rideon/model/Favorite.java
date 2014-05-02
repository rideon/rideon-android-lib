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
public class Favorite {

	private long id;

	private Device device;

	private StationInfo station;

	private int rank;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public StationInfo getStation() {
		return station;
	}

	public void setStation(StationInfo station) {
		this.station = station;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Favorite [id=");
		builder.append(id);
		builder.append(", device=");
		builder.append(device);
		builder.append(", station=");
		builder.append(station);
		builder.append(", rank=");
		builder.append(rank);
		builder.append("]");
		return builder.toString();
	}
}
