package com.tapum.rideon;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.tapum.rideon.activity.gtfs.GtfsRouteActivity;
import com.tapum.rideon.activity.gtfs.GtfsStationActivity;
import com.tapum.rideon.activity.realtime.AcTransitActivity;
import com.tapum.rideon.activity.realtime.BartActivity;
import com.tapum.rideon.activity.realtime.CaltrainActivity;
import com.tapum.rideon.activity.realtime.DumbartonExpressActivity;
import com.tapum.rideon.activity.realtime.MuniActivity;
import com.tapum.rideon.activity.realtime.SamTransActivity;
import com.tapum.rideon.activity.realtime.VtaActivity;
import com.tapum.rideon.activity.realtime.WestcatActivity;
import com.tapum.rideon.favorite.FavoriteBroker;
import com.tapum.rideon.lib.R;
import com.tapum.rideon.util.AppProperties;
import com.tapum.rideon.wheels.WheelsStandardActivity;

/**
 * This the main activity class that shows up with the app is started.
 * 
 * @author Neeraj Jain
 * 
 */

public class CommuteBuddyMainActivity extends ActionBarActivity {

	@Override
	protected void onResume() {
		super.onResume();
		setContentView(R.layout.commute_apps);
		new FavoriteBroker(this).execute();
		Log.d("onResume", "onResume");
	}

	/**
	 * Initialize the properties, read the apikey and url from assets.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("onCreate", "onCreate");
		super.onCreate(savedInstanceState);
		AppProperties.init(this);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
		getSupportActionBar().setDisplayUseLogoEnabled(true);
		getSupportActionBar().setLogo(R.drawable.tram_train_subway_36);
		
	}

	/**
	 * Intent to load and display the interface for wheels.
	 * 
	 * @param view
	 */
	public void wheels(View view) {
		Intent intent = new Intent(getApplicationContext(),
				WheelsStandardActivity.class);
		startActivity(intent);

	}

	/**
	 * Intent to load and display the interface for San Francisco - Bay Area
	 * Rapit Transit(BART).
	 * 
	 * @param view
	 */
	public void bart(View view) {
		Intent intent = new Intent(getApplicationContext(), BartActivity.class);
		startActivity(intent);
	}

	public void gtfsRoute(View view) {
		Intent intent = new Intent(getApplicationContext(),
				GtfsRouteActivity.class);
		startActivity(intent);
	}

	/**
	 * Intent to load and display the interface for Los Angeles metro schedule.
	 * 
	 * @param view
	 */
	public void metroLinks(View view) {
		Intent intent = new Intent(getApplicationContext(),
				GtfsRouteActivity.class);
		String agencyId = "Metrolink";
		intent.putExtra("gtfsAgencyId", agencyId);
		startActivity(intent);
	}

	/**
	 * Intent to load and display the interface for VTA schedule.
	 * 
	 * @param view
	 */
	public void vtaRails(View view) {
		Intent intent = new Intent(getApplicationContext(),
				GtfsRouteActivity.class);
		String agencyId = "VTA-Schedule";
		intent.putExtra("gtfsAgencyId", agencyId);
		startActivity(intent);
	}

	/**
	 * Intent to load and display the interface for Caltrain schedule.
	 * 
	 * @param view
	 */
	public void caltrain(View view) {
		Intent intent = new Intent(getApplicationContext(),
				GtfsStationActivity.class);
		String agencyId = "caltrain-ca-us";
		intent.putExtra("gtfsAgencyId", agencyId);
		startActivity(intent);
	}

	/**
	 * Intent to load and display the interface for Caltrain.
	 * 
	 * @param view
	 */
	public void caltrainRealtime(View view) {
		Intent intent = new Intent(getApplicationContext(),
				CaltrainActivity.class);
		startActivity(intent);
	}

	/**
	 * Intent to load and display the interface for San Francisco MUNI.
	 * 
	 * @param view
	 */
	public void muniStops(View view) {
		Intent intent = new Intent(getApplicationContext(), MuniActivity.class);
		startActivity(intent);
	}

	/**
	 * Intent to load and display the interface for AC Transit.
	 * 
	 * @param view
	 */
	public void acTransit(View view) {
		Intent intent = new Intent(getApplicationContext(),
				AcTransitActivity.class);
		startActivity(intent);
	}

	/**
	 * Intent to load and display the interface for VTA.
	 * 
	 * @param view
	 */
	public void vta(View view) {
		Intent intent = new Intent(getApplicationContext(), VtaActivity.class);
		startActivity(intent);
	}

	/**
	 * Intent to load and display the interface for Dumbarton Express.
	 * 
	 * @param view
	 */
	public void dumbartonExpress(View view) {
		Intent intent = new Intent(getApplicationContext(),
				DumbartonExpressActivity.class);
		startActivity(intent);
	}

	/**
	 * Intent to load and display the interface for Sam Trans.
	 * 
	 * @param view
	 */
	public void samTrans(View view) {
		Intent intent = new Intent(getApplicationContext(),
				SamTransActivity.class);
		startActivity(intent);
	}

	/**
	 * Intent to load and display the interface for WESTCAT.
	 * 
	 * @param view
	 */
	public void westcat(View view) {
		Intent intent = new Intent(getApplicationContext(),
				WestcatActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		int id = item.getItemId();
		if (id == R.id.menu_wheels) {
			wheels(this.getCurrentFocus());
			return true;
		} else if (id == R.id.menu_bart) {
			bart(this.getCurrentFocus());
			return true;
		} else if (id == R.id.menu_muni) {
			muniStops(this.getCurrentFocus());
			return true;
		} else if (id == R.id.menu_actransit) {
			acTransit(this.getCurrentFocus());
			return true;
		} else if (id == R.id.menu_dumbartonexpress) {
			dumbartonExpress(this.getCurrentFocus());
			return true;
		} else if (id == R.id.menu_samtrans) {
			samTrans(this.getCurrentFocus());
			return true;
		} else if (id == R.id.menu_westcat) {
			westcat(this.getCurrentFocus());
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}
}
