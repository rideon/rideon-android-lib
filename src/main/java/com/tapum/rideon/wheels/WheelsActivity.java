package com.tapum.rideon.wheels;

import java.util.HashMap;

import com.tapum.rideon.lib.R;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.tapum.rideon.AbstractEasyCommuteActivity;
import com.tapum.rideon.util.AppProperties;

/**
 * 
 * @author Neeraj Jain
 * 
 */

public class WheelsActivity extends AbstractEasyCommuteActivity {

	private static HashMap<String, String> stopMap = new HashMap<String, String>();
	private static HashMap<String, String> stopMap10W = new HashMap<String, String>();
	private static HashMap<String, String> stopMap8A = new HashMap<String, String>();
	private static HashMap<String, String> stopMap8B = new HashMap<String, String>();
	private static HashMap<String, String> stopMap1C = new HashMap<String, String>();

	static int startUp = 5;
	static Spinner spinner;
	static int position;
	// static AdapterView<?> adapterView;
	static boolean ref = false;

	static {
		int i = 0;
		stopMap.put("" + i++, "533");
		stopMap.put("" + i++, "805");
		stopMap.put("" + i++, "1178");
		stopMap.put("" + i++, "1173");
		stopMap.put("" + i++, "522");
		stopMap.put("" + i++, "523");
		stopMap.put("" + i++, "520");
		stopMap.put("" + i++, "519");

		stopMap.put("" + i++, "1192");
		stopMap.put("" + i++, "521");
		stopMap.put("" + i++, "1239");
		stopMap.put("" + i++, "399");
		stopMap.put("" + i++, "403");
		stopMap.put("" + i++, "406");
		stopMap.put("" + i++, "400");
		stopMap.put("" + i++, "401");
		stopMap.put("" + i++, "405");
		stopMap.put("" + i++, "404");
		stopMap.put("" + i++, "447");
		stopMap.put("" + i++, "402");
		stopMap.put("" + i++, "594");
		stopMap.put("" + i++, "534");
		stopMap.put("" + i++, "584");
		stopMap.put("" + i++, "1082");
		stopMap.put("" + i++, "593");
		stopMap.put("" + i++, "581");
		stopMap.put("" + i++, "583");
		stopMap.put("" + i++, "582");
		stopMap.put("" + i++, "592");
		stopMap.put("" + i++, "1241");
		stopMap.put("" + i++, "1238");
		stopMap.put("" + i++, "77");
		stopMap.put("" + i++, "76");
		stopMap.put("" + i++, "590");
		stopMap.put("" + i++, "591");
		stopMap.put("" + i++, "586");
		stopMap.put("" + i++, "589");
		stopMap.put("" + i++, "585");
		stopMap.put("" + i++, "587");
		stopMap.put("" + i++, "588");
		stopMap.put("" + i++, "596");
		stopMap.put("" + i++, "595");
		stopMap.put("" + i++, "355");
		stopMap.put("" + i++, "354");
		stopMap.put("" + i++, "74");
		stopMap.put("" + i++, "597");
		stopMap.put("" + i++, "75");
		stopMap.put("" + i++, "1243");
		stopMap.put("" + i++, "532");
		stopMap.put("" + i++, "1175");
		stopMap.put("" + i++, "1176");

		i = 0;
		stopMap10W.put("" + i++, "600");
		stopMap10W.put("" + i++, "537");
		stopMap10W.put("" + i++, "1179");
		stopMap10W.put("" + i++, "1174");
		stopMap10W.put("" + i++, "1083");
		stopMap10W.put("" + i++, "524");
		stopMap10W.put("" + i++, "526");
		stopMap10W.put("" + i++, "525");
		stopMap10W.put("" + i++, "527");
		stopMap10W.put("" + i++, "412");
		stopMap10W.put("" + i++, "410");
		stopMap10W.put("" + i++, "416");
		stopMap10W.put("" + i++, "415");
		stopMap10W.put("" + i++, "414");
		stopMap10W.put("" + i++, "411");
		stopMap10W.put("" + i++, "413");
		stopMap10W.put("" + i++, "447");
		stopMap10W.put("" + i++, "409");
		stopMap10W.put("" + i++, "536");
		stopMap10W.put("" + i++, "611");
		stopMap10W.put("" + i++, "344");
		stopMap10W.put("" + i++, "603");
		stopMap10W.put("" + i++, "429");
		stopMap10W.put("" + i++, "601");
		stopMap10W.put("" + i++, "612");
		stopMap10W.put("" + i++, "106");
		stopMap10W.put("" + i++, "105");
		stopMap10W.put("" + i++, "602");
		stopMap10W.put("" + i++, "79");
		stopMap10W.put("" + i++, "78");
		stopMap10W.put("" + i++, "1240");
		stopMap10W.put("" + i++, "604");
		stopMap10W.put("" + i++, "605");
		stopMap10W.put("" + i++, "606");
		stopMap10W.put("" + i++, "608");
		stopMap10W.put("" + i++, "610");
		stopMap10W.put("" + i++, "609");
		stopMap10W.put("" + i++, "607");
		stopMap10W.put("" + i++, "538");
		stopMap10W.put("" + i++, "599");
		stopMap10W.put("" + i++, "598");
		stopMap10W.put("" + i++, "768");
		stopMap10W.put("" + i++, "80");
		stopMap10W.put("" + i++, "357");
		stopMap10W.put("" + i++, "356");
		stopMap10W.put("" + i++, "532");
		stopMap10W.put("" + i++, "569");
		stopMap10W.put("" + i++, "1175");
		i = 0;
		stopMap8A.put("" + i++, "617");
		stopMap8A.put("" + i++, "616");
		stopMap8A.put("" + i++, "615");
		stopMap8A.put("" + i++, "624");
		stopMap8A.put("" + i++, "702");
		stopMap8A.put("" + i++, "1173");
		stopMap8A.put("" + i++, "651");
		stopMap8A.put("" + i++, "625");
		stopMap8A.put("" + i++, "594");
		stopMap8A.put("" + i++, "1119");
		stopMap8A.put("" + i++, "619");
		stopMap8A.put("" + i++, "618");
		stopMap8A.put("" + i++, "1058");
		stopMap8A.put("" + i++, "1057");
		stopMap8A.put("" + i++, "1056");
		stopMap8A.put("" + i++, "655");
		stopMap8A.put("" + i++, "656");
		stopMap8A.put("" + i++, "652");
		stopMap8A.put("" + i++, "653");
		stopMap8A.put("" + i++, "657");
		stopMap8A.put("" + i++, "654");
		stopMap8A.put("" + i++, "611");
		stopMap8A.put("" + i++, "603");
		stopMap8A.put("" + i++, "593");
		stopMap8A.put("" + i++, "1216");
		stopMap8A.put("" + i++, "612");
		stopMap8A.put("" + i++, "578");
		stopMap8A.put("" + i++, "106");
		stopMap8A.put("" + i++, "105");
		stopMap8A.put("" + i++, "602");
		stopMap8A.put("" + i++, "604");
		stopMap8A.put("" + i++, "605");
		stopMap8A.put("" + i++, "606");
		stopMap8A.put("" + i++, "608");
		stopMap8A.put("" + i++, "610");
		stopMap8A.put("" + i++, "609");
		stopMap8A.put("" + i++, "607");
		stopMap8A.put("" + i++, "623");
		stopMap8A.put("" + i++, "643");
		stopMap8A.put("" + i++, "644");
		stopMap8A.put("" + i++, "641");
		stopMap8A.put("" + i++, "642");
		stopMap8A.put("" + i++, "650");
		stopMap8A.put("" + i++, "648");
		i = 0;

		stopMap8B.put("" + i++, "632");
		stopMap8B.put("" + i++, "624");
		stopMap8B.put("" + i++, "628");
		stopMap8B.put("" + i++, "627");
		stopMap8B.put("" + i++, "626");
		stopMap8B.put("" + i++, "702");
		stopMap8B.put("" + i++, "1173");
		stopMap8B.put("" + i++, "651");
		stopMap8B.put("" + i++, "625");
		stopMap8B.put("" + i++, "594");
		stopMap8B.put("" + i++, "687");
		stopMap8B.put("" + i++, "634");
		stopMap8B.put("" + i++, "631");
		stopMap8B.put("" + i++, "630");
		stopMap8B.put("" + i++, "633");
		stopMap8B.put("" + i++, "655");
		stopMap8B.put("" + i++, "656");
		stopMap8B.put("" + i++, "652");
		stopMap8B.put("" + i++, "653");
		stopMap8B.put("" + i++, "657");
		stopMap8B.put("" + i++, "654");
		stopMap8B.put("" + i++, "584");
		stopMap8B.put("" + i++, "593");
		stopMap8B.put("" + i++, "1216");
		stopMap8B.put("" + i++, "576");
		stopMap8B.put("" + i++, "581");
		stopMap8B.put("" + i++, "583");
		stopMap8B.put("" + i++, "582");
		stopMap8B.put("" + i++, "577");
		stopMap8B.put("" + i++, "592");
		stopMap8B.put("" + i++, "590");
		stopMap8B.put("" + i++, "591");
		stopMap8B.put("" + i++, "586");
		stopMap8B.put("" + i++, "589");
		stopMap8B.put("" + i++, "585");
		stopMap8B.put("" + i++, "587");
		stopMap8B.put("" + i++, "588");
		stopMap8B.put("" + i++, "623");
		stopMap8B.put("" + i++, "629");
		stopMap8B.put("" + i++, "1033");
		stopMap8B.put("" + i++, "1272");
		stopMap8B.put("" + i++, "650");
		stopMap8B.put("" + i++, "648");
		i = 0;
		stopMap1C.put("" + i++, "1179");
		stopMap1C.put("" + i++, "559");
		stopMap1C.put("" + i++, "88");
		stopMap1C.put("" + i++, "97");
		stopMap1C.put("" + i++, "96");
		stopMap1C.put("" + i++, "95");
		stopMap1C.put("" + i++, "89");
		stopMap1C.put("" + i++, "90");
		stopMap1C.put("" + i++, "91");
		stopMap1C.put("" + i++, "86");
		stopMap1C.put("" + i++, "84");
		stopMap1C.put("" + i++, "85");
		stopMap1C.put("" + i++, "87");
		stopMap1C.put("" + i++, "106");
		stopMap1C.put("" + i++, "105");
		stopMap1C.put("" + i++, "99");
		stopMap1C.put("" + i++, "100");
		stopMap1C.put("" + i++, "101");

	}

