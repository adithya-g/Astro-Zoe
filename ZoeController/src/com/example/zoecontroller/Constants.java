package com.example.zoecontroller;

public class Constants {
	
	public static final String serverKey = "key_server";
	public static final String portKey = "key_port";
	
	public static final String minRadiusKey = "key_minradius";
	public static final String maxRadiusKey = "key_maxradius";
	
	public static final String minSpeedKey = "key_minspeed";
	public static final String maxSpeedKey = "key_maxspeed";
	public static final String speedKey = "key_speed";
	
	public static final String minTimeKey = "key_mintime";
	public static final String maxTimeKey = "key_maxtime";
	public static final String timeKey = "key_time";
	
	public static final String minCommandRateKey = "key_mincommandrate";
	public static final String maxCommandRateKey = "key_maxcommandrate";
	public static final String commandRateKey = "key_commandrate";
	
	public static final String minImageRateKey = "key_minimagerate";
	public static final String maxImageRateKey = "key_maximagerate";
	public static final String imageRateKey = "key_imagerate";
	
	public static final String whatSettingKey = "key_whatsetting";
	public static final String settingTitleKey = "key_settingtitle";
	
	public static final String displayServer = "Server IP";
	public static final String displayPort = "Port";
	public static final String displayRadiusRange = "Radius range";
	public static final String displaySpeedRange = "Speed range";
	public static final String displayTimeRange = "Time range";
	public static final String displayRateOfCommand = "Rate of command";
	public static final String displayRateOfImage = "Rate of image";
	
	public static final String defaultServer ="10.14.2.1";
	public static final String defaultport ="3015";
	public static final String minRange ="0";
	public static final String maxRange ="100";
	
	public static final String baseSpeed = "1";
	public static final String baseTime = "10";
	public static final String basRateOfCommand = "3";
	public static final String baseRateOfImage = "5";
	public static final String maximumRange = "1000";
	
	public enum setting
	{
		serverIP,
		port,
		radius,
		speed,
		time,
		rateofcommand,
		rateofimage
	}
	
	public enum socketConnectionState
	{
		NO_CONNECTION,
		ESTABLISHED,
		CLOSED,
		FAILED
	}
}
