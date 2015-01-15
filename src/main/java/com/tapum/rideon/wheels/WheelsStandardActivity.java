package com.tapum.rideon.wheels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;

import com.tapum.api.rideon.model.Agency;
import com.tapum.rideon.broker.RouteInfoBroker;
import com.tapum.rideon.broker.RouteModel;

/**
 * 
 * @author Neeraj Jain
 * 
 */

public class WheelsStandardActivity extends Activity {

	static int startUp = 5;
	static Spinner spinner;
	static int position;
	// static AdapterView<?> adapterView;
	static boolean ref = false;


	WheelsBroker wheelsBroker = null;
	public final static List<RouteModel> routeList = new ArrayList<RouteModel>();
	public final static Map<String, RouteModel> codeStationInfoMap = new HashMap<String, RouteModel>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new RouteInfoBroker(this, routeList, codeStationInfoMap, Agency.WHEELS)
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


