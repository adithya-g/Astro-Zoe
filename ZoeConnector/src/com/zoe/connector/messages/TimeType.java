package com.zoe.connector.messages;

/**
 * This class is the model class to represent the C structure of timeval
 * 
 * @author Satish
 * 
 */
public class TimeType {

	public long seconds;

	public long microSeconds;

	public long getSeconds() {
		return seconds;
	}

	public void setSeconds(long sec) {
		this.seconds = sec;
	}

	public long getMicroSeconds() {
		return microSeconds;
	}

	public void setMicroSeconds(long msec) {
		this.microSeconds = msec;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TimeType [seconds=");
		builder.append(seconds);
		builder.append(", microSeconds=");
		builder.append(microSeconds);
		builder.append("]");
		return builder.toString();
	}

}
