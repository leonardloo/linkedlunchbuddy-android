package com.linkedlunchbuddy;

import com.appspot.linear_axle_547.requestcontroller.Requestcontroller;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;
import com.linkedlunchbuddy.requestendpoint.Requestendpoint;
import com.linkedlunchbuddy.userendpoint.Userendpoint;

public class EndpointController {

	private static Userendpoint userEndpoint;
	private static Requestendpoint requestEndpoint;
	private static Requestcontroller requestController;

	public static Userendpoint getUserEndpoint() {
		if (userEndpoint == null) {
			Userendpoint.Builder userEndpointBuilder = new Userendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					new HttpRequestInitializer() {
						public void initialize(HttpRequest httpRequest) {
						}
					});
			userEndpoint = CloudEndpointUtils.updateBuilder(
					userEndpointBuilder).build();
		}
		return userEndpoint;
	}

	public static Requestendpoint getRequestEndpoint() {
		if (requestEndpoint == null) {
			Requestendpoint.Builder requestEndpointBuilder = new Requestendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					new HttpRequestInitializer() {
						public void initialize(HttpRequest httpRequest) {
						}
					});
			requestEndpoint = CloudEndpointUtils.updateBuilder(
					requestEndpointBuilder).build();
		}
		return requestEndpoint;
	}
	
	public static Requestcontroller getRequestController() {
		if (requestController == null) {
			Requestcontroller.Builder requestControllerBuilder = new Requestcontroller.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					new HttpRequestInitializer() {
						public void initialize(HttpRequest httpRequest) {
						}
					});
			requestController = CloudEndpointUtils.updateBuilder(
					requestControllerBuilder).build();
		}
		return requestController;
	}

}