	WheelsBroker wheelsBroker = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppProperties.init(this);
		setContentView(R.layout.activity_wheels);
		wheelsBroker = new WheelsBroker(this);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
		{
			Spinner spinner = (Spinner) findViewById(R.id.bus_stop_spinner_1c);
			setStatusSpinner(spinner, R.array.bus_stop_1c, "1", "109", "C",
					stopMap1C);
		}
		{
			Spinner spinner = (Spinner) findViewById(R.id.bus_stop_spinner_8a);
			setStatusSpinner(spinner, R.array.bus_stop_8a, "8", "136", "A",
					stopMap8A);
		}
		{
			Spinner spinner = (Spinner) findViewById(R.id.bus_stop_spinner_8b);
			setStatusSpinner(spinner, R.array.bus_stop_8b, "8", "137", "B",
					stopMap8B);
		}
		{
			Spinner spinner = (Spinner) findViewById(R.id.bus_stop_spinner_10w);
			setStatusSpinner(spinner, R.array.bus_stop_10w, "10", "17", "W",
					stopMap10W);
		}
		{
			Spinner spinner = (Spinner) findViewById(R.id.bus_stop_spinner_10e);
			setStatusSpinner(spinner, R.array.bus_stop_10e, "10", "15", "E",
					stopMap);
		}

	}

	@Override
	protected void onStop() {
		Log.i("onStop", "startup=" + WheelsActivity.startUp);
		WheelsActivity.startUp = 5;
		super.onStop();
	}

	private void setStatusSpinner(Spinner spinner, int data, String busNumber,
			String direction, String directionText, HashMap<String, String> map) {
		Log.i("setStatusSpinner", "startup=" + WheelsActivity.startUp);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, data, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new Number10OnItemSelectedListener(
				spinner, this, busNumber, direction, directionText, map));

	}

	// @Override
	public void refreshPage() {
		Log.d("WheelsActivity", "refresh, position=" + position);
		ref = true;
		if (spinner != null) {
			if (WheelsActivity.position != 0)
				spinner.setSelection(0, false);
			else spinner.setSelection(1, false);
		}
	}

}

