package com.zoe.connector.messages;

/**
 * This class is the model class used for drive arc response message
 * 
 * @author Satish
 * 
 */
public class DriveArcResponse implements Message {

	public DriveArcCommand command;

	public GenericReturn returnData;

	public SenderType sender;

	public TimeType timestamp;

	/**
	 * @return the command
	 */
	public DriveArcCommand getCommand() {
		return command;
	}

	/**
	 * @param command
	 *            the command to set
	 */
	public void setCommand(DriveArcCommand command) {
		this.command = command;
	}

	/**
	 * @return the returnData
	 */
	public GenericReturn getReturnData() {
		return returnData;
	}

	/**
	 * @param returnData
	 *            the returnData to set
	 */
	public void setReturnData(GenericReturn returnData) {
		this.returnData = returnData;
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
		builder.append("DriveArcResponse [command=");
		builder.append(command);
		builder.append(", returnData=");
		builder.append(returnData);
		builder.append(", sender=");
		builder.append(sender);
		builder.append(", timestamp=");
		builder.append(timestamp);
		builder.append("]");
		return builder.toString();
	}

}
