package com.tapum.rideon.util;

/**
 * 
 * @author Neeraj Jain
 * 
 */

public class AppConstants {
	public static String baseApiUrl = AppProperties.get().getProperty(
			"rideon_apiurl");
	public static String apikey = AppProperties.get().getProperty(
			"rideon_apikey");
	public static String WHEELS_URL = AppProperties.get().getProperty(
			"rideon_wheels_url");

	public static String VIEWSTATE_17 = AppProperties.get().getProperty(
			"VIEWSTATE_17");
	public static String VIEWSTATE_15 = AppProperties.get().getProperty(
			"VIEWSTATE_15");
	public static String VIEWSTATE_136 = AppProperties.get().getProperty(
			"VIEWSTATE_136");
	public static String VIEWSTATE_137 = AppProperties.get().getProperty(
			"VIEWSTATE_137");
	public static String VIEWSTATE_109 = AppProperties.get().getProperty(
			"VIEWSTATE_109");

	public static String EVENTVALIDATION_17 = AppProperties.get().getProperty(
			"EVENTVALIDATION_17");
	public static String EVENTVALIDATION_15 = AppProperties.get().getProperty(
			"EVENTVALIDATION_15");
	public static String EVENTVALIDATION_136 = AppProperties.get().getProperty(
			"EVENTVALIDATION_136");
	public static String EVENTVALIDATION_137 = AppProperties.get().getProperty(
			"EVENTVALIDATION_137");
	public static String EVENTVALIDATION_109 = AppProperties.get().getProperty(
			"EVENTVALIDATION_109");

	public static String STATION_INFO_URL = baseApiUrl + "api/station/";
	public static String GTFS_STATION_INFO_URL = baseApiUrl
			+ "api/station/gtfs/";
	public static String ARRIVAL_INFO_URL = baseApiUrl + "api/arrival/";
	public static String GTFS_ARRIVAL_INFO_URL = baseApiUrl
			+ "api/arrival/gtfs/";

	public static String ROUTE_INFO_URL = baseApiUrl
			+ "api/route_direction/?apikey=" + apikey;
	public static String GTFS_ROUTE_INFO_URL = baseApiUrl
			+ "api/route_direction/gtfs/?apikey=" + apikey;

	public static String PHONE_NUMBER = "phoneNumber";
	// public static String DEVICE_ID = "device_id";
	// public static String SOFTWARE_VERSION = "software_version";
	//
	//
	// public static String ROUTE_ID = "route_id";
	// public static String DIRECTION_ID = "direction_id";
	// public static String SERVICE_ID = "service_id";

	public static String STOP_ID = "stopId";
	public static String PHONE_NUMBER_CLIENT_ID = "clientId";
	public static String DEVICE_ID = "clientId";
	public static String SOFTWARE_VERSION = "clientId";

	public static String ROUTE = "route";
	public static String ROUTE_ID = "routeId";
	public static String DIRECTION_ID = "directionId";
	public static String SERVICE_ID = "serviceId";
	public static String AGENCY_NAME = "agency";

}
