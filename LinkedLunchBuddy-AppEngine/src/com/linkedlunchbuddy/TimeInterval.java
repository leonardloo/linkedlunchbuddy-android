package com.linkedlunchbuddy;

import java.util.Calendar;

import javax.persistence.Embeddable;

@Embeddable
public class TimeInterval {

	private long startTime;
	private long endTime;

	public TimeInterval(long start, long end) {
		if (start >= end) {
			throw new IllegalArgumentException();
		}
		this.startTime = start;
		this.endTime = end;
	}

	// Getters & Setters
	

	public long getLength() {
		return (this.endTime - this.startTime);
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	// helper methods
	public boolean hasExpired() {
		if (this.endTime < Calendar.getInstance().getTime().getTime()) {
			return true;
		} else {
			return false;
		}
	}

	// overlap between 2 intervals in ms, returns -1 if no overlap
	public long msOverlap(TimeInterval otherInterval) {

		// this interval completely before the otherInterval or the
		// otherInterval completely before the this interval
		if ((this.endTime <= otherInterval.getStartTime())
				|| (otherInterval.getEndTime() <= this.startTime)) {
			return -1;
		}
		// this.endTime is after otherInterval.startTime
		if (this.endTime > otherInterval.getStartTime()) {
			long overlap = Math.min(this.endTime
					- otherInterval.getStartTime(),
					otherInterval.getLength());
			return overlap;
		}
		// otherInterval.endTime is after this.startTime
		if (otherInterval.getEndTime() > (this.endTime) ) {
			long overlap = Math.min(otherInterval.getEndTime()
					- this.startTime, this.getLength());
			return overlap;
		}
		return 0;
	}

	// overlap method returning a TimeInterval object
	public TimeInterval overlapInterval(TimeInterval otherInterval) {

		long currentStart, currentEnd;

		if (this.startTime >=(otherInterval.startTime) ) {
			currentStart = this.startTime;
		} else {
			currentStart = otherInterval.startTime;
		}

		if (this.endTime <=(otherInterval.endTime) ) {
			currentEnd = this.endTime;
		} else {
			currentEnd = otherInterval.endTime;
		}

		if (currentStart < (currentEnd) ) {
			return new TimeInterval(currentStart, currentEnd);
		} else {
			return null;
		}
	}

}
