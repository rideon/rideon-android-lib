package com.tapum.rideon.favorite;

import static com.tapum.rideon.util.AppConstants.DEVICE_ID;
import static com.tapum.rideon.util.AppConstants.PHONE_NUMBER;
import static com.tapum.rideon.util.AppConstants.PHONE_NUMBER_CLIENT_ID;
import static com.tapum.rideon.util.AppConstants.SOFTWARE_VERSION;
import static com.tapum.rideon.util.AppConstants.apikey;

import com.tapum.rideon.util.AppConstants;
import com.tapum.rideon.util.RideonUtil;

public class FavoriteInfoRequestBuilder {

	private final StringBuilder request = new StringBuilder();

	private String phoneNumber;
	private String deviceId;
	private String softwareVersion;

	public FavoriteInfoRequestBuilder withPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}

	public FavoriteInfoRequestBuilder withDeviceId(String deviceId) {
		this.deviceId = deviceId;
		return this;
	}

	public FavoriteInfoRequestBuilder withSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
		return this;
	}

	private void appendParam(String name, String value) {
		request.append("&").append(name).append("=").append(value);
	}

	public String build() throws Exception {
		request.append(AppConstants.baseApiUrl + "api/device/favorite/")
				.append("?apikey=").append(apikey);
		appendParam(PHONE_NUMBER_CLIENT_ID, phoneNumber);
		appendParam(DEVICE_ID, RideonUtil.getEncodedString(deviceId));
		appendParam(SOFTWARE_VERSION,
				RideonUtil.getEncodedString(softwareVersion));
		appendParam(PHONE_NUMBER, phoneNumber);

		return request.toString();
	}
}
