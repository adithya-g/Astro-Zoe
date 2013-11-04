package com.zoe.connector.handlers;

import ipc.java.IPC;

import java.io.BufferedReader;
import java.io.IOException;

import com.zoe.connector.ZoeConnector;
import com.zoe.connector.processor.MessageProcessor;

/**
 * This class is the handler for listening to messages from the socket The
 * handle method is invoked whenever there is a change in the file descriptor of
 * the socket input stream
 * 
 * @author Satish
 * 
 */
public class SocketHandler implements IPC.FD_HANDLER_TYPE {
	
	private BufferedReader reader;

	private MessageProcessor processor;

	public SocketHandler(BufferedReader reader, MessageProcessor processor) {
		this.reader = reader;
		this.processor = processor;
	}

	public void handle(int fd) {
		try {
			// Reads message from socket reader
			String message = reader.readLine();
			if (message == null) {
				IPC.unsubscribeFD(fd);
				ZoeConnector.lookForConnection();
			} else {
				System.out.println("Received string " + message);
				processor.processAndPostMessage(message);
			}
		} catch (IOException e) {
			IPC.unsubscribeFD(fd);
			ZoeConnector.lookForConnection();
		}

	}

}
