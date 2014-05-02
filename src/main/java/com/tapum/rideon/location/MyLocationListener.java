package com.tapum.rideon.location;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.tapum.rideon.activity.realtime.BartActivity;

/**
 * 
 * @author Neeraj Jain
 * 
 */

public class MyLocationListener implements LocationListener {
	public void onLocationChanged(Location location) {
		if (location != null) {
			BartActivity.location = location;
		}
		// else {}

	}

	@Override
	public void onProviderDisabled(String provider) {
		Log.i("onProviderDisabled", provider);

	}

	@Override
	public void onProviderEnabled(String provider) {
		Log.i("onProviderEnabled", provider);

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// Log.i("onStatusChanged", provider);
		// Log.i("onStatusChanged-status", "" + status);

	}

}