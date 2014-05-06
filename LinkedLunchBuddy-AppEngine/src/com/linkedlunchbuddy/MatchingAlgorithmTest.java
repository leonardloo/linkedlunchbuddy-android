package com.linkedlunchbuddy;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MatchingAlgorithmTest {

	User armen;
	User benedikt;
	User rachel;
	User leonard;
	User murphy;

	Request vanPeltArmen = new Request(0, 3600000, "vaarmen@sas.upenn.edu", "armen", 39.952859, -75.193565);
	Request drlBenedikt = new Request(hours(10), hours(20), "2", "armen", 39.952863, -75.189901);
	Request huntsmanRachel = new Request(0, 7200000, "rmiao@sas.upenn.edu", "armen", 39.952472, -75.196679);
	Request harrisonLeonard = new Request(hours(3), hours(4.5), "4", "armen", 39.952232, -75.201053);
	Request yerevanMurphy = new Request(hours(0), hours(100), "5", "armen", 40.224549, 44.512024);

	Collection<Request> pool;

	@Before
	public void setUp() throws Exception {

		pool = new HashSet<Request>();

		pool.add(vanPeltArmen);
		pool.add(huntsmanRachel);
		pool.add(harrisonLeonard);
		pool.add(drlBenedikt);
		pool.add(yerevanMurphy);
	}

	@After
	public void tearDown() throws Exception {}

	@Test
	public void test1() {
		LunchDate date = MatchingAlgorithm.findMatch(vanPeltArmen, pool);

		System.out.println("User ID of match: " + date.getRequestB().getUserId());
		assertEquals(date.getRequestB(), huntsmanRachel);
	}

	@Test
	public void test2() {
		System.out.println("Size of pool: " + pool.size());

		LunchDate date = MatchingAlgorithm.findMatch(drlBenedikt, pool);

		System.out.println("User ID of match: " + date.getRequestB().getUserId());
		assertEquals(date.getRequestB(), yerevanMurphy);
	}

	@Test
	public void test3() {
		System.out.println("Size of pool: " + pool.size());

		LunchDate date = MatchingAlgorithm.findMatch(huntsmanRachel, pool);

		System.out.println("User ID of match: " + date.getRequestB().getUserId());
		assertEquals(date.getRequestB(), vanPeltArmen);
	}

	@Test
	public void test4() {
		System.out.println("Size of pool: " + pool.size());

		LunchDate date = MatchingAlgorithm.findMatch(harrisonLeonard, pool);

		System.out.println("User ID of match: " + date.getRequestB().getUserId());
		assertEquals(date.getRequestB(), huntsmanRachel);
	}

	@Test
	public void test5() {
		System.out.println("Size of pool: " + pool.size());

		LunchDate date = MatchingAlgorithm.findMatch(yerevanMurphy, pool);

		System.out.println("User ID of match: " + date.getRequestB().getUserId());
		assertEquals(date.getRequestB(), drlBenedikt);
	}
	public Long hours(double x) {
		return (long) x * 60 * 60 * 1000;
	}

}