package com.linkedlunchbuddy.placesapi;



public class ApiRequestBuilder {

	

	public static String getRequestObject(double lat, double lng,
			double radius, String key, String url) {

		StringBuilder temp = new StringBuilder(url);

		temp.append("location=" + String.valueOf(lat) + ","
				+ String.valueOf(lng));
		temp.append("&");
		temp.append("radius=" + String.valueOf(radius));
		temp.append("&");
		temp.append("sensor=false");
		temp.append("&");
		temp.append("key=" + key);
		temp.append("&");
		temp.append("types=restaurant");

		return temp.toString();
	}
}
