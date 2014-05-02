package com.tapum.api.rideon.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * 
 * @author Neeraj Jain
 * 
 */

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class,
		property = "@id")
public strictfp class StationInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private String abbreviation;

	private RouteDirection routeDirection;

	private double latitude;
	private double longitude;
	private String address;
	private String city;
	private String county;
	private String state;
	private String zipcode;
	private String country;
	private String agency;

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

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	// public String getColorName() {
	// return colorName;
	// }
	//
	// public void setColorName(String colorName) {
	// this.colorName = colorName;
	// }
	//
	// public String getHexColor() {
	// return hexColor;
	// }
	//
	// public void setHexColor(String hexColor) {
	// this.hexColor = hexColor;
	// }

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((abbreviation == null) ? 0 : abbreviation.hashCode());
		result = prime * result + ((agency == null) ? 0 : agency.hashCode());
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
		StationInfo other = (StationInfo) obj;
		if (abbreviation == null) {
			if (other.abbreviation != null)
				return false;
		} else if (!abbreviation.equals(other.abbreviation))
			return false;
		if (agency == null) {
			if (other.agency != null)
				return false;
		} else if (!agency.equals(other.agency))
			return false;
		return true;
	}

	// public Collection<RouteDirection> getRouteDirections() {
	// return routeDirections;
	// }
	//
	// public void setRouteDirections(Collection<RouteDirection>
	// routeDirections) {
	// this.routeDirections = routeDirections;
	// }

	public RouteDirection getRouteDirection() {
		return routeDirection;
	}

	public void setRouteDirection(RouteDirection routeDirection) {
		this.routeDirection = routeDirection;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StationInfo [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", abbreviation=");
		builder.append(abbreviation);
		builder.append(", routeDirection=");
		builder.append(routeDirection.getId());
		builder.append(", latitude=");
		builder.append(latitude);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append(", address=");
		builder.append(address);
		builder.append(", city=");
		builder.append(city);
		builder.append(", county=");
		builder.append(county);
		builder.append(", state=");
		builder.append(state);
		builder.append(", zipcode=");
		builder.append(zipcode);
		builder.append(", country=");
		builder.append(country);
		builder.append(", agency=");
		builder.append(agency);
		builder.append("]");
		return builder.toString();
	}

	public String getDisplayName() {
		// System.out.println("name=" + name);
		// System.out.println("routeDirection=" + routeDirection);
		// if (routeDirection != null)
		// System.out.println(routeDirection.getRoute());
		if ((name == null || name.equals("null")) && routeDirection != null
				&& routeDirection.getRoute() != null) {
			// System.out.println(routeDirection.getRoute().getName());
			return routeDirection.getRoute().getCode() + "-"
					+ routeDirection.getDirectionCode();
		}
		// System.out.println("returning empty");
		return name;
	}

}
