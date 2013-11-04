package com.zoe.connector.handlers;

import ipc.java.IPC;

import com.zoe.connector.messages.DriveArcResponse;
import com.zoe.connector.processor.MessageProcessor;

/**
 * This class is the handler for receiving messages from Zoe Simulator and write
 * them back to the socket.
 * 
 * @author Satish
 * 
 */
public class DriveArcResponseHandler implements IPC.HANDLER_TYPE {
	private MessageProcessor processor;

	public DriveArcResponseHandler(MessageProcessor processor) {
		this.processor = processor;
	}

	public void handle(IPC.MSG_INSTANCE msgRef, Object callData) {
		DriveArcResponse response = (DriveArcResponse) callData;
		System.out.println("DriveArcResponseHandler: Receiving " + IPC.msgInstanceName(msgRef) + "[" + callData + "]");
		processor.postMessageToAndroid(response);
	}
}
