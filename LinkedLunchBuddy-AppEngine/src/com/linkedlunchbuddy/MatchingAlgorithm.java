package com.linkedlunchbuddy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class MatchingAlgorithm {

	private static final String DEFAULT_RESTAURANT_ID = "restaurantId";



	private static final long ONE_HOUR = 60 * 60;

	static class MatchResult {


		private double distanceScore;
		private double timeIntervalScore;
		private double score;
		private Request matchedRequest;
		private TimeInterval dateTimeInterval;
		private String matchedRestaurant;

		public MatchResult(double score, double timeIntervalScore,
				double distanceScore, Request request, TimeInterval interval, 
				String matchedRestaurant) {
			this.distanceScore = distanceScore;
			this.timeIntervalScore = timeIntervalScore;
			this.score = score;
			this.matchedRequest = request;
			this.dateTimeInterval = interval;
			this.matchedRestaurant = matchedRestaurant;

		}
	}

	// helper method to calculate distances between lat/long points
	static class Haversine {
		public static final double R = 6372.8; // In kilometers

		public static double haversine(double lat1, double lon1, double lat2,
				double lon2) {
			double dLat = Math.toRadians(lat2 - lat1);
			double dLon = Math.toRadians(lon2 - lon1);
			lat1 = Math.toRadians(lat1);
			lat2 = Math.toRadians(lat2);

			double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
					+ Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1)
					* Math.cos(lat2);
			double c = 2 * Math.asin(Math.sqrt(a));
			return R * c;
		}
	}

	public static LunchDate findMatch(Request currentRequest,
			Collection<Request> requestPool) {
		double bestScore = 0;
		MatchResult bestMatchResult = null;

		for (Request candidate : requestPool) {
			if (candidate.getUserId().equals(currentRequest.getUserId())) {
				continue;
			}
			MatchResult matchResult = evaluateTheMatch(currentRequest,
					candidate);
			double matchScore = matchResult.score;

			System.out.println(currentRequest.getUserId() + " "
					+ candidate.getUserId() + " " + matchScore);

			if (matchScore > bestScore) {
				bestScore = matchScore;
				bestMatchResult = matchResult;
			}

		}

		if (bestMatchResult == null) {
			return null;
		} else {

			return new LunchDate(currentRequest,
					bestMatchResult.matchedRequest, bestMatchResult.matchedRestaurant,
					bestMatchResult.dateTimeInterval);
		}
	}

	private static Map<String, String> overlapVenues(List<Map<String, String>> venues1, 
			List<Map<String, String>> venues2) {

		for (Map<String, String> venue1 : venues1) {
			for (Map<String, String> venue2 : venues2) {
				// Check if venue 1 is the same as venue 2
				if (venue1.get("id").equals(venue2.get("id"))) {
					return venue1;
				}
			}
		}

		return null;
	}
	
	private static List<Map<String, String>> convertFromString(List<String> restaurants) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (String restaurant : restaurants) {
			Map<String, String> map = new HashMap<String, String>();
			// Convert String to JsonMap
			try {
				JSONObject restaurantJson = new JSONObject(restaurant);
				map.put("id", restaurantJson.get("id").toString());
				map.put("lat", restaurantJson.get("lat").toString());
				map.put("lon", restaurantJson.get("lon").toString());
				map.put("name", restaurantJson.get("name").toString());
				list.add(map);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return list;
	}
	private static MatchResult evaluateTheMatch(Request request1,
			Request request2) {

		TimeInterval interval1 = new TimeInterval(request1.getStartTime(),
				request1.getEndTime());
		TimeInterval interval2 = new TimeInterval(request2.getStartTime(),
				request2.getEndTime());
		TimeInterval intersection = interval1.overlapInterval(interval2);
		TimeInterval dateInterval;

		// Restaurants overlap?
		// TODO: Have to eventually convert
		String overlappedRestaurant = null;
		for (String restaurantMap : request1.getRestaurantPreferences()) {
			if (request2.getRestaurantPreferences().contains(restaurantMap)) {
				overlappedRestaurant = restaurantMap;
			}
		}
		/*
		Map<String, String> overlappedRestaurant = overlapVenues(
				convertFromString(request1.getRestaurantPreferences()), convertFromString(request2.getRestaurantPreferences()));
		*/
		if (overlappedRestaurant != null) {

			if (intersection == null) {
				return new MatchResult(0, 0, 0, null, null, null);

			} else {
				long length = intersection.getLength();
				long optimalLength = ONE_HOUR; // / One hour

				long start = intersection.getStartTime();
				long end = intersection.getEndTime();

				if (length > optimalLength) {
					long newEnd =  start + optimalLength;
					end = newEnd;
					dateInterval = new TimeInterval(start, end);

				} else {
					dateInterval = intersection;
				}

				if (length < optimalLength / 2) {
					return new MatchResult(0, 0, 0, null, null, null);
				} else {
					double intervalScore = intervalScore(length, optimalLength);
					double distanceScore = distanceScore(request1.getLat(),
							request1.getLon(), request2.getLat(), request2.getLon());

					if (distanceScore < 1.0 / 20.0) {
						return new MatchResult(0, 0, 0, null, null, null);
					}

					double score = intervalScore * distanceScore;

					return new MatchResult(score, intervalScore, distanceScore,
							request2, dateInterval, overlappedRestaurant);
				}
			}
		}
		
		return new MatchResult(0, 0, 0, null, null, null);
	}

	private static double intervalScore(long length, long optimalLength) {
		return Math.min(length / optimalLength, 1);
	}

	private static double distanceScore(double lat1, double lon1, double lat2,
			double lon2) {
		double dist = Haversine.haversine(lat1, lon1, lat2, lon2);
		return 1 / (1 + 3 * dist);

	}

	//	public static long hours(long x) {
	//		long temp = x * 60 * 60 * 1000;
	//		return temp;
	//	}

}
