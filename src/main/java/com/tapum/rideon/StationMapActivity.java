package com.tapum.rideon;

import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tapum.api.rideon.model.StationInfo;
import com.tapum.rideon.lib.R;
import com.tapum.rideon.location.MyLocationListener;

/**
 * 
 * @author Neeraj Jain
 * 
 */

public class StationMapActivity extends Activity implements
		OnNavigationListener, OnInfoWindowClickListener {
	private GoogleMap googleMap;
	private Location location;
	private LatLng userCoordinates;
	private LatLng stationCoordinates;
	private float mapZoom;
	private int screenWidth;
	private static final int[] MAP_TYPES = { GoogleMap.MAP_TYPE_NORMAL,
			GoogleMap.MAP_TYPE_HYBRID, GoogleMap.MAP_TYPE_SATELLITE,
			GoogleMap.MAP_TYPE_TERRAIN };

	StationInfo stationInfo;

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		googleMap.setMapType(MAP_TYPES[itemPosition]);

		return (true);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.station_map);
		displayMap();
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		// Toast.makeText(this, marker.getTitle(), Toast.LENGTH_LONG).show();
		// Intent intent = new Intent(getApplicationContext(),
		// StatusActivity.class);
		// startActivity(intent);

	}

	private void displayMap() {

		stationInfo = (StationInfo) getIntent().getSerializableExtra(
				"stationInfo");
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);

		Location myLocation = getMyLocation();
		Location stopLocation = new Location("");

		stopLocation.setLatitude(stationInfo.getLatitude());
		stopLocation.setLongitude(stationInfo.getLongitude());
		googleMap = ((MapFragment) getFragmentManager().findFragmentById(
				com.tapum.rideon.lib.R.id.map)).getMap();

		googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		googleMap.getUiSettings().setCompassEnabled(true);
		googleMap.getUiSettings().setZoomControlsEnabled(true);
		googleMap.getUiSettings().setMyLocationButtonEnabled(true);

		userCoordinates = new LatLng(myLocation.getLatitude(),
				myLocation.getLongitude());
		stationCoordinates = new LatLng(stopLocation.getLatitude(),
				stopLocation.getLongitude());

		Float distance = myLocation.distanceTo(stopLocation) * 0.000621371192f;
		java.text.DecimalFormat nft = new java.text.DecimalFormat(".##");

		googleMap.addMarker(new MarkerOptions()
				.position(userCoordinates)
				.title("You are here.")
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

		MarkerOptions options = new MarkerOptions()
				.position(stationCoordinates).title(
						stationInfo.getName() + ", " + nft.format(distance)
								+ " miles from you");
		String addr = stationInfo.getAddress();
		String city = stationInfo.getCity();

		StringBuffer snip = new StringBuffer();
		if (addr != null && !addr.trim().isEmpty()
				&& !addr.trim().equalsIgnoreCase("null")) {
			snip.append(addr);
		}
		if (city != null && !city.trim().isEmpty()
				&& !city.trim().equalsIgnoreCase("null")) {
			if (snip.toString().length() > 0) {
				snip.append(", ");
			}
			snip.append(city);
		}

		googleMap.addMarker(options.icon(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
		// get screen dimensions
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		screenWidth = size.x;
		mapZoom = calculateZoomLevel();

		googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
				stationCoordinates, mapZoom));

		googleMap.setOnInfoWindowClickListener(this);

	}

	private static final double DEFAULT_RADIUS_IN_MILES = 20;

	private int calculateZoomLevel() {
		double equatorLength = 40075004; // in meters
		double metersPerPixel = equatorLength / 256;
		int zoomLevel = 1;

		while ((metersPerPixel * screenWidth) > DEFAULT_RADIUS_IN_MILES * 1.6 * 1000) {
			metersPerPixel /= 2;
			++zoomLevel;
		}
		return zoomLevel;
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
