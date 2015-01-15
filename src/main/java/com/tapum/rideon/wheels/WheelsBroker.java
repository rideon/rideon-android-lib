package com.tapum.rideon.wheels;

import static com.tapum.rideon.util.AppConstants.EVENTVALIDATION_109;
import static com.tapum.rideon.util.AppConstants.EVENTVALIDATION_136;
import static com.tapum.rideon.util.AppConstants.EVENTVALIDATION_137;
import static com.tapum.rideon.util.AppConstants.EVENTVALIDATION_15;
import static com.tapum.rideon.util.AppConstants.EVENTVALIDATION_17;
import static com.tapum.rideon.util.AppConstants.VIEWSTATE_109;
import static com.tapum.rideon.util.AppConstants.VIEWSTATE_136;
import static com.tapum.rideon.util.AppConstants.VIEWSTATE_137;
import static com.tapum.rideon.util.AppConstants.VIEWSTATE_15;
import static com.tapum.rideon.util.AppConstants.VIEWSTATE_17;
import static com.tapum.rideon.util.AppConstants.WHEELS_URL;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tapum.rideon.broker.RouteModel;
import com.tapum.rideon.lib.R;

/**
 * 
 * @author Neeraj Jain
 * 
 */

public class WheelsBroker extends AsyncTask<String, Void, String> {
	private ProgressDialog progress = null;

	private WheelsActivity activity;

	public WheelsBroker(WheelsActivity activity) {
		this.activity = activity;
	}

	private String busNumber;
	private String stopName;
	private String directionText;

	public String getSchedule(String busNumber, String routeId,
			String busStopNumber, String direction, String directionText,
			String stopName) {

		try {
			this.busNumber = busNumber;
			this.directionText = directionText;
			this.stopName = stopName;

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost request = new HttpPost(WHEELS_URL);

			Log.i("WheelsBroker", WHEELS_URL);
			String json = "{routeID: \"" + routeId + "\",	directionID: \""
					+ direction + "\",	stopID: \"" + busStopNumber
					+ "\", useArrivalTimes: \"true\"}";
			Log.i("wheelsBroker", json);
			StringEntity se = new StringEntity(json);
			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));
			request.setEntity(se);

			ResponseHandler<String> handler = new BasicResponseHandler();
			String result = httpclient.execute(request, handler);
			return parseJson(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Try again";
	}

	/*
	 * Following is a sample response showing NO arrival times.
	 * {
	 * "d": {
	 * "errorMessage": null,
	 * "showArrivals": true,
	 * "showStopNumber": false,
	 * "showScheduled": false,
	 * "showDestination": false,
	 * "updateTime": "11:54",
	 * "updatePeriod": "am",
	 * "routeStops": [
	 * {
	 * "routeID": 28,
	 * "stops": [
	 * {
	 * "directionID": 136,
	 * "stopID": 628,
	 * "timePointID": 0,
	 * "sameDestination": false,
	 * "crossings": null
	 * }
	 * ]
	 * }
	 * ]
	 * }
	 * }
	 * 
	 * Following is a sample response showing arrival times.
	 * {
	 * "d": {
	 * "errorMessage": null,
	 * "showArrivals": true,
	 * "showStopNumber": false,
	 * "showScheduled": false,
	 * "showDestination": false,
	 * "updateTime": "12:26",
	 * "updatePeriod": "pm",
	 * "routeStops": [
	 * {
	 * "routeID": 10,
	 * "stops": [
	 * {
	 * "directionID": 109,
	 * "stopID": 90,
	 * "timePointID": 0,
	 * "sameDestination": false,
	 * "crossings": [
	 * {
	 * "cancelled": false,
	 * "schedTime": "12:39",
	 * "schedPeriod": "pm",
	 * "predTime": "12:39",
	 * "predPeriod": "pm",
	 * "countdown": null,
	 * "destination": null
	 * },
	 * {
	 * "cancelled": false,
	 * "schedTime": "1:09",
	 * "schedPeriod": "pm",
	 * "predTime": "1:09",
	 * "predPeriod": "pm",
	 * "countdown": null,
	 * "destination": null
	 * },
	 * {
	 * "cancelled": false,
	 * "schedTime": "1:39",
	 * "schedPeriod": "pm",
	 * "predTime": null,
	 * "predPeriod": null,
	 * "countdown": null,
	 * "destination": null
	 * }
	 * ]
	 * }
	 * ]
	 * }
	 * ]
	 * }
	 * }
	 */
	private String parseJson(String json) {
		String output = "Try again";
		try {
			if (json == null || json.isEmpty())
				return "Internal Error";

			JSONObject routeInfoJson = new JSONObject(json);
			JSONObject d = routeInfoJson.getJSONObject("d");

			String errorMessage = d.getString("errorMessage");
			if (errorMessage != null && !errorMessage.equals("null")) {
				Log.e("WheelsBroker", errorMessage.toString());
				return errorMessage;
			}
			JSONArray routeStops = d.getJSONArray("routeStops");
			if (routeStops.length() == 0) {
				Log.e("WheelsBroker", "No routeStops found");
				return "Internal Error";
			}
			for (int g = 0; g < routeStops.length(); g++) {
				JSONArray stops = routeStops.getJSONObject(g).getJSONArray(
						"stops");
				if (stops.length() == 0) {
					Log.e("WheelsBroker", "No stops found");
					return "Internal Error";
				}
				for (int s = 0; s < stops.length(); s++) {
					Object crossingObj = stops.getJSONObject(s)
							.get("crossings");
					if (crossingObj == null
							|| crossingObj.toString().equals("null")) {
						Log.e("WheelsBroker-crossings is null",
								crossingObj.toString());
						return "No upcoming stop times found";
					} else {
						Log.i("WheelsBroker-parsing crossing",
								crossingObj.toString());
						JSONArray crossings = stops.getJSONObject(s)
								.getJSONArray("crossings");

						output = "";
						for (int c = 0; c < crossings.length(); c++) {
							String time = "";
							JSONObject crossing = crossings.getJSONObject(c);
							String predTime = crossing.getString("predTime")
									.toString();
							if (!predTime.equals("null")) {
								time = predTime
										+ " "
										+ crossing.getString("predPeriod")
												.toString();
							} else {
								time = crossing.getString("schedTime")
										.toString()
										+ crossing.getString("schedPeriod")
												.toString();

							}
							if (!output.isEmpty()) {
								output = output + ", ";
							}
							output += time;
						}
						return output;
					}
				}
			}
		} catch (Exception ex) {
			Log.e("RouteInfoBroker", ex.toString());
		}
		return output;
	}

