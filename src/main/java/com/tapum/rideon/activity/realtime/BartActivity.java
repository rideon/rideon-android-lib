package com.tapum.rideon.activity.realtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.tapum.api.rideon.model.Agency;
import com.tapum.api.rideon.model.StationInfo;
import com.tapum.rideon.AbstractAdvancedEasyCommuteActivity;
import com.tapum.rideon.broker.StationInfoBroker;
import com.tapum.rideon.lib.R;
import com.tapum.rideon.location.MyLocationListener;

/**
 * 
 * @author Neeraj Jain
 * 
 */

public class BartActivity extends AbstractAdvancedEasyCommuteActivity {

	public static Location location = null;
	public final static List<StationInfo> stationList = new ArrayList<StationInfo>();
	public final static Map<String, StationInfo> codeStationInfoMap = new HashMap<String, StationInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_arrival);
		Agency agency = Agency.getByAgencyName("BART");
		
		new StationInfoBroker(this, null, null, agency, null, null, null, null,
				null, null, null).execute();
	}

	public Location getMyLocation() {
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Log.i("onCreate", "locationManager=" + locationManager.toString());
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		String provider = locationManager.getBestProvider(criteria, true);
		location = locationManager.getLastKnownLocation(provider);

		Log.i("getMyLocation", provider);
		locationManager.requestLocationUpdates(provider, 5000, 1,
				new MyLocationListener());
		return location;
	}
}