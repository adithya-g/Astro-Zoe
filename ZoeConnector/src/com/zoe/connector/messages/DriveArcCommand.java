package com.zoe.connector.messages;

/**
 * This class is the model class is used for the drive arc command message
 * 
 * @author Satish
 * 
 */
public class DriveArcCommand implements Message {

	public double radius;

	public double speed;

	public double time;

	public SenderType sender;

	public TimeType timestamp;

	/**
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * @param radius
	 *            the radius to set
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}

	/**
	 * @return the speed
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	/**
	 * @return the time
	 */
	public double getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(double time) {
		this.time = time;
	}

	/**
	 * @return the sender
	 */
	public SenderType getSender() {
		return sender;
	}

	/**
	 * @param sender
	 *            the sender to set
	 */
	public void setSender(SenderType sender) {
		this.sender = sender;
	}

	/**
	 * @return the timestamp
	 */
	public TimeType getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp
	 *            the timestamp to set
	 */
	public void setTimestamp(TimeType timestamp) {
		this.timestamp = timestamp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriveArcCommand [radius=");
		builder.append(radius);
		builder.append(", speed=");
		builder.append(speed);
		builder.append(", time=");
		builder.append(time);
		builder.append(", sender=");
		builder.append(sender);
		builder.append(", timestamp=");
		builder.append(timestamp);
		builder.append("]");
		return builder.toString();
	}

}
