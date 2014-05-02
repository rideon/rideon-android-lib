package com.tapum.api.rideon.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

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
@JsonIgnoreProperties({ "stations" })
public class RouteDirection implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;

	// @Transient
	private Route route;

	private Collection<StationInfo> stations;

	private String directionCode;

	private String directionName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDirectionCode() {
		return directionCode;
	}

	public void setDirectionCode(String directionCode) {
		this.directionCode = directionCode;
	}

	public String getDirectionName() {
		return directionName;
	}

	public void setDirectionName(String directionName) {
		this.directionName = directionName;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RouteDirection [id=");
		builder.append(id);
		builder.append(", route=");
		builder.append(route.getId());
		builder.append(", directionCode=");
		builder.append(directionCode);
		builder.append(", directionName=");
		builder.append(directionName);
		builder.append("]");
		return builder.toString();
	}

	public Collection<StationInfo> getStations() {
		if (stations == null)
			stations = new ArrayList<StationInfo>();
		return stations;
	}

	public void setStations(Collection<StationInfo> stations) {
		this.stations = stations;
	}

}
