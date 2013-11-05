package com.zoe.connector.common;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.zoe.connector.messages.TimeType;

/**
 * This class contains utility methods.
 * 
 * @author Satish
 * 
 */
public class ZoeConnectorUtils {

	/**
	 * This method converts a json string into a json object
	 * 
	 * @param jsonString
	 * @return
	 * @throws ParseException
	 */
	public static JSONObject stringToJson(String jsonString) throws ParseException {
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = null;

		jsonObject = (JSONObject) jsonParser.parse(jsonString);

		return jsonObject;
	}

	/**
	 * This method converts time in milliseconds to time type
	 * 
	 * @param millis
	 * @return
	 */
	public static TimeType millisToTimeType(long millis) {
		long sec = 0;
		long microSec = 0;
		TimeType timeType = new TimeType();

		microSec = (millis % 1000) * 1000;
		sec = (millis / 1000);
		timeType.setSeconds(sec);
		timeType.setMicroSeconds(microSec);

		return timeType;

	}

	/**
	 * This method converts time type to time milliseconds
	 * 
	 * @param timeType
	 * @return
	 */
	public static long timeTypeToMillis(TimeType timeType) {
		long millis = 0;
		millis = (timeType.getSeconds() * 1000) + (timeType.getMicroSeconds() / 1000);
		return millis;
	}

}
