package com.example.zoecontroller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SettingDetails extends Activity {
	
	Constants.setting whatSetting;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settingdetail);
		
		Intent myIntent= getIntent(); // gets the previously created intent
		String settingType = myIntent.getStringExtra(ZCPreference.getStringFor(R.string.key_settingtype));
		
		whatSetting =(Constants.setting)myIntent.getSerializableExtra(Constants.whatSettingKey);
		
		if (settingType.equals(ZCPreference.getStringFor(R.string.value_settingsingle)))
		{
			TextView txtView = (TextView) findViewById(R.id.textView2);
			EditText editTxtView = (EditText) findViewById(R.id.editText2);
			
			txtView.setVisibility(View.GONE);
			editTxtView.setVisibility(View.GONE);
			
			String parameterTitle = myIntent.getStringExtra(Constants.settingTitleKey);
			
			TextView paramTxtView = (TextView) findViewById(R.id.textView1);
			paramTxtView.setText(parameterTitle);
		}
		else
		{
			String parameterTitle = myIntent.getStringExtra(Constants.settingTitleKey);
			
			TextView paramTxtViewOne = (TextView) findViewById(R.id.textView1);
			paramTxtViewOne.setText(parameterTitle + " maximum");
			
			TextView paramTxtViewTwo = (TextView) findViewById(R.id.textView2);
			paramTxtViewTwo.setText(parameterTitle + " minimum");
		}
		
		setCurrentValues();
	}
	
	private void setCurrentValues()
	{
		EditText editTxtViewOne = (EditText) findViewById(R.id.editText1);
		EditText editTxtViewTwo = (EditText) findViewById(R.id.editText2);
		
		switch (whatSetting)
		{
			case serverIP:
				editTxtViewOne.setText(ZCPreference.preferences.getString(Constants.serverKey, Constants.defaultServer));
				break;
			case port:
				editTxtViewOne.setText(ZCPreference.preferences.getString(Constants.portKey, Constants.defaultport));
				break;
			case radius:
				editTxtViewOne.setText(ZCPreference.preferences.getString(Constants.maxRadiusKey, Constants.maximumRange));
				editTxtViewTwo.setText(ZCPreference.preferences.getString(Constants.minRadiusKey, Constants.minRange));
				break;
			case speed:
				editTxtViewOne.setText(ZCPreference.preferences.getString(Constants.maxSpeedKey, Constants.maxRange));
				editTxtViewTwo.setText(ZCPreference.preferences.getString(Constants.minSpeedKey, Constants.minRange));
				break;
			case time:
				editTxtViewOne.setText(ZCPreference.preferences.getString(Constants.maxTimeKey, Constants.maxRange));
				editTxtViewTwo.setText(ZCPreference.preferences.getString(Constants.minTimeKey, Constants.minRange));
				break;
			case rateofcommand:
				editTxtViewOne.setText(ZCPreference.preferences.getString(Constants.maxCommandRateKey, Constants.maxRange));
				editTxtViewTwo.setText(ZCPreference.preferences.getString(Constants.minCommandRateKey, Constants.minRange));
				break;
			case rateofimage:
				editTxtViewOne.setText(ZCPreference.preferences.getString(Constants.maxImageRateKey, Constants.maxRange));
				editTxtViewTwo.setText(ZCPreference.preferences.getString(Constants.minImageRateKey, Constants.minRange));
				break;
		}
	}
	
	public void saveBtnPressed(View view)
	{
		EditText paramTxtViewOne = (EditText) findViewById(R.id.editText1);
		EditText paramTxtViewTwo = (EditText) findViewById(R.id.editText2);
		
		switch (whatSetting)
		{
			case serverIP:
				ZCPreference.setValueForString(Constants.serverKey, paramTxtViewOne.getText().toString());
				break;
			case port:
				ZCPreference.setValueForString(Constants.portKey, paramTxtViewOne.getText().toString());
				break;
			case radius:
				ZCPreference.setValueForString(Constants.maxRadiusKey, paramTxtViewOne.getText().toString());
				ZCPreference.setValueForString(Constants.minRadiusKey, paramTxtViewTwo.getText().toString());
				break;
			case speed:
				ZCPreference.setValueForString(Constants.maxSpeedKey, paramTxtViewOne.getText().toString());
				ZCPreference.setValueForString(Constants.minSpeedKey, paramTxtViewTwo.getText().toString());
				break;
			case time:
				ZCPreference.setValueForString(Constants.maxTimeKey, paramTxtViewOne.getText().toString());
				ZCPreference.setValueForString(Constants.minTimeKey, paramTxtViewTwo.getText().toString());
				break;
			case rateofcommand:
				ZCPreference.setValueForString(Constants.maxCommandRateKey, paramTxtViewOne.getText().toString());
				ZCPreference.setValueForString(Constants.minCommandRateKey, paramTxtViewTwo.getText().toString());
				break;
			case rateofimage:
				System.out.println("Rate of image");
				ZCPreference.setValueForString(Constants.maxImageRateKey, paramTxtViewOne.getText().toString());
				ZCPreference.setValueForString(Constants.minImageRateKey, paramTxtViewTwo.getText().toString());
				break;
		}
		
		finish();
	}
}
