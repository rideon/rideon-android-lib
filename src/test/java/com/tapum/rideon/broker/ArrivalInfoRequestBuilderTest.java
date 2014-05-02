package com.tapum.rideon.broker;

import junit.framework.Assert;
import android.test.AndroidTestCase;

import com.tapum.api.rideon.model.Agency;

import junit.framework.*;

public class ArrivalInfoRequestBuilderTest extends AndroidTestCase {
	
	public void testRemoteCall() {
		try {
			ArrivalInfoRequestBuilder builder = new ArrivalInfoRequestBuilder();
			Agency agency = Agency.BART;
			String stopCode = "unit-stopCode";
			String mPhoneNumber = "unit-phone";
			String deviceId = "unit-device";
			String softwareVersion = "unit-software";
			final String reqLine = builder
					.withScheduleProvider(agency.getType())
					.withAgencyName(agency.getName()).withStopCode(stopCode)
					.withPhoneNumber(mPhoneNumber).withDeviceId(deviceId)
					.withSoftwareVersion(softwareVersion).build();
			System.out.println(reqLine);
		} catch (Exception ex) {
			Assert.fail(ex.getMessage());
		}

	}

}
