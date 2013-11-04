/**
 * 
 */
package com.zoe.connector.processor;

import org.json.simple.JSONObject;

import com.zoe.connector.common.Constants;
import com.zoe.connector.messages.DriveArcCommand;
import com.zoe.connector.messages.DriveArcResponse;
import com.zoe.connector.messages.Message;
import com.zoe.connector.messages.MessageNames;

/**
 * @author Satish
 * 
 */
public class MessageFactory {

	/**
	 * @param jsonObject
	 * @return
	 */
	public static Message createMessageFromJson(JSONObject jsonObject) {
		String messageName = null;
		String sender = null;
		String timestamp = null;
		JSONObject messageParam = null;
		Message message = null;

		messageName = (String) jsonObject.get(Constants.JSON_MSG_NAME);
		sender = (String) jsonObject.get(Constants.JSON_SENDER);
		timestamp = (String) jsonObject.get(Constants.JSON_TIMESTAMP);
		messageParam = (JSONObject) jsonObject.get(Constants.JSON_MSG_PARAM);

		if (MessageNames.MSG_DRIVE_ARC_COMMAND.equalsIgnoreCase(messageName)) {
			DriveArcCommand command = new DriveArcCommand();
			command.setSender(sender);
			command.setTimestamp(timestamp);
			command.setRadius((Double) messageParam.get(Constants.JSON_RADIUS));
			command.setSpeed((Double) messageParam.get(Constants.JSON_SPEED));
			command.setTime((Double) messageParam.get(Constants.JSON_TIME));
			message = command;
		}

		return message;
	}

	/**
	 * @param messageName
	 * @param message
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String createJsonFromMessage(Message message) {
		String jsonString = null;
		JSONObject jsonObject = new JSONObject();

		if (message instanceof DriveArcResponse) {
			DriveArcResponse response = (DriveArcResponse) message;
			DriveArcCommand command = response.getCommand();
			JSONObject jsonParamObj = new JSONObject();
			JSONObject jsonParamCommand = new JSONObject();
			
			jsonParamCommand.put(Constants.JSON_RADIUS, command.getRadius());
			jsonParamCommand.put(Constants.JSON_SPEED, command.getSpeed());
			jsonParamCommand.put(Constants.JSON_TIME, command.getTime());
			jsonParamCommand.put(Constants.JSON_SENDER, command.getSender());
			jsonParamCommand.put(Constants.JSON_TIMESTAMP, command.getTimestamp());
			
			jsonParamObj.put(Constants.JSON_COMMAND, jsonParamCommand);
			jsonParamObj.put(Constants.JSON_ISVALID, response.getReturnData().getIsValid());
			jsonParamObj.put(Constants.JSON_RETVAL, response.getReturnData().getReturnValue());
			
			jsonObject.put(Constants.JSON_MSG_NAME, MessageNames.MSG_DRIVE_ARC_RESPONSE);
			jsonObject.put(Constants.JSON_MSG_PARAM, jsonParamObj);
			jsonObject.put(Constants.JSON_SENDER, response.getSender());
			jsonObject.put(Constants.JSON_TIMESTAMP, response.getTimestamp());
		}
		
		jsonString = jsonObject.toJSONString();

		return jsonString;
	}

}
