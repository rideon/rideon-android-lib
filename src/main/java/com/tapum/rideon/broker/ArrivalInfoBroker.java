package com.tapum.rideon.broker;

import static com.tapum.api.rideon.model.ScheduleProvider.GTFS;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tapum.api.rideon.model.Agency;
import com.tapum.api.rideon.model.BartDepartureEstimate;
import com.tapum.api.rideon.model.EtdInfo;
import com.tapum.api.rideon.model.StationInfo;
import com.tapum.rideon.lib.R;
import com.tapum.rideon.view.DisplayOptions;

/**
 * 
 * @author Neeraj Jain
 * 
 */

public class ArrivalInfoBroker extends AsyncTask<String, Void, List<EtdInfo>> {

	public static ArrivalInfoBroker instance;
	private ProgressDialog progress = null;

	private Activity activity;
	private static HttpClient httpclient;
	private String stopName = "";
	private String stopCode = "";

	private DisplayOptions option;
	private Agency agency;
	private StationInfo stationInfo;
	private static DisplayMetrics metrics = new DisplayMetrics();

	private float weight = 1.0f;

	public ArrivalInfoBroker(Activity activity, Agency agency,
			StationInfo stationInfo) {
		this.activity = activity;
		this.agency = agency;
		this.stationInfo = stationInfo;
	}

	@Override
	protected List<EtdInfo> doInBackground(String... stationCode) {
		instance = this;
		Log.i("ArrivalInfoBroker", "doInBackground");
		if (httpclient == null)
			httpclient = new DefaultHttpClient();

		option = new DisplayOptions();
		option.setDisplayBikeInfo(true);
		option.setDisplayLength(true);
		option.setDisplayColor(false);

		stopName = stationCode[1];
		stopCode = stationCode[0];

		return remoteCall(stopCode);
	}

	private List<EtdInfo> remoteCall(String stopCode) {
		try {
			final TelephonyManager tMgr = (TelephonyManager) activity
					.getSystemService(Context.TELEPHONY_SERVICE);
			String mPhoneNumber = tMgr.getLine1Number();
			String deviceId = tMgr.getDeviceId();
			String softwareVersion = tMgr.getDeviceSoftwareVersion();
			ArrivalInfoRequestBuilder builder = new ArrivalInfoRequestBuilder();
			final String reqLine = builder
					.withScheduleProvider(agency.getType())
					.withAgencyName(agency.getName()).withStopCode(stopCode)
					.withPhoneNumber(mPhoneNumber).withDeviceId(deviceId)
					.withSoftwareVersion(softwareVersion).build();

			if (agency.getType() == GTFS) {
				weight = 2.5f;
			}
			// Log.i("ArrivalInfoBroker-reqLine=", reqLine.toString());

			HttpGet request = new HttpGet(reqLine.toString());
			ResponseHandler<String> handler = new BasicResponseHandler();
			String result = httpclient.execute(request, handler);
			// Log.i("ArrivalInfoBroker-getSchedule", result);

			List<EtdInfo> etdList = parseJson(result);

			return etdList;
		} catch (Exception ex) {
			Log.e("ArrivalInfoBroker-remoteCall", ex.toString());
			return null;
		} finally {

		}
	}

