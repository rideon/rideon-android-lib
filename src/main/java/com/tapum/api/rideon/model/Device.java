package com.tapum.api.rideon.model;

import java.util.Collection;
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
@JsonIgnoreProperties({ "accessRecords", "favorites" })
public class Device {
	private long id;

	private String phone;
	private String deviceId;
	private String version;
	private Date dateAdded;
	private Collection<AccessRecord> accessRecords;
	private Collection<Favorite> favorites;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Device [id=");
		builder.append(id);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", deviceId=");
		builder.append(deviceId);
		builder.append(", version=");
		builder.append(version);
		builder.append(", dateAdded=");
		builder.append(dateAdded);
		// builder.append(", accessRecords=");
		// builder.append(accessRecords);
		builder.append("]");
		return builder.toString();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Collection<AccessRecord> getAccessRecords() {
		return accessRecords;
	}

	public void setAccessRecords(Collection<AccessRecord> accessRecords) {
		this.accessRecords = accessRecords;
	}

	public Collection<Favorite> getFavorites() {
		return favorites;
	}

	public void setFavorites(Collection<Favorite> favorites) {
		this.favorites = favorites;
	}

}
