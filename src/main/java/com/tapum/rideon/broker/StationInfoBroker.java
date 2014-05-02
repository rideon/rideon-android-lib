package com.tapum.rideon.broker;

import static com.tapum.api.rideon.model.ScheduleProvider.GTFS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tapum.api.rideon.model.Agency;
import com.tapum.api.rideon.model.StationInfo;
import com.tapum.rideon.lib.R;
import com.tapum.rideon.location.LocationHelper;
import com.tapum.rideon.view.StationButton;
import com.tapum.rideon.view.StationButtonRelativeLayout;

/**
 * 
 * @author Neeraj Jain
 * 
 */

public class StationInfoBroker extends AsyncTask<Void, Void, List<StationInfo>> {
	private ProgressDialog progress = null;

	private Activity activity;
	private static HttpClient httpclient;
	// List<StationInfo> stationList;
	private String routeDirectionId;
	private String stationCode;
	private String stationName;

	private Agency agency;

	private StationInfo stationInfo;

	private String gtfsRouteId;
	private String gtfsDirectionId;
	private String gtfsServiceId;

	public StationInfoBroker(Activity activity, List<StationInfo> stationList,
			Map<String, StationInfo> codeStationInfoMap, Agency agency,
			String routeDirectionId, String stationCode, String stationName,
			StationInfo stationInfo, String gtfsRouteId,
			String gtfsDirectionId, String gtfsServiceId) {
		this.activity = activity;
		this.stationCode = stationCode;
		this.stationName = stationName;
		this.agency = agency;
		this.stationInfo = stationInfo;
		this.gtfsRouteId = gtfsRouteId;
		this.gtfsDirectionId = gtfsDirectionId;
		this.gtfsServiceId = gtfsServiceId;

		Log.i("StationInfoBroker-agency", "" + agency);
		Log.i("StationInfoBroker-stationCode", "" + stationCode);
		Log.i("StationInfoBroker-stationName", "" + stationName);
		Log.i("StationInfoBroker-routeDirectionId", "" + routeDirectionId);
		Log.i("StationInfoBroker-stationInfo", "" + stationInfo);
		Log.i("StationInfoBroker-gtfsRouteId", "" + gtfsRouteId);
		Log.i("StationInfoBroker-gtfsDirectionId", "" + gtfsDirectionId);
		Log.i("StationInfoBroker-gtfsServiceId", "" + gtfsServiceId);
		this.routeDirectionId = routeDirectionId;
	}

	@Override
	protected List<StationInfo> doInBackground(Void... param) {
		Log.i("StationInfoBroker", "doInBackground");
		if (httpclient == null)
			httpclient = new DefaultHttpClient();
		return remoteCall();
	}

	private List<StationInfo> remoteCall() {
		try {

			final TelephonyManager tMgr = (TelephonyManager) activity
					.getSystemService(Context.TELEPHONY_SERVICE);
			String mPhoneNumber = tMgr.getLine1Number();
			String deviceId = tMgr.getDeviceId();
			String softwareVersion = tMgr.getDeviceSoftwareVersion();
			StationInfoRequestBuilder builder = new StationInfoRequestBuilder();

			builder.withScheduleProvider(agency.getType())
					.withAgencyName(agency.getName())
					.withPhoneNumber(mPhoneNumber).withDeviceId(deviceId)
					.withSoftwareVersion(softwareVersion);
			builder.withRouteDirectionId(routeDirectionId);

			if (agency.getType() == GTFS) {
				if (gtfsRouteId != null) {
					builder.withRouteId(gtfsRouteId)
							.withGtfsDirectionId(gtfsDirectionId)
							.withGtfsServiceId(gtfsServiceId);
				}
			}
			final String reqLine = builder.build();
			// Log.d("StationInfoBroker", reqLine.toString());
			HttpGet request = new HttpGet(reqLine.toString());
			ResponseHandler<String> handler = new BasicResponseHandler();
			String result = httpclient.execute(request, handler);
			// Log.i("StationInfoBroker-remoteCall", "result+" + result);
			List<StationInfo> stationList = parseJson(result);
			return stationList;
		} catch (Exception ex) {
			Log.e("StationInfoBroker-getSchedule", ex.toString());
			return null;
		} finally {

		}
	}

