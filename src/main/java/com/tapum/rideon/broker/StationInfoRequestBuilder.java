package com.tapum.rideon.broker;

import static com.tapum.api.rideon.model.ScheduleProvider.GTFS;
import static com.tapum.rideon.util.AppConstants.AGENCY_NAME;
import static com.tapum.rideon.util.AppConstants.DEVICE_ID;
import static com.tapum.rideon.util.AppConstants.DIRECTION_ID;
import static com.tapum.rideon.util.AppConstants.PHONE_NUMBER_CLIENT_ID;
import static com.tapum.rideon.util.AppConstants.ROUTE;
import static com.tapum.rideon.util.AppConstants.ROUTE_ID;
import static com.tapum.rideon.util.AppConstants.SERVICE_ID;
import static com.tapum.rideon.util.AppConstants.SOFTWARE_VERSION;
import static com.tapum.rideon.util.AppConstants.apikey;

import com.tapum.api.rideon.model.ScheduleProvider;
import com.tapum.rideon.util.AppConstants;
import com.tapum.rideon.util.RideonUtil;

public class StationInfoRequestBuilder {

	private final StringBuilder request = new StringBuilder();

	private String routeDirectionId;
	private String gtfsRouteId;
	private String gtfsDirectionId;
	private String gtfsServiceId;
	private String phoneNumber;
	private String deviceId;
	private String softwareVersion;
	private String agency;
	private ScheduleProvider scheduleProvider;

	public StationInfoRequestBuilder withScheduleProvider(
			ScheduleProvider scheduleProvider) {
		this.scheduleProvider = scheduleProvider;
		return this;
	}

	public StationInfoRequestBuilder withRouteDirectionId(
			String routeDirectionId) {
		this.routeDirectionId = routeDirectionId;
		return this;
	}

	public StationInfoRequestBuilder withRouteId(String gtfsRouteId) {
		this.gtfsRouteId = gtfsRouteId;
		return this;
	}

	public StationInfoRequestBuilder withGtfsDirectionId(String gtfsDirectionId) {
		this.gtfsDirectionId = gtfsDirectionId;
		return this;
	}

	public StationInfoRequestBuilder withGtfsServiceId(String gtfsServiceId) {
		this.gtfsServiceId = gtfsServiceId;
		return this;
	}

	public StationInfoRequestBuilder withPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}

	public StationInfoRequestBuilder withDeviceId(String deviceId) {
		this.deviceId = deviceId;
		return this;
	}

	public StationInfoRequestBuilder withSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
		return this;
	}

	public StationInfoRequestBuilder withAgencyName(String agency) {
		this.agency = agency;
		return this;
	}

	private void appendParam(String name, String value) {
		request.append("&").append(name).append("=").append(value);
	}

	public String build() throws Exception {
		if (scheduleProvider == GTFS) {
			request.append(AppConstants.GTFS_STATION_INFO_URL).append("/")
					.append("/?apikey=").append(apikey);
			if (gtfsRouteId != null) {
				appendParam(ROUTE_ID, gtfsRouteId);
				appendParam(DIRECTION_ID, gtfsDirectionId);
				appendParam(SERVICE_ID, gtfsServiceId);
			}
		} else {
			request.append(AppConstants.STATION_INFO_URL).append("/?apikey=")
					.append(apikey);
			if (routeDirectionId != null && !routeDirectionId.trim().isEmpty())
				appendParam(ROUTE, routeDirectionId);
		}
		appendParam(PHONE_NUMBER_CLIENT_ID, phoneNumber);
		appendParam(DEVICE_ID, RideonUtil.getEncodedString(deviceId));
		appendParam(SOFTWARE_VERSION,
				RideonUtil.getEncodedString(softwareVersion));
		appendParam(AGENCY_NAME, RideonUtil.getEncodedString(agency));

		return request.toString();
	}
}
