package com.tapum.rideon.favorite;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tapum.api.rideon.model.Favorite;
import com.tapum.rideon.util.AppConstants;

/**
 * 
 * @author Neeraj Jain
 * 
 */
public class FavoriteSubmissionBroker extends AsyncTask<String, Void, String> {
	// private ProgressDialog progress = null;
	private Activity activity;

	public FavoriteSubmissionBroker(Activity activity) {
		this.activity = activity;
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			String agency = params[0];
			String stopCode = params[1];
			String stationId = params[2];
			Log.i("FavoriteSubmissionBroker", "doInBackground");
			final TelephonyManager tMgr = (TelephonyManager) activity
					.getSystemService(Context.TELEPHONY_SERVICE);
			String mPhoneNumber = tMgr.getLine1Number();

			String deviceId = tMgr.getDeviceId();
			String softwareVersion = tMgr.getDeviceSoftwareVersion();

			HttpClient httpclient = new DefaultHttpClient();

			String URL = AppConstants.baseApiUrl + "api/device/favorite/";

			HttpPost post = new HttpPost(URL);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
			nameValuePairs
					.add(new BasicNameValuePair("abbreviation", stopCode));
			nameValuePairs.add(new BasicNameValuePair("phoneNumber",
					mPhoneNumber));
			nameValuePairs.add(new BasicNameValuePair("agency", agency));
			nameValuePairs.add(new BasicNameValuePair("stationId", stationId));
			nameValuePairs
					.add(new BasicNameValuePair("clientId", mPhoneNumber));
			nameValuePairs.add(new BasicNameValuePair("clientId", deviceId));
			nameValuePairs.add(new BasicNameValuePair("clientId",
					softwareVersion));
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			ResponseHandler<String> handler = new BasicResponseHandler();
			String result = httpclient.execute(post, handler);
			FavoriteBroker.favorites = parseJson(result);
			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					activity.invalidateOptionsMenu();
				}
			});

		} catch (Exception ex) {
			Log.e("FavoriteSubmissionBroker", ex.toString());
		}
		return "success";
	}

	@Override
	protected void onPreExecute() {
		// progress = ProgressDialog.show(activity, null, "Loading results");
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(String result) {
		// progress.dismiss();
		super.onPostExecute(result);

	}

	private List<Favorite> parseJson(String json) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
					false);
			ArrayList<Favorite> etdInfos = mapper.readValue(
					new ByteArrayInputStream(json.getBytes()),
					new TypeReference<ArrayList<Favorite>>() {});
			return etdInfos;

		} catch (Exception ex) {
			Log.e("FavoriteBroker", ex.toString());
		}
		return null;

	}

}
