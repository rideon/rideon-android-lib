package com.tapum.rideon;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * This class is not in use at the moment.
 * 
 * @author Neeraj Jain
 * 
 */

public class AppInstalled extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("AppInstalled", "installed");
	}

}
