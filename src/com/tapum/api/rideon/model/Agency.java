package com.tapum.api.rideon.model;

import static com.tapum.api.rideon.model.ScheduleProvider.MY_511;
import static com.tapum.api.rideon.model.ScheduleProvider.GTFS;

public enum Agency {
	AC_TRANSIT("AC Transit", MY_511), BART("BART", MY_511), CALTRAIN(
			"Caltrain", MY_511), DUMBARTON_EXPRESS("Dumbarton Express", MY_511), SAMTRANS(
			"SamTrans", MY_511), SF_MUNI("SF-MUNI", MY_511), VTA("VTA", MY_511), WESTCAT(
			"WESTCAT", MY_511), CALTRAIN_SCHEDULE("caltrain-ca-us", GTFS), VTA_SCHEDULE(
			"VTA-Schedule", GTFS), LA_METROLINK("Metrolink", GTFS);

	private String name;
	private ScheduleProvider type;

	private Agency(String name, ScheduleProvider type) {
		this.name = name;
		this.type = type;
	}

	public static Agency getByAgencyName(String name) {
		for (Agency agency : Agency.values()) {
			if (agency.name.equalsIgnoreCase(name))
				return agency;
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public ScheduleProvider getType() {
		return type;
	}

}
