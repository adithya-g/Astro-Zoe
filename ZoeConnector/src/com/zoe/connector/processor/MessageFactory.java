/**
 * 
 */
package com.zoe.connector.processor;

import org.json.simple.JSONObject;

import com.zoe.connector.common.Constants;
import com.zoe.connector.common.ZoeConnectorUtils;
import com.zoe.connector.messages.DriveArcCommand;
import com.zoe.connector.messages.DriveArcResponse;
import com.zoe.connector.messages.Message;
import com.zoe.connector.messages.MessageNames;
import com.zoe.connector.messages.SenderType;
import com.zoe.connector.messages.TimeType;

/**
 * This class contains factory methods to convert different types of Messages to
 * and from JSON
 * 
 * @author Satish
 * 
 */
public class MessageFactory {

	/**
	 * This methods converts the JSON Object into the appropriate message
	 * 
	 * @param jsonObject
	 * @return
	 */
	public static Message createMessageFromJson(JSONObject jsonObject) {
		String messageName = null;
		String sender = null;
		long timestamp = 0;
		JSONObject messageParam = null;
		Message message = null;
		SenderType senderObj = new SenderType();
		TimeType timeObj = new TimeType();

		messageName = (String) jsonObject.get(Constants.JSON_MSG_NAME);
		sender = (String) jsonObject.get(Constants.JSON_SENDER);
		timestamp = Long.parseLong((String) jsonObject.get(Constants.JSON_TIMESTAMP));
		messageParam = (JSONObject) jsonObject.get(Constants.JSON_MSG_PARAM);

		senderObj.setSender(sender);
		timeObj = ZoeConnectorUtils.millisToTimeType(timestamp);

		if (MessageNames.MSG_DRIVE_ARC_COMMAND.equalsIgnoreCase(messageName)) {
			DriveArcCommand command = new DriveArcCommand();
			command.setSender(senderObj);
			command.setTimestamp(timeObj);
			command.setRadius(Double.parseDouble((String) messageParam.get(Constants.JSON_RADIUS)));
			command.setSpeed(Double.parseDouble((String) messageParam.get(Constants.JSON_SPEED)));
			command.setTime(Double.parseDouble((String) messageParam.get(Constants.JSON_TIME)));
			message = command;
		}

		return message;
	}

	/**
	 * This method converts any message to JSON Object
	 * 
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

			jsonParamCommand.put(Constants.JSON_RADIUS, String.valueOf(command.getRadius()));
			jsonParamCommand.put(Constants.JSON_SPEED, String.valueOf(command.getSpeed()));
			jsonParamCommand.put(Constants.JSON_TIME, String.valueOf(command.getTime()));
			jsonParamCommand.put(Constants.JSON_SENDER, command.getSender().getSenderAsString());
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
