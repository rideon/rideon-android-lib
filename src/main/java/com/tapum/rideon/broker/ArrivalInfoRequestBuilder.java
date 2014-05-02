package com.tapum.rideon.broker;

import static com.tapum.api.rideon.model.ScheduleProvider.GTFS;
import static com.tapum.rideon.util.AppConstants.AGENCY_NAME;
import static com.tapum.rideon.util.AppConstants.DEVICE_ID;
import static com.tapum.rideon.util.AppConstants.PHONE_NUMBER_CLIENT_ID;
import static com.tapum.rideon.util.AppConstants.SOFTWARE_VERSION;
import static com.tapum.rideon.util.AppConstants.STOP_ID;
import static com.tapum.rideon.util.AppConstants.apikey;

import com.tapum.api.rideon.model.ScheduleProvider;
import com.tapum.rideon.util.AppConstants;
import com.tapum.rideon.util.RideonUtil;

public class ArrivalInfoRequestBuilder {

	private final StringBuilder request = new StringBuilder();

	private String stopCode;
	private String phoneNumber;
	private String deviceId;
	private String softwareVersion;
	private String agency;
	private ScheduleProvider scheduleProvider;

	public ArrivalInfoRequestBuilder withScheduleProvider(
			ScheduleProvider scheduleProvider) {
		this.scheduleProvider = scheduleProvider;
		return this;
	}

	public ArrivalInfoRequestBuilder withStopCode(String stopCode) {
		this.stopCode = stopCode;
		return this;
	}

	public ArrivalInfoRequestBuilder withPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}

	public ArrivalInfoRequestBuilder withDeviceId(String deviceId) {
		this.deviceId = deviceId;
		return this;
	}

	public ArrivalInfoRequestBuilder withSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
		return this;
	}

	public ArrivalInfoRequestBuilder withAgencyName(String agency) {
		this.agency = agency;
		return this;
	}

	private void appendParam(String name, String value) {
		request.append("&").append(name).append("=").append(value);
	}

	public String build() throws Exception {
		if (scheduleProvider == GTFS) {
			request.append(AppConstants.GTFS_ARRIVAL_INFO_URL).append("/")
					.append("/?apikey=").append(apikey);
			appendParam(STOP_ID, stopCode);
		} else {
			request.append(AppConstants.ARRIVAL_INFO_URL).append("/")
					.append(stopCode).append("/?apikey=").append(apikey);
		}
		appendParam(PHONE_NUMBER_CLIENT_ID, phoneNumber);
		appendParam(DEVICE_ID, RideonUtil.getEncodedString(deviceId));
		appendParam(SOFTWARE_VERSION,
				RideonUtil.getEncodedString(softwareVersion));
		appendParam(AGENCY_NAME, RideonUtil.getEncodedString(agency));

		return request.toString();
	}
}
