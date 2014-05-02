package com.tapum.rideon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.tapum.api.rideon.model.Agency;
import com.tapum.rideon.broker.RouteModel;
import com.tapum.rideon.broker.StationInfoBroker;

/**
 * 
 * @author Neeraj Jain
 * 
 */

public class RouteActivity extends AbstractAdvancedEasyCommuteActivity {

	static Location location = null;
	public final static List<RouteModel> routeList = new ArrayList<RouteModel>();
	public final static Map<String, RouteModel> codeStationInfoMap = new HashMap<String, RouteModel>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RouteModel routeInfo = (RouteModel) getIntent().getSerializableExtra(
				"routeInfo");
		Log.d("routeInfo=", "" + routeInfo);
		Agency agencyObj = Agency.getByAgencyName(routeInfo.getAgency());

		new StationInfoBroker(this, null, null, agencyObj, ""
				+ routeInfo.getRouteDirectionId(), null, null, null,
				routeInfo.getRouteCode(), routeInfo.getRouteDirectionCode(),
				routeInfo.getServiceId()).execute();

	}

}