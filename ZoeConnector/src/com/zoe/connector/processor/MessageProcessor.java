package com.zoe.connector.processor;

import java.io.PrintWriter;

import ipc.java.IPC;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.zoe.connector.common.Constants;
import com.zoe.connector.common.ZoeConnectorUtils;
import com.zoe.connector.messages.Message;

/**
 * @author Satish
 *
 */
public class MessageProcessor {
	
	private PrintWriter writer = null;
	
	public MessageProcessor(PrintWriter writer) {
		this.writer = writer;
	}
	
	/**
	 * 
	 * @param msgAsJson
	 */
	public void processAndPostMessage(String msgAsJson) {
		JSONObject jsonObject = null;
		String messageName = null;
		Message message = null;
		try {
			jsonObject = ZoeConnectorUtils.stringToJson(msgAsJson);
			messageName = (String) jsonObject.get(Constants.JSON_MSG_NAME);
			System.out.println("Message name: " + messageName);
			message = MessageFactory.createMessageFromJson(jsonObject);
			System.out.println("Publishing message " + message);
			IPC.publishData(messageName, message);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void postMessageToAndroid(Message message) {
		String jsonString = null;
		
		jsonString = MessageFactory.createJsonFromMessage(message);
		
		writer.write(jsonString);
		writer.flush();
	}
}
