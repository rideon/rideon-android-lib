package com.tapum.rideon;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import com.tapum.rideon.activity.gtfs.GtfsRouteActivity;
import com.tapum.rideon.activity.realtime.AcTransitActivity;
import com.tapum.rideon.activity.realtime.BartActivity;
import com.tapum.rideon.activity.realtime.DumbartonExpressActivity;
import com.tapum.rideon.activity.realtime.MuniActivity;
import com.tapum.rideon.activity.realtime.SamTransActivity;
import com.tapum.rideon.activity.realtime.VtaActivity;
import com.tapum.rideon.activity.realtime.WestcatActivity;
import com.tapum.rideon.broker.ArrivalInfoBroker;
import com.tapum.rideon.favorite.FavoriteSubmissionBroker;
import com.tapum.rideon.lib.R;
import com.tapum.rideon.wheels.WheelsActivity;

/**
 * 
 * @author Neeraj Jain
 * 
 */

public abstract class AbstractEasyCommuteActivity extends ActionBarActivity
		implements OnQueryTextListener {
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.top_menu, menu);
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("oncreate", "dbg");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(true);
		getSupportActionBar().setLogo(R.drawable.tram_train_subway_36);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.menu_bart) {
			Intent intent = new Intent(getApplicationContext(),
					BartActivity.class);
			startActivity(intent);
			return true;
		} else if (id == R.id.menu_wheels) {
			Intent intent = new Intent(getApplicationContext(),
					WheelsActivity.class);
			startActivity(intent);
			return true;
		} else if (id == R.id.menu_muni) {
			Intent intent = new Intent(getApplicationContext(),
					MuniActivity.class);
			startActivity(intent);
			return true;
		} else if (id == R.id.menu_actransit) {
			Intent intent = new Intent(getApplicationContext(),
					AcTransitActivity.class);
			startActivity(intent);
			return true;
		} else if (id == R.id.menu_dumbartonexpress) {
			Intent intent = new Intent(getApplicationContext(),
					DumbartonExpressActivity.class);
			startActivity(intent);
			return true;
		} else if (id == R.id.menu_samtrans) {
			Intent intent = new Intent(getApplicationContext(),
					SamTransActivity.class);
			startActivity(intent);
			return true;
		} else if (id == R.id.menu_vta) {
			Intent intent = new Intent(getApplicationContext(),
					VtaActivity.class);
			startActivity(intent);
			return true;
		} else if (id == R.id.menu_vta_schedule) {
			Intent intent = new Intent(getApplicationContext(),
					GtfsRouteActivity.class);
			String agencyId = "VTA-Schedule";
			intent.putExtra("gtfsAgencyId", agencyId);
			startActivity(intent);
			return true;
		} else if (id == R.id.menu_westcat) {
			Intent intent = new Intent(getApplicationContext(),
					WestcatActivity.class);
			startActivity(intent);
			return true;
		} else if (id == R.id.menu_refresh) {
			Log.i("onOptionsItemSelected", "Refreshing");
			refreshPage();
			return true;
		} else if (id == R.id.menu_favorite) {
			Log.i("onOptionsItemSelected", "favorite");
			favoriteAction(item);
			return true;
		} else if (id == R.id.menu_map) {
			Log.i("onOptionsItemSelected", "Mapping");
			showMap(item);
			return true;
		}

		else {
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onQueryTextChange(String newText) {

		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		Log.i("onQueryTextSubmit", query + "*");
		// TODO Auto-generated method stub
		return false;
	}

	public void refreshPage() {
		new ArrivalInfoBroker(ArrivalInfoBroker.instance.getActivity(),
				ArrivalInfoBroker.instance.getAgency(),
				ArrivalInfoBroker.instance.getStationInfo()).execute(
				ArrivalInfoBroker.instance.getStopCode(),
				ArrivalInfoBroker.instance.getStopName());

	}

	public void favoriteAction(MenuItem item) {
		Log.i("favoriteAction", ArrivalInfoBroker.instance.getStopName() + ", "
				+ ArrivalInfoBroker.instance.getStopCode());
		new FavoriteSubmissionBroker(ArrivalInfoBroker.instance.getActivity())
				.execute(ArrivalInfoBroker.instance.getAgency().getName(),
						ArrivalInfoBroker.instance.getStopCode(), "");
	}

	public void showMap(MenuItem item) {
		if (ArrivalInfoBroker.instance.getStationInfo() == null
				|| ArrivalInfoBroker.instance.getStationInfo().getLatitude() == 0
				|| ArrivalInfoBroker.instance.getStationInfo().getLongitude() == 0) {
			Toast.makeText(this, "Map not available for this stop.",
					Toast.LENGTH_LONG).show();
			return;

		}
		Intent intent = new Intent(ArrivalInfoBroker.instance.getActivity(),
				StationMapActivity.class);
		intent.putExtra("stationInfo",
				ArrivalInfoBroker.instance.getStationInfo());
		ArrivalInfoBroker.instance.getActivity().startActivity(intent);
	}
}
