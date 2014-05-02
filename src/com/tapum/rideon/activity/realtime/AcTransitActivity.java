package com.tapum.rideon.activity.realtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tapum.api.rideon.model.Agency;
import com.tapum.rideon.broker.RouteInfoBroker;
import com.tapum.rideon.broker.RouteModel;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * 
 * @author Neeraj Jain
 * 
 */

public class AcTransitActivity extends Activity {

	static Location location = null;
	public final static List<RouteModel> routeList = new ArrayList<RouteModel>();
	public final static Map<String, RouteModel> codeStationInfoMap = new HashMap<String, RouteModel>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new RouteInfoBroker(this, routeList, codeStationInfoMap, Agency.AC_TRANSIT)
				.execute();
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