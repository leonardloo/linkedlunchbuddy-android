package com.linkedlunchbuddy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TimeIntervalTest {

	private TimeInterval t1;
	private TimeInterval t2;
	private TimeInterval t3;
	private TimeInterval t4;

	@Before
	public void setUp() throws Exception {
		t1 = new TimeInterval(1L, 10L);
		t2 = new TimeInterval(5L, 15L);
		t3 = new TimeInterval(16L, 20L);
		t4 = new TimeInterval(17L, 19L);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHasExpired() {
		assertTrue(t1.hasExpired());
	}

	@Test
	public void testMsOverlap() {
		assertEquals(5, t1.msOverlap(t2));
		assertEquals(-1, t2.msOverlap(t3));
		assertEquals(2, t3.msOverlap(t4));
	}

	@Test
	public void testOverlapIntervalPartial() {
		TimeInterval t1t2 = t1.overlapInterval(t2);
		TimeInterval t2t1 = t2.overlapInterval(t1);

		TimeInterval ti = new TimeInterval(5L, 10L);
		assertEquals(t1t2.getStartTime(), ti.getStartTime());
		assertEquals(t1t2.getEndTime(), ti.getEndTime());

		assertEquals(t2t1.getStartTime(), ti.getStartTime());
		assertEquals(t2t1.getEndTime(), ti.getEndTime());
	}

	@Test
	public void testOverlapIntervalTotal() {
		TimeInterval t3t4 = t3.overlapInterval(t4);
		TimeInterval t4t3 = t4.overlapInterval(t3);

		TimeInterval ti = new TimeInterval(17L, 19L);

		assertEquals(t3t4.getStartTime(), ti.getStartTime());
		assertEquals(t4t3.getEndTime(), ti.getEndTime());
	}

	@Test
	public void testOverlapIntervalNull() {

		TimeInterval t1t3 = t1.overlapInterval(t3);
		TimeInterval t3t1 = t3.overlapInterval(t1);

		assertEquals(t1t3, null);
		assertEquals(t3t1, null);
	}
}
