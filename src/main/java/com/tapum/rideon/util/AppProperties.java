package com.tapum.rideon.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

/**
 * Properties object reads from app.properties file and make them available for
 * rest of the app to use.
 * 
 * @author Neeraj Jain
 * 
 */

public class AppProperties {
	private static Properties properties;
	private static final String fileName = "app.properties";

	public static Properties get() {
		return properties;
	}

	/**
	 * Initialize the properties only once during app's runtime.
	 * 
	 * @param context
	 * @return
	 */
	public static Properties init(Context context) {
		if (properties == null)
			new AppProperties(context);
		return properties;
	}

	private AppProperties(Context context) {
		/**
		 * Constructs a new Properties object.
		 */
		properties = new Properties();

		try {
			/**
			 * getAssets() Return an AssetManager instance for your
			 * application's package. AssetManager Provides access to an
			 * application's raw asset files;
			 */
			AssetManager assetManager = context.getAssets();
			/**
			 * Open an asset using ACCESS_STREAMING mode. This
			 */
			Log.d("properties", "loading-" + fileName);

			InputStream inputStream = assetManager.open(fileName);
			/**
			 * Loads properties from the specified InputStream,
			 */
			properties.load(inputStream);

		} catch (IOException e) {
			Log.e("AssetsPropertyReader", e.toString());
		}
	}
}
