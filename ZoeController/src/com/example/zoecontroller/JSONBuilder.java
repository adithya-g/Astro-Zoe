package com.example.zoecontroller;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONBuilder 
{
	public static JSONObject messageForDrive(double speed, double radius)
	{
		String time = ZCPreference.preferences.getString(Constants.timeKey, "100");
		
		String appName = ZCPreference.preferences.getString(ZCPreference.getStringFor(R.string.app_name), "ZC");

		long timestamp = System.currentTimeMillis();
		
		JSONObject msg = null;

		try
		{
			JSONObject msgParamObj = new JSONObject();
			msgParamObj.put("radius", Double.toString(radius));
			msgParamObj.put("speed", Double.toString(speed));
			msgParamObj.put("time",time);

			msg = new JSONObject();
			msg.put("messageParam",msgParamObj);		
			msg.put("messageName", "driveArcCommand");
			msg.put("sender", appName);
			msg.put("timestamp",Long.toString(timestamp));
		}
		catch(JSONException e) 
		{
			e.printStackTrace();
		}

		return msg;
	}
}
