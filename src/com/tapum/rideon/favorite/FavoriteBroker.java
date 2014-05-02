package com.tapum.rideon.favorite;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tapum.api.rideon.model.Favorite;
import com.tapum.rideon.lib.R;

/**
 * 
 * @author Neeraj Jain
 * 
 */

public class FavoriteBroker extends AsyncTask<Void, Void, String> {
	private static Activity activity;
	public static List<Favorite> favorites = null;

	public FavoriteBroker(Activity activity) {
		FavoriteBroker.activity = activity;
	}

	@Override
	protected String doInBackground(Void... params) {
		try {
			Log.i("FavoriteBroker", "doInBackground");
			if (favorites == null) {
				final TelephonyManager tMgr = (TelephonyManager) activity
						.getSystemService(Context.TELEPHONY_SERVICE);
				String mPhoneNumber = tMgr.getLine1Number();
				String deviceId = tMgr.getDeviceId();
				String softwareVersion = tMgr.getDeviceSoftwareVersion();
				// String phoneNumber = "";

				// if (mPhoneNumber != null && !mPhoneNumber.trim().isEmpty())
				// phoneNumber = "&phoneNumber=" + mPhoneNumber;

				HttpClient httpclient = new DefaultHttpClient();

				FavoriteInfoRequestBuilder builder = new FavoriteInfoRequestBuilder();
				builder.withPhoneNumber(mPhoneNumber).withDeviceId(deviceId)
						.withSoftwareVersion(softwareVersion);
				final String reqLine = builder.build();
				// Log.i("FavoriteBroker, reqLine=", reqLine.toString());

				HttpGet request = new HttpGet(reqLine.toString());
				ResponseHandler<String> handler = new BasicResponseHandler();
				String result = httpclient.execute(request, handler);
				favorites = parseJson(result);
			}
			repaint();

		} catch (Exception ex) {
			Log.e("FavoriteBroker", ex.toString());
		}
		return "success";
	}

	private List<Favorite> parseJson(String json) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
					false);
			ArrayList<Favorite> favorites = mapper.readValue(
					new ByteArrayInputStream(json.getBytes()),
					new TypeReference<ArrayList<Favorite>>() {});
			return favorites;

		} catch (Exception ex) {
			Log.e("FavoriteBroker", ex.toString());
		}
		return null;
	}

	@Override
	protected void onPreExecute() {
		// progress = ProgressDialog.show(activity, null, "Loading results");
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		// super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
	}

	public static void repaint() {
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {

				RelativeLayout rl1 = (RelativeLayout) activity
						.findViewById(R.id.commuter_app_layout_main);

				ScrollView sv = new ScrollView(activity);

				RelativeLayout rl = new RelativeLayout(activity);

				RelativeLayout.LayoutParams p1 = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, (int) TypedValue
								.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
										180, activity.getResources()
												.getDisplayMetrics()));
				int prevId = R.id.commuter_app_layout;

				p1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				rl1.setId(prevId + 100);
				rl1.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
				p1.setMargins(0, 10, 0, 10);
				rl.setBackground(activity.getResources().getDrawable(
						R.drawable.roundbg));
				rl1.addView(sv, p1);

				if (favorites != null && favorites.size() > 0) {
					sv.addView(rl);
					prevId = rl.getId();
					RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT);
					TextView favoritesHeader = new TextView(activity);
					p.addRule(RelativeLayout.BELOW, prevId);
					favoritesHeader.setText("Favorites");
					favoritesHeader.setTypeface(null, Typeface.BOLD);
					favoritesHeader.setTextColor(Color.rgb(0, 102, 0));
					favoritesHeader.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
					favoritesHeader.setId(prevId + 100);
					prevId = favoritesHeader.getId();
					rl.addView(favoritesHeader, p);
				}
				for (final Favorite fav : favorites) {
					RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(
							LayoutParams.MATCH_PARENT, (int) TypedValue
									.applyDimension(
											TypedValue.COMPLEX_UNIT_DIP, 30,
											activity.getResources()
													.getDisplayMetrics()));
					p.setMargins(10, 10, 10, 10);
					Button favoriteButton = new Button(activity);
					favoriteButton.setPadding(0, 0, 0, 0);
					favoriteButton.setGravity(Gravity.TOP);
					favoriteButton.setBackground(activity.getResources()
							.getDrawable(R.drawable.roundfavbutton));
					favoriteButton.setTextColor(Color.rgb(17, 17, 17));
					favoriteButton.setText(fav.getStation().getName() + "("
							+ fav.getStation().getAgency() + ")");
					favoriteButton
							.setOnClickListener(new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									Intent intent = new Intent(activity
											.getApplicationContext(),
											FavoriteActivity.class);

									// StationInfo info = new StationInfo();
									// info.setAbbreviation(fav.getStation()
									// .getAbbreviation());
									// info.setName(fav.getStation().getName());
									// info.setAgency(fav.getStation().getAgency());
									// info.setRouteDirection(fav.getStation()
									// .getRouteDirection());
									intent.putExtra("stationInfo",
											fav.getStation());
									activity.startActivity(intent);
								}
							});
					p.addRule(RelativeLayout.BELOW, prevId);
					favoriteButton.setId(prevId + 100);
					rl.addView(favoriteButton, p);
					prevId = favoriteButton.getId();
				}
			}
		});
	}
}
