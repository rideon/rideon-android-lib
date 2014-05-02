package com.tapum.rideon.activity.gtfs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.tapum.api.rideon.model.Agency;
import com.tapum.rideon.broker.RouteModel;
import com.tapum.rideon.broker.StationInfoBroker;

/**
 * 
 * @author Neeraj Jain
 * 
 */

public class GtfsStationActivity extends Activity {

	static Location location = null;
	public final static List<RouteModel> routeList = new ArrayList<RouteModel>();
	public final static Map<String, RouteModel> codeStationInfoMap = new HashMap<String, RouteModel>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String gtfsAgencyId = (String) getIntent().getSerializableExtra(
				"gtfsAgencyId");
		Agency agency = Agency.getByAgencyName(gtfsAgencyId);
		new StationInfoBroker(this, null, null, agency, null, null, null,
				null, null, null, null).execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return false;
	}

}