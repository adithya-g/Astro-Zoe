package com.zoe.connector;

import ipc.java.IPC;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.ServerSocket;
import java.net.Socket;

import com.zoe.connector.common.Constants;
import com.zoe.connector.handlers.DriveArcResponseHandler;
import com.zoe.connector.handlers.SocketHandler;
import com.zoe.connector.messages.DriveArcResponse;
import com.zoe.connector.messages.MessageNames;
import com.zoe.connector.processor.MessageProcessor;

/**
 * This class is the main class for ZoeConnector which opens a socket and
 * listens for socket connections.
 * 
 * @author Satish
 * 
 */
public class ZoeConnector {

	private static ServerSocket servSocket = null;

	private static Field __fd;
	static {
		try {
			__fd = FileDescriptor.class.getDeclaredField("fd");
			__fd.setAccessible(true);
		} catch (Exception ex) {
			__fd = null;
		}
	}

	/**
	 * This method listens for connections and once connection is established,
	 * it creates handlers for listening to messages from the socket and for
	 * receiving messages from Zoe
	 */
	public static void lookForConnection() {
		PrintWriter writer = null;
		MessageProcessor processor = null;

		System.out.println("Waiting for connection");
		try {
			Socket socket = servSocket.accept();
			System.out.println("Connection accepted");

			writer = new PrintWriter(socket.getOutputStream());
			processor = new MessageProcessor(writer);

			createSocketHandler(socket, processor);
			createMessageHandlers(processor);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method creates handler for listening for messages from the socket.
	 * 
	 * @param socket
	 * @param processor
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	private static void createSocketHandler(Socket socket, MessageProcessor processor) throws IllegalArgumentException, IllegalAccessException, IOException {
		BufferedReader reader;
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		IPC.subscribeFD(__fd.getInt(((FileInputStream) socket.getInputStream()).getFD()), new SocketHandler(reader, processor));
	}

	/**
	 * This message creates handlers for listening for messages from Zoe.
	 * 
	 * @param socket
	 * @throws IOException
	 */
	private static void createMessageHandlers(MessageProcessor processor) throws IOException {
		IPC.subscribeData(MessageNames.MSG_DRIVE_ARC_RESPONSE, new DriveArcResponseHandler(processor), DriveArcResponse.class);
	}

	public static void main(String[] args) {
		IPC.connect(Constants.APP_ID);
		try {
			servSocket = new ServerSocket(Constants.PORT_NO);
			lookForConnection();
			IPC.dispatch();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
