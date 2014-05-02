package com.tapum.rideon.broker;

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
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.tapum.api.rideon.model.Agency;
import com.tapum.rideon.RouteActivity;
import com.tapum.rideon.lib.R;
import com.tapum.rideon.view.RouteButton;

/**
 * 
 * @author Neeraj Jain
 * 
 */

public class RouteInfoBroker extends AsyncTask<Void, Void, List<RouteModel>> {
	private ProgressDialog progress = null;

	private Activity activity;
	private static HttpClient httpclient;

	// private String agencyName;
	private Agency agency;

	public RouteInfoBroker(Activity activity, List<RouteModel> routeList,
			Map<String, RouteModel> codeStationInfoMap, Agency agency) {
		this.activity = activity;
		// this.agencyName = agency.getName();
		this.agency = agency;
	}

	@Override
	protected List<RouteModel> doInBackground(Void... param) {
		Log.i("RouteInfoBroker", "doInBackground");
		if (httpclient == null)
			httpclient = new DefaultHttpClient();
		return remoteCall();
	}

	private List<RouteModel> remoteCall() {
		try {
			final TelephonyManager tMgr = (TelephonyManager) activity
					.getSystemService(Context.TELEPHONY_SERVICE);
			String mPhoneNumber = tMgr.getLine1Number();
			String deviceId = tMgr.getDeviceId();
			String softwareVersion = tMgr.getDeviceSoftwareVersion();

			RouteInfoRequestBuilder builder = new RouteInfoRequestBuilder();
			builder.withScheduleProvider(agency.getType())
					.withAgencyName(agency.getName())
					.withPhoneNumber(mPhoneNumber).withDeviceId(deviceId)
					.withSoftwareVersion(softwareVersion);
			final String reqLine = builder.build();
			// Log.d("RouteInfoBroker", "agency=" + agencyName);
			// Log.d("RouteInfoBroker", reqLine.toString());
			HttpGet request = new HttpGet(reqLine.toString());
			ResponseHandler<String> handler = new BasicResponseHandler();
			String result = httpclient.execute(request, handler);
			return parseJson(result);
		} catch (Exception ex) {
			Log.e("RouteInfoBroker-getSchedule", ex.toString());
			return null;
		} finally {

		}
	}

	private List<RouteModel> parseJson(String json) {
		try {
			List<RouteModel> routeList = new ArrayList<RouteModel>();
			JSONArray jObject = new JSONArray(json);

			for (int g = 0; g < jObject.length(); g++) {
				JSONObject routeInfoJson = jObject.getJSONObject(g);
				final RouteModel info = new RouteModel();

				info.setAgency(routeInfoJson.getString("agency"));
				info.setId(routeInfoJson.getInt("id"));
				info.setRouteCode(routeInfoJson.getString("routeCode"));
				info.setRouteDirectionCode(routeInfoJson
						.getString("routeDirectionCode"));
				info.setRouteDirectionName(routeInfoJson
						.getString("routeDirectionName"));
				info.setRouteName(routeInfoJson.getString("routeName"));
				info.setRouteDirectionId(routeInfoJson
						.getLong("routeDirectionId"));
				info.setRouteId(routeInfoJson.getLong("routeId"));
				// info.setServiceId(routeInfoJson.getString("serviceId"));
				routeList.add(info);
			}
			return routeList;
		} catch (Exception ex) {
			Log.e("RouteInfoBroker", ex.toString());
		}
		return null;
	}

	@Override
	protected void onPostExecute(List<RouteModel> routeList) {
		progress.dismiss();
		super.onPostExecute(routeList);
		activity.setContentView(R.layout.activity_route);
		RelativeLayout headerLayout = (RelativeLayout) activity
				.findViewById(R.id.arrival_info_page_header);

		int i = 0;
		int id = 2000;
		if (routeList == null) {
			LinearLayout ll = (LinearLayout) activity
					.findViewById(R.id.activity_arrival_layout);
			ll.removeAllViewsInLayout();
			Toast.makeText(
					activity,
					"Could not retrieve information. Please make sure that network and GPS are available",
					Toast.LENGTH_LONG).show();
		} else {
			RelativeLayout rl = (RelativeLayout) activity
					.findViewById(R.id.route_list_layout);
			LinearLayout ll = new LinearLayout(activity);
			android.widget.LinearLayout.LayoutParams params1 = new android.widget.LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 2f);

			// rl.removeView(rl.findViewById(R.id.ScrollView01));
			TextView selectRoute = new TextView(activity);
			selectRoute.setText("Select " + agency.getName() + " Route");
			selectRoute.setTypeface(null, Typeface.BOLD);
			selectRoute.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
			selectRoute.setTextColor(Color.rgb(0, 102, 102));
			// selectRoute.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

			ll.addView(selectRoute, params1);
			headerLayout.addView(ll);
			boolean even = false;
			for (final RouteModel info : routeList) {

				RouteButton button = new RouteButton(activity);
				if (info.getRouteDirectionName() == null
						|| info.getRouteDirectionName().trim().isEmpty()
						|| info.getRouteDirectionName().equals("null")) {
					button.setText(info.getRouteName());
				} else {
					button.setText(info.getRouteName() + "("
							+ info.getRouteDirectionName() + ")");
				}

				RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);
				p.setMargins(4, 2, 0, 0);
				p.width = ViewGroup.LayoutParams.MATCH_PARENT;
				p.height = (int) (activity.getResources().getDisplayMetrics().density * 40);// 80
				button.setId(id);
				if (i > 0)
					p.addRule(RelativeLayout.BELOW, id - 1);
				button.setBackgroundForRow(even);
				even = !even;
				button.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(activity,
								RouteActivity.class);
						intent.putExtra("routeInfo", info);
						activity.startActivity(intent);
					}
				});

				rl.addView(button, p);
				id++;
				i++;
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
}
