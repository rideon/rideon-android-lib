package com.tapum.api.rideon.model;

import java.util.List;

/**
 * 
 * @author Neeraj Jain
 * 
 */

public class EtdInfo {

	public StationInfo getStationInfo() {
		return departureStationInfo;
	}

	public void setStationInfo(StationInfo stationInfo) {
		this.departureStationInfo = stationInfo;
	}

	private StationInfo departureStationInfo;
	private List<BartDepartureEstimate> estimates;

	public List<BartDepartureEstimate> getEstimates() {
		return estimates;
	}

	public void setEstimates(List<BartDepartureEstimate> estimates) {
		this.estimates = estimates;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EtdInfo [stationInfo=");
		builder.append(departureStationInfo);
		builder.append(", estimates=");
		builder.append(estimates);
		builder.append("]");
		return builder.toString();
	}
}
