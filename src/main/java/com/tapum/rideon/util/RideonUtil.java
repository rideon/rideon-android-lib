package com.tapum.rideon.util;

import java.net.URLEncoder;

import android.util.DisplayMetrics;

/**
 * 
 * @author Neeraj Jain
 * 
 */

public class RideonUtil {

	public static String getEncodedString(String toEncode) throws Exception {
		if (toEncode == null)
			return "";
		return URLEncoder.encode(toEncode, "utf-8");
	}

	public static int getDPI(int size, DisplayMetrics metrics) {
		return (size * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;
	}

}
