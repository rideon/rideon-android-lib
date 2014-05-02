package com.tapum.rideon.activity.gtfs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.tapum.api.rideon.model.Agency;
import com.tapum.rideon.broker.RouteInfoBroker;
import com.tapum.rideon.broker.RouteModel;
import com.tapum.rideon.util.AppProperties;

/**
 * 
 * @author Neeraj Jain
 * 
 */

public class GtfsRouteActivity extends Activity {

	static Location location = null;
	public final static List<RouteModel> routeList = new ArrayList<RouteModel>();
	public final static Map<String, RouteModel> codeStationInfoMap = new HashMap<String, RouteModel>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			AppProperties.init(this);
			ApplicationInfo ai = getPackageManager().getApplicationInfo(
					this.getPackageName(), PackageManager.GET_META_DATA);
			Bundle bundle = ai.metaData;
			String agencyId = bundle.getString("agency_id");
			Log.d("metaData", "agency_id=" + agencyId);
			if (agencyId == null) {
				agencyId = (String) getIntent().getSerializableExtra(
						"gtfsAgencyId");
			}
			new RouteInfoBroker(this, routeList, codeStationInfoMap,
					Agency.getByAgencyName(agencyId)).execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return false;
	}

}