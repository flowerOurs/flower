package com.huhuo.apns;

import java.util.Date;
import java.util.Map;

import org.junit.Test;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;

public class ApnsSenderTest {

	@Test
	public void sendTest() {
		/**
		 * https://github.com/notnoop/java-apns
		 */
		// 1. Setup the connection
		String certFileName = "d:/apn_developer_identity.p12";
		String password = "123456";
		ApnsService service = APNS.newService()
				.withCert(certFileName, password).withSandboxDestination()
				.build();
		// 2. Create and send the message
		String payload = APNS.newPayload().alertBody("Can't simple than this!").build();
//		String deviceToken = "afb49b04 884a34d3 86e90097 a9c4b425 d6f6e50b 304635d3 7b6dc33e 84330b58";
		String deviceToken = "8";
		service.push(deviceToken, payload);
		// 3. To query the feedback service for inactive devices
		Map<String, Date> inactiveDevices = service.getInactiveDevices();
		for(String token : inactiveDevices.keySet()) {
			Date date = inactiveDevices.get(token);
			System.out.println(date);
		}
	}
	
}
