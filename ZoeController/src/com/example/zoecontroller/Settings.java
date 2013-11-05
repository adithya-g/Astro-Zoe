package com.example.zoecontroller;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Settings extends Activity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		// Find the ListView resource.   
		ListView mainListView = (ListView) findViewById(R.id.listView1);  
		
		List<Item> items = new ArrayList<Item>();
        items.add(new Header("Connect"));
        items.add(new ListItem(Constants.displayServer, ZCPreference.preferences.getString(Constants.serverKey, Constants.defaultServer)));
        items.add(new ListItem(Constants.displayPort, ZCPreference.preferences.getString(Constants.portKey, Constants.defaultport)));
        
        items.add(new Header("Drive"));
        
        String radius = ZCPreference.preferences.getString(Constants.minRadiusKey, Constants.minRange) + " to " + ZCPreference.preferences.getString(Constants.maxRadiusKey, Constants.maximumRange);
        items.add(new ListItem(Constants.displayRadiusRange, radius));
        
        String speed = ZCPreference.preferences.getString(Constants.minSpeedKey, Constants.minRange) + " to " + ZCPreference.preferences.getString(Constants.maxSpeedKey, Constants.maxRange);
        items.add(new ListItem(Constants.displaySpeedRange, speed));
        
        String time = ZCPreference.preferences.getString(Constants.minTimeKey, Constants.minRange) + " to " + ZCPreference.preferences.getString(Constants.maxTimeKey, Constants.maxRange);
        items.add(new ListItem(Constants.displayTimeRange, time));
        
        String rateofcommands = ZCPreference.preferences.getString(Constants.maxCommandRateKey, Constants.minRange) + " to " + ZCPreference.preferences.getString(Constants.minCommandRateKey, Constants.maxRange);
        items.add(new ListItem(Constants.displayRateOfCommand, rateofcommands));
        
        items.add(new Header("Navigation cameras"));
        
        String rateofimage = ZCPreference.preferences.getString(Constants.minImageRateKey, Constants.minRange) + " to " + ZCPreference.preferences.getString(Constants.maxImageRateKey, Constants.maxRange);
        items.add(new ListItem(Constants.displayRateOfImage, rateofimage));

        TwoTextArrayAdapter adapter = new TwoTextArrayAdapter(this, items);
        mainListView.setAdapter(adapter);
        
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parentAdapter, View view, int position,long id) {
        		
        		if (position ==0 || position == 3 || position == 8)
        		{
        			Toast.makeText(getApplicationContext(), "Header", Toast.LENGTH_SHORT).show();
        		}
        		else
        		{
        			Intent intent = new Intent(Settings.this,SettingDetails.class);
        			
        			switch (position) {
					case 1:
						intent.putExtra(Constants.settingTitleKey, Constants.displayServer);
						intent.putExtra(Constants.whatSettingKey, Constants.setting.serverIP);
						break;
					case 2:
						intent.putExtra(Constants.settingTitleKey, Constants.displayPort);
						intent.putExtra(Constants.whatSettingKey, Constants.setting.port);
						break;
					case 4:
						intent.putExtra(Constants.settingTitleKey, Constants.displayRadiusRange);
						intent.putExtra(Constants.whatSettingKey, Constants.setting.radius);
						break;
					case 5:
						intent.putExtra(Constants.settingTitleKey, Constants.displaySpeedRange);
						intent.putExtra(Constants.whatSettingKey, Constants.setting.speed);
						break;
					case 6:
						intent.putExtra(Constants.settingTitleKey, Constants.displayTimeRange);
						intent.putExtra(Constants.whatSettingKey, Constants.setting.time);
						break;
					case 7:
						intent.putExtra(Constants.settingTitleKey, Constants.displayRateOfCommand);
						intent.putExtra(Constants.whatSettingKey, Constants.setting.rateofcommand);
						break;
					case 9:
						intent.putExtra(Constants.settingTitleKey, Constants.displayRateOfImage);
						intent.putExtra(Constants.whatSettingKey, Constants.setting.rateofimage);
						break;
					default:
						break;
					}
        			
        			if (position == 1 || position == 2)
        			{
        				intent.putExtra(ZCPreference.getStringFor(R.string.key_settingtype), ZCPreference.getStringFor(R.string.value_settingsingle));
        			}
        			else
        			{
        				intent.putExtra(ZCPreference.getStringFor(R.string.key_settingtype), ZCPreference.getStringFor(R.string.value_settingrange));
        			}
        			
        			startActivity(intent);
        		}
        		
        		System.out.println("Item selected" + position);
        }
	});
	}
}
