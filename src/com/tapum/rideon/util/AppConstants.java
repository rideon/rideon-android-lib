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
