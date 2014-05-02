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
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.ProgressDialog;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

	final static HashMap<String, String> routeState = new HashMap<String, String>();
	final static HashMap<String, String> routeValidation = new HashMap<String, String>();

	static {
		// 10W
		routeState.put("17", VIEWSTATE_17);
		// 10E
		routeState.put("15", VIEWSTATE_15);
		// 8A
		routeState.put("136", VIEWSTATE_136);
		// 8B
		routeState.put("137", VIEWSTATE_137);
		// 1C
		routeState.put("109", VIEWSTATE_109);
	}
	static {
		// 10W
		routeValidation.put("17", EVENTVALIDATION_17);
		// 10E
		routeValidation.put("15", EVENTVALIDATION_15);
		// 8A
		routeValidation.put("136", EVENTVALIDATION_136);
		// 8B
		routeValidation.put("137", EVENTVALIDATION_137);
		// 1C
		routeValidation.put("109", EVENTVALIDATION_109);

	}

	private String busNumber;
	private String stopName;
	private String directionText;

	public String getSchedule(String busNumber, String busStopNumber,
			String direction, String directionText, String stopName) {

		try {
			this.busNumber = busNumber;
			this.directionText = directionText;
			this.stopName = stopName;

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost request = new HttpPost(WHEELS_URL);

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("ddlRoutes", busNumber));
			nameValuePairs.add(new BasicNameValuePair("ddlDirections",
					direction));
			nameValuePairs
					.add(new BasicNameValuePair("ddlStops", busStopNumber));
			nameValuePairs.add(new BasicNameValuePair("btnCrossingTimes",
					"Get Arrival Times"));
			nameValuePairs.add(new BasicNameValuePair("__VIEWSTATE", routeState
					.get(direction)));
			nameValuePairs.add(new BasicNameValuePair("__EVENTVALIDATION",
					routeValidation.get(direction)));

			request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(request);
			// System.out.println(response.getEntity().getContent());
			// Log.i("wheelsbroker", "content="
			// + response.getEntity().getContent());

			BufferedReader is = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = is.readLine()) != null) {
				// Log.i("wheelsbroker", "line=" + line);
				sb.append(line);
			}
			String content = sb.toString();

			int startIndex = content
					.indexOf("CrossingTimes\" align=\"center\">");
			// System.out.println(startIndex);
			if (startIndex == -1) {
				startIndex = content.indexOf("No Schedule Found");
				if (startIndex >= 0) {
					return "No Schedule Found";
				} else {
					return "Internal Error";
				}
			} else {

				String patternStr = "CrossingTimes\" align=\"center\">(\\d{1,2}:\\d{1,2}\\s[AP]M)<";
				Pattern pattern = Pattern.compile(patternStr);
				Matcher matcher = pattern
						.matcher(content.substring(startIndex));
				String time = "";
				while (matcher.find()) {
					if (!time.isEmpty())
						time = time + ", ";
					time = time + matcher.group(1);
				}
				return time;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Try again";
	}

	@Override
	protected String doInBackground(String... params) {
		return getSchedule(params[0], params[1], params[2], params[3],
				params[4]);
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
