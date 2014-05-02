package com.tapum.rideon.location;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

/**
 * 
 * @author Neeraj Jain
 * 
 */

public class LocationHelper {

	public static Location getMyLocation(Context context) {
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		Log.i("onCreate", "locationManager=" + locationManager.toString());
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		String provider = locationManager.getBestProvider(criteria, true);
		Location location = locationManager.getLastKnownLocation(provider);

		Log.i("getMyLocation", provider);
		locationManager.requestLocationUpdates(provider, 5000, 1,
				new MyLocationListener());
		return location;
	}
}
