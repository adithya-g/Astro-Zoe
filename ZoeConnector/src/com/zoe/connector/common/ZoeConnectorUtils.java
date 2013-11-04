package com.zoe.connector.common;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

}