class Number10OnItemSelectedListener implements OnItemSelectedListener {
	WheelsActivity parentActivity;

	private String busNumber;
	private String direction;
	private String directionText;
	private HashMap<String, String> map;
	private Spinner spinner;

	public Number10OnItemSelectedListener(Spinner spinner,
			WheelsActivity activity, String busNumber, String direction,
			String directionText, HashMap<String, String> map) {
		this.parentActivity = activity;
		this.busNumber = busNumber;
		this.direction = direction;
		this.map = map;
		this.directionText = directionText;
		this.spinner = spinner;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		Log.i("Number10OnItemSelectedListener", "startup="
				+ WheelsActivity.startUp);
		if (WheelsActivity.startUp > 0) {
			WheelsActivity.startUp--;
			return;
		}
		if (WheelsActivity.ref) {
			WheelsActivity.ref = false;
			Log.i("Number10OnItemSelectedListener", "return");
			pos = WheelsActivity.position;
			spinner.setSelection(pos, true);
			return;
		}
		// TextView tv = null;
		{
			Log.i("Number10OnItemSelectedListener",
					"pos=" + pos + ", view=" + parent.getId() + ", pos str="
							+ parent.getItemAtPosition(pos).toString());
			// tv = (TextView)
			// parentActivity.findViewById(R.id.next_bus_at_10e);
			// tv.setText("Retrieving");

			String stopNumber = "";
			stopNumber = map.get("" + pos);

			Log.d("Number10OnItemSelectedListener", "stopNumber=" + stopNumber);
			WheelsBroker wheelsBroker = new WheelsBroker(parentActivity);
			wheelsBroker.execute(busNumber, stopNumber, direction,
					directionText, parent.getItemAtPosition(pos).toString());
			// WheelsBroker.getSchedule(busNumber, stopNumber,
			// direction);
			// Log.i("Number10OnItemSelectedListener", "schedule=" + sch);

			// tv.setText("Bus number-" + busNumber + directionText + "\n"
			// + parent.getItemAtPosition(pos).toString() + "\n" + sch);
			WheelsActivity.spinner = spinner;
			WheelsActivity.position = pos;
		}

	}

	public void onNothingSelected(AdapterView<?> parent) {
		// Another interface callback
		Log.d("StatusSpinnerOnItemSelectedListener", "Nothing selected");
	}

}

class WheelsLocationListener implements LocationListener {
	public void onLocationChanged(Location location) {
		if (location != null) {
			// System.out.println("La localizacion es: (lon: "
			// + location.getLongitude() + ", lat: "
			// + location.getLatitude() + ")");
			// doYourMapMagicBaby(location);
		} else {
			// System.out.println("La location es nula");
		}

	}

	@Override
	public void onProviderDisabled(String provider) {
		Log.i("onProviderDisabled", provider);

	}

	@Override
	public void onProviderEnabled(String provider) {
		Log.i("onProviderEnabled", provider);

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Log.i("onStatusChanged", provider);

	}
}