	/**
	 * Parse the JSON response and populate a list of StationInfo objects.
	 * 
	 * @param json
	 * @return list of StationInfo objects. Returns null in case there was any
	 *         issue in parsing the JSON response.
	 */
	private List<StationInfo> parseJson(String json) {
		try {
			List<StationInfo> stationList = new ArrayList<StationInfo>();
			JSONArray jObject = new JSONArray(json);

			for (int g = 0; g < jObject.length(); g++) {
				// JSONObject etdJson = jObject.getJSONObject(g);
				// This must be a StationInfo object.
				JSONObject stationInfoJson = jObject.getJSONObject(g);
				final StationInfo info = new StationInfo();

				info.setId(stationInfoJson.getInt("id"));
				info.setName(stationInfoJson.getString("name"));
				info.setAbbreviation(stationInfoJson.getString("abbreviation"));
				info.setLatitude(stationInfoJson.getDouble("latitude"));
				info.setLongitude(stationInfoJson.getDouble("longitude"));

				info.setAddress(stationInfoJson.getString("address"));
				info.setCity(stationInfoJson.getString("city"));
				// info.setCounty(stationInfoJson.getString("county"));
				info.setState(stationInfoJson.getString("state"));
				info.setZipcode(stationInfoJson.getString("zipcode"));
				// info.setCountry(stationInfoJson.getString("country"));
				// codeStationInfoMap.put(
				// stationInfoJson.getString("abbreviation"), info);
				stationList.add(info);

			}
			return stationList;
		} catch (Exception ex) {
			Log.i("StationInfoBroker-json", "json" + json);
			Log.e("StationInfoBroker", ex.toString());
		}
		return null;
	}

	/**
	 * Render the view list of stations, or display an error message if the
	 * StationInfo list is null.
	 */
	@Override
	protected void onPostExecute(List<StationInfo> stationList) {
		progress.dismiss();

		super.onPostExecute(stationList);
		int i = 0;
		int id = 2000;
		// DisplayMetrics metrics = new DisplayMetrics();

		if (stationList == null) {
			LinearLayout linearLayout = (LinearLayout) activity
					.findViewById(R.id.activity_arrival_layout);
			linearLayout.removeAllViewsInLayout();
			Toast.makeText(
					activity,
					"Could not retrieve information. Please make sure that network and GPS are available",
					Toast.LENGTH_LONG).show();
		} else {
			activity.setContentView(R.layout.activity_arrival);
			RelativeLayout rl = (RelativeLayout) activity
					.findViewById(R.id.station_list_layout);
			rl.removeAllViewsInLayout();
			for (final StationInfo info : stationList) {

				Button button = new StationButton(activity);
				button.setText(info.getName());

				RelativeLayout.LayoutParams p = new StationButtonRelativeLayout(
						activity);
				button.setId(id);
				if (i > 0)
					p.addRule(RelativeLayout.BELOW, id - 1);
				/**
				 * Each station button is supposed to load the arrival page
				 * again, this time with the arrival information for the station
				 * that was clicked.
				 */
				button.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						new ArrivalInfoBroker(activity, agency, info).execute(
								info.getAbbreviation(), info.getName());
					}
				});

				rl.addView(button, p);
				id++;
				i++;
			}
			if (stationCode != null && stationName != null
					&& !stationCode.isEmpty() && !stationName.isEmpty()) {
				new ArrivalInfoBroker(activity, agency, stationInfo).execute(
						stationCode, stationName);

			} else {
				StationInfo nearestStation = getClosestStation(stationList);
				if (nearestStation == null) {
					Log.i("No closest station found", "");
					if (stationList != null && stationList.size() > 0)
						new ArrivalInfoBroker(activity, agency,
								stationList.get(0)).execute(stationList.get(0)
								.getAbbreviation(), stationList.get(0)
								.getName());
				} else new ArrivalInfoBroker(activity, agency, nearestStation)
						.execute(nearestStation.getAbbreviation(),
								nearestStation.getName());
			}
		}
	}

	@Override
	protected void onPreExecute() {
		progress = ProgressDialog.show(activity, null, "Loading station info");
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onCancelled() {
		Log.i("onCanceled", "cancelled");
		progress.cancel();
	}

	private StationInfo getClosestStation(List<StationInfo> stationList) {
		if (stationList == null)
			return null;
		Location myLocation = LocationHelper.getMyLocation(activity);
		if (myLocation == null) {
			myLocation = new Location("");
			myLocation.setLatitude(37.774929);
			myLocation.setLongitude(-122.419416);
		}
		float dist = Float.MAX_VALUE;
		StationInfo nearestLocation = null;
		for (StationInfo info : stationList) {
			Location l = new Location("");
			l.setLatitude(info.getLatitude());
			l.setLongitude(info.getLongitude());

			float dist1 = myLocation.distanceTo(l);
			if (dist1 < dist) {
				dist = dist1;
				nearestLocation = info;
			}
		}
		return nearestLocation;
	}

	public static int getDPI(int size, DisplayMetrics metrics) {
		return (size * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;
	}
}