	@Override
	protected String doInBackground(String... params) {
		return getSchedule(params[0], params[1], params[2], params[3],
				params[4], params[5]);
	}

	@Override
	protected void onPreExecute() {
		progress = ProgressDialog.show(activity, null, "Loading results");
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(String result) {
		progress.dismiss();
		super.onPostExecute(result);
		RelativeLayout rl = (RelativeLayout) activity
				.findViewById(R.id.status_layout);
		rl.removeAllViewsInLayout();
		rl.setPadding(10, 2, 10, 0);
		int id = 1000;

		if (result != null && result.equals("Try again")) {
			Toast.makeText(
					activity,
					"Could not retrieve information. Please make sure that network and GPS are available",
					Toast.LENGTH_LONG).show();
		} else {

			addRouteColumn(stopName, id, rl, true, false);
			id++;
			addTimeColumn("", id, rl, true, false);
			id++;

			addRouteColumn("Bus number", id, rl, false, false);
			id++;
			addTimeColumn(busNumber + directionText, id, rl, false, false);
			id++;
			// addRouteColumn("", id, rl, false, false);
			// id++;
			if (result.equals("No Schedule Found")) {
				addRouteColumn(" ", id, rl, true, false);
			} else {
				addRouteColumn("Arriving at", id, rl, false, false);
			}
			id++;
			addTimeColumn(result, id, rl, false, false);
			id++;
		}
		// TextView tv = new TextView(activity);
		// tv.setText("Bus number-");
		// TextView tv = (TextView) activity.findViewById(R.id.next_bus_at_10e);
		// tv.setText("Bus number-" + busNumber + directionText + "\n" +
		// stopName
		// + "\n\n" + result);

	}

	private void addRouteColumn(String route, int prevId, RelativeLayout rl,
			boolean firstRow, boolean pageTitle) {
		/**
		 * Add the route name
		 */
		TextView routeName = new TextView(activity);
		routeName.setText(route);
		RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		if (prevId > 1000) {
			p.addRule(RelativeLayout.BELOW, prevId - 2);
			p.addRule(RelativeLayout.ALIGN_PARENT_LEFT, prevId - 2);
		}
		routeName.setId(prevId++);
		if (pageTitle) {
			routeName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
			routeName.setTypeface(null, Typeface.BOLD);
			routeName.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
			routeName.setGravity(Gravity.CENTER);
		} else if (firstRow) {
			routeName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			routeName.setTypeface(null, Typeface.BOLD);
		} else {
			routeName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			routeName.setTypeface(null, Typeface.NORMAL);
		}
		// p.setMargins(0, 0, 1, 1);
		rl.addView(routeName, p);

	}

	private void addTimeColumn(String route, int id, RelativeLayout rl,
			boolean firstRow, boolean pageTitle) {
		/**
		 * Add the departing time
		 */
		TextView departingTime = new TextView(activity);
		departingTime.setText(route);

		RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		if (id > 1001) {
			p.addRule(RelativeLayout.BELOW, id - 2);
		}
		p.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, (id - 1));
		departingTime.setId(id++);
		if (pageTitle) {
			departingTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
			departingTime.setTypeface(null, Typeface.BOLD);
			departingTime.setGravity(Gravity.CENTER);

		} else if (firstRow) {
			departingTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			departingTime.setTypeface(null, Typeface.BOLD);
		} else {
			departingTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			departingTime.setTypeface(null, Typeface.BOLD);
		}
		// p.setMargins(0, 0, 1, 1);
		rl.addView(departingTime, p);

	}
}
