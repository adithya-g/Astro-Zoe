package com.zoe.connector.messages;

import com.zoe.connector.common.Constants;

/**
 * This class is the model class for sender type
 * 
 * @author Satish
 * 
 */
public class SenderType {

	public char[] sender = new char[Constants.SENDER_LENGTH];

	public char[] getSender() {
		return sender;
	}

	public String getSenderAsString() {
		return new String(sender);
	}

	public void setSender(char[] sender) {
		this.sender = sender;
	}

	/**
	 * This method converts string to character array of fixed length
	 * 
	 * @param sender
	 */
	public void setSender(String sender) {
		if (sender.length() > Constants.SENDER_LENGTH) {
			sender = sender.substring(0, Constants.SENDER_LENGTH);
		}
		sender += '\0';
		while (sender.length() < Constants.SENDER_LENGTH) {
			sender += ' ';
		}
		this.sender = sender.toCharArray();

	}

	@Override
	public String toString() {
		return "SenderType [sender=" + getSenderAsString() + "]";
	}

}
