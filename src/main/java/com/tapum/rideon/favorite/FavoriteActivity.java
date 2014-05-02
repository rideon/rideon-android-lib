package com.tapum.rideon.favorite;

import android.os.Bundle;

import com.tapum.api.rideon.model.Agency;
import com.tapum.api.rideon.model.StationInfo;
import com.tapum.rideon.AbstractAdvancedEasyCommuteActivity;
import com.tapum.rideon.broker.StationInfoBroker;
import com.tapum.rideon.lib.R;

/**
 * 
 * @author Neeraj Jain
 * 
 */

public class FavoriteActivity extends AbstractAdvancedEasyCommuteActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_arrival);
		StationInfo stationInfo = (StationInfo) getIntent()
				.getSerializableExtra("stationInfo");
		if (stationInfo.getRouteDirection() == null) {
			Agency agencyObj = Agency.getByAgencyName(stationInfo.getAgency());

			new StationInfoBroker(this, null, null, agencyObj, null,
					stationInfo.getAbbreviation(), stationInfo.getName(),
					stationInfo, null, null, null).execute();
		} else {
			// new RouteInfoBroker(activity, routeList, codeStationInfoMap,
			// agency)
			Agency agencyObj = Agency.getByAgencyName(stationInfo.getAgency());
			new StationInfoBroker(this, null, null, agencyObj, ""
					+ stationInfo.getRouteDirection().getId(),
					stationInfo.getAbbreviation(), stationInfo.getName(),
					stationInfo, null, null, null).execute();
		}
	}

}