	private List<EtdInfo> parseJson(String json) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
					false);
			ArrayList<EtdInfo> etdInfos = mapper.readValue(
					new ByteArrayInputStream(json.getBytes()),
					new TypeReference<ArrayList<EtdInfo>>() {});
			return etdInfos;

		} catch (Exception ex) {
			Log.e("ArrivalInfoBroker", ex.toString());
		}
		return null;
	}

	@Override
	protected void onPostExecute(List<EtdInfo> etdList) {
		progress.dismiss();

		super.onPostExecute(etdList);
		RelativeLayout headerLayout = (RelativeLayout) activity
				.findViewById(R.id.arrival_info_page_header);
		headerLayout.removeAllViews();
		headerLayout.removeAllViewsInLayout();
		headerLayout.setPadding(0, 0, 0, 0);

		RelativeLayout scheduleOutputLayout = (RelativeLayout) activity
				.findViewById(R.id.schedule_output_layout);

		scheduleOutputLayout.removeAllViews();
		scheduleOutputLayout.removeAllViewsInLayout();
		scheduleOutputLayout.setPadding(0, 0, 0, 0);
		int id = 1000;

		/**
		 * Set the click listener for refresh button, just call the
		 * ArrivalInfoBroker with current stopCode and stopName
		 */
		activity.invalidateOptionsMenu();
		if (etdList == null) {
			addRow("Arriving at " + stopName, "", headerLayout, id++, /* singleColumn */
					false, /* pageTitle */
					true, /* header */false);
			addRow(agency.getName() + " towards", "Departure info", headerLayout,
					id++, false, false, true);
			Toast.makeText(
					activity,
					"Could not retrieve information. Please make sure that network and GPS are available",
					Toast.LENGTH_LONG).show();

		} else {
			addRow("Arriving at " + stopName, "", headerLayout, id++, false,
					true, false);
			addRow(agency.getName() + " towards", "Departure info", headerLayout,
					id++, false, false, true);
			if (etdList.size() < 1) {
				addRow("", "No schedule found", scheduleOutputLayout, id++,
						false, false, false);
			}
			for (EtdInfo etd : etdList) {
				String dispString = "";
				if (etd.getEstimates() == null || etd.getEstimates().isEmpty()) {
					Log.d("EtdInfo", "no estimates found");
					addRow(etd.getStationInfo().getDisplayName(),
							"No schedule found", scheduleOutputLayout, id++,
							false, false, false);
				}
				boolean newStation = true;
				for (BartDepartureEstimate disp : etd.getEstimates()) {
					dispString = "";
					try {
						dispString += Integer.parseInt(disp.getMinutes())
								+ " min";
					} catch (NumberFormatException parseEx) {
						dispString += disp.getMinutes();
					}
					if (option.isDisplayLength()) {
						if (disp.getLength() != null
								&& !disp.getLength().equals("null"))
							dispString += "/" + disp.getLength() + " car";
					}
					if (option.isDisplayBikeInfo()) {
						if (disp.getBikeFlag() == 1) {
							dispString += "/bike ok";
						} else if (disp.getBikeFlag() == 1) {
							dispString += "/no bike";
						}
					}
					if (newStation) {
						id++;
						addRow(etd.getStationInfo().getDisplayName(),
								dispString, scheduleOutputLayout, id++, false,
								false, true);
					} else {
						addRow("", dispString, scheduleOutputLayout, id++,
								false);
					}
					newStation = false;
				}
			}
		}
	}

	private void addRow(String left, String right, RelativeLayout rl,
			int prevId, boolean singleColumn) {
		addRow(left, right, rl, prevId, singleColumn, false, false);
	}

	public static int getDPI(int size, DisplayMetrics metrics) {
		return (size * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;
	}

	// @SuppressWarnings("deprecation")
	private void addRow(String left, String right, RelativeLayout rl,
			int prevId, boolean singleColumn, boolean pageTitle, boolean header) {
		LinearLayout ll = new LinearLayout(activity);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		ll.setLayoutParams(lp);
		ll.setId(prevId);
		lp.setMargins(0, 0, 0, 0);
		RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
		params.setMargins(0, 0, 10, 0);
		ll.setOrientation(LinearLayout.HORIZONTAL);
		TextView leftSide = new TextView(activity);
		leftSide.setText(left);
		if (header) {
			leftSide.setTypeface(null, Typeface.BOLD);
		}
		if (pageTitle) {
			leftSide.setTypeface(null, Typeface.BOLD);
			leftSide.setTextColor(Color.rgb(0, 102, 0));
			leftSide.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
		}
		// if (pageTitle) routeName.setTextColor(colors)
		ll.addView(leftSide, params);
		if (!singleColumn) {
			TextView rightSide = new TextView(activity);
			rightSide.setText(right);
			android.widget.LinearLayout.LayoutParams params1 = new android.widget.LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
					weight);
			if (header) {
				rightSide.setTypeface(null, Typeface.BOLD);
			}
			if (pageTitle) {

				activity.getWindowManager().getDefaultDisplay()
						.getMetrics(metrics);
				params1 = new android.widget.LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
						1.0f);
			} else {
				ll.addView(rightSide, params1);
			}
		}
		if (prevId > 1000) {
			if (header && prevId > 1003) {
				LinearLayout ll1 = new LinearLayout(activity);
				android.widget.LinearLayout.LayoutParams params1 = new android.widget.LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,
						1.0f);
				ImageView view = new ImageView(activity);
				view.setImageDrawable(activity.getResources().getDrawable(
						R.drawable.horizontal_line));
				ll1.setId(prevId - 1);
				ll1.addView(view, params1);
				RelativeLayout.LayoutParams p1 = new RelativeLayout.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.MATCH_PARENT);
				p1.addRule(RelativeLayout.BELOW, prevId - 2);
				rl.addView(ll1, p1);
				p.addRule(RelativeLayout.BELOW, prevId - 1);
			} else p.addRule(RelativeLayout.BELOW, prevId - 1);
		}
		rl.addView(ll, p);

	}

	@Override
	protected void onPreExecute() {
		progress = ProgressDialog
				.show(activity, null, "Loading real time info");
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

	public String getStopName() {
		return stopName;
	}

	public void setStopName(String stopName) {
		this.stopName = stopName;
	}

	public String getStopCode() {
		return stopCode;
	}

	public void setStopCode(String stopCode) {
		this.stopCode = stopCode;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Agency getAgency() {
		return agency;
	}

//	public void setAgency(String agency) {
//		this.agencyName = agency;
//	}

	public StationInfo getStationInfo() {
		return stationInfo;
	}

	public void setStationInfo(StationInfo stationInfo) {
		this.stationInfo = stationInfo;
	}
}
