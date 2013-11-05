package com.example.zoecontroller;

import android.content.SharedPreferences;
import android.content.res.Resources;

public class ZCPreference 
{	
	public static SharedPreferences preferences;
	public static Resources resource;
	
	public static String getStringFor(int id)
	{
		return resource.getString(id);
	}
	
	public static void setValueForString(String key, String value)
	{
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(key, value); 
		editor.commit();
	}
}
