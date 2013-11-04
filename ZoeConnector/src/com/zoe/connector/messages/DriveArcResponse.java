package com.zoe.connector.messages;

/**
 * @author Satish
 *
 */
public class DriveArcResponse extends Message {
	
	private DriveArcCommand command;
	
	private GenericReturn returnData;
	
	private String sender;
	
	private String timestamp;

	/**
	 * @return the command
	 */
	public DriveArcCommand getCommand() {
		return command;
	}

	/**
	 * @param command the command to set
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
	 * @param returnData the returnData to set
	 */
	public void setReturnData(GenericReturn returnData) {
		this.returnData = returnData;
	}

	/**
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * @param sender the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}

	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/* (non-Javadoc)
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
