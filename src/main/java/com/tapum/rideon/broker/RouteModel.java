package com.tapum.rideon.broker;

import java.io.Serializable;

/**
 * 
 * @author Neeraj Jain
 * 
 */

public class RouteModel implements Comparable<RouteModel>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String agency;

	private String routeCode;
	private String routeName;
	private String routeDirectionCode;
	private String routeDirectionName;
	private long routeId;
	private long routeDirectionId;
	private String serviceId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getRouteDirectionCode() {
		return routeDirectionCode;
	}

	public void setRouteDirectionCode(String routeDirectionCode) {
		this.routeDirectionCode = routeDirectionCode;
	}

	public String getRouteDirectionName() {
		return routeDirectionName;
	}

	public void setRouteDirectionName(String routeDirectionName) {
		this.routeDirectionName = routeDirectionName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RouteModel [id=");
		builder.append(id);
		builder.append(", agency=");
		builder.append(agency);
		builder.append(", routeCode=");
		builder.append(routeCode);
		builder.append(", routeName=");
		builder.append(routeName);
		builder.append(", routeDirectionCode=");
		builder.append(routeDirectionCode);
		builder.append(", routeDirectionName=");
		builder.append(routeDirectionName);
		builder.append("]");
		return builder.toString();
	}

	public int compareTo(RouteModel o) {
		int c = routeDirectionCode.compareTo(o.getRouteDirectionCode());
		if (c == 0 && this.equals(o))
			return 0;

		return 1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agency == null) ? 0 : agency.hashCode());
		result = prime * result
				+ ((routeCode == null) ? 0 : routeCode.hashCode());
		result = prime
				* result
				+ ((routeDirectionCode == null) ? 0 : routeDirectionCode
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RouteModel other = (RouteModel) obj;
		if (agency == null) {
			if (other.agency != null)
				return false;
		} else if (!agency.equals(other.agency))
			return false;
		if (routeCode == null) {
			if (other.routeCode != null)
				return false;
		} else if (!routeCode.equals(other.routeCode))
			return false;
		if (routeDirectionCode == null) {
			if (other.routeDirectionCode != null)
				return false;
		} else if (!routeDirectionCode.equals(other.routeDirectionCode))
			return false;
		return true;
	}

	public long getRouteId() {
		return routeId;
	}

	public void setRouteId(long routeId) {
		this.routeId = routeId;
	}

	public long getRouteDirectionId() {
		return routeDirectionId;
	}

	public void setRouteDirectionId(long routeDirectionId) {
		this.routeDirectionId = routeDirectionId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

}
