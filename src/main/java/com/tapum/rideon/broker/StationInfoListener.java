package com.tapum.rideon.broker;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.tapum.api.rideon.model.StationInfo;
import com.tapum.rideon.StationMapActivity;

/**
 * 
 * @author Neeraj Jain
 * 
 */

public class StationInfoListener implements OnClickListener {

	Activity activity;
	String stationCode;
	StationInfo info;

	public StationInfoListener(Activity activity, String stationCode,
			StationInfo info) {

		this.activity = activity;
		this.stationCode = stationCode;
		this.info = info;

	}

	@Override
	public void onClick(View v) {

		Intent intent = new Intent(activity.getApplicationContext(),
				StationMapActivity.class);
		// StationInfo stationInfo = BartActivity.codeStationInfoMap
		// .get(stationCode);
		intent.putExtra("stationInfo", info);
		activity.startActivity(intent);

		/*
		 * RelativeLayout rl = (RelativeLayout) activity
		 * .findViewById(R.id.bart_schedule_output_layout);
		 * rl.removeAllViewsInLayout();
		 * 
		 * StationInfo stationInfo = BartActivity.codeStationInfoMap
		 * .get(stationCode); int id = 3000; addField(id++, rl, "Stop Name:",
		 * true); addField(id++, rl, stationInfo.getName()); addField(id++, rl,
		 * "Address:", true); addField( id++, rl, stationInfo.getAddress() +
		 * ", \n" + stationInfo.getCity() + ", " + stationInfo.getState() + ". "
		 * + stationInfo.getZipcode()); addField(id++, rl,
		 * "Latitude, Longitude:", true); addField(id++, rl,
		 * stationInfo.getLatitude() + ", " + stationInfo.getLongitude());
		 */
	}

	// private void addField(int id, RelativeLayout rl, String value) {
	// addField(id, rl, value, false);
	// }

	// private void addField(int id, RelativeLayout rl, String value, boolean
	// bold) {
	// TextView tv = new TextView(activity);
	// tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
	//
	// if (bold) {
	// tv.setTypeface(null, Typeface.BOLD);
	// }
	// tv.setText(value);
	// tv.setId(id);
	// RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(
	// ViewGroup.LayoutParams.WRAP_CONTENT,
	// ViewGroup.LayoutParams.WRAP_CONTENT);
	// if (id > 3000)
	// p.addRule(RelativeLayout.BELOW, id - 1);
	// rl.addView(tv, p);
	//
	// }
}
